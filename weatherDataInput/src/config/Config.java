package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    private String host;
    private int processorPort;
    private int monitoringPort;
    private String path;
    private static final String propertiesFile = "application.properties";
    private final Logger logger = Logger.getLogger(Config.class.getName());

    public Config() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(propertiesFile)) {
            properties.load(input);
            processorPort = Integer.parseInt(properties.getProperty(ConfigProperty.PROCESSOR_PORT.getKey()));
            monitoringPort = Integer.parseInt(properties.getProperty(ConfigProperty.MONITORING_PORT.getKey()));
            path = properties.getProperty(ConfigProperty.FILE_PATH.getKey());
            host = properties.getProperty(ConfigProperty.HOST.getKey());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public int getProcessorPort() {
        return processorPort;
    }

    public int getMonitoringPort() {
        return monitoringPort;
    }

}
