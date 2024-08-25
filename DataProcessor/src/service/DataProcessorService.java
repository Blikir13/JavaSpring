package service;

import config.Config;
import controller.Controller;
import repository.entity.ResponseEntity;
import repository.entity.StationDataEntity;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DataProcessorService {
    private final int serverPort;
    private StationDataEntity processorEntity = new StationDataEntity();
    private final Config config = new Config();
    private final Controller controller = new Controller(config);

    public DataProcessorService(String serverHost, int serverPort) throws IOException {
        this.serverPort = config.getPort();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Сервер запущен, ожидаем подключения...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    System.out.println("Клиент подключен.");

                    // Принимаем объект от клиента
                    StationDataEntity stationDataEntity = (StationDataEntity) in.readObject();
                    ResponseEntity responseEntity = controller.process(stationDataEntity, config);
                    System.out.println("Принят объект: " + stationDataEntity);

                    // Отправляем ответ клиенту
                    out.writeObject(responseEntity);
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}