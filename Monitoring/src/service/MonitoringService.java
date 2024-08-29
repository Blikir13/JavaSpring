package service;

import repository.entity.MonitoringEntity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class MonitoringService {
    private final int serverPort;

    public MonitoringService() throws IOException {
        this.serverPort = 5002;
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
                    MonitoringEntity monitoringEntity = (MonitoringEntity) in.readObject();
                    System.out.println("Принят объект: " + monitoringEntity.getStatus());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}