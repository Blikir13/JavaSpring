import console.Console;
import dto.StationDataDto;
import map.StationDataMap;
import repository.entity.StationDataCsvEntity;
import repository.impl.WriteStationDataCsv;
import validation.Validation;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        StationDataMap stationDataMap = new StationDataMap();
        Validation validation = new Validation();
        WriteStationDataCsv writeStationDataCsv = new WriteStationDataCsv("/Users/mac/IdeaProjects/Practice/dataReceiver/stationData.csv");


//        int stationNumber = console.getIntInput("Input station number: ");
//        String city = console.getStringInput("input city: ");
//        double temperature = console.getDoubleInput("Input temperature: ");
//        double pressure = console.getDoubleInput("Input pressure: ");
//        double windSpeed = console.getDoubleInput("Input wind speed: ");
//        String windDirection = console.getStringInput("input wind direction: ");
        while (true) {
            try {
                StationDataDto stationDataDto = stationDataMap.toStationDataDto(console);
                validation.firstValidation(stationDataDto);
                StationDataCsvEntity stationDataCsvEntity = stationDataMap.toStationDataCsvEntity(stationDataDto);
                writeStationDataCsv.writeEntityCsv(stationDataCsvEntity);
            } catch (IllegalArgumentException ignored) {
                console.printException(ignored.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}