package service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import console.Console;
import dto.StationDataDto;
import mapper.StationDataMapper;
import repository.entity.StationDataEntity;


public class DataReceiverService {
    private int port;
    private Console console = new Console();
    private StationDataDto stationDataDto = new StationDataDto();
    private StationDataMapper stationDataMapper = new StationDataMapper();

    public DataReceiverService(int port) {
        this.port = port;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("ServiceB is listening on port " + port);


                console.EnterDataStation(stationDataDto);
                StationDataEntity stationDataEntity = stationDataMapper.toStationDataEntity(stationDataDto);

                System.out.println("res " + stationDataDto.getStationNumber());

                try (Socket socket = serverSocket.accept()) {


                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(stationDataEntity);
                    objectOutputStream.flush();

                    byte[] byteArray = byteArrayOutputStream.toByteArray();

                    socket.getOutputStream().write(byteArray);
                    socket.getOutputStream().flush();


                    System.out.println("Sent: " + stationDataEntity.getCity());


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}