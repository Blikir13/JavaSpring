import service.MonitoringService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MonitoringService monitoringService = new MonitoringService();
        monitoringService.start();
    }
}