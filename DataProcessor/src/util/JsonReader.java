package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import repository.entity.StationDataJsonEntity;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: change names, аккуратно посмотреть запись
public class JsonReader {
    private Map<String, Map<String, Double>> data;
    private Map<String, String> data1;
    private static final Pattern FILE_PATTERN = Pattern.compile("(\\d+)\\.json");

    // Конструктор загружает данные из JSON файла
    public JsonReader(String filename, String filename1) throws IOException {
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            data = gson.fromJson(reader, Map.class);
        }
        try (FileReader reader = new FileReader(filename1)) {
            Gson gson = new Gson();
            data1 = gson.fromJson(reader, Map.class);
        }
    }

    public JsonReader() {
    }

    // Метод для получения минимальной температуры для города
    public double getMinTemperature(String city) {
        if (data.containsKey(city)) {
            return data.get(city).get("min");
        } else {
            throw new IllegalArgumentException("Данные для города " + city + " отсутствуют.");
        }
    }

    // Метод для получения максимальной температуры для города
    public double getMaxTemperature(String city) {
        if (data.containsKey(city)) {
            return data.get(city).get("max");
        } else {
            throw new IllegalArgumentException("Данные для города " + city + " отсутствуют.");
        }
    }

    public void writeJson(StationDataJsonEntity stationDataJsonEntity) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        stationDataJsonEntity.setId(getNextFileNumber("."));
        System.out.println("new id: " + getNextFileNumber("."));
        String path = getNextFileNumber(".") + ".json";
        File file = new File(path);
        file.createNewFile();
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(stationDataJsonEntity, writer);
            System.out.println("Данные успешно записаны в файл weatherData.json");
        } catch (IOException e) {
            e.printStackTrace();
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
