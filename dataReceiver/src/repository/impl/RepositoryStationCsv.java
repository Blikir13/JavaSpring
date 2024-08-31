package repository.impl;

//FIXME not imports? <3
import java.io.IOException;
import java.util.List;
import repository.Repository;
import repository.entity.StationDataCsvEntity;
import util.CsvLoader;

//TODO: совместить с CVL loader <3
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

    public List<StationDataCsvEntity> read() throws IOException {
        return csvLoader.loadStationData();
    }

    public String deleteRecord(int id) throws IOException {
        List<StationDataCsvEntity> csvEntities = csvLoader.loadStationData();
        String path = "";
        for (int i = csvEntities.size() - 1; i >= 0; i--) {
            StationDataCsvEntity stationDataCsvEntity = csvEntities.get(i);
            if (stationDataCsvEntity.getId() == id) {
                path = stationDataCsvEntity.getFileName() != null ? stationDataCsvEntity.getFileName() : path;
                csvEntities.remove(i);
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
                path = stationDataCsvEntity.getFileName() != null ? stationDataCsvEntity.getFileName() : path;
                // TODO: возмонжо стоит обновлять номер станции после 2 валидации? К
                stationDataCsvEntity.setStationNumber(stationNumber);
            }
        }
        csvLoader.reWrite(csvEntities);
        return path;
    }

}
