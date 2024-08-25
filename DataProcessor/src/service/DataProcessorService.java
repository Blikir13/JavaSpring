package service;

import controller.Controller;
import repository.entity.ResponseEntity;
import repository.entity.StationDataEntity;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DataProcessorService {
    private String serverHost;
    private int serverPort;
    private StationDataEntity processorEntity = new StationDataEntity();
    private final Controller controller = new Controller();

    public DataProcessorService(String serverHost, int serverPort) throws IOException {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(5001)) {
            System.out.println("Сервер запущен, ожидаем подключения...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    System.out.println("Клиент подключен.");

                    // Принимаем объект от клиента
                    StationDataEntity stationDataEntity = (StationDataEntity) in.readObject();
                    ResponseEntity responseEntity = controller.process(stationDataEntity);
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


    public static void main(String[] args) throws IOException {
        DataProcessorService serviceA = new DataProcessorService("localhost", 12345);
        while (true) {
            serviceA.start();
        }
    }
}