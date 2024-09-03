package console;

import dto.StationDataDto;

import java.util.Scanner;
import java.util.logging.Logger;

public class Console {
    private final Scanner scanner;
    private final Logger logger = Logger.getLogger(Console.class.getName());

    public Console() {
        this.scanner = new Scanner(System.in);
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public <T extends Number> T getNumberInput(String prompt, Class<T> clazz) {
        System.out.print(prompt);
        while (true) {
            if (clazz == Integer.class && scanner.hasNextInt()) {
                int result = scanner.nextInt();
                scanner.nextLine();
                return clazz.cast(result);
            } else if (clazz == Double.class && scanner.hasNextDouble()) {
                double result = scanner.nextDouble();
                scanner.nextLine();
                return clazz.cast(result);
            } else {
                logger.warning(ConsolePrompt.ENTER_NUMBER.getPrompt());
                scanner.next();
            }
        }
    }

    public StationDataDto inputFromConsole() {
        StationDataDto stationDataDto = new StationDataDto();
        int stationNumber = getNumberInput(ConsolePrompt.STATION_NUMBER.getPrompt(), Integer.class);
        stationDataDto.setStationNumber(stationNumber);
        String city = getStringInput(ConsolePrompt.CITY.getPrompt());
        stationDataDto.setCity(city);
        double temperature = getNumberInput(ConsolePrompt.TEMPERATURE.getPrompt(), Double.class);
        stationDataDto.setTemperature(temperature);
        double pressure = getNumberInput(ConsolePrompt.PRESSURE.getPrompt(), Double.class);
        stationDataDto.setPressure(pressure);
        double windSpeed = getNumberInput(ConsolePrompt.WIND_SPEED.getPrompt(), Double.class);
        stationDataDto.setWindSpeed(windSpeed);
        String windDirection = getStringInput(ConsolePrompt.WIND_DIRECTION.getPrompt());
        stationDataDto.setWindDirection(windDirection);
        return stationDataDto;
    }
}
