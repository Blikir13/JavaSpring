package repository.impl;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import repository.entity.StationDataCsvEntity;

public class WriteStationDataCsv {
    private String filePath;

    public WriteStationDataCsv(String filePath) {
        this.filePath = filePath;
    }

    public void writeEntityCsv(StationDataCsvEntity stationDataCsvEntity) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(stationDataCsvEntity.toCSV());
            writer.newLine();
        }
    }
}
