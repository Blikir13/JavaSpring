package service;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import config.Config;
import dto.Request.DeleteEntity;
import dto.Request.TransferableObject;
import dto.StationDataDto;
import mapper.WeatherInputMapper;
import repository.entity.*;

import repository.impl.RepositoryStationCsv;
import transport.client.WeatherStorageClient;
import transport.client.MonitoringClient;
import validation.Validation;


public class WeatherInputService {
    private final static String created = "created";
    private final static String mistake = "mistake";
    private final static String success = "success";
    private final WeatherInputMapper stationDataMapper = new WeatherInputMapper();
    private final RepositoryStationCsv repositoryStationCsv; //FIXME path to var? <3
    private final Validation validation = new Validation();
    private final WeatherStorageClient dataProcessorClient;
    private final MonitoringClient monitoringClient;
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    public WeatherInputService(Config config) {
        dataProcessorClient = new WeatherStorageClient(config);
        monitoringClient = new MonitoringClient(config);
        repositoryStationCsv = new RepositoryStationCsv(config.getPath());
    }

    public boolean checkHasId(int id) {
        List<WeatherInputCsvEntity> weatherInputCsvEntities = repositoryStationCsv.read();
        for (WeatherInputCsvEntity weatherInputCsvEntity : weatherInputCsvEntities) {
            if (Objects.equals(weatherInputCsvEntity.getId(), id)) {
                return false;
            }
        }
        return true;
    }

    public void updateRequest(StationDataDto stationDataDto, int id) {
        try {
            validation.firstValidation(stationDataDto);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
//            System.out.println("Неверные данные: " + e.getMessage());
        }

        String path = repositoryStationCsv.updateRecord(id, stationDataDto.getStationNumber());

        TransferableObject transferableObject = stationDataMapper.toUpdateEntity(stationDataDto, path);
        String newPath = dataProcessorClient.sendRequest(transferableObject); //FIXME return value? добавить возвращение реза
        if (!Objects.equals(newPath, "")) {
            repositoryStationCsv.updateWithId(newPath, id);
        }

        logger.log(Level.INFO, "Updated station data with id: " + id);

    }


    public void createRequest(StationDataDto stationDataDto) {
        try {
            validation.firstValidation(stationDataDto);

            WeatherInputCsvEntity stationDataCsvEntity = stationDataMapper.toStationDataCsvEntity(stationDataDto);
            repositoryStationCsv.write(stationDataCsvEntity);

            MonitoringDto monitoringEntity = new MonitoringDto(stationDataCsvEntity.toString(), created);
            monitoringClient.sendRequest(monitoringEntity);

            TransferableObject transferableObject = stationDataMapper.toStationDataEntity(stationDataDto);
            String path = dataProcessorClient.sendRequest(transferableObject);

            logger.log(Level.INFO, "Created record with path: " + path);

            if (!Objects.equals(path, "")) {
                monitoringClient.sendRequest(new MonitoringDto(stationDataCsvEntity.toString(), mistake));
                repositoryStationCsv.update(path);
            } else {
                monitoringClient.sendRequest(new MonitoringDto(stationDataCsvEntity.toString(), success));
            }

        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
//            System.out.println("Неверные данные: " + e.getMessage());
        }
    }

    public void deleteRecordRequest(int id) {
        DeleteEntity deleteEntity = new DeleteEntity();
        String path = repositoryStationCsv.deleteRecord(id);
        if (!Objects.equals(path, "")) {
            deleteEntity.setPath(path);
            dataProcessorClient.sendRequest(deleteEntity);
        }
        logger.log(Level.INFO, "Deleted record with id: " + id);
    }

    public List<WeatherInputCsvEntity> read() {
        return repositoryStationCsv.read();
    }
}