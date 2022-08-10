package net.hserver.hp.server.handler.proxy;

import cn.hserver.plugin.web.handlers.BuildResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import net.hserver.hp.server.utils.FileUtil;

import java.util.Objects;

import static net.hserver.hp.server.utils.FileUtil.readFile;


public class RouterHandler extends SimpleChannelInboundHandler<HttpRequest> {

    private final ERROR error;

    public RouterHandler(ERROR error) {
        this.error = error;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception {
        if (error == ERROR.ERROR) {
            ctx.writeAndFlush(BuildResponse.buildString(Objects.requireNonNull(readFile(FileUtil.class.getResourceAsStream("/static/tmp.html")))));
        } else {
            ctx.writeAndFlush(BuildResponse.buildString(Objects.requireNonNull(readFile(FileUtil.class.getResourceAsStream("/static/tmp.html")))));
        }
    }


    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        BuildResponse.writeException(ctx, cause);
    }

    public enum ERROR {
        OFF_LINE,
        ERROR
    }

}
