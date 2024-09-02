package service;

import java.io.*;
import java.util.List;
import java.util.Objects;

import config.Config;
import dto.Request.DeleteEntity;
import dto.Request.TransferableObject;
import dto.StationDataDto;
import mapper.StationDataMapper;
import repository.entity.*;

import repository.impl.RepositoryStationCsv;
import transport.client.DataProcessorClient;
import validation.Validation;


public class DataReceiverService {
    private final static String pattern = "yyyy-MM-dd HH:mm:ss";
    private final static String created = "created";
    private final StationDataMapper stationDataMapper = new StationDataMapper();
    private static final String filePath = "stationData.csv";
    private final RepositoryStationCsv repositoryStationCsv = new RepositoryStationCsv(filePath); //FIXME path to var? <3
    private final Validation validation = new Validation();
    private final DataProcessorClient dataProcessorClient;


    public DataReceiverService(Config config) {
        dataProcessorClient = new DataProcessorClient(config);
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


    public void createRequest(StationDataDto stationDataDto) { //FIXME exception? <3
        // TODO: validation -> repository -> transfer
        try {
            // TODO: валидация конкретной переменной
            validation.firstValidation(stationDataDto);
            StationDataCsvEntity stationDataCsvEntity = stationDataMapper.toStationDataCsvEntity(stationDataDto);

            repositoryStationCsv.write(stationDataCsvEntity);

            TransferableObject transferableObject = stationDataMapper.toStationDataEntity(stationDataDto);
            // возвращает путь созданного json файла
            String path = dataProcessorClient.sendRequest(transferableObject);
            // TODO: пока для отладки так создаю
//            MonitoringEntity monitoringEntity = new MonitoringEntity();
//            monitoringEntity.setStatus("new"); //FIXME to var?
//            sendMessageMonitoring(monitoringEntity, created); //FIXME to var?

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

    public List<StationDataCsvEntity> read() throws IOException {
        return repositoryStationCsv.read();
    }
}