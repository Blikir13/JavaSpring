package repository.entity.Request;

import java.io.Serial;

public class UpdateEntity extends TransferableObject {
    @Serial
    private static final long serialVersionUID = 1L;
    private int stationNumber;
    private String city;
    private double temperature;
    private double pressure;
    private double windSpeed;
    private String windDirection;
    private String path;

    public UpdateEntity() {

    }

    public int getStationNumber() {
        return stationNumber;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setStationNumber(int stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }
}
