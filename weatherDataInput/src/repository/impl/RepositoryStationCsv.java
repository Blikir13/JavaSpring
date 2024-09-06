package repository.impl;

import java.util.List;
import repository.Repository;
import repository.entity.WeatherInputCsvEntity;
import util.CsvLoader;

public class RepositoryStationCsv implements Repository {
    private final CsvLoader csvLoader;

    public RepositoryStationCsv(String filePath) {
        this.csvLoader = new CsvLoader(filePath);
    }

    @Override
    public void write(WeatherInputCsvEntity stationDataCsvEntity) {
        List<WeatherInputCsvEntity> csvEntities = csvLoader.loadStationData();
        if (csvEntities.isEmpty()) {
            stationDataCsvEntity.setId(1);
        } else {
            int newId = csvEntities.get(csvEntities.size()-1).getId();
            stationDataCsvEntity.setId(++newId);
        }
        csvLoader.write(stationDataCsvEntity);
    }

    public void updateWithId(String path, int id) {
        List<WeatherInputCsvEntity> csvEntities = csvLoader.loadStationData();
        for (int i = csvEntities.size() - 1; i >= 0; i--) {
            WeatherInputCsvEntity stationDataCsvEntity = csvEntities.get(i);
            if (stationDataCsvEntity.getId() == id) {
                stationDataCsvEntity.setFileName(path);
            }
        }
        csvLoader.reWrite(csvEntities);
    }

    public void update(String path) {
        List<WeatherInputCsvEntity> csvEntities = csvLoader.loadStationData();
        WeatherInputCsvEntity last = csvEntities.get(csvEntities.size() - 1);
        last.setFileName(path);
        csvLoader.reWrite(csvEntities);
    }

    public List<WeatherInputCsvEntity> read() {
        return csvLoader.loadStationData();
    }

    public String deleteRecord(int id) {
        List<WeatherInputCsvEntity> csvEntities = csvLoader.loadStationData();
        String path = "";
        for (int i = csvEntities.size() - 1; i >= 0; i--) {
            WeatherInputCsvEntity stationDataCsvEntity = csvEntities.get(i);
            if (stationDataCsvEntity.getId() == id) {
                path = stationDataCsvEntity.getFileName() != null ? stationDataCsvEntity.getFileName() : path;
                csvEntities.remove(i);
            }
        }
        csvLoader.reWrite(csvEntities);
        return path;
    }

    public String updateRecord(int id, int stationNumber) {
        List<WeatherInputCsvEntity> csvEntities = csvLoader.loadStationData();
        String path = "";
        for (int i = csvEntities.size() - 1; i >= 0; i--) {
            WeatherInputCsvEntity stationDataCsvEntity = csvEntities.get(i);
            if (stationDataCsvEntity.getId() == id) {
                path = stationDataCsvEntity.getFileName() != null ? stationDataCsvEntity.getFileName() : path;
                // TODO: возмонжо стоит обновлять номер станции после 2 валидации?
                stationDataCsvEntity.setStationNumber(stationNumber);
            }
        }
        csvLoader.reWrite(csvEntities);
        return path;
    }

}
