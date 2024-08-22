import service.DataProcessorService;

public class Main {
    public static void main(String[] args) {
        DataProcessorService serviceA = new DataProcessorService("localhost", 12345);
        while (true) {
            serviceA.start();
        }
    }
}