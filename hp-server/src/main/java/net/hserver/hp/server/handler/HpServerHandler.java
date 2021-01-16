package net.hserver.hp.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.hserver.hp.common.exception.HpException;
import net.hserver.hp.common.handler.HpCommonHandler;
import net.hserver.hp.common.protocol.HpMessage;
import net.hserver.hp.common.protocol.HpMessageType;
import net.hserver.hp.server.domian.vo.UserVo;
import net.hserver.hp.server.init.TcpServer;
import net.hserver.hp.server.service.UserService;
import net.hserver.hp.server.service.impl.UserServiceImpl;
import top.hserver.core.ioc.IocUtil;

import java.util.HashMap;


/**
 * @author hxm
 */
public class HpServerHandler extends HpCommonHandler {

    private TcpServer remoteConnectionServer = new TcpServer();

    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private int port;

    private boolean register = false;

    public HpServerHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        HpMessage hpMessage = (HpMessage) msg;
        if (hpMessage.getType() == HpMessageType.REGISTER) {
            processRegister(hpMessage);
        } else if (register) {
            if (hpMessage.getType() == HpMessageType.DISCONNECTED) {
                processDisconnected(hpMessage);
            } else if (hpMessage.getType() == HpMessageType.DATA) {
                processData(hpMessage);
            } else if (hpMessage.getType() == HpMessageType.KEEPALIVE) {
                // 心跳包, 不处理
            } else {
                throw new HpException("未知类型: " + hpMessage.getType());
            }
        } else {
            ctx.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        remoteConnectionServer.close();
        if (register) {
            System.out.println("停止服务器的端口: " + port);
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.REGISTER
     */
    private void processRegister(HpMessage hpMessage) {
        HashMap<String, Object> metaData = new HashMap<>();
        String password = hpMessage.getMetaData().get("password").toString();
        String username = hpMessage.getMetaData().get("username").toString();
        int port = (int) hpMessage.getMetaData().get("port");
        UserService userService = IocUtil.getBean(UserServiceImpl.class);
        UserVo login = userService.login(username, password);
        /**
         * 查询这个用户是否是合法的，不是合法的直接干掉
         */
        if (login == null || !login.getPorts().contains(port)) {
            metaData.put("success", false);
            metaData.put("reason", "用户非法，有疑问请联系管理员");
        } else {
            try {
                HpServerHandler thisHandler = this;
                remoteConnectionServer.bind(port, new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ByteArrayDecoder(), new ByteArrayEncoder(), new RemoteProxyHandler(thisHandler));
                        channels.add(ch);
                    }
                });
                metaData.put("success", true);
                this.port = port;
                register = true;
                System.out.println("注册成功，在端口上启动服务器: " + port);
            } catch (Exception e) {
                metaData.put("success", false);
                metaData.put("reason", e.getMessage());
                e.printStackTrace();
            }
        }
        HpMessage sendBackMessage = new HpMessage();
        sendBackMessage.setType(HpMessageType.REGISTER_RESULT);
        sendBackMessage.setMetaData(metaData);
        ctx.writeAndFlush(sendBackMessage);
        if (!register) {
            System.out.println("客户注册错误: " + metaData.get("reason"));
            ctx.close();
        }
    }

    /**
     * if HpMessage.getType() == HpMessageType.DATA
     */
    private void processData(HpMessage hpMessage) {
        channels.writeAndFlush(hpMessage.getData(), channel -> channel.id().asLongText().equals(hpMessage.getMetaData().get("channelId")));
    }

    /**
     * if HpMessage.getType() == HpMessageType.DISCONNECTED
     *
     * @param hpMessage
     */
    private void processDisconnected(HpMessage hpMessage) {
        channels.close(channel -> channel.id().asLongText().equals(hpMessage.getMetaData().get("channelId")));
    }
}