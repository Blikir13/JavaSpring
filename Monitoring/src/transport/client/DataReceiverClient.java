package transport.client;

import config.Config;
import repository.entity.MonitoringEntity;


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

                MonitoringEntity monitoringEntity = (MonitoringEntity) objectInputStream.readObject();


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            disconnect();
        }
    }
}
