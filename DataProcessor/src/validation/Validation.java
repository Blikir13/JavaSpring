package validation;

import repository.entity.StationDataEntity;
import util.JsonReader;

import java.io.IOException;
import java.util.Objects;

public class Validation {
    private JsonReader jsonReader;

    public Validation() throws IOException {
        jsonReader = new JsonReader("json1.json", "json2.json");
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
