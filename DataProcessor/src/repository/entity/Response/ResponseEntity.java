package repository.entity.Response;

import java.io.Serializable;

public class ResponseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String errorMessage;

    public ResponseEntity(String id, String errorMessage) {
        this.id = id;
        this.errorMessage = errorMessage;
    }

    public ResponseEntity() {

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString() {
        return "id: " + id +  ", error message: " + errorMessage;
    }
}
