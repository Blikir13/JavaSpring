package validation;

import dto.StationDataDto;

import java.util.ArrayList;
import java.util.Arrays;

public class Validation {
    public void firstValidation(StationDataDto stationDataDto) {
        if (stationDataDto.getStationNumber() <= 0 || stationDataDto.getStationNumber() > 8) {
            throw new IllegalArgumentException("some");
        }
        if (stationDataDto.getWindSpeed() <= 0) {
            throw new IllegalArgumentException("someText");
        }
        if (stationDataDto.getPressure() <= 0) {
            throw new IllegalArgumentException("someText");
        }


    }

//    private void checkWindDirection(String windDirection) {
//        ArrayList<String> windDirections = new ArrayList<>(Arrays.asList("N", "S", "W", "E", "SW", "SE", "NW", "NE"));
//        if (!windDirections.contains(windDirection)) {
//            throw new IllegalArgumentException("some");
//        }
//    }
}
