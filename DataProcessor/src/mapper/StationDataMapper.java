package mapper;

import repository.entity.StationDataEntity;
import repository.entity.StationDataJsonEntity;

public class StationDataMapper {
    public StationDataJsonEntity toStationDataJsonEntity(StationDataEntity stationDataEntity) {
        StationDataJsonEntity stationDataJsonEntity = new StationDataJsonEntity();
        stationDataJsonEntity.setStationNumber(stationDataEntity.getStationNumber());
        stationDataJsonEntity.setCity(stationDataEntity.getCity());
        stationDataJsonEntity.setPressure(stationDataEntity.getPressure());
        stationDataJsonEntity.setTemperature(stationDataEntity.getTemperature());
        stationDataJsonEntity.setWindDirection(stationDataEntity.getWindDirection());
        stationDataJsonEntity.setWindSpeed(stationDataEntity.getWindSpeed());
        return stationDataJsonEntity;
    }
}
