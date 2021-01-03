package net.hserver.hp.server.config;

import com.zaxxer.hikari.HikariDataSource;
import net.hserver.hp.server.utils.FileUtil;
import org.beetl.sql.core.*;
import org.beetl.sql.core.db.SQLiteStyle;
import org.beetl.sql.core.loader.MarkdownClasspathLoader;
import org.beetl.sql.ext.DBInitHelper;
import org.beetl.sql.ext.DebugInterceptor;
import top.hserver.core.ioc.annotation.Bean;
import top.hserver.core.ioc.annotation.Configuration;

import java.io.File;
import java.io.InputStream;

/**
 * @author hxm
 */
@Configuration
public class SqlConfig {

    @Bean
    public SQLManager sql()  {
        try {
            String path = System.getProperty("user.dir") + File.separator + "db.db";
            File file = new File(path);
            boolean flag = false;
            if (!file.exists()) {
                //进行导出初始化一个sqlite
                InputStream is = SqlConfig.class.getResourceAsStream("/db/db.db");
                FileUtil.copyFile(is, path);
                flag = true;
            }
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl( "jdbc:sqlite:" + path);
            ds.setDriverClassName( "org.sqlite.JDBC");
            ConnectionSource source = ConnectionSourceHelper.getSingle(ds);
            SQLManagerBuilder builder = new SQLManagerBuilder(source);
            builder.setSqlLoader(new MarkdownClasspathLoader("/sql"));
            builder.setNc(new UnderlinedNameConversion());
            builder.setInters(new Interceptor[]{new DebugInterceptor()});
            builder.setDbStyle(new SQLiteStyle());
            SQLManager sqlManager = builder.build();
            if (flag) {
                DBInitHelper.executeSqlScript(sqlManager, "db/init.sql");
            }
            return sqlManager;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}