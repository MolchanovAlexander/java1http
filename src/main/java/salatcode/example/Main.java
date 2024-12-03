package salatcode.example;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import salatcode.example.server.config.Configuration;
import salatcode.example.server.config.ConfigurationManager;
import salatcode.example.server.core.ServerListnerThread;

/**
 * Driver Class for Http Server
 */
public class Main {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String path = "http.json"; // PC
        //String path = "app/resources/http.json"; // docker

        LOGGER.info(" Server starting... ");

        File resourcesDir = new File("./resources");
        File[] files = resourcesDir.listFiles();
        if (files != null) {
            LOGGER.info("Contents of /app/resources:");
            for (File file : files) {
                LOGGER.info(file.getName());
            }
        } else {
            LOGGER.warn("The /app/resources directory does not exist or is empty.");
        }
        ConfigurationManager.getInstance().loadConfigurationFile(path);

        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port: {}", conf.getPort());
        LOGGER.info("Using webroot: {}", conf.getWebroot());
        try {
            ServerListnerThread serverListnerThread = new ServerListnerThread(conf.getPort(), conf.getWebroot());
            serverListnerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
