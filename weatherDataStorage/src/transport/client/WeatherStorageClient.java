package transport.client;

import config.Config;
import dto.Request.CreateEntity;
import dto.Request.DeleteEntity;
import dto.Request.TransferableObject;
import dto.Request.UpdateEntity;
import repository.entity.Response.ResponseDto;
import service.WeatherStorageService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherStorageClient {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final int serverPort;
    private final Config config = new Config();
    private final WeatherStorageService dataProcessorService = new WeatherStorageService(config);
    private final Logger logger = Logger.getLogger(WeatherStorageClient.class.getName());


    public WeatherStorageClient() {
        this.serverPort = config.getPort();
    }

    private void connect() throws IOException {
        serverSocket = new ServerSocket(serverPort);
    }

    private void acceptRequest() {
        try {
            clientSocket = serverSocket.accept();
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void disconnect() {
        try {
            objectOutputStream.close();
            objectInputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void sendRequest() {
        try {
            connect();

            while (true) {
                acceptRequest();
                logger.log(Level.INFO, "Клиент подключен.");
                ResponseDto responseEntity = new ResponseDto();

                TransferableObject readObject = (TransferableObject) objectInputStream.readObject();
                if (readObject instanceof UpdateEntity) {
                    UpdateEntity stationDataEntity = (UpdateEntity) readObject;
                    responseEntity = dataProcessorService.update(stationDataEntity);
                    logger.log(Level.INFO, "Принят объект: " + stationDataEntity);
                } else if (readObject instanceof CreateEntity) {
                    CreateEntity createEntity = (CreateEntity) readObject;
                    responseEntity = dataProcessorService.process(createEntity);
                    logger.log(Level.INFO, "Принят объект: " + createEntity);
                } else if (readObject instanceof DeleteEntity) {
                    DeleteEntity deleteEntity = (DeleteEntity) readObject;
                    responseEntity = dataProcessorService.delete(deleteEntity.getPath());
                    logger.log(Level.INFO, "Принят объект: " + deleteEntity);
                }
                objectOutputStream.writeObject(responseEntity);
                objectOutputStream.flush();

            }


        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        finally {
            disconnect();
        }
    }
}
