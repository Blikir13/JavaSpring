package validation;

import dto.StationDataDto;

import java.util.ArrayList;
import java.util.Arrays;

public class Validation {
    public void firstValidation(StationDataDto stationDataDto) {
        if (stationDataDto.getStationNumber() <= 0 || stationDataDto.getStationNumber() > 8) {
            //TODO: add text
            throw new IllegalArgumentException("some");
        }
        if (stationDataDto.getWindSpeed() <= 0) {
            throw new IllegalArgumentException("someText");
        }
        if (stationDataDto.getPressure() <= 0) {
            throw new IllegalArgumentException("someText");
        }
    }
}
