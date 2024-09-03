package transport.client;

import config.Config;
import repository.entity.MonitoringDto;

import java.io.*;
import java.net.Socket;

public class MonitoringClient {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private final int monitoringPort;
    private final String host;

    public MonitoringClient(Config config) {
        this.monitoringPort = config.getMonitoringPort();;
        this.host = config.getHost();
    }

    private void connect() throws IOException {
        socket = new Socket(host, monitoringPort);
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    private void disconnect() throws IOException {
        objectOutputStream.close();
        socket.close();
    }

    public void sendRequest(MonitoringDto monitoringEntity) throws IOException { //FIXME in var?
        try {
            connect();

            objectOutputStream.writeObject(monitoringEntity);
            objectOutputStream.flush();

            disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
