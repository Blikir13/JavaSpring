package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import repository.entity.StationDataJsonEntity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonReader {
    private Map<String, Map<String, Double>> data;
    private Map<String, String> data1;
    private static final Pattern FILE_PATTERN = Pattern.compile("(\\d+)\\.json");
    private final Logger logger = Logger.getLogger(JsonReader.class.getName());
    private static final String min = "min";
    private static final String max = "max";
    private static final String postfix = ".json";
    private static final String slash = "/";

    // Конструктор загружает данные из JSON файла
    public JsonReader(String filenameTemp, String filenameCity) {
        try (FileReader readerTemp = new FileReader(filenameTemp);
             FileReader readerCity = new FileReader(filenameCity)) {
            Gson gsonTemp = new Gson();
            data = gsonTemp.fromJson(readerTemp, Map.class);
            Gson gsonCity = new Gson();
            data1 = gsonCity.fromJson(readerCity, Map.class);
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public JsonReader() {
    }

    public double getMinTemperature(String city) {
        if (data.containsKey(city)) {
            return data.get(city).get(min);
        } else {
            throw new IllegalArgumentException("Данные для города " + city + " отсутствуют.");
        }
    }

    public double getMaxTemperature(String city) {
        if (data.containsKey(city)) {
            return data.get(city).get(max);
        } else {
            throw new IllegalArgumentException("Данные для города " + city + " отсутствуют.");
        }
    }

    public void writeJson(StationDataJsonEntity stationDataJsonEntity, String resPath, String updatePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String path;
        if (Objects.equals(updatePath, "")) {
            stationDataJsonEntity.setId(getNextFileNumber(resPath));
            path = resPath + slash + getNextFileNumber(resPath) + postfix;
        } else {
            int dotIndex1 = updatePath.indexOf(postfix);
            int id = Integer.parseInt(updatePath.substring(0, dotIndex1));
            stationDataJsonEntity.setId(id);
            path = resPath + slash + updatePath;
        }

        try {
            File file = new File(path);
            file.createNewFile();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }

        try (FileWriter writer = new FileWriter(path);){
            gson.toJson(stationDataJsonEntity, writer);
            System.out.println("city"+ stationDataJsonEntity.getCity() + " " + path);
            logger.log(Level.INFO, "Данные успешно записаны в файл weatherData.json");
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public String getCityByStation(int stationNumber) {
        String strStationNumber = Integer.toString(stationNumber);
        if (data1.containsKey(strStationNumber)) {
            return data1.get(strStationNumber);
        } else {
            throw new IllegalArgumentException("Данные station number " + strStationNumber + " отсутствуют.");
        }
    }

    private static int getNextFileNumber(String directoryPath) {

        File directory = new File(directoryPath);
        File[] files = directory.listFiles((dir, name) -> FILE_PATTERN.matcher(name).matches());

        int maxNumber = 0;

        if (files != null) {
            for (File file : files) {
                Matcher matcher = FILE_PATTERN.matcher(file.getName());
                if (matcher.matches()) {
                    int number = Integer.parseInt(matcher.group(1));
                    if (number > maxNumber) {
                        maxNumber = number;
                    }
                }
            }
        }

        return maxNumber + 1;
    }
}
