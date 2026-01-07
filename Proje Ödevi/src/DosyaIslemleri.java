import java.io.*;
import java.util.*;

public class DosyaIslemleri {
    public static ArrayList<String> dosyaOku(String dosyaAdi) {
        ArrayList<String> satirlar = new ArrayList<>();
        File dosya = new File(dosyaAdi);
        
        if (!dosya.exists()) {
            return satirlar;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                satirlar.add(satir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return satirlar;
    }
    
    public static boolean dosyayaEkle(String dosyaAdi, String icerik) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dosyaAdi, true))) {
            bw.write(icerik);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public static boolean dosyayaYaz(String dosyaAdi, ArrayList<String> satirlar) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dosyaAdi))) {
            for (String satir : satirlar) {
                bw.write(satir);
                bw.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean dosyaVarMi(String dosyaAdi) {
        File dosya = new File(dosyaAdi);
        return dosya.exists();
    }

    public static boolean dosyaSil(String dosyaAdi) {
        File dosya = new File(dosyaAdi);
        if (dosya.exists()) {
            return dosya.delete();
        }
        return false;
    }

    public static String kullaniciBul(String kullaniciAdi, String sifre) {
        ArrayList<String> satirlar = dosyaOku("user.txt");
        
        for (String satir : satirlar) {
            String[] parca = satir.split(",");
            if (parca.length == 3 && parca[0].equals(kullaniciAdi) && parca[1].equals(sifre)) {
                return parca[2];
            }
        }
        return null; 
    }
    
   
    public static boolean kullaniciKaydet(String kullaniciAdi, String sifre, String rol) {
        String satir = kullaniciAdi + "," + sifre + "," + rol;
        return dosyayaEkle("user.txt", satir);
    }
    
   
    public static ArrayList<String[]> tumKullanicilariGetir() {
        ArrayList<String[]> kullanicilar = new ArrayList<>();
        ArrayList<String> satirlar = dosyaOku("user.txt");
        
        for (String satir : satirlar) {
            String[] parca = satir.split(",");
            if (parca.length == 3) {
                kullanicilar.add(parca);
            }
        }
        return kullanicilar;
    }
}