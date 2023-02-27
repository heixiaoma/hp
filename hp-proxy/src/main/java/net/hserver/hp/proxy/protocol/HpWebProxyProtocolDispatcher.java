package net.hserver.hp.proxy.protocol;

import cn.hserver.core.ioc.annotation.Autowired;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Order;
import cn.hserver.core.server.util.ExceptionUtil;
import cn.hserver.core.server.util.protocol.HostUtil;
import cn.hserver.core.server.util.protocol.SSLUtils;
import cn.hserver.plugin.web.context.WebConstConfig;
import cn.hserver.plugin.web.protocol.DispatchHttp;
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
public class HpWebProxyProtocolDispatcher extends DispatchHttp {

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
                    // 检查是否是否官方主域名？如果是主域名走内部接口
                    if (host.startsWith(webConfig.getHost())) {
                        return super.dispatcher(ctx, channelPipeline, headers);
                    }
                    String[] split = host.split("\\.");
                    String domain = split[0];

                    ConnectInfo connectInfo = HpServerHandler.CURRENT_STATUS.stream().filter(v -> domain!=null&&v!=null&&v.getDomain()!=null&&domain.equals(v.getDomain())).findFirst().orElse(null);
                    if (connectInfo==null){
                         connectInfo = HpServerHandler.CURRENT_STATUS.stream().filter(v -> v!=null&& v.getCustomDomain()!=null&&host.equals(v.getCustomDomain())).findFirst().orElse(null);
                    }

                    if (connectInfo==null) {
                        addErrorHandler(channelPipeline, RouterHandler.ERROR.OFF_LINE);
                    } else {
                        addProxyHandler(channelPipeline, connectInfo.getPort());
                    }
                    return true;
                }
            } catch (Exception e) {
                log.error(e.getMessage(),e);
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
