package net.hserver.hp.client;

import org.apache.commons.cli.*;

/**
 * @author hxm
 */
public class TestHpClient {

    public static void main(String[] args) throws Exception {
            HpClient client = new HpClient(new CallMsg() {
                @Override
                public void message(String msg) {
                    System.out.println(msg);
                }
            });
            client.connect("127.0.0.1",7731, "jishunan","123456", 12000, "127.0.0.1", 3306);
    }
}
