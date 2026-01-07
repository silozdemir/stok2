/**
 * User sınıfı - Kalıtım örneği
 * - KALITIM: Veri abstract class'ından extends ediyor
 */
class User extends Veri {
    String kullaniciAdi, sifre, rol;
    
    public User(String ad, String sifre, String rol) {
        super(ad); // Parent constructor - KALITIM
        this.kullaniciAdi = ad;
        this.sifre = sifre;
        this.rol = rol;
    }
    
    // Abstract metodların implementasyonu
    @Override
    public void kaydet() {
        System.out.println("Kullanıcı kaydedildi: " + kullaniciAdi);
    }
    
    @Override
    public void goster() {
        System.out.println("Kullanıcı: " + kullaniciAdi + " (" + rol + ")");
    }
}