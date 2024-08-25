package controller;

import config.Config;
import mapper.StationDataMapper;
import repository.entity.ResponseEntity;
import repository.entity.StationDataEntity;
import repository.entity.StationDataJsonEntity;
import repository.impl.StationDataJson;
import validation.Validation;

import java.io.IOException;

public class Controller {
    private final StationDataJson stationDataJson = new StationDataJson();
    private final StationDataMapper stationDataMapper = new StationDataMapper();
    private final Config config;
    private final Validation validation;

    public Controller(Config configd) throws IOException {
        config = configd;
        validation = new Validation(config);
    }

    public ResponseEntity process(StationDataEntity stationDataEntity, Config config){
        try {
            validation.isStationExist(stationDataEntity);
            StationDataJsonEntity stationDataJsonEntity = stationDataMapper.toStationDataJsonEntity(stationDataEntity);
            stationDataJson.write(stationDataJsonEntity, config.getResultJsonPath());
            return new ResponseEntity(stationDataJsonEntity.getId() + ".json", "");


        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity("", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
