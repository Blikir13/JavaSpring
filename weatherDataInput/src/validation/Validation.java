package validation;

import dto.StationDataDto;


public class Validation {
    public void firstValidation(StationDataDto stationDataDto) {
        if (stationDataDto.getStationNumber() <= 0 || stationDataDto.getStationNumber() > 8) {
            throw new IllegalArgumentException("Номер станции должен лежать в диапазоне от 0 до 10");
        }
        if (stationDataDto.getWindSpeed() <= 0) {
            throw new IllegalArgumentException("Скорость ветра не может быть отрицательной");
        }
        if (stationDataDto.getPressure() <= 0) {
            throw new IllegalArgumentException("Давление не может быть отрицательным");
        }
    }
}
