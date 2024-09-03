package repository.impl;

import config.Config;
import repository.entity.MonitoringDto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFileRepository {
    private final String logDirectory;
    private final long maxFileSize;
    private final int rotationIntervalHours;
    private static final byte[] delimiter = new byte[] { (byte) '\n' };

    private File currentFile;
    private FileOutputStream outputStream;

    public LogFileRepository(Config config) throws IOException {
        this.logDirectory = config.getLogDirectory();
        this.maxFileSize = config.getMaxFileSize();
        this.rotationIntervalHours = config.getRotationIntervalHours();

        Files.createDirectories(Paths.get(logDirectory));
        rotateFileIfNeeded();
    }

    public synchronized void writeLogEntry(MonitoringDto monitoringEntity) {
        try {
            rotateFileIfNeeded();

            outputStream.write(monitoringEntity.getStatus().getBytes());
            outputStream.write(monitoringEntity.toString().getBytes());
            outputStream.write(delimiter);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void rotateFileIfNeeded() throws IOException {
        if (currentFile == null || isRotationNeeded()) {
            if (outputStream != null) {
                outputStream.close();
            }
            currentFile = createNewLogFile();
            outputStream = new FileOutputStream(currentFile);
        }
    }

    private boolean isRotationNeeded() {
        return currentFile.length() > maxFileSize || isTimeToRotate();
    }

    private boolean isTimeToRotate() {
        LocalDateTime now = LocalDateTime.now();
        String fileName = currentFile.getName();
        String timestamp = fileName.substring(fileName.lastIndexOf('_') + 1, fileName.indexOf('.'));
        LocalDateTime fileDateTime = LocalDateTime.parse(timestamp, DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return now.isAfter(fileDateTime.plusHours(rotationIntervalHours));
    }

    private File createNewLogFile() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String fileName = String.format("logfile_%s.bin", timestamp);
        return new File(logDirectory, fileName);
    }
}
