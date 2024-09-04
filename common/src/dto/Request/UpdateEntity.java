package dto.Request;


public class UpdateEntity extends CreateEntity { //FIXME extends Create Entity? <3
    private static final long serialVersionUID = 1L;
    private String path;

    public UpdateEntity() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
