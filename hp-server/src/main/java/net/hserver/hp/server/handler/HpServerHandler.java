package net.hserver.hp.server.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.hserver.hp.common.exception.HpException;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessageData;
import net.hserver.hp.server.codec.HpByteArrayDecoder;
import net.hserver.hp.server.codec.HpByteArrayEncoder;
import net.hserver.hp.server.config.WebConfig;
import net.hserver.hp.server.domian.bean.ConnectInfo;
import net.hserver.hp.server.domian.bean.Statistics;
import net.hserver.hp.server.domian.vo.UserVo;
import net.hserver.hp.server.service.UserService;
import net.hserver.hp.server.service.impl.StatisticsServiceImpl;
import net.hserver.hp.server.service.impl.UserServiceImpl;
import net.hserver.hp.server.utils.NetUtil;
import cn.hserver.core.ioc.IocUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author hxm
 */
@ChannelHandler.Sharable
public class HpServerHandler extends HpCommonHandler {


    public static String tips = "禁止穿透违法程序，免费不易 请大家谅解";

    private final TcpServer remoteConnectionServer = new TcpServer();

    public static final Map<String, ConnectInfo> CURRENT_STATUS = new ConcurrentHashMap<>();

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private int port;

    private boolean register = false;

    public HpServerHandler() {

    }

    public static void offline(String username) {
        CURRENT_STATUS.forEach((k, v) -> {
            if (v.getUsername().equals(username)) {
                v.getChannel().close();
            }
        });
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HpMessageData.HpMessage hpMessage) throws Exception {
        if (hpMessage.getType() == HpMessageData.HpMessage.HpMessageType.REGISTER) {
            processRegister(hpMessage);
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
        try {
            CURRENT_STATUS.remove(String.valueOf(port));
            Statistics statistics = remoteConnectionServer.getStatistics();
            if (statistics != null) {
                IocUtil.getBean(StatisticsServiceImpl.class).add(statistics);
            }
        } catch (Throwable ignored) {
        }
        remoteConnectionServer.close();
        if (register) {
            System.out.println("停止服务器的端口: " + port);
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.REGISTER
     */
    private void processRegister(HpMessageData.HpMessage hpMessage) {
        String password = hpMessage.getMetaData().getPassword();
        String username = hpMessage.getMetaData().getUsername();
        int tempPort = 0;
        Object port = hpMessage.getMetaData().getPort();
        if (port != null) {
            tempPort = (int) port;
        }
        UserService userService = IocUtil.getBean(UserServiceImpl.class);
        UserVo login = userService.login(username, password);
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
        } else {
            try {
                if (!login.getPorts().contains(tempPort) || tempPort < 0) {
                    tempPort = NetUtil.getAvailablePort();
                }
                HpServerHandler thisHandler = this;
                remoteConnectionServer.bind(tempPort, new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                                //添加编码器作用是进行统计，包数据
                                new HpByteArrayDecoder(remoteConnectionServer),
                                new HpByteArrayEncoder(remoteConnectionServer),
                                new RemoteProxyHandler(thisHandler, remoteConnectionServer)
                        );
                        channels.add(ch);
                    }
                }, login.getUsername());
                metaDataBuild.setSuccess(true);
                this.port = tempPort;
                register = true;
                CURRENT_STATUS.put(String.valueOf(tempPort), new ConnectInfo(login.getUsername(), ctx.channel()));
                String host = IocUtil.getBean(WebConfig.class).getHost();
                metaDataBuild.setReason( "连接成功，外网TCP地址是:" + host + ":" + tempPort + ",外网HTTP地址是：http://" + login.getUsername() + "." + host + " " + (tips.trim().length() > 0 ? "公告提示："+tips : ""));
                System.out.println("注册成功，外网地址是:  " + host + ":" + tempPort);
                System.out.println("用户名：" + username + " 来源IP：" + ctx.channel().remoteAddress());
                userService.updateLogin(username, ctx.channel().remoteAddress().toString());
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
     * if HpMessage.getType() == HpMessageType.DATA
     */
    private void processData(HpMessageData.HpMessage hpMessage) {
        channels.writeAndFlush(hpMessage.getData().toByteArray(), channel -> channel.id().asLongText().equals(hpMessage.getMetaData().getChannelId()));
    }

    /**
     * if HpMessage.getType() == HpMessageType.DISCONNECTED
     *
     * @param hpMessage
     */
    private void processDisconnected(HpMessageData.HpMessage hpMessage) {
        channels.close(channel -> channel.id().asLongText().equals(hpMessage.getMetaData().getChannelId()));
    }
}
