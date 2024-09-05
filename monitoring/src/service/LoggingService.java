package service;

import config.Config;
import repository.entity.MonitoringDto;
import repository.impl.LogFileRepository;

import java.io.IOException;

public class LoggingService {
    private final LogFileRepository logFileRepository;

    public LoggingService(Config config) throws IOException {
        this.logFileRepository = new LogFileRepository(config);
    }

    public void log(MonitoringDto monitoringEntity) {

        // Записываем данные в лог-файл через репозиторий
        logFileRepository.writeLogEntry(monitoringEntity);
    }

}