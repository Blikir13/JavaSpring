package util;

import repository.entity.StationDataCsvEntity;
import repository.entity.StationDataEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {
    private final String filePath;

    public CsvLoader(String filePath) {
        this.filePath = filePath;
    }

    private void createFileIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write("id,station_number,timestamp,file_name");
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createFile(File file) throws IOException {
        file.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("id,station_number,timestamp,file_name");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDataToFile(StationDataCsvEntity stationDataCsvEntity) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(stationDataCsvEntity.toCSV());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reWrite(List<StationDataCsvEntity> csvEntities) throws IOException {
        File file = new File(filePath);
        createFile(file);
        for (StationDataCsvEntity csvEntity : csvEntities) {
            writeDataToFile(csvEntity);
        }
    }

    public void write(StationDataCsvEntity stationDataCsvEntity) throws IOException {
        File file = new File(filePath);
        createFileIfNotExists(file);
        writeDataToFile(stationDataCsvEntity);
    }

    public List<StationDataCsvEntity> loadStationData() throws IOException {
        String line;
        String csvFile = "stationData.csv";
        List<StationDataCsvEntity> csvEntities = new ArrayList<>();
        String csvSplitBy = ",";

        File file = new File(filePath);
        if (!file.exists()) {
            return csvEntities;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(csvSplitBy);

                StationDataCsvEntity record = new StationDataCsvEntity();
                record.setId(Integer.parseInt(values[0]));
                record.setStationNumber(Integer.parseInt(values[1]));
                record.setTimestamp(values[2]);
                if (values.length == 4) {
                    record.setFileName(values[3]);
                } else {
                    record.setFileName("");
                }

                csvEntities.add(record);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvEntities;
    }
}
