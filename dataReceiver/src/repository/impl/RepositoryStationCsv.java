package repository.impl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
        if (csvEntities.isEmpty()) {
            stationDataCsvEntity.setId(1);
        } else {
            int newId = csvEntities.get(csvEntities.size()-1).getId();
            stationDataCsvEntity.setId(++newId);
        }
        csvLoader.write(stationDataCsvEntity);
    }

    public void update(String path) throws IOException {
        List<StationDataCsvEntity> csvEntities = csvLoader.loadStationData();
        StationDataCsvEntity last = csvEntities.get(csvEntities.size() - 1);
        last.setFileName(path);
        csvLoader.reWrite(csvEntities);
    }

    public List<String> read() throws IOException {
        List<StationDataCsvEntity> csvEntities = csvLoader.loadStationData();
        List<String> result = new ArrayList<String>();;
        for (StationDataCsvEntity stationDataCsvEntity : csvEntities) {
            result.add(String.valueOf(stationDataCsvEntity));
        }
        return result;
    }

    public String deleteRecord(int id) throws IOException {
        List<StationDataCsvEntity> csvEntities = csvLoader.loadStationData();
        String path = "";
        for (int i = csvEntities.size() - 1; i >= 0; i--) {
            StationDataCsvEntity stationDataCsvEntity = csvEntities.get(i);
            if (stationDataCsvEntity.getId() == id) {
                if (stationDataCsvEntity.getFileName() != null) {
                    path = stationDataCsvEntity.getFileName();
                }
                csvEntities.remove(i); // Удаляем элемент по индексу
            }
        }
        csvLoader.reWrite(csvEntities);
        return path;
    }

    public String updateRecord(int id, int stationNumber) throws IOException {
        List<StationDataCsvEntity> csvEntities = csvLoader.loadStationData();
        String path = "";
        for (int i = csvEntities.size() - 1; i >= 0; i--) {
            StationDataCsvEntity stationDataCsvEntity = csvEntities.get(i);
            if (stationDataCsvEntity.getId() == id) {
                if (stationDataCsvEntity.getFileName() != null) {
                    path = stationDataCsvEntity.getFileName();
                }
                stationDataCsvEntity.setStationNumber(stationNumber);
            }
        }
        csvLoader.reWrite(csvEntities);
        return path;
    }

}
