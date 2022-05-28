package net.hserver.hp.client;

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
            client.connect("127.0.0.1",9090, "jishunan","123456", 12000, "127.0.0.1", 8888);
    }
}
