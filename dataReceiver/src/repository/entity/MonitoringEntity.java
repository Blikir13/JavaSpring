package repository.entity;

import repository.entity.Request.TransferableObject;

import java.io.Serial;

public class MonitoringEntity extends TransferableObject {
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
