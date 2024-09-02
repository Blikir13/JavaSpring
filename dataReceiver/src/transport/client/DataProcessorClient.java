package transport.client;

import config.Config;
import dto.Request.TransferableObject;
import repository.entity.Response.ResponseEntity;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class DataProcessorClient {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final int processorPort;
    private final int monitoringPort;
    private final String host;

    public DataProcessorClient(Config config) {
        this.processorPort = config.getProcessorPort();;
        this.monitoringPort = config.getMonitoringPort();
        this.host = config.getHost();
    }

    private void connect() throws IOException {
        Socket socket = new Socket(host, processorPort);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    private void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
    }

    public String sendRequest(TransferableObject transferableObject) throws IOException { //FIXME in var?
        try {
            connect();

            // Отправляем объект на сервер
            objectOutputStream.writeObject(transferableObject);
            objectOutputStream.flush();

            // Получаем ответ от сервера
            ResponseEntity response = (ResponseEntity) objectInputStream.readObject(); //FIXME import?
            System.out.println("Ответ сервера: " + response.toString());

            if (Objects.equals(response.getId(), "")) {
                // TODO: log error
                System.out.println("error" + response.getErrorMessage());
            } else {
                //TODO: !!!!
                return response.getId();
            }
            disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
