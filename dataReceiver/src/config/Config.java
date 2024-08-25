package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private String host;
    private int port;
    private String path;

    public Config() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("application.properties")) {
            properties.load(input);
            port = Integer.parseInt(properties.getProperty("receiver.port"));
            host = properties.getProperty("receiver.port");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHost() {
        return host;
    }

    public String getPath() {
        return path;
    }

    public int getPort() {
        return port;
    }
}
