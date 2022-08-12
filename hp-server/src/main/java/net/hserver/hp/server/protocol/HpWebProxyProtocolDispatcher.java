package net.hserver.hp.server.protocol;

import cn.hserver.HServerApplication;
import cn.hserver.core.server.util.ExceptionUtil;
import cn.hserver.core.server.util.protocol.HostUtil;
import cn.hserver.core.server.util.protocol.ProtocolUtil;
import cn.hserver.core.server.util.protocol.SSLUtils;
import cn.hserver.plugin.web.annotation.POST;
import cn.hserver.plugin.web.context.WebConstConfig;
import cn.hserver.plugin.web.handlers.BuildResponse;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.util.ReferenceCountUtil;
import net.hserver.hp.server.handler.HpServerHandler;
import net.hserver.hp.server.handler.proxy.FrontendHandler;
import cn.hserver.core.interfaces.ProtocolDispatcherAdapter;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Order;
import net.hserver.hp.server.handler.proxy.RouterHandler;
import net.hserver.hp.server.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Objects;

import static net.hserver.hp.server.utils.FileUtil.readFile;

/**
 * 优先级要调整到自己的管理后台http协议的之上，都是http协议，所以这里需要判断是否是80
 */
@Order(0)
@Bean
public class HpWebProxyProtocolDispatcher implements ProtocolDispatcherAdapter {
    private static final Logger log = LoggerFactory.getLogger(HpWebProxyProtocolDispatcher.class);

    //判断HP头
    @Override
    public boolean dispatcher(ChannelHandlerContext ctx, ChannelPipeline channelPipeline, byte[] headers) {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().localAddress();
        if (socketAddress.getPort() == 80 || socketAddress.getPort() == 443) {
            try {
                String host = HostUtil.getHost(ByteBuffer.wrap(headers));
                log.debug("version:{},host:{}", SSLUtils.verifyPacket(ByteBuffer.wrap(headers)), host);
                if (host != null) {
                    final Integer[] userPort = {-1};
                    String[] split = host.split("\\.");
                    String username = split[0];
                    HpServerHandler.CURRENT_STATUS.forEach((k, v) -> {
                        if (v.getUsername().equals(username)) {
                            userPort[0] = Integer.parseInt(k);
                        }
                    });
                    if (userPort[0] == -1) {
                        addErrorHandler(channelPipeline, RouterHandler.ERROR.OFF_LINE);
                    } else {
                        addProxyHandler(channelPipeline, userPort[0]);
                    }
                    return true;
                }
            } catch (Exception e) {
                log.error(ExceptionUtil.getMessage(e));
                return false;
            }
        }
        return false;
    }

    public void addErrorHandler(ChannelPipeline pipeline, RouterHandler.ERROR error) {
        pipeline.addLast(WebConstConfig.BUSINESS_EVENT, new HttpServerCodec());
        pipeline.addLast(WebConstConfig.BUSINESS_EVENT, new RouterHandler(error));
    }

    public void addProxyHandler(ChannelPipeline pipeline, Integer port) {
        pipeline.addLast(WebConstConfig.BUSINESS_EVENT, new FrontendHandler(port));
    }

}
