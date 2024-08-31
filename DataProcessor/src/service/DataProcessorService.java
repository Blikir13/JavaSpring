package service;

import Request.CreateEntity;
import config.Config;
import controller.Controller;
import dto.Request.DeleteEntity;
import dto.Request.TransferableObject;
import dto.Request.UpdateEntity;
import repository.entity.Response.ResponseEntity;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DataProcessorService {
    private final int serverPort;
    private CreateEntity processorEntity = new CreateEntity();
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
                    ResponseEntity responseEntity = new ResponseEntity();
                    // Принимаем объект от клиента
                    TransferableObject readObject = (TransferableObject) in.readObject();
                    if (readObject instanceof CreateEntity) {
                        CreateEntity createEntity = (CreateEntity) readObject;
                        responseEntity = controller.process(createEntity, config);
                        System.out.println("Принят объект: " + createEntity);

                    }
                    if (readObject instanceof DeleteEntity) {
                        DeleteEntity deleteEntity = (DeleteEntity) readObject;
                        responseEntity = controller.delete(deleteEntity.getPath());
                        System.out.println("Принят объект: " + deleteEntity);
                    }
                    if (readObject instanceof UpdateEntity) {
                        UpdateEntity stationDataEntity = (UpdateEntity) readObject;
                        responseEntity = controller.update(stationDataEntity, config);
                        System.out.println("Принят объект: " + stationDataEntity);
                    }
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