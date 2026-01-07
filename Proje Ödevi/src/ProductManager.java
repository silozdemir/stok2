import java.io.*;
import java.util.*;

public class ProductManager {
    private static final String URUN_DOSYA = "urunler.txt";
    private static final String SIPARIS_DOSYA = "siparisler.txt";


    public static ArrayList<Product> tumUrunleriGetir() {
        ArrayList<Product> urunler = new ArrayList<>();
        File dosya = new File(URUN_DOSYA);
        
        if (!dosya.exists()) {
            System.out.println("Ürün dosyası bulunamadı, oluşturuluyor...");
            ilkUrunleriOlustur();
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(URUN_DOSYA))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                satir = satir.trim();
                if (satir.isEmpty()) continue;
                
                String[] parca = satir.split(",");
                if (parca.length >= 8) {
                    try {
                        Product p = new Product(
                            parca[0].trim(), 
                            parca[1].trim(), 
                            parca[2].trim(),
                            parca[3].trim(), 
                            Integer.parseInt(parca[4].trim()), 
                            Integer.parseInt(parca[5].trim()), 
                            Double.parseDouble(parca[6].trim()), 
                            parca[7].trim()  
                        );
                        urunler.add(p);
                    } catch (NumberFormatException e) {
                        System.err.println("Hatalı satır atlandı: " + satir);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Dosya okuma hatası: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Toplam " + urunler.size() + " ürün yüklendi.");
        return urunler;
    }

   
    public static Map<String, ArrayList<Product>> markalaragGrupla() {
        Map<String, ArrayList<Product>> markaMap = new LinkedHashMap<>();
        ArrayList<Product> tumUrunler = tumUrunleriGetir();
        
        for (Product p : tumUrunler) {
            String marka = p.getMarka();
            if (!markaMap.containsKey(marka)) {
                markaMap.put(marka, new ArrayList<>());
            }
            markaMap.get(marka).add(p);
        }
        
        System.out.println("Markalar: " + markaMap.keySet());
        return markaMap;
    }

   
    public static Map<String, ArrayList<Product>> kategorilereGrupla(String marka) {
        Map<String, ArrayList<Product>> kategoriMap = new LinkedHashMap<>();
        ArrayList<Product> tumUrunler = tumUrunleriGetir();
        
        for (Product p : tumUrunler) {
            if (p.getMarka().equalsIgnoreCase(marka)) {
                String kategori = p.getKategori();
                if (!kategoriMap.containsKey(kategori)) {
                    kategoriMap.put(kategori, new ArrayList<>());
                }
                
                if (kategoriMap.get(kategori).size() < 3) {
                    kategoriMap.get(kategori).add(p);
                }
            }
        }
        
        System.out.println(marka + " kategorileri: " + kategoriMap.keySet());
        return kategoriMap;
    }

    
    public static ArrayList<Product> kategoriUrunleriGetir(String marka, String kategori) {
        ArrayList<Product> sonuc = new ArrayList<>();
        ArrayList<Product> tumUrunler = tumUrunleriGetir();
        
        
        for (Product p : tumUrunler) {
            if (p.getMarka().equalsIgnoreCase(marka) && p.getKategori().equalsIgnoreCase(kategori)) {
                sonuc.add(p);
            }
        }
        
        System.out.println(marka + " - " + kategori + ": " + sonuc.size() + " ürün");
        return sonuc;
    }
    
  
    public static ArrayList<Product> urunNumaralariniGetir(String marka, String kategori, String model) {
        ArrayList<Product> sonuc = new ArrayList<>();
        ArrayList<Product> tumUrunler = tumUrunleriGetir();
        
        for (Product p : tumUrunler) {
            if (p.getMarka().equalsIgnoreCase(marka) && 
                p.getKategori().equalsIgnoreCase(kategori) && 
                p.getModel().equalsIgnoreCase(model)) {
                sonuc.add(p);
            }
        }
        
        return sonuc;
    }

   
    public static void siparisKaydet(String kullaniciAdi, Product urun, int adet) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SIPARIS_DOSYA, true))) {
            String tarih = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
            bw.write(kullaniciAdi + "," + urun.getKod() + "," + urun.getModel() + " (Numara: " + urun.getNumara() + ")," + 
                     adet + "," + (urun.getFiyat() * adet) + "," + tarih);
            bw.newLine();
            
            
            stokGuncelle(urun.getKod(), urun.getStok() - adet);
            System.out.println("Sipariş kaydedildi: " + urun.getModel() + " x" + adet);
        } catch (IOException e) {
            System.err.println("Sipariş kaydetme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

  
    private static void stokGuncelle(String kod, int yeniStok) {
        ArrayList<Product> urunler = tumUrunleriGetir();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(URUN_DOSYA))) {
            for (Product p : urunler) {
                if (p.getKod().equals(kod)) {
                    p.setStok(yeniStok);
                }
                bw.write(p.toString());
                bw.newLine();
            }
            System.out.println("Stok güncellendi: " + kod + " -> " + yeniStok);
        } catch (IOException e) {
            System.err.println("Stok güncelleme hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

  
    public static ArrayList<String> kullaniciSiparisleriGetir(String kullaniciAdi) {
        ArrayList<String> siparisler = new ArrayList<>();
        File dosya = new File(SIPARIS_DOSYA);
        if (!dosya.exists()) return siparisler;
        
        try (BufferedReader br = new BufferedReader(new FileReader(SIPARIS_DOSYA))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.startsWith(kullaniciAdi + ",")) {
                    siparisler.add(satir);
                }
            }
        } catch (IOException e) {
            System.err.println("Sipariş okuma hatası: " + e.getMessage());
            e.printStackTrace();
        }
        return siparisler;
    }

  
    private static void ilkUrunleriOlustur() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(URUN_DOSYA))) {
    
            bw.write("N001-37,Nike,Spor,Air Max 270,37,15,2499.90,Günlük kullanım için rahat spor ayakkabı");
            bw.newLine();
            bw.write("N001-38,Nike,Spor,Air Max 270,38,18,2499.90,Günlük kullanım için rahat spor ayakkabı");
            bw.newLine();
            bw.write("N001-39,Nike,Spor,Air Max 270,39,20,2499.90,Günlük kullanım için rahat spor ayakkabı");
            bw.newLine();
            
            bw.write("N002-37,Nike,Spor,Revolution 6,37,12,1899.90,Koşu ve antrenman için ideal");
            bw.newLine();
            bw.write("N002-38,Nike,Spor,Revolution 6,38,20,1899.90,Koşu ve antrenman için ideal");
            bw.newLine();
            bw.write("N002-39,Nike,Spor,Revolution 6,39,16,1899.90,Koşu ve antrenman için ideal");
            bw.newLine();
            
            bw.write("N003-37,Nike,Spor,Court Vision,37,10,1799.90,Basketbol tarzı günlük ayakkabı");
            bw.newLine();
            bw.write("N003-38,Nike,Spor,Court Vision,38,12,1799.90,Basketbol tarzı günlük ayakkabı");
            bw.newLine();
            bw.write("N003-39,Nike,Spor,Court Vision,39,14,1799.90,Basketbol tarzı günlük ayakkabı");
            bw.newLine();
            
        
            bw.write("N004-37,Nike,Koşu,Pegasus 40,37,8,3299.90,Profesyonel koşu ayakkabısı");
            bw.newLine();
            bw.write("N004-38,Nike,Koşu,Pegasus 40,38,10,3299.90,Profesyonel koşu ayakkabısı");
            bw.newLine();
            bw.write("N004-39,Nike,Koşu,Pegasus 40,39,12,3299.90,Profesyonel koşu ayakkabısı");
            bw.newLine();
            
            bw.write("N005-37,Nike,Koşu,Downshifter 12,37,18,1599.90,Başlangıç seviyesi koşu ayakkabısı");
            bw.newLine();
            bw.write("N005-38,Nike,Koşu,Downshifter 12,38,20,1599.90,Başlangıç seviyesi koşu ayakkabısı");
            bw.newLine();
            bw.write("N005-39,Nike,Koşu,Downshifter 12,39,15,1599.90,Başlangıç seviyesi koşu ayakkabısı");
            bw.newLine();
            
            bw.write("N006-37,Nike,Koşu,React Infinity,37,10,3599.90,Uzun mesafe koşu için");
            bw.newLine();
            bw.write("N006-38,Nike,Koşu,React Infinity,38,12,3599.90,Uzun mesafe koşu için");
            bw.newLine();
            bw.write("N006-39,Nike,Koşu,React Infinity,39,14,3599.90,Uzun mesafe koşu için");
            bw.newLine();
            
          
            bw.write("N007-37,Nike,Casual,Air Force 1,37,25,2999.90,Klasik streetwear ayakkabı");
            bw.newLine();
            bw.write("N007-38,Nike,Casual,Air Force 1,38,22,2999.90,Klasik streetwear ayakkabı");
            bw.newLine();
            bw.write("N007-39,Nike,Casual,Air Force 1,39,28,2999.90,Klasik streetwear ayakkabı");
            bw.newLine();
            
            bw.write("N008-37,Nike,Casual,Blazer Mid,37,14,2699.90,Retro tasarım günlük ayakkabı");
            bw.newLine();
            bw.write("N008-38,Nike,Casual,Blazer Mid,38,16,2699.90,Retro tasarım günlük ayakkabı");
            bw.newLine();
            bw.write("N008-39,Nike,Casual,Blazer Mid,39,18,2699.90,Retro tasarım günlük ayakkabı");
            bw.newLine();
            
            bw.write("N009-37,Nike,Casual,Dunk Low,37,9,2899.90,Sokak modasının ikonu");
            bw.newLine();
            bw.write("N009-38,Nike,Casual,Dunk Low,38,11,2899.90,Sokak modasının ikonu");
            bw.newLine();
            bw.write("N009-39,Nike,Casual,Dunk Low,39,13,2899.90,Sokak modasının ikonu");
            bw.newLine();
            
          
            bw.write("A001-37,Adidas,Spor,Superstar,37,22,2299.90,Efsanevi shell toe tasarım");
            bw.newLine();
            bw.write("A001-38,Adidas,Spor,Superstar,38,25,2299.90,Efsanevi shell toe tasarım");
            bw.newLine();
            bw.write("A001-39,Adidas,Spor,Superstar,39,20,2299.90,Efsanevi shell toe tasarım");
            bw.newLine();
            
            bw.write("A002-37,Adidas,Spor,Grand Court,37,16,1699.90,Tenis tarzı günlük ayakkabı");
            bw.newLine();
            bw.write("A002-38,Adidas,Spor,Grand Court,38,18,1699.90,Tenis tarzı günlük ayakkabı");
            bw.newLine();
            bw.write("A002-39,Adidas,Spor,Grand Court,39,20,1699.90,Tenis tarzı günlük ayakkabı");
            bw.newLine();
            
            bw.write("A003-37,Adidas,Spor,Stan Smith,37,19,2199.90,Minimalist klasik tasarım");
            bw.newLine();
            bw.write("A003-38,Adidas,Spor,Stan Smith,38,22,2199.90,Minimalist klasik tasarım");
            bw.newLine();
            bw.write("A003-39,Adidas,Spor,Stan Smith,39,24,2199.90,Minimalist klasik tasarım");
            bw.newLine();
            
         
            bw.write("A004-37,Adidas,Koşu,Ultraboost 23,37,11,4299.90,Premium koşu performansı");
            bw.newLine();
            bw.write("A004-38,Adidas,Koşu,Ultraboost 23,38,13,4299.90,Premium koşu performansı");
            bw.newLine();
            bw.write("A004-39,Adidas,Koşu,Ultraboost 23,39,15,4299.90,Premium koşu performansı");
            bw.newLine();
            
            bw.write("A005-37,Adidas,Koşu,Solar Glide,37,13,2799.90,Uzun mesafe konforu");
            bw.newLine();
            bw.write("A005-38,Adidas,Koşu,Solar Glide,38,15,2799.90,Uzun mesafe konforu");
            bw.newLine();
            bw.write("A005-39,Adidas,Koşu,Solar Glide,39,17,2799.90,Uzun mesafe konforu");
            bw.newLine();
            
            bw.write("A006-37,Adidas,Koşu,Duramo SL,37,17,1499.90,Bütçe dostu koşu ayakkabısı");
            bw.newLine();
            bw.write("A006-38,Adidas,Koşu,Duramo SL,38,19,1499.90,Bütçe dostu koşu ayakkabısı");
            bw.newLine();
            bw.write("A006-39,Adidas,Koşu,Duramo SL,39,21,1499.90,Bütçe dostu koşu ayakkabısı");
            bw.newLine();
            
         
            bw.write("A007-37,Adidas,Casual,Gazelle,37,20,2399.90,Vintage süet tasarım");
            bw.newLine();
            bw.write("A007-38,Adidas,Casual,Gazelle,38,22,2399.90,Vintage süet tasarım");
            bw.newLine();
            bw.write("A007-39,Adidas,Casual,Gazelle,39,24,2399.90,Vintage süet tasarım");
            bw.newLine();
            
            bw.write("A008-37,Adidas,Casual,Samba,37,15,2499.90,Futsal tarzı günlük ayakkabı");
            bw.newLine();
            bw.write("A008-38,Adidas,Casual,Samba,38,17,2499.90,Futsal tarzı günlük ayakkabı");
            bw.newLine();
            bw.write("A008-39,Adidas,Casual,Samba,39,19,2499.90,Futsal tarzı günlük ayakkabı");
            bw.newLine();
            
            bw.write("A009-37,Adidas,Casual,Forum Low,37,12,2599.90,90'lar basketbol estetiği");
            bw.newLine();
            bw.write("A009-38,Adidas,Casual,Forum Low,38,14,2599.90,90'lar basketbol estetiği");
            bw.newLine();
            bw.write("A009-39,Adidas,Casual,Forum Low,39,16,2599.90,90'lar basketbol estetiği");
            bw.newLine();
            

            bw.write("NB001-37,New Balance,Spor,574 Core,37,18,2199.90,İkonik retro running tasarım");
            bw.newLine();
            bw.write("NB001-38,New Balance,Spor,574 Core,38,20,2199.90,İkonik retro running tasarım");
            bw.newLine();
            bw.write("NB001-39,New Balance,Spor,574 Core,39,22,2199.90,İkonik retro running tasarım");
            bw.newLine();
            
            bw.write("NB002-37,New Balance,Spor,327,37,21,2399.90,70'lerin klasik koşu estetiği");
            bw.newLine();
            bw.write("NB002-38,New Balance,Spor,327,38,23,2399.90,70'lerin klasik koşu estetiği");
            bw.newLine();
            bw.write("NB002-39,New Balance,Spor,327,39,25,2399.90,70'lerin klasik koşu estetiği");
            bw.newLine();
            
            bw.write("NB003-37,New Balance,Spor,480,37,14,1899.90,Basketbol esinli günlük ayakkabı");
            bw.newLine();
            bw.write("NB003-38,New Balance,Spor,480,38,16,1899.90,Basketbol esinli günlük ayakkabı");
            bw.newLine();
            bw.write("NB003-39,New Balance,Spor,480,39,18,1899.90,Basketbol esinli günlük ayakkabı");
            bw.newLine();
            
         
            bw.write("NB004-37,New Balance,Koşu,Fresh Foam 1080,37,9,3799.90,Premium koşu yastıklama");
            bw.newLine();
            bw.write("NB004-38,New Balance,Koşu,Fresh Foam 1080,38,11,3799.90,Premium koşu yastıklama");
            bw.newLine();
            bw.write("NB004-39,New Balance,Koşu,Fresh Foam 1080,39,13,3799.90,Premium koşu yastıklama");
            bw.newLine();
            
            bw.write("NB005-37,New Balance,Koşu,FuelCell Rebel,37,16,2999.90,Hafif ve hızlı koşu ayakkabısı");
            bw.newLine();
            bw.write("NB005-38,New Balance,Koşu,FuelCell Rebel,38,18,2999.90,Hafif ve hızlı koşu ayakkabısı");
            bw.newLine();
            bw.write("NB005-39,New Balance,Koşu,FuelCell Rebel,39,20,2999.90,Hafif ve hızlı koşu ayakkabısı");
            bw.newLine();
            
            bw.write("NB006-37,New Balance,Koşu,880 v12,37,11,2599.90,Günlük antrenman için ideal");
            bw.newLine();
            bw.write("NB006-38,New Balance,Koşu,880 v12,38,13,2599.90,Günlük antrenman için ideal");
            bw.newLine();
            bw.write("NB006-39,New Balance,Koşu,880 v12,39,15,2599.90,Günlük antrenman için ideal");
            bw.newLine();
            
          
            bw.write("NB007-37,New Balance,Casual,550,37,13,2899.90,Vintage basketbol silüeti");
            bw.newLine();
            bw.write("NB007-38,New Balance,Casual,550,38,15,2899.90,Vintage basketbol silüeti");
            bw.newLine();
            bw.write("NB007-39,New Balance,Casual,550,39,17,2899.90,Vintage basketbol silüeti");
            bw.newLine();
            
            bw.write("NB008-37,New Balance,Casual,2002R,37,17,2699.90,Y2K estetiği modern konfor");
            bw.newLine();
            bw.write("NB008-38,New Balance,Casual,2002R,38,19,2699.90,Y2K estetiği modern konfor");
            bw.newLine();
            bw.write("NB008-39,New Balance,Casual,2002R,39,21,2699.90,Y2K estetiği modern konfor");
            bw.newLine();
            
            bw.write("NB009-37,New Balance,Casual,9060,37,15,3199.90,Chunky retro hybrid tasarım");
            bw.newLine();
            bw.write("NB009-38,New Balance,Casual,9060,38,17,3199.90,Chunky retro hybrid tasarım");
            bw.newLine();
            bw.write("NB009-39,New Balance,Casual,9060,39,19,3199.90,Chunky retro hybrid tasarım");
            bw.newLine();
            
            System.out.println("Örnek ürünler oluşturuldu: 81 ürün (27 model x 3 numara)");
        } catch (IOException e) {
            System.err.println("Dosya oluşturma hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        System.out.println("=== ProductManager TEST ===");
        
       
        ArrayList<Product> urunler = tumUrunleriGetir();
        System.out.println("\nToplam ürün: " + urunler.size());
        
        
        System.out.println("\n=== MARKALAR ===");
        Map<String, ArrayList<Product>> markalar = markalaragGrupla();
        for (String marka : markalar.keySet()) {
            System.out.println(marka + ": " + markalar.get(marka).size() + " ürün");
        }
        
        
        System.out.println("\n=== NIKE KATEGORİLERİ ===");
        Map<String, ArrayList<Product>> nikeKategoriler = kategorilereGrupla("Nike");
        for (String kategori : nikeKategoriler.keySet()) {
            System.out.println(kategori + ": " + nikeKategoriler.get(kategori).size() + " ürün");
        }
    }

}

