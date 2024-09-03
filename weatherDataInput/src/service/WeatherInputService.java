package service;

import java.io.*;
import java.util.List;
import java.util.Objects;

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
    private final static String pattern = "yyyy-MM-dd HH:mm:ss";
    private final static String created = "created";
    private final WeatherInputMapper stationDataMapper = new WeatherInputMapper();
    private final RepositoryStationCsv repositoryStationCsv; //FIXME path to var? <3
    private final Validation validation = new Validation();
    private final WeatherStorageClient dataProcessorClient;
    private final MonitoringClient monitoringClient;


    public WeatherInputService(Config config) {
        dataProcessorClient = new WeatherStorageClient(config);
        monitoringClient = new MonitoringClient(config);
        repositoryStationCsv = new RepositoryStationCsv(config.getPath());
    }

    public void updateRequest(StationDataDto stationDataDto, int id) throws IOException {
        // TODO: пока для отладки так создаю
        try {
            validation.firstValidation(stationDataDto);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверные данные: " + e.getMessage());
        }

        String path = repositoryStationCsv.updateRecord(id, stationDataDto.getStationNumber());

        if (!Objects.equals(path, "")) {
            TransferableObject transferableObject = stationDataMapper.toUpdateEntity(stationDataDto, path);
            dataProcessorClient.sendRequest(transferableObject); //FIXME return value? добавить возвращение реза
        }

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

            if (!Objects.equals(path, "")) {
                repositoryStationCsv.update(path);
            }

        } catch (IllegalArgumentException | IOException e) {
            System.out.println("Неверные данные: " + e.getMessage());
        }
    }

    public void deleteRecordRequest(int id) throws IOException {
        DeleteEntity deleteEntity = new DeleteEntity();
        String path = repositoryStationCsv.deleteRecord(id);
        System.out.println("path " + path);
        if (!Objects.equals(path, "")) {
            deleteEntity.setPath(path);
            dataProcessorClient.sendRequest(deleteEntity);
        }
    }

    public List<WeatherInputCsvEntity> read() throws IOException {
        return repositoryStationCsv.read();
    }
}