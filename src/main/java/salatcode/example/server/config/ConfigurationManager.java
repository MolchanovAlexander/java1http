package salatcode.example.server.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
     * Used to load a config file by te path provider
     * */
    public void loadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationException(e);
        }
        StringBuffer sb = new StringBuffer();
        int i;
        try {
            while ((i = fileReader.read()) != -1) {
                sb.append((char) i);
            }

        } catch (IOException e) {
            throw new HttpConfigurationException(e);
        }

        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationException("Error parsing conf file", e);
        }
        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationException("Error parsing conf file internal", e);
        }
    }

    // returns the current config
    public Configuration getCurrentConfiguration() {
        if (myCurrentConfiguration == null) {
            throw new HttpConfigurationException("No current cohfig is set");
        }
        return myCurrentConfiguration;
    }

}
