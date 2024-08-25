package repository.impl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import dto.StationDataDto;
import repository.Repository;
import repository.entity.StationDataCsvEntity;
import util.CsvLoader;

//TODO: совместить с CVL loader
public class RepositoryStationCsv implements Repository {
    private final CsvLoader csvLoader;

    public RepositoryStationCsv(String filePath) {
        this.csvLoader = new CsvLoader(filePath);
    }

    @Override
    public void write(StationDataCsvEntity stationDataCsvEntity) throws IOException {
        List<StationDataCsvEntity> csvEntities = csvLoader.loadStationData();
        stationDataCsvEntity.setId(csvEntities.size()+1);
        csvLoader.write(stationDataCsvEntity);

    }

    public void update(String path) throws IOException {
        List<StationDataCsvEntity> csvEntities = csvLoader.loadStationData();
        StationDataCsvEntity last = csvEntities.get(csvEntities.size() - 1);
        last.setFileName(path);
        csvLoader.reWrite(csvEntities);
    }
}
