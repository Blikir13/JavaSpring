package console;

import config.Config;
import dto.StationDataDto;
import repository.entity.WeatherInputCsvEntity;
import service.WeatherInputService;

import java.util.Locale;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleManager {
    private final Config config = new Config();
    private final WeatherInputService dataReceiverService = new WeatherInputService(config);
    private final Console console = new Console();
    private final Logger logger = Logger.getLogger(ConsoleManager.class.getName());
    private boolean flag;

    public ConsoleManager() {
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
        return new Scanner(System.in).nextLine().toUpperCase(Locale.ROOT);
    }

    private void defineCommand(String scannedCommand) {
        Commands command = Commands.valueOf(scannedCommand);
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
                break;
        }
    }

    public void scanCommand() {
        flag = true;
        while (flag) {
            System.out.println(ConsolePrompt.INPUT_COMMAND.getPrompt());
            String scannedCommand = this.scan();
            if (!isKnownCommand(scannedCommand)) {
                logger.log(Level.INFO, ConsolePrompt.UNKNOWN_COMMAND.getPrompt());
            } else {
                defineCommand(scannedCommand);
            }
        }
    }

    private void createRecord() {
        StationDataDto stationDataDto = console.inputFromConsole();
        dataReceiverService.createRequest(stationDataDto);
    }

    private void readRecords() {
        List<WeatherInputCsvEntity> readRecords = dataReceiverService.read();
        System.out.println(ConsolePrompt.STATION_INFO.getPrompt());
        for (WeatherInputCsvEntity record : readRecords) {
            System.out.println(record);
        }
    }

    private void deleteRecord() {
        int id = console.getNumberInput(ConsolePrompt.ENTER_ID_DELETE.getPrompt(), Integer.class);
        if (dataReceiverService.checkHasId(id)) {
            logger.log(Level.INFO, ConsolePrompt.UNKNOWN_ID.getPrompt() + id);
            return;
        }
        dataReceiverService.deleteRecordRequest(id);
    }

    private void updateRecord() {
        int id = console.getNumberInput(ConsolePrompt.ENTER_ID_UPDATE.getPrompt(), Integer.class);
        if (dataReceiverService.checkHasId(id)) {
            logger.log(Level.INFO, ConsolePrompt.UNKNOWN_ID.getPrompt() + id);
            return;
        }
        StationDataDto stationDataDto = console.inputFromConsole();

        dataReceiverService.updateRequest(stationDataDto, id);

    }

}