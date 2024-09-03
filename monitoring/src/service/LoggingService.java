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

    private byte[] createHeader() {
        // Заголовок: [created, mistake, success]
        byte[] header = new byte[7];
        header[0] = (byte) (System.currentTimeMillis() >> 24);  // Пример создания заголовка
        header[1] = 0;  // mistake (0 - нет ошибки, 1 - ошибка)
        header[2] = 1;  // success (0 - неудача, 1 - успех)
        return header;
    }
}