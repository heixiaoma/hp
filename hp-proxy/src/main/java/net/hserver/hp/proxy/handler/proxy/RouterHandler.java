package net.hserver.hp.proxy.handler.proxy;

import cn.hserver.core.ioc.IocUtil;
import cn.hserver.plugin.web.handlers.BuildResponse;
import cn.hserver.plugin.web.util.FreemarkerUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpRequest;
import net.hserver.hp.proxy.config.WebConfig;
import net.hserver.hp.proxy.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static net.hserver.hp.proxy.utils.FileUtil.readFile;


public class RouterHandler extends SimpleChannelInboundHandler<HttpRequest> {
    public static String dataHtml = "与服务器断开了连接";

    static {
        Map<String, Object> data = new HashMap<>();
        try {
            data.put("adminAddress", IocUtil.getBean(WebConfig.class).getAdminAddress());
            dataHtml = FreemarkerUtil.getTemplate("/tmp.ftl", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final ERROR error;

    public RouterHandler(ERROR error) {
        this.error = error;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest request) throws Exception {
        if (error == ERROR.ERROR) {
            ctx.writeAndFlush(BuildResponse.buildString(dataHtml));
        } else {
            ctx.writeAndFlush(BuildResponse.buildString(dataHtml));
        }
        ctx.close();
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        BuildResponse.writeException(ctx, cause);
    }

    public enum ERROR {
        OFF_LINE,
        ERROR
    }

}
