package util;

import repository.entity.StationDataCsvEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {
    private static final String csvHeader = "id,station_number,timestamp,file_name";
    private static final String csvFile = "stationData.csv";
    private static final String csvSeparator = ",";
    private final String filePath;


    public CsvLoader(String filePath) {
        this.filePath = filePath;
    }

    private void createFileIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(csvHeader); //FIXME var? <3
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createFile(File file) throws IOException {
        file.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(csvHeader); //FIXME var? <3
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

    private List<StationDataCsvEntity> getLinesFromFile(BufferedReader bufferedReader) throws IOException {
        String header =  bufferedReader.readLine(); //FIXME definition of read header?
        String line;
        List<StationDataCsvEntity> csvEntities = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) { //FIXME definition of read dataLine?
            String[] values = line.split(csvSeparator);

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

        return csvEntities;
    }

    public List<StationDataCsvEntity> loadStationData() { //FIXME delete throws? <3
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<StationDataCsvEntity> csvEntities = null;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {//FIXME to method? <3
            csvEntities = getLinesFromFile(br);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return csvEntities;
    }
}
