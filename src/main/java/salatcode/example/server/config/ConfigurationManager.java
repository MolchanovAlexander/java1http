package salatcode.example.server.config;

import java.io.InputStream;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import salatcode.example.server.util.Json;

public class ConfigurationManager {
    private static ConfigurationManager myConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        if (myConfigurationManager == null)
            myConfigurationManager = new ConfigurationManager();
        return myConfigurationManager;
    }

    /*
     * Used to load a config file by the file name (inside resources)
     */
    public void loadConfigurationFile(String fileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new HttpConfigurationException("Configuration file not found: " + fileName);
            }

            // Read the file content
            StringBuilder sb = new StringBuilder();
            int i;
            while ((i = inputStream.read()) != -1) {
                sb.append((char) i);
            }

            // Parse JSON
            JsonNode conf = Json.parse(sb.toString());
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (IOException e) {
            throw new HttpConfigurationException("Error loading configuration file", e);
        }
    }

    // Returns the current configuration
    public Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No current configuration is set");
        }
        return myCurrentConfiguration;
    }
}
