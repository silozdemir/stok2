import java.util.ArrayList;
import java.util.HashMap;

public class Sepet {
    private static HashMap<String, SepetItem> items = new HashMap<>();
    
    // Sepet öğesi sınıfı
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
    
    // Sepete ürün ekle
    public static boolean urunEkle(Product urun, int adet) {
        String key = urun.getKod();
        
        // Stok kontrolü
        if (adet > urun.getStok()) {
            return false;
        }
        
        // Zaten sepette varsa adet artır
        if (items.containsKey(key)) {
            SepetItem mevcut = items.get(key);
            int yeniAdet = mevcut.getAdet() + adet;
            
            // Stok kontrolü
            if (yeniAdet > urun.getStok()) {
                return false;
            }
            
            mevcut.setAdet(yeniAdet);
        } else {
            items.put(key, new SepetItem(urun, adet));
        }
        
        return true;
    }
    
    // Sepetten ürün çıkar
    public static void urunCikar(String urunKodu) {
        items.remove(urunKodu);
    }
    
    // Ürün adedini güncelle
    public static boolean adetGuncelle(String urunKodu, int yeniAdet) {
        if (!items.containsKey(urunKodu)) {
            return false;
        }
        
        SepetItem item = items.get(urunKodu);
        
        // Stok kontrolü
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
    
    // Sepetteki tüm ürünleri getir
    public static ArrayList<SepetItem> tumUrunleriGetir() {
        return new ArrayList<>(items.values());
    }
    
    // Sepet boş mu?
    public static boolean bosMu() {
        return items.isEmpty();
    }
    
    // Sepetteki toplam ürün sayısı
    public static int toplamUrunSayisi() {
        int toplam = 0;
        for (SepetItem item : items.values()) {
            toplam += item.getAdet();
        }
        return toplam;
    }
    
    // Sepetteki toplam tutar
    public static double toplamTutar() {
        double toplam = 0;
        for (SepetItem item : items.values()) {
            toplam += item.getToplamFiyat();
        }
        return toplam;
    }
    
    // Sepeti temizle
    public static void temizle() {
        items.clear();
    }
    
    // Sepeti kullanıcıya özel yap (her kullanıcı için ayrı sepet)
    private static String mevcutKullanici = "";
    
    public static void kullaniciAyarla(String kullaniciAdi) {
        if (!mevcutKullanici.equals(kullaniciAdi)) {
            temizle();
            mevcutKullanici = kullaniciAdi;
        }
    }
}