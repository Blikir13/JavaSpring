package repository;

import repository.entity.StationDataCsvEntity;

import java.io.IOException;

public interface Repository <E extends StationDataCsvEntity> {
    void write(E entity) throws IOException;
}
