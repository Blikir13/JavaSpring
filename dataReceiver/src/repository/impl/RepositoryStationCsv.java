package repository.impl;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import dto.StationDataDto;
import repository.Repository;
import repository.entity.StationDataCsvEntity;

public class RepositoryStationCsv implements Repository {
    private final String filePath;

    public RepositoryStationCsv(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void write(StationDataCsvEntity stationDataCsvEntity) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write("id,station_number,timestamp,file_name");
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(stationDataCsvEntity.toCSV());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
