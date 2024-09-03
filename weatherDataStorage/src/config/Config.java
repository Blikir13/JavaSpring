package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Config {
    private int port;
    private String citiesJsonPath;
    private String temperatureJsonPath;
    private String resultJsonPath;
    private final String path = "application.properties";
    private final Logger logger = Logger.getLogger(Config.class.getName());

    public Config() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(path)) {
            properties.load(input);
            this.port = Integer.parseInt(properties.getProperty(ConfigProperty.PROCESSOR_PORT.getKey()));
            this.citiesJsonPath = properties.getProperty(ConfigProperty.CITIES_JSON_PATH.getKey());
            this.temperatureJsonPath = properties.getProperty(ConfigProperty.TEMPERATURE_JSON_PATH.getKey());
            this.resultJsonPath = properties.getProperty(ConfigProperty.RESULT_JSON_PATH.getKey());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String getResultJsonPath() {
        return resultJsonPath;
    }

    public int getPort() {
        return port;
    }

    public String getCitiesJsonPath() {
        return citiesJsonPath;
    }

    public String getTemperatureJsonPath() {
        return temperatureJsonPath;
    }
}
