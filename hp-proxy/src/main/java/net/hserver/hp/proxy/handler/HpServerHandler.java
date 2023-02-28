package net.hserver.hp.proxy.handler;

import cn.hserver.core.ioc.IocUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.Attribute;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.hserver.hp.common.exception.HpException;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessageData;
import net.hserver.hp.proxy.config.WebConfig;
import net.hserver.hp.proxy.domian.bean.ConnectInfo;
import net.hserver.hp.proxy.domian.bean.Statistics;
import net.hserver.hp.proxy.domian.vo.UserVo;
import net.hserver.hp.proxy.handler.proxy.RemoteUdpServerHandler;
import net.hserver.hp.proxy.service.HttpService;
import net.hserver.hp.proxy.utils.NetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hxm
 */
@ChannelHandler.Sharable
public class HpServerHandler extends HpCommonHandler {
    private static final Logger log = LoggerFactory.getLogger(HpServerHandler.class);

    private final TcpServer remoteConnectionServer = new TcpServer();

    public static final List<ConnectInfo> CURRENT_STATUS = new ArrayList<>();

    public static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup udp_channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private boolean register = false;

    public HpServerHandler() {
    }

    public static void offline(String domain) {
        List<ConnectInfo> collect = CURRENT_STATUS.stream().filter(v -> domain.equals(v.getDomain())).collect(Collectors.toList());
        for (ConnectInfo connectInfo : collect) {
            connectInfo.getChannel().close();
        }
        CURRENT_STATUS.removeAll(collect);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HpMessageData.HpMessage hpMessage) throws Exception {
        if (hpMessage.getType() == HpMessageData.HpMessage.HpMessageType.REGISTER) {
            if (hpMessage.getMetaData().getType() == HpMessageData.HpMessage.MessageType.TCP) {
                processRegisterTcp(hpMessage);
            } else if (hpMessage.getMetaData().getType() == HpMessageData.HpMessage.MessageType.UDP) {
                processRegisterUdp(hpMessage);
            } else if (hpMessage.getMetaData().getType() == HpMessageData.HpMessage.MessageType.TCP_UDP) {
                processRegisterUdp(hpMessage);
                processRegisterTcp(hpMessage);
            } else {
                ctx.close();
            }
        } else if (register) {
            if (hpMessage.getType() == HpMessageData.HpMessage.HpMessageType.DISCONNECTED) {
                processDisconnected(hpMessage);
            } else if (hpMessage.getType() == HpMessageData.HpMessage.HpMessageType.DATA) {
                processData(hpMessage);
            } else if (hpMessage.getType() == HpMessageData.HpMessage.HpMessageType.KEEPALIVE) {
                // 心跳包
            } else {
                throw new HpException("未知类型: " + hpMessage.getType());
            }
        } else {
            ctx.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        List<ConnectInfo> collect = CURRENT_STATUS.stream().filter(v -> !v.getChannel().isActive()||v.getChannel().id().asLongText().equals(ctx.channel().id().asLongText())).collect(Collectors.toList());
        try {
            CURRENT_STATUS.removeAll(collect);
            Statistics statistics = remoteConnectionServer.getStatistics();
            HttpService.updateStatistics(statistics);
        } catch (Throwable ignored) {
        }
        remoteConnectionServer.close();
        if (register) {
            System.out.println("停止服务器的端口: " + collect);
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.REGISTER
     */
    private void processRegisterTcp(HpMessageData.HpMessage hpMessage) {
        String password = hpMessage.getMetaData().getPassword();
        String username = hpMessage.getMetaData().getUsername();
        String domain = hpMessage.getMetaData().getDomain();
        int tempPort = hpMessage.getMetaData().getPort();
        WebConfig webConfig = IocUtil.getBean(WebConfig.class);
        UserVo login = HttpService.login(username, password, domain, ctx.channel().remoteAddress().toString());
        /**
         * 查询这个用户是否是合法的，不是合法的直接干掉
         */
        HpMessageData.HpMessage.MetaData.Builder metaDataBuild = HpMessageData.HpMessage.MetaData.newBuilder();
        if (login == null) {
            metaDataBuild.setSuccess(false);
            metaDataBuild.setReason("非法用户，登录失败，有疑问请联系管理员");
        } else if (login.getType() != null && login.getType() == -1) {
            metaDataBuild.setSuccess(false);
            metaDataBuild.setReason("账号被封，请穿透正能量，有意义的程序哦。用户名：" + username + " 域名：" + domain + " 来源IP：" + ctx.channel().remoteAddress());
        } else if (webConfig.getLevel() != null && webConfig.getLevel() != 0 && login.getLevel() < webConfig.getLevel()) {
            metaDataBuild.setSuccess(false);
            metaDataBuild.setReason("当前穿透服务限定用户级别： " + webConfig.getLevel() + "、请升级后享受该服务。用户名：" + username + " 域名：" + domain + " 来源IP：" + ctx.channel().remoteAddress());
        } else {
            try {
                if (!login.getPorts().contains(tempPort) || tempPort <= 0) {
                    tempPort = NetUtil.getAvailablePort();
                }
                HpServerHandler thisHandler = this;
                remoteConnectionServer.bindTcp(tempPort, new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                //添加编码器作用是进行统计，包数据
//                                new FlowGlobalTrafficShapingHandler(ctx.executor()),
                                new ByteArrayDecoder(),
                                new ByteArrayEncoder(),
                                new RemoteProxyHandler(thisHandler, remoteConnectionServer)
                        );
                        channels.add(ch);
                    }
                }, login.getUsername());
                metaDataBuild.setSuccess(true);
                register = true;
                CURRENT_STATUS.add(new ConnectInfo(tempPort,username, domain,login.getDomains().get(domain), ctx.channel()));
                String host = IocUtil.getBean(WebConfig.class).getUserHost();
                metaDataBuild.setReason("连接成功，外网TCP地址是:" + IocUtil.getBean(WebConfig.class).getHost() + ":" + tempPort + ",外网HTTP地址是：http://" + domain + "." + host + " " + (login.getTips().trim().length() > 0 ? "公告提示：" + login.getTips() : ""));
                System.out.println("注册成功，外网地址是:  " + host + ":" + tempPort);
                System.out.println("用户名：" + username + " 域名：" + domain + " 来源IP：" + ctx.channel().remoteAddress());
            } catch (Exception e) {
                metaDataBuild.setSuccess(false);
                metaDataBuild.setReason(e.getMessage());
                e.printStackTrace();
            }
        }
        HpMessageData.HpMessage.Builder sendBackMessageBuilder = HpMessageData.HpMessage.newBuilder();
        sendBackMessageBuilder.setType(HpMessageData.HpMessage.HpMessageType.REGISTER_RESULT);
        HpMessageData.HpMessage.MetaData metaData = metaDataBuild.build();
        sendBackMessageBuilder.setMetaData(metaData);
        ctx.writeAndFlush(sendBackMessageBuilder.build());
        if (!register) {
            System.out.println("客户注册错误: " + metaData.getReason());
            ctx.close();
        }
    }


