package service;

import repository.entity.StationDataEntity;

import java.io.*;
import java.net.Socket;

public class DataProcessorService {
    private String serverHost;
    private int serverPort;
    private StationDataEntity processorEntity = new StationDataEntity();

    public DataProcessorService(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void start() {
        try (Socket socket = new Socket(serverHost, serverPort)) {


            System.out.println("Connected to ServiceB");


            try {

                byte[] byteArray = socket.getInputStream().readAllBytes();  // Чтение всех байт

                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                StationDataEntity responseObject = (StationDataEntity) objectInputStream.readObject();

                System.out.println("Received from ServiceB: " + responseObject.getCity());


            } catch (EOFException eof) {
                System.out.println("End of stream reached. Waiting for new data...");
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        DataProcessorService serviceA = new DataProcessorService("localhost", 12345);
        while (true) {
            serviceA.start();
        }
    }
}