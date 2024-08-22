package util;

import repository.entity.StationDataCsvEntity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvLoader {
    private final String filePath;
    ;

    public CsvLoader(String filePath) {
        this.filePath = filePath;
    }

    public void saveGuests(StationDataCsvEntity stationDataCsvEntity) throws IOException {
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            if (!file.exists()) {
                file.createNewFile();
                writer.write("id, station_number, timestamp, file_name");
            }
            writer.write(stationDataCsvEntity.toCSV());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
