package console;

import dto.StationDataDto;
import mapper.StationDataMapper;
import repository.entity.StationDataCsvEntity;
import repository.impl.RepositoryStationCsv;
import service.DataReceiverService;
import validation.Validation;

import java.util.Locale;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Console {
    private final Scanner scanner;
    //TODO: "stationData.csv вынести в проперти
    private final RepositoryStationCsv repositoryStationCsv = new RepositoryStationCsv("stationData.csv");
    private final Validation validation = new Validation();
    private final StationDataMapper stationDataMapper = new StationDataMapper();
    private final DataReceiverService dataReceiverService = new DataReceiverService();

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    public String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Пожалуйста, введите целое число.");
            scanner.next(); // Пропускаем некорректный ввод
        }
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    //TODO: дублирование кода, один метод на операцию, различие только в scanner.hasNextDouble(); -- Обсудить с Андреем как стандартизировать
    public double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("Пожалуйста, введите число.");
            scanner.next(); // Пропускаем некорректный ввод
        }
        double result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }


    private boolean isKnownCommand(String command) {
        for (Commands commands : Commands.values()) {
            if (commands.name().equalsIgnoreCase(command)) {
                return true;
            }
        }
        return false;
    }

    private String scan() {
        return scanner.nextLine().toUpperCase(Locale.ROOT);
    }

    public void scanCommand() {
        while (true) {
            StationDataDto stationDataDto = new StationDataDto();
            String scannedCommand = this.scan();
            Commands command = Commands.valueOf(scannedCommand);
            try {
                if (!isKnownCommand(scannedCommand)) {
                    System.out.println("Вы ввели неизвестную команду. Попробуйте еще раз");
                    scannedCommand = this.scan();
                }
                switch (command) {
                    case HELP:
                        command.printHelp();
                        break;
                    case CREATE:
                        createRecord(stationDataDto);
                        break;
                    case READ:
                        readRecords();
                        break;
                    case DELETE:
                        deleteRecord();
                        break;
                    case UPDATE:
                        updateRecord(stationDataDto);
                        break;
                    case EXIT:
                        System.exit(0);
                    default:
                        System.out.println("Unknown command");
                        break;
                }
            } catch (IOException e) {
                //TODO: логгирировать
                throw new RuntimeException(e);
            } catch (Exception exception) {
                //пример
            }
        }
    }

    public void createRecord(StationDataDto stationDataDto) throws IOException {
        int stationNumber = getIntInput("Input station number: ");
        stationDataDto.setStationNumber(stationNumber);
        String city = getStringInput("input city: ");
        stationDataDto.setCity(city);
        double temperature = getDoubleInput("Input temperature: ");
        stationDataDto.setTemperature(temperature);
        double pressure = getDoubleInput("Input pressure: ");
        stationDataDto.setPressure(pressure);
        double windSpeed = getDoubleInput("Input wind speed: ");
        stationDataDto.setWindSpeed(windSpeed);
        String windDirection = getStringInput("input wind direction: ");
        stationDataDto.setWindDirection(windDirection);
        try {
            validation.firstValidation(stationDataDto);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверные данные: " + e.getMessage());
        }
        StationDataCsvEntity stationDataCsvEntity = stationDataMapper.toStationDataCsvEntity(stationDataDto);

        repositoryStationCsv.write(stationDataCsvEntity);

        // возвращает путь созданного json файла
        String path = dataReceiverService.createRequest(stationDataDto);
        // TODO: po idee ne mozhet bit' ""
        if (!Objects.equals(path, "")) {
            repositoryStationCsv.update(path);
        }

    }

    public void readRecords() throws IOException {
        List<String> readRecords = repositoryStationCsv.read();
        System.out.println("Доступные записи по станциям:");
        for (String record : readRecords) {
            System.out.println(record);
        }
    }

    public void deleteRecord() throws IOException {
        int id = getIntInput("Input id to delete: ");
        String path = repositoryStationCsv.deleteRecord(id);
        System.out.println("path " + path);
        if (!Objects.equals(path, "")) {
            dataReceiverService.deleteRecordRequest(path);
        }
    }

    // TODO: check parameter
    public void updateRecord(StationDataDto stationDataDto) throws IOException {
        int id = getIntInput("Input id to update: ");

        int stationNumber = getIntInput("Input station number: ");
        stationDataDto.setStationNumber(stationNumber);
        String city = getStringInput("input city: ");
        stationDataDto.setCity(city);
        double temperature = getDoubleInput("Input temperature: ");
        stationDataDto.setTemperature(temperature);
        double pressure = getDoubleInput("Input pressure: ");
        stationDataDto.setPressure(pressure);
        double windSpeed = getDoubleInput("Input wind speed: ");
        stationDataDto.setWindSpeed(windSpeed);
        String windDirection = getStringInput("input wind direction: ");
        stationDataDto.setWindDirection(windDirection);
        try {
            validation.firstValidation(stationDataDto);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверные данные: " + e.getMessage());
        }

        String path = repositoryStationCsv.updateRecord(id, stationNumber);

        if (!Objects.equals(path, "")) {
            dataReceiverService.updateRequest(stationDataDto, path);
        }

    }

    // Закрытие сканера
    public void close() {
        scanner.close();
    }
}