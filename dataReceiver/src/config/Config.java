package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {//FIXME not used? <3
    private String host;
    private int processorPort;
    private int monitoringPort;
    private String path;

    public Config() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("application.properties")) {
            properties.load(input);
            processorPort = Integer.parseInt(properties.getProperty("receiver.processor.port"));
            monitoringPort = Integer.parseInt(properties.getProperty("receiver.monitoring.port"));
            host = properties.getProperty("receiver.host");
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


    public int getProcessorPort() {
        return processorPort;
    }


    public int getMonitoringPort() {
        return monitoringPort;
    }

}
