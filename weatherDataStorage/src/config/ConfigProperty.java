package config;

public enum ConfigProperty {
    PROCESSOR_PORT("processor.port"),
    CITIES_JSON_PATH("processor.cities.json"),
    TEMPERATURE_JSON_PATH("processor.temperature.json"),
    RESULT_JSON_PATH("processor.result.json");

    private final String key;

    ConfigProperty(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