    /**
     * if HpMessage.getType() == HpMessageType.REGISTER
     */
    private void processRegisterUdp(HpMessageData.HpMessage hpMessage) {
        String password = hpMessage.getMetaData().getPassword();
        String username = hpMessage.getMetaData().getUsername();
        int tempPort = hpMessage.getMetaData().getPort();
        WebConfig webConfig = IocUtil.getBean(WebConfig.class);
        UserVo login = HttpService.login(username, password, "udp", ctx.channel().remoteAddress().toString());
        /**
         * 查询这个用户是否是合法的，不是合法的直接干掉
         */
        HpMessageData.HpMessage.MetaData.Builder metaDataBuild = HpMessageData.HpMessage.MetaData.newBuilder();
        if (login == null) {
            metaDataBuild.setSuccess(false);
            metaDataBuild.setReason("非法用户，登录失败，有疑问请联系管理员");
        } else if (login.getType() != null && login.getType() == -1) {
            metaDataBuild.setSuccess(false);
            metaDataBuild.setReason("账号被封，请穿透正能量，有意义的程序哦。用户名：" + username + " 来源IP：" + ctx.channel().remoteAddress());
        } else if (webConfig.getLevel() != null && webConfig.getLevel() != 0 && login.getLevel() < webConfig.getLevel()) {
            metaDataBuild.setSuccess(false);
            metaDataBuild.setReason("当前穿透服务限定用户级别： " + webConfig.getLevel() + "、请升级后享受该服务。用户名：" + username + " 来源IP：" + ctx.channel().remoteAddress());
        } else {
            try {
                if (!login.getPorts().contains(tempPort) || tempPort <= 0) {
                    tempPort = NetUtil.getAvailablePort();
                }
                HpServerHandler thisHandler = this;
                remoteConnectionServer.bindUdp(tempPort, new ChannelInitializer<Channel>() {
                    @Override
                    public void initChannel(Channel ch) throws Exception {
                        RemoteUdpServerHandler remoteUdpServerHandler = new RemoteUdpServerHandler(thisHandler, remoteConnectionServer);
                        ch.pipeline().addLast(
                                //添加编码器作用是进行统计，包数据
                                remoteUdpServerHandler
                        );
                        udp_channels.add(ch);
                    }
                }, login.getUsername());
                metaDataBuild.setSuccess(true);
                register = true;
                CURRENT_STATUS.add(new ConnectInfo(tempPort,username, "(udp)", ctx.channel()));
                metaDataBuild.setReason("连接成功，外网UDP地址是:" + IocUtil.getBean(WebConfig.class).getHost() + ":" + tempPort + (login.getTips().trim().length() > 0 ? " 公告提示：" + login.getTips() : ""));
            } catch (Exception e) {
                metaDataBuild.setSuccess(false);
                metaDataBuild.setReason(e.getMessage());
                e.printStackTrace();
            }
        }
        HpMessageData.HpMessage.Builder sendBackMessageBuilder = HpMessageData.HpMessage.newBuilder();
        sendBackMessageBuilder.setType(HpMessageData.HpMessage.HpMessageType.REGISTER_RESULT);
        HpMessageData.HpMessage.MetaData metaData = metaDataBuild.build();
        sendBackMessageBuilder.setMetaData(metaData);
        ctx.writeAndFlush(sendBackMessageBuilder.build());
        if (!register) {
            System.out.println("客户注册错误: " + metaData.getReason());
            ctx.close();
        }
    }

