package mapper;

import dto.StationDataDto;
import repository.entity.StationDataCsvEntity;
import repository.entity.Request.CreateEntity;
import repository.entity.Request.UpdateEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StationDataMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public StationDataCsvEntity toStationDataCsvEntity(StationDataDto stationDataDto) {
        StationDataCsvEntity stationDataCsvEntity = new StationDataCsvEntity();
        stationDataCsvEntity.setId(1);
        stationDataCsvEntity.setStationNumber(stationDataDto.getStationNumber());
        String now = LocalDateTime.now().format(formatter);;
        stationDataCsvEntity.setTimestamp(now);
        stationDataCsvEntity.setFileName("");
        return stationDataCsvEntity;
    }

    public CreateEntity toStationDataEntity(StationDataDto stationDataDto) {
        CreateEntity stationDataEntity = new CreateEntity();
        stationDataEntity.setStationNumber(stationDataDto.getStationNumber());
        stationDataEntity.setCity(stationDataDto.getCity());
        stationDataEntity.setPressure(stationDataDto.getPressure());
        stationDataEntity.setTemperature(stationDataDto.getTemperature());
        stationDataEntity.setWindSpeed(stationDataDto.getWindSpeed());
        stationDataEntity.setWindDirection(stationDataDto.getWindDirection());
        return stationDataEntity;
    }

    public UpdateEntity toUpdateEntity(StationDataDto stationDataDto, String path) {
        UpdateEntity updateEntity = new UpdateEntity();
        updateEntity.setStationNumber(stationDataDto.getStationNumber());
        updateEntity.setCity(stationDataDto.getCity());
        updateEntity.setPressure(stationDataDto.getPressure());
        updateEntity.setTemperature(stationDataDto.getTemperature());
        updateEntity.setWindSpeed(stationDataDto.getWindSpeed());
        updateEntity.setWindDirection(stationDataDto.getWindDirection());
        updateEntity.setPath(path);
        return updateEntity;
    }

}
