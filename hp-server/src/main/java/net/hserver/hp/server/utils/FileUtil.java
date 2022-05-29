
package net.hserver.hp.server.utils;


import java.io.*;

/**
 * @author hxm
 */
public class FileUtil {

    public static void copyFile(InputStream is, String outPath) {
        try {
            FileOutputStream fos = new FileOutputStream(outPath);
            byte[] b = new byte[1024];
            while ((is.read(b)) != -1) {
                fos.write(b);
            }
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String readFile(InputStream is) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(inputStreamReader);
            String st = "";
            String s = "";
            while ((st = br.readLine()) != null)
                s += st+"\r\n";

            br.close();
            inputStreamReader.close();
            is.close();
            return s;
        } catch (Exception e) {

        }
        return null;
    }

}