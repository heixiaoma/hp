package net.hserver.hp.proxy.config;

import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Value;
import lombok.Data;

@Bean
@Data
public class WebConfig {

    @Value("adminAddress")
    private String adminAddress;

    @Value("host")
    private String host;

    @Value("name")
    private String name;


}
