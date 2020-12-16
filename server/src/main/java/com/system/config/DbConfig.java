package com.system.config;

import com.zaxxer.hikari.HikariDataSource;
import org.beetl.sql.clazz.kit.ClassLoaderKit;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.DBStyle;
import org.beetl.sql.core.db.MySqlStyle;
import org.beetl.sql.core.loader.MarkdownClasspathLoader;
import org.beetl.sql.core.loader.SQLLoader;
import org.beetl.sql.ext.DebugInterceptor;
import top.hserver.core.ioc.annotation.Bean;
import top.hserver.core.ioc.annotation.Configuration;
import top.hserver.core.ioc.annotation.Value;

/**
 * @author hxm
 */
@Configuration
public class DbConfig {

    @Value("mysql.url")
    private String mySqlUrl;

    @Value("mysql.userName")
    private String mySqlUserName;

    @Value("mysql.password")
    private String mySqlPassword;

    @Value("mysql.driver")
    private String mySqlDriver;

    @Bean
    public SQLManager sql() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(mySqlUrl);
        ds.setUsername(mySqlUserName);
        ds.setPassword(mySqlPassword);
        ds.setDriverClassName(mySqlDriver);
        ConnectionSource source = ConnectionSourceHelper.getSingle(ds);
        SQLManagerBuilder builder = new SQLManagerBuilder(source);
        builder.setSqlLoader(new MarkdownClasspathLoader("/sql"));
        builder.setNc(new UnderlinedNameConversion());
        builder.setInters(new Interceptor[]{new DebugInterceptor()});
        return builder.build();
    }
}