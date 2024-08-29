package mapper;

import repository.entity.Request.CreateEntity;
import repository.entity.StationDataJsonEntity;
import repository.entity.Request.UpdateEntity;

public class StationDataMapper {
    public StationDataJsonEntity toStationDataJsonEntity(CreateEntity createEntity) {
        StationDataJsonEntity stationDataJsonEntity = new StationDataJsonEntity();
        stationDataJsonEntity.setStationNumber(createEntity.getStationNumber());
        stationDataJsonEntity.setCity(createEntity.getCity());
        stationDataJsonEntity.setPressure(createEntity.getPressure());
        stationDataJsonEntity.setTemperature(createEntity.getTemperature());
        stationDataJsonEntity.setWindDirection(createEntity.getWindDirection());
        stationDataJsonEntity.setWindSpeed(createEntity.getWindSpeed());
        return stationDataJsonEntity;
    }

    public CreateEntity toStationDataEntity(UpdateEntity updateEntity) {
        CreateEntity createEntity = new CreateEntity();
        createEntity.setStationNumber(updateEntity.getStationNumber());
        createEntity.setCity(updateEntity.getCity());
        createEntity.setPressure(updateEntity.getPressure());
        createEntity.setTemperature(updateEntity.getTemperature());
        createEntity.setWindDirection(updateEntity.getWindDirection());
        createEntity.setWindSpeed(updateEntity.getWindSpeed());
        return createEntity;
    }
}
