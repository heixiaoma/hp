package net.hserver.hp.client;

import net.hserver.hp.client.hp.CallMsg;
import net.hserver.hp.client.hp.HpClient;
import net.hserver.hp.common.protocol.HpMessageData;
import org.apache.commons.cli.*;

/**
 * @author hxm
 */
public class HpClientStarter {

    public static void main(String[] args) throws Exception {

        // args
        Options options = new Options();
        options.addOption("h", false, "Help");
        options.addOption("server_addr", true, "Hp server address");
        options.addOption("server_port", true, "Hp server port");
        options.addOption("username", true, "Hp server username");
        options.addOption("password", true, "Hp server password");
        options.addOption("domain", true, "Proxy server domain");
        options.addOption("type", true, " TCP,UDP,TCP_UDP");
        options.addOption("proxy_addr", true, "Proxy server address");
        options.addOption("proxy_port", true, "Proxy server port");
        options.addOption("remote_port", true, "Proxy server remote port");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h")) {
            // print help
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("options", options);
        } else {

            String serverAddress = cmd.getOptionValue("server_addr");
            if (serverAddress == null) {
                System.out.println("server_addr cannot be null");
                return;
            }
            String serverPort = cmd.getOptionValue("server_port");
            if (serverPort == null) {
                System.out.println("server_port cannot be null");
                return;
            }
            String username = cmd.getOptionValue("username");
            if (username == null) {
                System.out.println("username cannot be null");
                return;
            }
            String password = cmd.getOptionValue("password");
            if (password == null) {
                System.out.println("password cannot be null");
                return;
            }
            String domain = cmd.getOptionValue("domain");
            if (domain == null) {
                System.out.println("domain cannot be null");
                return;
            }
            String type = cmd.getOptionValue("type");
            if (type == null) {
                System.out.println("domain is_tcp be null");
                return;
            }
            String proxyAddress = cmd.getOptionValue("proxy_addr");
            if (proxyAddress == null) {
                System.out.println("proxy_addr cannot be null");
                return;
            }
            String proxyPort = cmd.getOptionValue("proxy_port");
            if (proxyPort == null) {
                System.out.println("proxy_port cannot be null");
                return;
            }
            String remotePort = cmd.getOptionValue("remote_port");
            if (remotePort == null) {
                System.out.println("remote_port cannot be null");
                return;
            }

            HpClient client = new HpClient(new CallMsg() {
                @Override
                public void message(String msg) {
                    System.out.println(msg);
                }
            });
            client.connect(HpMessageData.HpMessage.MessageType.valueOf(type),serverAddress, Integer.parseInt(serverPort), username,password,domain, Integer.parseInt(remotePort), proxyAddress, Integer.parseInt(proxyPort));
        }
    }
}
