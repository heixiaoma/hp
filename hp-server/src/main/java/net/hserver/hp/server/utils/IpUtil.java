package net.hserver.hp.server.utils;


import org.lionsoul.ip2region.xdb.Searcher;

import java.io.*;

public class IpUtil {
    private static Searcher ipSearcher;

    public static String getIp(String ip) {
        try {
            if (ipSearcher == null) {
                ipSearcher = Searcher.newWithFileOnly(data().getPath());
            }
            return ipSearcher.search(ip);
        } catch (Exception e) {
        }
        return "";
    }


    public static void main(String[] args) {
        String ip = getIp("/125.70.78.224");
        System.out.println(ip);
    }


    public static File data() {
        try {
            File DBTmpFile = File.createTempFile("ip2region", ".xdb");
            InputStream is = IpUtil.class.getResourceAsStream("/ip2region.xdb");
            OutputStream os = new FileOutputStream(DBTmpFile);
            byte[] buffer = new byte[2048];

            int reader;
            while ((reader = is.read(buffer)) != -1) {
                os.write(buffer, 0, reader);
            }
            is.close();
            os.close();
            return DBTmpFile;
        } catch (IOException var6) {
            return null;
        }
    }
}
