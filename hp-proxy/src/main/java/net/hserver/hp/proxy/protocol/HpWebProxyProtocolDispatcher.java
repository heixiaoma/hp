package net.hserver.hp.proxy.protocol;

import cn.hserver.core.interfaces.ProtocolDispatcherAdapter;
import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Order;
import cn.hserver.core.server.util.protocol.HostUtil;
import cn.hserver.core.server.util.protocol.SSLUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;
import net.hserver.hp.proxy.config.WebConfig;
import net.hserver.hp.proxy.domian.bean.ConnectInfo;
import net.hserver.hp.proxy.handler.HpServerHandler;
import net.hserver.hp.proxy.handler.proxy.FrontendHandler;
import net.hserver.hp.proxy.handler.proxy.RouterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * 优先级要调整到自己的管理后台http协议的之上，都是http协议，所以这里需要判断是否是80
 */
@Order(0)
@Bean
public class HpWebProxyProtocolDispatcher implements ProtocolDispatcherAdapter {

    @Autowired
    private WebConfig webConfig;

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
                    String[] split = host.split("\\.");
                    String domain = split[0];

                    ConnectInfo connectInfo = HpServerHandler.CURRENT_STATUS.stream().filter(v -> domain != null && v != null && v.getDomain() != null && domain.equals(v.getDomain())).findFirst().orElse(null);
                    if (connectInfo == null) {
                        connectInfo = HpServerHandler.CURRENT_STATUS.stream().filter(v -> v != null && v.getCustomDomain() != null && host.equals(v.getCustomDomain())).findFirst().orElse(null);
                    }

                    if (connectInfo == null) {
                        addErrorHandler(channelPipeline);
                    } else {
                        addProxyHandler(host, channelPipeline, connectInfo.getPort());
                    }
                    return true;
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return false;
            }
        }
        return false;
    }

    /**
     * 未知来源的访问直接响应错误的穿透
     * @param pipeline
     */
    public void addErrorHandler(ChannelPipeline pipeline) {
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new RouterHandler());
    }

    /**
     * 存在反向代理
     * @param host
     * @param pipeline
     * @param port
     */
    public void addProxyHandler(String host, ChannelPipeline pipeline, Integer port) {
        pipeline.addLast(new FrontendHandler(host,port));
    }

}
