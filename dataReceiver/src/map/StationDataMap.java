package map;

import console.Console;
import dto.StationDataDto;
import repository.entity.StationDataCsvEntity;

public class StationDataMap {
    public StationDataCsvEntity toStationDataCsvEntity(StationDataDto stationDataDto) {
        StationDataCsvEntity stationDataCsvEntity = new StationDataCsvEntity();
        stationDataCsvEntity.setId(1);
        stationDataCsvEntity.setStationNumber(stationDataDto.getStationNumber());
        stationDataCsvEntity.setTimestamp("");
        stationDataCsvEntity.setFileName("");
        return stationDataCsvEntity;
    }

    public StationDataDto toStationDataDto(Console console) {
        int stationNumber = console.getIntInput("Input station number: ");
        String city = console.getStringInput("input city: ");
        double temperature = console.getDoubleInput("Input temperature: ");
        double pressure = console.getDoubleInput("Input pressure: ");
        System.out.println(pressure);
        double windSpeed = console.getDoubleInput("Input wind speed: ");
        String windDirection = console.getStringInput("input wind direction: ");
        StationDataDto stationDataDto = new StationDataDto();
        stationDataDto.setStationNumber(stationNumber);
        stationDataDto.setCity(city);
        stationDataDto.setTemperature(temperature);
        stationDataDto.setPressure(pressure);
        stationDataDto.setWindSpeed(windSpeed);
        stationDataDto.setWindDirection(windDirection);
        return stationDataDto;
    }
}
