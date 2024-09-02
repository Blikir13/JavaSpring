package transport.client;

import config.Config;
import dto.Request.CreateEntity;
import dto.Request.DeleteEntity;
import dto.Request.TransferableObject;
import dto.Request.UpdateEntity;
import repository.entity.Response.ResponseEntity;
import service.DataProcessorService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DataReceiverClient {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final int serverPort;
    private final Config config = new Config();
    public final DataProcessorService dataProcessorService = new DataProcessorService(config);


    public DataReceiverClient() throws IOException {
        this.serverPort = config.getPort();
    }

    private void connect() throws IOException {
        serverSocket = new ServerSocket(serverPort);
    }

    private void acceptRequest() throws IOException {
        clientSocket = serverSocket.accept();
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    private void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        clientSocket.close();
    }

    public void sendRequest() throws IOException { //FIXME in var?
        try {
            connect();

            while (true) {
                acceptRequest();
                System.out.println("Клиент подключен.");
                ResponseEntity responseEntity = new ResponseEntity();

                TransferableObject readObject = (TransferableObject) objectInputStream.readObject();
                if (readObject instanceof UpdateEntity stationDataEntity) {
                    responseEntity = dataProcessorService.update(stationDataEntity);
                    System.out.println("Принят объект: " + stationDataEntity);
                } else if (readObject instanceof CreateEntity createEntity) {
                    responseEntity = dataProcessorService.process(createEntity);
                    System.out.println("Принят объект: " + createEntity);
                } else if (readObject instanceof DeleteEntity deleteEntity) {
                    responseEntity = dataProcessorService.delete(deleteEntity.getPath());
                    System.out.println("Принят объект: " + deleteEntity);
                }
                objectOutputStream.writeObject(responseEntity);
                objectOutputStream.flush();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
    }
}
