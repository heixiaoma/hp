package net.hserver.hp.client;

import java.io.IOException;

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

        client.connect("hp.nsjiasu.com", 9091, "heixiaoma", "123456", "private",-1, "192.168.5.214", 5000);

//        new Thread(() -> {
//            while (true) {
//                try {
//                    if (!client.getStatus()) {
//                        client.connect("ksweb.club", 9091, "nas", "123456", 5000, "192.168.5.214", 5000);
//                    }
//                    Thread.sleep(10000);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
}
