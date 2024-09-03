package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class Config {
    private int port;
    private  String logDirectory;
    private  long maxFileSize;
    private  int rotationIntervalHours;
    private final Logger logger = Logger.getLogger(Config.class.getName());

    public Config() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("application.properties")) {
            properties.load(input);
            this.port = Integer.parseInt(properties.getProperty("monitoring.port"));
            this.logDirectory = properties.getProperty("monitoring.logDirectory");
            this.maxFileSize = Integer.parseInt(properties.getProperty("monitoring.maxFileSize"));
            this.rotationIntervalHours = Integer.parseInt(properties.getProperty("monitoring.rotationIntervalHours"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public String getLogDirectory() {
        return logDirectory;
    }

    public int getRotationIntervalHours() {
        return rotationIntervalHours;
    }


    public int getPort() {
        return port;
    }

}
