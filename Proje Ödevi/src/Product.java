/**
 * Product sınıfı - Kalıtım ve Interface kullanımı
 * - KALITIM: Veri abstract class'ından extends ediyor
 * - INTERFACE: SatinAlinabilir interface'ini implement ediyor
 * - POLYMORPHISM: Method overloading ile
 */
public class Product extends Veri implements SatinAlinabilir {
    private String kod;
    private String marka;
    private String kategori;
    private String model;
    private int numara;
    private int stok;
    private double fiyat;
    private String aciklama;

    public Product(String kod, String marka, String kategori, String model, 
                   int numara, int stok, double fiyat, String aciklama) {
        super(kod); // Parent constructor çağrısı - KALITIM
        this.kod = kod;
        this.marka = marka;
        this.kategori = kategori;
        this.model = model;
        this.numara = numara;
        this.stok = stok;
        this.fiyat = fiyat;
        this.aciklama = aciklama;
    }

    
    public String getKod() { return kod; }
    public String getMarka() { return marka; }
    public String getKategori() { return kategori; }
    public String getModel() { return model; }
    public int getNumara() { return numara; }
    public int getStok() { return stok; }
    public double getFiyat() { return fiyat; }
    public String getAciklama() { return aciklama; }

    
    public void setStok(int stok) { this.stok = stok; }

    
    public String toString() {
        return kod + "," + marka + "," + kategori + "," + model + "," + 
               numara + "," + stok + "," + fiyat + "," + aciklama;
    }
    
    // ==========================================
    // ABSTRACT METODLARIN IMPLEMENTASYONU (Veri'den)
    // ==========================================
    
    @Override
    public void kaydet() {
        System.out.println("Ürün kaydedildi: " + model);
    }
    
    @Override
    public void goster() {
        System.out.println("Ürün: " + marka + " " + model + " - " + fiyat + " TL");
    }
    
    // ==========================================
    // INTERFACE METODLARININ IMPLEMENTASYONU (SatinAlinabilir)
    // ==========================================
    
    @Override
    public double fiyatGetir() {
        return fiyat;
    }
    
    @Override
    public boolean stokVarMi() {
        return stok > 0;
    }
    
    @Override
    public boolean satinAl(int adet) {
        if (adet > stok) {
            return false;
        }
        stok -= adet;
        return true;
    }
    
    // ==========================================
    // POLYMORPHISM - METHOD OVERLOADING
    // ==========================================
    
    // Basit bilgi göster
    public void bilgiGoster() {
        System.out.println(marka + " " + model + " - " + numara + " numara");
    }
    
    // Detaylı bilgi göster - OVERLOADING
    public void bilgiGoster(boolean detayli) {
        bilgiGoster();
        if (detayli) {
            System.out.println("Fiyat: " + fiyat + " TL");
            System.out.println("Stok: " + stok);
            System.out.println("Kategori: " + kategori);
        }
    }
}