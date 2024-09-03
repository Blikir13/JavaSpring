package repository.impl;

import repository.entity.StationDataJsonEntity;
import util.JsonReader;

public class WeatherStorageJson {
    private final JsonReader jsonReader = new JsonReader();

    public WeatherStorageJson() {
    }

    public void write(StationDataJsonEntity stationDataJsonEntity, String path, String updatePath) {
        jsonReader.writeJson(stationDataJsonEntity, path, updatePath);
    }
}
