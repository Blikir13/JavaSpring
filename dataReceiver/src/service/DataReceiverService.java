package service;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import config.Config;
import dto.Request.DeleteEntity;
import dto.Request.TransferableObject;
import dto.StationDataDto;
import mapper.StationDataMapper;
import repository.entity.*;

import repository.entity.Response.ResponseEntity;


public class DataReceiverService {
    private final int processorPort;
    private final int monitoringPort;
    private final String host;
    private final static String pattern = "yyyy-MM-dd HH:mm:ss";
    private final static String created = "created";
    private final StationDataMapper stationDataMapper = new StationDataMapper();


    public DataReceiverService(Config config) {
        processorPort = config.getProcessorPort();
        monitoringPort = config.getMonitoringPort();
        host = config.getHost();
    }

    public void sendMessageMonitoring(MonitoringEntity monitoringEntity, String status) {
        try (Socket socket = new Socket(host, monitoringPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) { //FIXME not usage?<3

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern); //FIXME in var? <3
            String now = LocalDateTime.now().format(formatter);
            ; //FIXME duplicate? ?
            monitoringEntity.setDate(now);
            monitoringEntity.setStatus(status);

            // Отправляем объект на сервер
            out.writeObject(monitoringEntity);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace(); //FIXME in var?
        }
    }
    //FIXME public?
    private String connection(TransferableObject transferableObject){ //FIXME in var?
        try (Socket socket = new Socket(host, processorPort); //FIXME variables?
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            // Отправляем объект на сервер
            out.writeObject(transferableObject);
            out.flush();

            // Получаем ответ от сервера
            repository.entity.Response.ResponseEntity response = (ResponseEntity) in.readObject(); //FIXME import?
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
        TransferableObject transferableObject = stationDataMapper.toUpdateEntity(stationDataDto, path); //FIXME inline?
        // TODO: пока для отладки так создаю
        MonitoringEntity monitoringEntity = new MonitoringEntity();
        monitoringEntity.setStatus("new");
        sendMessageMonitoring(monitoringEntity, created);

        connection(transferableObject); //FIXME return value? добавить возвращение реза
    }


    public String createRequest(StationDataDto stationDataDto) { //FIXME exception? <3
        TransferableObject transferableObject = stationDataMapper.toStationDataEntity(stationDataDto); //FIXME inline? <3
        // TODO: пока для отладки так создаю
        MonitoringEntity monitoringEntity = new MonitoringEntity();
        monitoringEntity.setStatus("new"); //FIXME to var?
        sendMessageMonitoring(monitoringEntity, created); //FIXME to var?

        return connection(transferableObject);
    }

    public void deleteRecordRequest(String path) {
        DeleteEntity deleteEntity = new DeleteEntity(); //FIXME inline?
        deleteEntity.setPath(path);

        connection(deleteEntity);

    }

}