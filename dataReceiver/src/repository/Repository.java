package repository;

import dto.StationDataDto;
import repository.entity.StationDataCsvEntity;

import java.io.IOException;

public interface Repository <E extends StationDataCsvEntity> {
    void write(E entity) throws IOException;
}
