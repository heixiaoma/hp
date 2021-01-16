package test;

import java.io.IOException;
import java.net.ServerSocket;

public class PortTest {
    public static void main(String[] args) {
        ServerSocket serverSocket = null; //读取空闲的可用端口
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {

            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(serverSocket.getLocalPort());
        }
    }
}
