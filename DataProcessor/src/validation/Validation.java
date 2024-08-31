package validation;

import Request.CreateEntity;
import config.Config;
import util.JsonReader;

import java.io.IOException;
import java.util.Objects;

public class Validation {
    private JsonReader jsonReader;
    private Config config;

    public Validation(Config configd) throws IOException {
        config = configd;
        jsonReader = new JsonReader(configd.getTemperatureJsonPath(), config.getCitiesJsonPath());
    }

    public void isStationExist(CreateEntity createEntity) {
        String city = jsonReader.getCityByStation(createEntity.getStationNumber());
        if (!Objects.equals(createEntity.getCity(), city)) {
            throw new IllegalArgumentException("Net takogo");
        }
        double minTemp = jsonReader.getMinTemperature(city);
        double maxTemp = jsonReader.getMaxTemperature(city);
        double temp = createEntity.getTemperature();
        if (temp < minTemp || temp > maxTemp) {
            throw new IllegalArgumentException("Incorrect temperature");
        }

    }

}
