/**
 * Abstract base class - Tüm veri sınıfları için
 */
public abstract class Veri {
    protected String id;
    
    public Veri(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    
    // Abstract metodlar - alt sınıflar implement etmek zorunda
    public abstract void kaydet();
    public abstract void goster();
}