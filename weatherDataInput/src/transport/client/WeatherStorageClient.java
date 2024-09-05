package transport.client;

import config.Config;
import dto.Request.TransferableObject;
import repository.entity.Response.ResponseDto;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WeatherStorageClient {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final int processorPort;
    private final String host;
    private final Logger logger = Logger.getLogger(WeatherStorageClient.class.getName());

    public WeatherStorageClient(Config config) {
        this.processorPort = config.getProcessorPort();;
        this.host = config.getHost();
    }

    private void connect() throws IOException {
        socket = new Socket(host, processorPort);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    private void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
    }

    public String sendRequest(TransferableObject transferableObject) { //FIXME in var?
        try {
            connect();

            // Отправляем объект на сервер
            objectOutputStream.writeObject(transferableObject);
            objectOutputStream.flush();

            // Получаем ответ от сервера
            ResponseDto response = (ResponseDto) objectInputStream.readObject(); //FIXME import?
            logger.log(Level.INFO, "Ответ сервера: " + response.toString());

            if (Objects.equals(response.getId(), "")) {
                // TODO: log error
//                logger.warning("error" + response.getErrorMessage());
//                System.out.println("error" + response.getErrorMessage());
            } else {
                //TODO: !!!!
                return response.getId();
            }
            disconnect();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return "";
    }
}
