package net.hserver.hp.server;

import org.apache.commons.cli.*;

/**
 * @author hxm
 */
public class HpServerStarter {

    public static void main(String[] args) throws ParseException, InterruptedException {

        // args
        Options options = new Options();
        options.addOption("h", false, "Help");
        options.addOption("port", true, "Hp server port");
        options.addOption("password", true, "Hp server password");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        if (cmd.hasOption("h")) {
            // print help
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("options", options);
        } else {

            int port = Integer.parseInt(cmd.getOptionValue("port", "7731"));
            String password = cmd.getOptionValue("password");
            HpServer server = new HpServer();
            server.start(port, password);

            System.out.println("Hp server started on port " + port);
        }
    }
}
