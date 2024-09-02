import service.DataProcessorService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DataProcessorService serviceA = new DataProcessorService();
        serviceA.start();
    }
}