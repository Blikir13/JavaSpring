package console;

import dto.StationDataDto;
import mapper.StationDataMapper;
import repository.entity.StationDataCsvEntity;
import repository.impl.RepositoryStationCsv;
import validation.Validation;

import java.io.IOException;
import java.util.Scanner;

public class Console {
    private final Scanner scanner;
    //TODO: "stationData.csv вынести в проперти
    private final RepositoryStationCsv repositoryStationCsv = new RepositoryStationCsv("stationData.csv");
    private final Validation validation = new Validation();
    private final StationDataMapper stationDataMapper = new StationDataMapper();

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

//    public StationDataDto inputStationData() {
//        int stationNumber = getIntInput("Input station number: ");
//        String city = getStringInput("input city: ");
//        double temperature = getDoubleInput("Input temperature: ");
//        double pressure = getDoubleInput("Input pressure: ");
//        double windSpeed = getDoubleInput("Input wind speed: ");
//        String windDirection = getStringInput("input wind direction: ");
//    }

    public void printException(String exceptionMessage) {
        System.out.println("Ошибка: " + exceptionMessage);
    }

    //FIXME: метод с большой буквы
    public void enterDataStation(StationDataDto stationDataDto) throws IOException {
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
        try{
            validation.firstValidation(stationDataDto);
        } catch (IllegalArgumentException e) {
            System.out.println("Неверные данные: " + e.getMessage());
        }
        StationDataCsvEntity stationDataCsvEntity = stationDataMapper.toStationDataCsvEntity(stationDataDto);

        repositoryStationCsv.write(stationDataCsvEntity);

    }

    public void updateCsv(String path) throws IOException {
        repositoryStationCsv.update(path);
    }



    // Закрытие сканера
    public void close() {
        scanner.close();
    }
}