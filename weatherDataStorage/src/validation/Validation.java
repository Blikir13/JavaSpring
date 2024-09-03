package validation;

import config.Config;
import dto.Request.CreateEntity;
import util.JsonReader;

import java.util.Objects;

public class Validation {
    private final JsonReader jsonReader;

    public Validation(Config config) {
        jsonReader = new JsonReader(config.getTemperatureJsonPath(), config.getCitiesJsonPath());
    }

    public void isStationExist(CreateEntity createEntity) {
        String city = jsonReader.getCityByStation(createEntity.getStationNumber());
        if (!Objects.equals(createEntity.getCity(), city)) {
            throw new IllegalArgumentException("Город с именем " + createEntity.getCity() + " отсутсвтует в справочнике");
        }
        double minTemp = jsonReader.getMinTemperature(city);
        double maxTemp = jsonReader.getMaxTemperature(city);
        double temp = createEntity.getTemperature();
        if (temp < minTemp || temp > maxTemp) {
            throw new IllegalArgumentException("Температура " + temp + " невозможна!");
        }

    }

}
