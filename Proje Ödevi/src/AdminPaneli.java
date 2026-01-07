import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class AdminPaneli extends JFrame {
    private String kullaniciAdi;
    
    public AdminPaneli(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
        
        setTitle("ShoeStock - Admin Paneli");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 215, 215));
        
        // ÜST PANEL
        JPanel ustPanel = new JPanel(new BorderLayout());
        ustPanel.setBackground(new Color(0, 215, 215));
        ustPanel.setPreferredSize(new Dimension(900, 100));
        
        JButton btnCikis = new JButton("Çıkış Yap");
        btnCikis.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnCikis.setBackground(new Color(231, 76, 60));
        btnCikis.setForeground(Color.WHITE);
        btnCikis.setFocusPainted(false);
        btnCikis.setBorderPainted(false);
        btnCikis.setPreferredSize(new Dimension(130, 40));
        btnCikis.addActionListener(e -> {
            int onay = JOptionPane.showConfirmDialog(this,
                "Çıkış yapmak istediğinize emin misiniz?",
                "Çıkış Onayı",
                JOptionPane.YES_NO_OPTION);
            if (onay == JOptionPane.YES_OPTION) {
                dispose();
                GirisEkrani.main(new String[]{});
            }
        });
        
        JLabel lblBaslik = new JLabel("ADMİN PANELİ");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 32));
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblKullanici = new JLabel("Hoş geldin, " + kullaniciAdi);
        lblKullanici.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblKullanici.setForeground(Color.WHITE);
        lblKullanici.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel baslikPanel = new JPanel();
        baslikPanel.setLayout(new BoxLayout(baslikPanel, BoxLayout.Y_AXIS));
        baslikPanel.setBackground(new Color(0, 215, 215));
        lblBaslik.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblKullanici.setAlignmentX(Component.CENTER_ALIGNMENT);
        baslikPanel.add(lblBaslik);
        baslikPanel.add(Box.createVerticalStrut(5));
        baslikPanel.add(lblKullanici);
        
        ustPanel.add(btnCikis, BorderLayout.WEST);
        ustPanel.add(baslikPanel, BorderLayout.CENTER);
        ustPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(ustPanel, BorderLayout.NORTH);
        
        // ORTA PANEL
        JPanel ortaPanel = new JPanel();
        ortaPanel.setBackground(new Color(0, 215, 215));
        ortaPanel.setLayout(new GridLayout(2, 2, 30, 30));
        ortaPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        
        JButton btnUrunYonetimi = new JButton("<html><center>ÜRÜN<br>YÖNETİMİ</center></html>");
        btnUrunYonetimi.setFont(new Font("Tahoma", Font.BOLD, 24));
        btnUrunYonetimi.setBackground(new Color(155, 89, 182));
        btnUrunYonetimi.setForeground(Color.WHITE);
        btnUrunYonetimi.setFocusPainted(false);
        btnUrunYonetimi.setBorderPainted(false);
        btnUrunYonetimi.addActionListener(e -> {
            new UrunYonetimPaneli().setVisible(true);
        });
        
        JButton btnKullaniciYonetimi = new JButton("<html><center>KULLANICI<br>YÖNETİMİ</center></html>");
        btnKullaniciYonetimi.setFont(new Font("Tahoma", Font.BOLD, 24));
        btnKullaniciYonetimi.setBackground(new Color(52, 152, 219));
        btnKullaniciYonetimi.setForeground(Color.WHITE);
        btnKullaniciYonetimi.setFocusPainted(false);
        btnKullaniciYonetimi.setBorderPainted(false);
        btnKullaniciYonetimi.addActionListener(e -> {
            new KullaniciYonetimPaneli().setVisible(true);
        });
        
        JButton btnSiparisYonetimi = new JButton("<html><center>SİPARİŞ<br>YÖNETİMİ</center></html>");
        btnSiparisYonetimi.setFont(new Font("Tahoma", Font.BOLD, 24));
        btnSiparisYonetimi.setBackground(new Color(46, 204, 113));
        btnSiparisYonetimi.setForeground(Color.WHITE);
        btnSiparisYonetimi.setFocusPainted(false);
        btnSiparisYonetimi.setBorderPainted(false);
        btnSiparisYonetimi.addActionListener(e -> {
            new SiparisYonetimPaneli().setVisible(true);
        });
        
        JButton btnIstatistikler = new JButton("<html><center>İSTATİSTİKLER</center></html>");
        btnIstatistikler.setFont(new Font("Tahoma", Font.BOLD, 24));
        btnIstatistikler.setBackground(new Color(241, 196, 15));
        btnIstatistikler.setForeground(Color.WHITE);
        btnIstatistikler.setFocusPainted(false);
        btnIstatistikler.setBorderPainted(false);
        btnIstatistikler.addActionListener(e -> {
            istatistikleriGoster();
        });
        
        ortaPanel.add(btnUrunYonetimi);
        ortaPanel.add(btnKullaniciYonetimi);
        ortaPanel.add(btnSiparisYonetimi);
        ortaPanel.add(btnIstatistikler);
        
        add(ortaPanel, BorderLayout.CENTER);
        
        // ALT PANEL
        JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        altPanel.setBackground(new Color(0, 215, 215));
        altPanel.setPreferredSize(new Dimension(900, 80));
        
        JButton btnMusteriModu = new JButton("Müşteri Moduna Geç");
        btnMusteriModu.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnMusteriModu.setBackground(new Color(41, 128, 185));
        btnMusteriModu.setForeground(Color.WHITE);
        btnMusteriModu.setFocusPainted(false);
        btnMusteriModu.setBorderPainted(false);
        btnMusteriModu.setPreferredSize(new Dimension(250, 50));
        btnMusteriModu.addActionListener(e -> {
            dispose();
            new MarkaPaneli(kullaniciAdi);
        });
        
        altPanel.add(btnMusteriModu);
        add(altPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private void istatistikleriGoster() {
        ArrayList<Product> tumUrunler = ProductManager.tumUrunleriGetir();
        ArrayList<String> siparisler = new ArrayList<>();
        
        File dosya = new File("siparisler.txt");
        if (dosya.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {
                String satir;
                while ((satir = br.readLine()) != null) {
                    siparisler.add(satir);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        double toplamSatis = 0;
        for (String siparis : siparisler) {
            String[] parca = siparis.split(",");
            if (parca.length >= 5) {
                try {
                    toplamSatis += Double.parseDouble(parca[4]);
                } catch (NumberFormatException e) {
                }
            }
        }
        
        int toplamStok = 0;
        for (Product p : tumUrunler) {
            toplamStok += p.getStok();
        }
        
        JPanel istatistikPanel = new JPanel();
        istatistikPanel.setLayout(new BoxLayout(istatistikPanel, BoxLayout.Y_AXIS));
        istatistikPanel.setBackground(Color.WHITE);
        istatistikPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblBaslik = new JLabel("SİSTEM İSTATİSTİKLERİ");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblBaslik.setForeground(new Color(41, 128, 185));
        lblBaslik.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        istatistikPanel.add(lblBaslik);
        istatistikPanel.add(Box.createVerticalStrut(20));
        
        istatistikPanel.add(istatistikSatiri("Toplam Ürün Sayısı:", tumUrunler.size() + " adet"));
        istatistikPanel.add(Box.createVerticalStrut(10));
        istatistikPanel.add(istatistikSatiri("Toplam Stok:", toplamStok + " adet"));
        istatistikPanel.add(Box.createVerticalStrut(10));
        istatistikPanel.add(istatistikSatiri("Toplam Sipariş:", siparisler.size() + " adet"));
        istatistikPanel.add(Box.createVerticalStrut(10));
        istatistikPanel.add(istatistikSatiri("Toplam Satış:", String.format("%.2f TL", toplamSatis)));
        
        JOptionPane.showMessageDialog(this, istatistikPanel, "İstatistikler", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private JPanel istatistikSatiri(String baslik, String deger) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(400, 30));
        
        JLabel lblBaslik = new JLabel(baslik);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        JLabel lblDeger = new JLabel(deger);
        lblDeger.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblDeger.setForeground(new Color(46, 204, 113));
        
        panel.add(lblBaslik, BorderLayout.WEST);
        panel.add(lblDeger, BorderLayout.EAST);
        
        return panel;
    }
}