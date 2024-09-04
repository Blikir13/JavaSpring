package transport.client;

import config.Config;
import repository.entity.MonitoringDto;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonitoringClient {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private final int monitoringPort;
    private final String host;
    private final Logger logger = Logger.getLogger(MonitoringClient.class.getName());

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

    public void sendRequest(MonitoringDto monitoringEntity) {
        try {
            connect();

            objectOutputStream.writeObject(monitoringEntity);
            objectOutputStream.flush();

            disconnect();
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
