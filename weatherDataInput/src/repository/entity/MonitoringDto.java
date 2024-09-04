package repository.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MonitoringDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String object;
    private String status;
    private final static String pattern = "yyyy-MM-dd HH:mm:ss";

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MonitoringDto(String object, String status) {
        this.object = object;
        this.status = status;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        this.date = LocalDateTime.now().format(formatter);;
    }

    public String toString() {
        return "[object=" + object + ", status=" + status + ", date=" + date + "]";
    }


}
