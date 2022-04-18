package net.hserver.hp.windows.config;


import net.hserver.hp.windows.vo.UserVo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final static File configFile = new File(top.hserver.core.server.context.ConstConfig.PATH + "app.properties");
    private final static Properties p = new Properties();
    private static Config config = null;

    public static Config getInstance() {
        if (config == null) {
            if (!configFile.isFile()) {
                try {
                    configFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(configFile);
                p.load(fileInputStream);
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            config = new Config();
        }
        return config;
    }

    public UserVo getUser() {
        String u = p.getProperty(ConstConfig.USERNAME);
        String ps = p.getProperty(ConstConfig.PASSWORD);
        if (ps != null && ps.trim().length() > 0 && u != null && u.trim().length() > 0) {
            UserVo userVo = new UserVo();
            userVo.setUsername(u);
            userVo.setUsername(ps);
            return userVo;
        }
        return null;
    }

    public void setUSer(String username, String password) {
        p.setProperty(ConstConfig.USERNAME, username);
        p.setProperty(ConstConfig.PASSWORD, password);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(configFile);
            p.store(fileOutputStream, "");
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }

}
