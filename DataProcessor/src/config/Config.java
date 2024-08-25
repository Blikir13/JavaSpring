package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private int port;
    private String citiesJsonPath;
    private String temperatureJsonPath;

    public Config() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("application.properties")) {
            properties.load(input);
            this.port = Integer.parseInt(properties.getProperty("processor.port"));
            this.citiesJsonPath = properties.getProperty("processor.cities.json");
            this.temperatureJsonPath = properties.getProperty("processor.temperature.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
