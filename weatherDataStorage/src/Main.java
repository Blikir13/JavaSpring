import transport.client.WeatherStorageClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        WeatherStorageClient dataReceiverClient = new WeatherStorageClient();
        dataReceiverClient.sendRequest();
    }
}