package controller;

import mapper.StationDataMapper;
import repository.entity.ResponseEntity;
import repository.entity.StationDataEntity;
import repository.entity.StationDataJsonEntity;
import repository.impl.StationDataJson;
import validation.Validation;

import java.io.IOException;

public class Controller {
    private final Validation validation = new Validation();
    private final StationDataJson stationDataJson = new StationDataJson();
    private final StationDataMapper stationDataMapper = new StationDataMapper();

    public Controller() throws IOException {
    }

    public ResponseEntity process(StationDataEntity stationDataEntity){
        try {
            validation.isStationExist(stationDataEntity);
            StationDataJsonEntity stationDataJsonEntity = stationDataMapper.toStationDataJsonEntity(stationDataEntity);
            stationDataJson.write(stationDataJsonEntity);
            return new ResponseEntity(stationDataJsonEntity.getId() + ".json", "");


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity("", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
