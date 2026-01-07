
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
        super(kod); 
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
    

    public void kaydet() {
        System.out.println("Ürün kaydedildi: " + model);
    }
    

    public void goster() {
        System.out.println("Ürün: " + marka + " " + model + " - " + fiyat + " TL");
    }

    public double fiyatGetir() {
        return fiyat;
    }

    public boolean stokVarMi() {
        return stok > 0;
    }
    

    public boolean satinAl(int adet) {
        if (adet > stok) {
            return false;
        }
        stok -= adet;
        return true;
    }
    
   
    public void bilgiGoster() {
        System.out.println(marka + " " + model + " - " + numara + " numara");
    }
    

    public void bilgiGoster(boolean detayli) {
        bilgiGoster();
        if (detayli) {
            System.out.println("Fiyat: " + fiyat + " TL");
            System.out.println("Stok: " + stok);
            System.out.println("Kategori: " + kategori);
        }
    }

}
