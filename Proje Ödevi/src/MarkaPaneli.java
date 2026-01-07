import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MarkaPaneli extends JFrame {
    private String kullaniciAdi;

    public MarkaPaneli(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;

        setTitle("ShoeStock - Marka Seçimi");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 215, 215));

        // ÜST PANEL - GERİ BUTONU VE BAŞLIK
        JPanel ustPanel = new JPanel(new BorderLayout());
        ustPanel.setBackground(new Color(0, 215, 215));
        ustPanel.setPreferredSize(new Dimension(800, 100));

        // GERİ BUTONU - GİRİŞ EKRANINA DÖN
        JButton btnGeri = new JButton("← Geri");
        btnGeri.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnGeri.setBackground(new Color(52, 152, 219));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setFocusPainted(false);
        btnGeri.setBorderPainted(false);
        btnGeri.setPreferredSize(new Dimension(100, 40));
        btnGeri.addActionListener(e -> {
            int onay = JOptionPane.showConfirmDialog(this,
                "Giriş ekranına dönmek istediğinize emin misiniz?",
                "Geri Dön",
                JOptionPane.YES_NO_OPTION);
            
            if (onay == JOptionPane.YES_OPTION) {
                dispose();
                GirisEkrani.main(new String[]{});
            }
        });

        JLabel lblBaslik = new JLabel("MARKALAR");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 32));
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);

        ustPanel.add(btnGeri, BorderLayout.WEST);
        ustPanel.add(lblBaslik, BorderLayout.CENTER);
        ustPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(ustPanel, BorderLayout.NORTH);

        // ORTA PANEL - MARKA BUTONLARI
        JPanel ortaPanel = new JPanel();
        ortaPanel.setBackground(new Color(0, 215, 215));
        ortaPanel.setLayout(new GridLayout(2, 2, 30, 30));
        ortaPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        Map<String, ArrayList<Product>> markaMap = ProductManager.markalaragGrupla();

        // Marka renkleri
        Color[] markaRenkleri = {
            new Color(155, 89, 182), // Mor
            new Color(52, 152, 219),  // Mavi
            new Color(46, 204, 113)   // Yeşil
        };

        int renkIndex = 0;
        for (String marka : markaMap.keySet()) {
            JButton btnMarka = new JButton(marka.toUpperCase());
            btnMarka.setFont(new Font("Tahoma", Font.BOLD, 26));
            btnMarka.setBackground(markaRenkleri[renkIndex % markaRenkleri.length]);
            btnMarka.setForeground(Color.WHITE);
            btnMarka.setFocusPainted(false);
            btnMarka.setBorderPainted(false);

            int urunSayisi = markaMap.get(marka).size();
            btnMarka.setToolTipText(urunSayisi + " ürün mevcut");

            // Marka butonuna tıklandığında kategori paneline git
            btnMarka.addActionListener(e -> {
                dispose();
                new KatagoriPaneli(kullaniciAdi, marka);
            });

            ortaPanel.add(btnMarka);
            renkIndex++;
        }

        add(ortaPanel, BorderLayout.CENTER);

        // ALT PANEL - SEPETİM, SİPARİŞLERİM VE ÇIKIŞ BUTONLARI
        JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        altPanel.setBackground(new Color(0, 215, 215));
        altPanel.setPreferredSize(new Dimension(800, 80));
        
        // Sepet kullanıcıyı ayarla
        Sepet.kullaniciAyarla(kullaniciAdi);

        JButton btnSepetim = new JButton("🛒 Sepetim (" + Sepet.toplamUrunSayisi() + ")");
        btnSepetim.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnSepetim.setBackground(new Color(155, 89, 182));
        btnSepetim.setForeground(Color.WHITE);
        btnSepetim.setFocusPainted(false);
        btnSepetim.setBorderPainted(false);
        btnSepetim.setPreferredSize(new Dimension(180, 50));
        btnSepetim.addActionListener(e -> {
            new SepetEkrani(kullaniciAdi).setVisible(true);
        });

        JButton btnSiparislerim = new JButton("📦 Siparişlerim");
        btnSiparislerim.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnSiparislerim.setBackground(new Color(41, 128, 185));
        btnSiparislerim.setForeground(Color.WHITE);
        btnSiparislerim.setFocusPainted(false);
        btnSiparislerim.setBorderPainted(false);
        btnSiparislerim.setPreferredSize(new Dimension(180, 50));
        btnSiparislerim.addActionListener(e -> {
            new SiparisGecmisiEkrani(kullaniciAdi).setVisible(true);
        });

        JButton btnCikis = new JButton("🚪 Çıkış");
        btnCikis.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCikis.setBackground(new Color(231, 76, 60));
        btnCikis.setForeground(Color.WHITE);
        btnCikis.setFocusPainted(false);
        btnCikis.setBorderPainted(false);
        btnCikis.setPreferredSize(new Dimension(150, 50));
        btnCikis.addActionListener(e -> {
            int onay = JOptionPane.showConfirmDialog(this,
                "Uygulamadan tamamen çıkmak istediğinize emin misiniz?",
                "Uygulamayı Kapat",
                JOptionPane.YES_NO_OPTION);
            if (onay == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        altPanel.add(btnSepetim);
        altPanel.add(btnSiparislerim);
        altPanel.add(btnCikis);
        add(altPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}