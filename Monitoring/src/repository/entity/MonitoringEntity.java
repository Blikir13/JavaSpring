package repository.entity;

import java.io.Serial;
import java.io.Serializable;

public class MonitoringEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String status;
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
}
