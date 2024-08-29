package service;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import dto.StationDataDto;
import mapper.StationDataMapper;
import repository.entity.*;
import repository.entity.Request.TransferableObject;
import repository.entity.Request.UpdateEntity;
import repository.entity.Response.ResponseEntity;


public class DataReceiverService {
//    private final int port;
//    private final String host;
//    private final Config config = new Config();
//    private final Console console = new Console();
    private final StationDataDto stationDataDto = new StationDataDto();
    private final StationDataMapper stationDataMapper = new StationDataMapper();

    public DataReceiverService() {
//        this.port = config.getPort();
//        this.host = config.getHost();
    }

    public void sendMessageMonitoring(MonitoringEntity monitoringEntity, String status) {
        try (Socket socket = new Socket("localhost", 5002);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String now = LocalDateTime.now().format(formatter);
            ;
            monitoringEntity.setDate(now);
            monitoringEntity.setStatus(status);

            // Отправляем объект на сервер
            out.writeObject(monitoringEntity);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String connection(repository.entity.Request.TransferableObject transferableObject){
        try (Socket socket = new Socket("localhost", 5001);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Отправляем объект на сервер
            out.writeObject(transferableObject);
            out.flush();

            // Получаем ответ от сервера
            repository.entity.Response.ResponseEntity response = (ResponseEntity) in.readObject();
            System.out.println("Ответ сервера: " + response.toString());

            if (Objects.equals(response.getId(), "")) {
                // TODO: log error

                System.out.println("error" + response.getErrorMessage());
            } else {
                //TODO: !!!!
                return response.getId();
//                console.updateCsv(response.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void updateRequest(StationDataDto stationDataDto, String path) {
        UpdateEntity stationDataEntity = stationDataMapper.toUpdateEntity(stationDataDto, path);
        repository.entity.Request.TransferableObject transferableObject = stationDataEntity;
        // TODO: пока для отладки так создаю
        MonitoringEntity monitoringEntity = new MonitoringEntity();
        monitoringEntity.setStatus("new");
        sendMessageMonitoring(monitoringEntity, "created");

        connection(transferableObject);
    }


    public String createRequest(StationDataDto stationDataDto) throws IOException {
        repository.entity.Request.CreateEntity stationDataEntity = stationDataMapper.toStationDataEntity(stationDataDto);
        TransferableObject transferableObject = stationDataEntity;
        // TODO: пока для отладки так создаю
        MonitoringEntity monitoringEntity = new MonitoringEntity();
        monitoringEntity.setStatus("new");
        sendMessageMonitoring(monitoringEntity, "created");

        return connection(transferableObject);
    }

    public void deleteRecordRequest(String path) {
        repository.entity.Request.DeleteEntity deleteEntity = new repository.entity.Request.DeleteEntity();
        deleteEntity.setPath(path);

        connection(deleteEntity);

    }

}