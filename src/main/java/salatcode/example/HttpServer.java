package salatcode.example;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import salatcode.example.server.config.Configuration;
import salatcode.example.server.config.ConfigurationManager;
import salatcode.example.server.core.ServerListnerThread;

/**
 * Driver Class for Http Server
 */
public class HttpServer {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) {
        String path = "src/main/resources/http.json";
        LOGGER.info(" Server starting... ");
        ConfigurationManager.getInstance().loadConfigurationFile(path);

        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port: " + conf.getPort());
        LOGGER.info("Using webroot: " + conf.getWebroot());
        try {
            ServerListnerThread serverListnerThread = new ServerListnerThread(conf.getPort(), conf.getWebroot());
            serverListnerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
