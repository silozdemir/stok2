import java.util.ArrayList;
import java.util.HashMap;

public class Sepet {
    private static HashMap<String, SepetItem> items = new HashMap<>();

    public static class SepetItem {
        private Product urun;
        private int adet;
        
        public SepetItem(Product urun, int adet) {
            this.urun = urun;
            this.adet = adet;
        }
        
        public Product getUrun() { return urun; }
        public int getAdet() { return adet; }
        public void setAdet(int adet) { this.adet = adet; }
        public double getToplamFiyat() { return urun.getFiyat() * adet; }
    }
    

    public static boolean urunEkle(Product urun, int adet) {
        String key = urun.getKod();

        if (adet > urun.getStok()) {
            return false;
        }
        
   
        if (items.containsKey(key)) {
            SepetItem mevcut = items.get(key);
            int yeniAdet = mevcut.getAdet() + adet;
            
            
            if (yeniAdet > urun.getStok()) {
                return false;
            }
            
            mevcut.setAdet(yeniAdet);
        } else {
            items.put(key, new SepetItem(urun, adet));
        }
        
        return true;
    }
    
  
    public static void urunCikar(String urunKodu) {
        items.remove(urunKodu);
    }
    

    public static boolean adetGuncelle(String urunKodu, int yeniAdet) {
        if (!items.containsKey(urunKodu)) {
            return false;
        }
        
        SepetItem item = items.get(urunKodu);
        
    
        if (yeniAdet > item.getUrun().getStok()) {
            return false;
        }
        
        if (yeniAdet <= 0) {
            urunCikar(urunKodu);
        } else {
            item.setAdet(yeniAdet);
        }
        
        return true;
    }
    

    public static ArrayList<SepetItem> tumUrunleriGetir() {
        return new ArrayList<>(items.values());
    }
    
  
    public static boolean bosMu() {
        return items.isEmpty();
    }
    

    public static int toplamUrunSayisi() {
        int toplam = 0;
        for (SepetItem item : items.values()) {
            toplam += item.getAdet();
        }
        return toplam;
    }
    
  
    public static double toplamTutar() {
        double toplam = 0;
        for (SepetItem item : items.values()) {
            toplam += item.getToplamFiyat();
        }
        return toplam;
    }
    

    public static void temizle() {
        items.clear();
    }
    
  
    private static String mevcutKullanici = "";
    
    public static void kullaniciAyarla(String kullaniciAdi) {
        if (!mevcutKullanici.equals(kullaniciAdi)) {
            temizle();
            mevcutKullanici = kullaniciAdi;
        }
    }

}

