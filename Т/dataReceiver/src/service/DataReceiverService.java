package service;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

import config.Config;
import console.Console;
import dto.StationDataDto;
import mapper.StationDataMapper;
import repository.entity.ResponseEntity;
import repository.entity.StationDataEntity;


public class DataReceiverService {
    private final int port;
    private final String host;
    private final Config config = new Config();
    private final Console console = new Console();
    private final StationDataDto stationDataDto = new StationDataDto();
    private final StationDataMapper stationDataMapper = new StationDataMapper();

    public DataReceiverService() {
        this.port = config.getPort();
        this.host = config.getHost();
    }


    public void start() throws IOException {


        String continueInput;
        while (true) {

            console.enterDataStation(stationDataDto);
            StationDataEntity stationDataEntity = stationDataMapper.toStationDataEntity(stationDataDto);

            try (Socket socket = new Socket("localhost", 5001);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                // Отправляем объект на сервер
                out.writeObject(stationDataEntity);
                out.flush();

                // Получаем ответ от сервера
                ResponseEntity response = (ResponseEntity) in.readObject();
                System.out.println("Ответ сервера: " + response.toString());

                if (Objects.equals(response.getId(), "")) {
                    // TODO: log error
                    System.out.println("error" + response.getErrorMessage());
                } else {
                    console.updateCsv(response.getId());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

}