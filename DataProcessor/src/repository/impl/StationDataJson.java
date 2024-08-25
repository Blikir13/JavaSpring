package repository.impl;

import mapper.StationDataMapper;
import repository.entity.StationDataEntity;
import repository.entity.StationDataJsonEntity;
import util.JsonReader;

import java.io.IOException;

public class StationDataJson {
    private final JsonReader jsonReader = new JsonReader();

    public StationDataJson() throws IOException {
    }

    public void write(StationDataJsonEntity stationDataJsonEntity, String path) throws IOException {
        jsonReader.writeJson(stationDataJsonEntity, path);
    }
}
