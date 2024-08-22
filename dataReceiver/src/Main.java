import console.Console;
import service.DataReceiverService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DataReceiverService serviceB = new DataReceiverService(12345);
        while (true) {
            serviceB.start();
        }
    }
}