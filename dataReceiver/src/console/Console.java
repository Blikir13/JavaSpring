package console;

import config.Config;
import dto.StationDataDto;
import repository.entity.StationDataCsvEntity;
import service.DataReceiverService;

import java.util.Locale;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Console {
    private final Scanner scanner;
    //TODO: "stationData.csv вынести в проперти
    private final Config config = new Config();
    private final DataReceiverService dataReceiverService = new DataReceiverService(config);

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

    private StationDataDto inputFromConsole() {
        StationDataDto stationDataDto = new StationDataDto();
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
        return stationDataDto;
    }

    private String scan() {
        return scanner.nextLine().toUpperCase(Locale.ROOT);
    }

    public void scanCommand() {
        boolean flag = true;
        while (flag) { //FIXME need a flag?
            try {
                String scannedCommand = this.scan();
                Commands command = Commands.valueOf(scannedCommand); //FIXME error? <3 перенес в try
                if (!isKnownCommand(scannedCommand)) {  //FIXME never usage?
                    System.out.println("Вы ввели неизвестную команду. Введите help, чтобы узнать о командах");
                }
                switch (command) {
                    case HELP:
                        command.printHelp();
                        break;
                    case CREATE:
                        createRecord();
                        break;
                    case READ:
                        readRecords();
                        break;
                    case DELETE:
                        deleteRecord();
                        break;
                    case UPDATE:
                        updateRecord();
                        break;
                    case EXIT:
                        flag = false;
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


    //FIXME public? <3
    private void createRecord() throws IOException {
        StationDataDto stationDataDto = inputFromConsole();
        dataReceiverService.createRequest(stationDataDto);


    }
    //FIXME public? <3
    private void readRecords() throws IOException {
        List<StationDataCsvEntity> readRecords = dataReceiverService.read();
        System.out.println("Доступные записи по станциям:");
        for (StationDataCsvEntity record : readRecords) {
            System.out.println(record);
        }
    }
    //FIXME public? <3
    private void deleteRecord() throws IOException {
        int id = getIntInput("Input id to delete: ");
        dataReceiverService.deleteRecordRequest(id);
    }
    //FIXME public? <3
    private void updateRecord() throws IOException {
        int id = getIntInput("Input id to update: ");
        StationDataDto stationDataDto = inputFromConsole();

        dataReceiverService.updateRequest(stationDataDto, id);

    }
    //FIXME public? <3
    // Закрытие сканера
    private void close() {
        scanner.close();
    }
}