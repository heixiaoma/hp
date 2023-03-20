package net.hserver.hp.server.utils;

public class UserCheckUtil {
    public static boolean checkUsername(String str) {
        return str.endsWith("@qq.com");
    }
    public static boolean checkDomain(String str) {
        String regex = "^[a-z0-9]+$";
        return str.matches(regex);
    }
}
