package validation;

import config.Config;
import repository.entity.StationDataEntity;
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

    public void isStationExist(StationDataEntity stationDataEntity) {
        String city = jsonReader.getCityByStation(stationDataEntity.getStationNumber());
        if (!Objects.equals(stationDataEntity.getCity(), city)) {
            throw new IllegalArgumentException("Net takogo");
        }
        double minTemp = jsonReader.getMinTemperature(city);
        double maxTemp = jsonReader.getMaxTemperature(city);
        double temp = stationDataEntity.getTemperature();
        if (temp < minTemp || temp > maxTemp) {
            throw new IllegalArgumentException("Incorrect temperature");
        }

    }

}