    /**
     * 内网数据返回到公网，这里做数据交换，返回给公网用户
     * if HpMessage.getType() == HpMessageType.DATA
     */
    private  void processData(HpMessageData.HpMessage hpMessage) {
        byte[] bytes = hpMessage.getData().toByteArray();
        remoteConnectionServer.addReceive((long) bytes.length);
        if (hpMessage.getMetaData().getType() == HpMessageData.HpMessage.MessageType.TCP) {
            channels.stream().filter(channel ->
                    channel.id().asLongText().equals(hpMessage.getMetaData().getChannelId())
            ).findFirst().ifPresent(targetChannel ->{
                targetChannel.writeAndFlush(bytes).addListener(future -> {
                    getCtx().channel().config().setAutoRead(targetChannel.isWritable());
                });
            });
        }

        if (hpMessage.getMetaData().getType() == HpMessageData.HpMessage.MessageType.UDP) {
            udp_channels.stream().filter(channel ->
                    channel.id().asLongText().equals(hpMessage.getMetaData().getChannelId())
            ).findFirst().ifPresent(targetChannel ->{
                final Attribute<InetSocketAddress> attr = targetChannel.attr(RemoteUdpServerHandler.SENDER);
                final InetSocketAddress inetSocketAddress = attr.get();
                if (inetSocketAddress != null) {
                    targetChannel.writeAndFlush(new DatagramPacket(Unpooled.wrappedBuffer(bytes), inetSocketAddress)).addListener(future -> {
                        getCtx().channel().config().setAutoRead(targetChannel.isWritable());
                    });
                }
            });
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.DISCONNECTED
     *
     * @param hpMessage
     */
    private void processDisconnected(HpMessageData.HpMessage hpMessage) {
        channels.close(channel -> channel.id().asLongText().equals(hpMessage.getMetaData().getChannelId()));
        udp_channels.close(channel -> channel.id().asLongText().equals(hpMessage.getMetaData().getChannelId()));
    }
}
