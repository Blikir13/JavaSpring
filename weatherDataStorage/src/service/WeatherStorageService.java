package service;

import dto.Request.CreateEntity;
import dto.Request.UpdateEntity;
import config.Config;
import mapper.WeatherStorageMapper;
import repository.entity.Response.ResponseDto;
import repository.entity.StationDataJsonEntity;
import repository.impl.WeatherStorageJson;
import validation.Validation;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherStorageService {
    private final WeatherStorageJson stationDataJson = new WeatherStorageJson();
    private final WeatherStorageMapper stationDataMapper = new WeatherStorageMapper();
    private final Validation validation;
    private final Config config;
    private static final Logger logger = Logger.getLogger(WeatherStorageService.class.getName());
    private static final String postfix = ".json";
    private static final String deleted = "Deleted";
    private static final String slash = "/";

    public WeatherStorageService(Config config) {
        validation = new Validation(config);
        this.config = config;
    }

    public ResponseDto process(CreateEntity createEntity) {
        try {
            validation.isStationExist(createEntity);
            StationDataJsonEntity stationDataJsonEntity = stationDataMapper.toStationDataJsonEntity(createEntity);
            stationDataJson.write(stationDataJsonEntity, config.getResultJsonPath(), "");
            return new ResponseDto(stationDataJsonEntity.getId() + postfix, "");
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ResponseDto("", e.getMessage());
        }
    }


    public ResponseDto update(UpdateEntity updateEntity) {
        try {
            validation.isStationExist(updateEntity);
            StationDataJsonEntity stationDataJsonEntity = stationDataMapper.toStationDataJsonEntity(updateEntity);
            stationDataJson.write(stationDataJsonEntity, config.getResultJsonPath(), updateEntity.getPath());
            return new ResponseDto(stationDataJsonEntity.getId() + postfix, "");
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return new ResponseDto("", e.getMessage());
        }
    }

    public ResponseDto delete(String path) {
        File file = new File(config.getResultJsonPath() + slash + path);
        file.delete();
        return new ResponseDto(deleted + path, "");
    }

}
