import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UrunListesiPaneli extends JFrame {
    private String kullaniciAdi;
    private String marka;
    private String kategori;
    
    public UrunListesiPaneli(String kullaniciAdi, String marka, String kategori) {
        this.kullaniciAdi = kullaniciAdi;
        this.marka = marka;
        this.kategori = kategori;
        
        setTitle("ShoeStock - " + marka.toUpperCase() + " > " + kategori.toUpperCase());
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 215, 215));
        
      
        JPanel ustPanel = new JPanel(new BorderLayout());
        ustPanel.setBackground(new Color(0, 215, 215));
        ustPanel.setPreferredSize(new Dimension(1000, 80));
        
        JButton btnGeri = new JButton("← Geri");
        btnGeri.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnGeri.setBackground(new Color(41, 128, 185));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setFocusPainted(false);
        btnGeri.setBorderPainted(false);
        btnGeri.setPreferredSize(new Dimension(100, 40));
        btnGeri.addActionListener(e -> {
            dispose();
            new KatagoriPaneli(kullaniciAdi, marka);
        });
        
        JLabel lblBaslik = new JLabel(marka.toUpperCase() + " - " + kategori.toUpperCase());
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        
        ustPanel.add(btnGeri, BorderLayout.WEST);
        ustPanel.add(lblBaslik, BorderLayout.CENTER);
        ustPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(ustPanel, BorderLayout.NORTH);
        
       
        ArrayList<Product> urunler = ProductManager.kategoriUrunleriGetir(marka, kategori);
        
        
        int urunSayisi = urunler.size();
        int satirSayisi = (int) Math.ceil(urunSayisi / 3.0);
        
        JPanel ortaPanel = new JPanel();
        ortaPanel.setBackground(new Color(0, 215, 215));
        ortaPanel.setLayout(new GridLayout(satirSayisi, 3, 20, 20));
        ortaPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        for (Product urun : urunler) {
            JPanel urunKart = urunKartiOlustur(urun);
            ortaPanel.add(urunKart);
        }
        
    
        JScrollPane scrollPane = new JScrollPane(ortaPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    
    private JPanel urunKartiOlustur(Product urun) {
        JPanel kart = new JPanel();
        kart.setLayout(new BorderLayout());
        kart.setBackground(Color.WHITE);
        kart.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
       
        JPanel bilgiPanel = new JPanel();
        bilgiPanel.setLayout(new BoxLayout(bilgiPanel, BoxLayout.Y_AXIS));
        bilgiPanel.setBackground(Color.WHITE);
        bilgiPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        
        JLabel lblModel = new JLabel(urun.getModel());
        lblModel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblModel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       
        JLabel lblMarka = new JLabel(urun.getMarka());
        lblMarka.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblMarka.setForeground(Color.GRAY);
        lblMarka.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        JLabel lblKategori = new JLabel("Kategori: " + urun.getKategori());
        lblKategori.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblKategori.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        JLabel lblNumara = new JLabel("Numara: " + urun.getNumara());
        lblNumara.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblNumara.setForeground(new Color(155, 89, 182));
        lblNumara.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        JLabel lblFiyat = new JLabel(String.format("%.2f TL", urun.getFiyat()));
        lblFiyat.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblFiyat.setForeground(new Color(46, 204, 113));
        lblFiyat.setAlignmentX(Component.CENTER_ALIGNMENT);
        
       
        JLabel lblStok = new JLabel();
        lblStok.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblStok.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if (urun.getStok() == 0) {
            lblStok.setText(" STOKTA YOK!");
            lblStok.setForeground(Color.RED);
        } else if (urun.getStok() < 10) {
            lblStok.setText(" KRİTİK STOK: " + urun.getStok() + " adet!");
            lblStok.setForeground(new Color(255, 140, 0)); 
        } else {
            lblStok.setText(urun.getStok() + " adet");
            lblStok.setForeground(new Color(46, 204, 113));
        }
        
        
        bilgiPanel.add(lblModel);
        bilgiPanel.add(Box.createVerticalStrut(8));
        bilgiPanel.add(lblMarka);
        bilgiPanel.add(Box.createVerticalStrut(12));
        bilgiPanel.add(lblKategori);
        bilgiPanel.add(Box.createVerticalStrut(8));
        bilgiPanel.add(lblNumara);
        bilgiPanel.add(Box.createVerticalStrut(12));
        bilgiPanel.add(lblFiyat);
        bilgiPanel.add(Box.createVerticalStrut(8));
        bilgiPanel.add(lblStok);
        
        kart.add(bilgiPanel, BorderLayout.CENTER);
        
       
        JPanel butonPanel = new JPanel(new GridLayout(2, 1, 8, 8));
        butonPanel.setBackground(Color.WHITE);
        butonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        
    
        JButton btnDetay = new JButton("Detayları Gör");
        btnDetay.setBackground(new Color(41, 128, 185));
        btnDetay.setForeground(Color.WHITE);
        btnDetay.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnDetay.setFocusPainted(false);
        btnDetay.setBorderPainted(false);
        btnDetay.addActionListener(e -> {
            new UrunDetayDialog(this, urun, kullaniciAdi).setVisible(true);
        });
        

        JButton btnSepeteEkle = new JButton("Sepete Ekle");
        btnSepeteEkle.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnSepeteEkle.setFocusPainted(false);
        btnSepeteEkle.setBorderPainted(false);
        
        if (urun.getStok() == 0) {
            btnSepeteEkle.setText("Stokta Yok");
            btnSepeteEkle.setEnabled(false);
            btnSepeteEkle.setBackground(Color.GRAY);
            btnSepeteEkle.setForeground(Color.WHITE);
        } else {
            btnSepeteEkle.setBackground(new Color(155, 89, 182));
            btnSepeteEkle.setForeground(Color.WHITE);
            btnSepeteEkle.addActionListener(e -> {
                sepeteEkleIslem(urun);
            });
        }
        
        butonPanel.add(btnDetay);
        butonPanel.add(btnSepeteEkle);
        
        kart.add(butonPanel, BorderLayout.SOUTH);
        
        return kart;
    }
    
    
    private void sepeteEkleIslem(Product urun) {
        if (urun.getStok() == 0) {
            JOptionPane.showMessageDialog(this, 
                "Bu ürün stokta yok!", 
                "Uyarı", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Adet seçimi
        String[] secenekler = new String[Math.min(urun.getStok(), 10)];
        for (int i = 0; i < secenekler.length; i++) {
            secenekler[i] = String.valueOf(i + 1);
        }
        
        String secim = (String) JOptionPane.showInputDialog(
            this,
            "Kaç adet sepete eklemek istiyorsunuz?",
            "Adet Seçimi",
            JOptionPane.QUESTION_MESSAGE,
            null,
            secenekler,
            secenekler[0]
        );
        
        if (secim != null) {
            int adet = Integer.parseInt(secim);
            
     
            if (Sepet.urunEkle(urun, adet)) {
                JOptionPane.showMessageDialog(this, 
                    "Ürün sepete eklendi!\n\n" +
                    "Ürün: " + urun.getModel() + " (Numara: " + urun.getNumara() + ")\n" +
                    "Adet: " + adet + "\n\n" +
                    "Sepetinizde toplam " + Sepet.toplamUrunSayisi() + " ürün var.", 
                    "Sepete Eklendi", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Yetersiz stok!\n\nBu üründen en fazla " + urun.getStok() + " adet ekleyebilirsiniz.", 
                    "Stok Hatası", 
                    JOptionPane.WARNING_MESSAGE);
            }
        }
    }

}
