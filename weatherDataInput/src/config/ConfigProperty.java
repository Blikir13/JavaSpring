package config;

public enum ConfigProperty {
    PROCESSOR_PORT("receiver.processor.port"),
    MONITORING_PORT("receiver.monitoring.port"),
    FILE_PATH("receiver.csv.filepath"),
    HOST("receiver.host");

    private final String key;

    ConfigProperty(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
