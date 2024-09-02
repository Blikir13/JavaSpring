package service;

import dto.Request.CreateEntity;
import dto.Request.UpdateEntity;
import config.Config;
import mapper.StationDataMapper;
import repository.entity.Response.ResponseEntity;
import repository.entity.StationDataJsonEntity;
import repository.impl.StationDataJson;
import validation.Validation;

import java.io.File;
import java.io.IOException;

public class DataProcessorService {
    private final StationDataJson stationDataJson = new StationDataJson();
    private final StationDataMapper stationDataMapper = new StationDataMapper();
    private final Validation validation;
    private final Config config;

    public DataProcessorService(Config config) throws IOException {
        validation = new Validation(config);
        this.config = config;
    }

    public ResponseEntity process(CreateEntity createEntity){
        try {
            validation.isStationExist(createEntity);
            StationDataJsonEntity stationDataJsonEntity = stationDataMapper.toStationDataJsonEntity(createEntity);
            stationDataJson.write(stationDataJsonEntity, config.getResultJsonPath(), "");
            return new ResponseEntity(stationDataJsonEntity.getId() + ".json", "");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity("", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public ResponseEntity update(UpdateEntity updateEntity){
        try {
            validation.isStationExist(updateEntity);
            StationDataJsonEntity stationDataJsonEntity = stationDataMapper.toStationDataJsonEntity(updateEntity);
            stationDataJson.write(stationDataJsonEntity, config.getResultJsonPath(), updateEntity.getPath());
            return new ResponseEntity(stationDataJsonEntity.getId() + ".json", "");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity("", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity delete(String path){
        File file = new File("DataProcessor/resultJson/"+path);
        file.delete();
        return new ResponseEntity("Deleted " + path, "");
    }

}
