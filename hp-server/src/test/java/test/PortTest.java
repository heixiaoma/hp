package test;

import java.io.IOException;
import java.net.ServerSocket;

public class PortTest {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10000; i++) {
                int localPort = new ServerSocket(0).getLocalPort();
                System.out.println(localPort);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
