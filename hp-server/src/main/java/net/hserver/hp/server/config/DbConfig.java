package net.hserver.hp.server.config;

import cn.hserver.plugin.mybatis.bean.MybatisConfig;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.plugin.Interceptor;
import cn.hserver.core.ioc.annotation.Bean;
import cn.hserver.core.ioc.annotation.Configuration;
import cn.hserver.core.ioc.annotation.Value;

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
    public MybatisConfig sql() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(mySqlUrl);
        ds.setUsername(mySqlUserName);
        ds.setPassword(mySqlPassword);
        ds.setDriverClassName(mySqlDriver);
        MybatisConfig mybatisConfig = new MybatisConfig();
        mybatisConfig.setMapUnderscoreToCamelCase(true);
        //默认数据源
        mybatisConfig.addDataSource(ds);
        //resource/mapper 全部.xml扫描
        mybatisConfig.setMapperLocations("mapper");
        //分页插件
        mybatisConfig.setPlugins(new Interceptor[]{initInterceptor()});
        return mybatisConfig;

    }

    private Interceptor initInterceptor() {
        //创建mybatis-plus插件对象
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //构建分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(true);
        paginationInnerInterceptor.setMaxLimit(500L);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }
}
