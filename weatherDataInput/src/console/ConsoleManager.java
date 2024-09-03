package console;

import config.Config;
import dto.StationDataDto;
import repository.entity.WeatherInputCsvEntity;
import service.WeatherInputService;

import java.util.Locale;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleManager {
    private final Config config = new Config();
    private final WeatherInputService dataReceiverService = new WeatherInputService(config);
    private final Console console = new Console();
    private final Logger logger = Logger.getLogger(ConsoleManager.class.getName());

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

    public void scanCommand() {
        boolean flag = true;
        while (flag) {
            try {
                String scannedCommand = this.scan();
                Commands command = Commands.valueOf(scannedCommand);
                if (!isKnownCommand(scannedCommand)) {
                    logger.log(Level.INFO, ConsolePrompt.UNKNOWN_COMMAND.getPrompt());
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
                        break;
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    private void createRecord() throws IOException {
        StationDataDto stationDataDto = console.inputFromConsole();
        dataReceiverService.createRequest(stationDataDto);
    }

    private void readRecords() throws IOException {
        List<WeatherInputCsvEntity> readRecords = dataReceiverService.read();
        System.out.println(ConsolePrompt.STATION_INFO.getPrompt());
        for (WeatherInputCsvEntity record : readRecords) {
            System.out.println(record);
        }
    }

    private void deleteRecord() throws IOException {
        int id = console.getNumberInput(ConsolePrompt.ENTER_ID_DELETE.getPrompt(), Integer.class);
        dataReceiverService.deleteRecordRequest(id);
    }

    private void updateRecord() throws IOException {
        int id = console.getNumberInput(ConsolePrompt.ENTER_ID_UPDATE.getPrompt(), Integer.class);
        StationDataDto stationDataDto = console.inputFromConsole();

        dataReceiverService.updateRequest(stationDataDto, id);

    }

}