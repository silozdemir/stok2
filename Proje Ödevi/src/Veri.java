
public abstract class Veri {
    protected String id;
    
    public Veri(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public abstract void kaydet();
    public abstract void goster();

}
