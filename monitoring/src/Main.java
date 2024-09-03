import transport.client.DataReceiverClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DataReceiverClient dataReceiverClient = new DataReceiverClient();
        dataReceiverClient.sendRequest();
    }
}