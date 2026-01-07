import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class SepetEkrani extends JFrame {
    private JTable tablo;
    private DefaultTableModel model;
    private JLabel lblToplamTutar;
    private String kullaniciAdi;
    
    public SepetEkrani(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
        
        setTitle("Sepetim - " + kullaniciAdi);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        

        JPanel ustPanel = new JPanel(new BorderLayout());
        ustPanel.setBackground(new Color(155, 89, 182));
        ustPanel.setPreferredSize(new Dimension(1000, 80));
        
     
        JButton btnGeri = new JButton("Geri");
        btnGeri.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnGeri.setBackground(new Color(127, 140, 141));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setFocusPainted(false);
        btnGeri.setBorderPainted(false);
        btnGeri.setPreferredSize(new Dimension(100, 40));
        btnGeri.addActionListener(e -> dispose());
        
        JLabel lblBaslik = new JLabel("SEPETİM");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        
        ustPanel.add(btnGeri, BorderLayout.WEST);
        ustPanel.add(lblBaslik, BorderLayout.CENTER);
        ustPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(ustPanel, BorderLayout.NORTH);
    
        String[] sutunlar = {"Ürün Kodu", "Marka", "Model", "Numara", "Fiyat", "Adet", "Toplam"};
        model = new DefaultTableModel(sutunlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablo = new JTable(model);
        tablo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tablo.setRowHeight(35);
        tablo.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        tablo.getTableHeader().setBackground(new Color(155, 89, 182));
        tablo.getTableHeader().setForeground(Color.WHITE);
        tablo.setSelectionBackground(new Color(41, 128, 185));
        tablo.setSelectionForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tablo);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);
        
     
        JPanel altPanel = new JPanel(new BorderLayout());
        altPanel.setBackground(Color.WHITE);
        altPanel.setPreferredSize(new Dimension(1000, 120));
        altPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
   
        JPanel toplamPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        toplamPanel.setBackground(Color.WHITE);
        
        JLabel lblToplamBaslik = new JLabel("TOPLAM TUTAR:");
        lblToplamBaslik.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblToplamBaslik.setForeground(new Color(44, 62, 80));
        
        lblToplamTutar = new JLabel(String.format("%.2f TL", Sepet.toplamTutar()));
        lblToplamTutar.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblToplamTutar.setForeground(new Color(46, 204, 113));
        
        toplamPanel.add(lblToplamBaslik);
        toplamPanel.add(lblToplamTutar);
 
        JPanel butonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        butonPanel.setBackground(Color.WHITE);
        
        JButton btnAdetArtir = new JButton("Adet Artır");
        btnAdetArtir.setPreferredSize(new Dimension(140, 45));
        btnAdetArtir.setBackground(new Color(52, 152, 219));
        btnAdetArtir.setForeground(Color.WHITE);
        btnAdetArtir.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAdetArtir.setFocusPainted(false);
        btnAdetArtir.setBorderPainted(false);
        btnAdetArtir.addActionListener(e -> adetDegistir(1));
        
        JButton btnAdetAzalt = new JButton("Adet Azalt");
        btnAdetAzalt.setPreferredSize(new Dimension(140, 45));
        btnAdetAzalt.setBackground(new Color(230, 126, 34));
        btnAdetAzalt.setForeground(Color.WHITE);
        btnAdetAzalt.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnAdetAzalt.setFocusPainted(false);
        btnAdetAzalt.setBorderPainted(false);
        btnAdetAzalt.addActionListener(e -> adetDegistir(-1));
        
        JButton btnSil = new JButton("Sepetten Çıkar");
        btnSil.setPreferredSize(new Dimension(160, 45));
        btnSil.setBackground(new Color(231, 76, 60));
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnSil.setFocusPainted(false);
        btnSil.setBorderPainted(false);
        btnSil.addActionListener(e -> sepettenCikar());
        
        JButton btnTemizle = new JButton("Sepeti Temizle");
        btnTemizle.setPreferredSize(new Dimension(160, 45));
        btnTemizle.setBackground(new Color(149, 165, 166));
        btnTemizle.setForeground(Color.WHITE);
        btnTemizle.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnTemizle.setFocusPainted(false);
        btnTemizle.setBorderPainted(false);
        btnTemizle.addActionListener(e -> sepetiTemizle());
        
        JButton btnSiparisVer = new JButton("Sipariş Ver");
        btnSiparisVer.setPreferredSize(new Dimension(160, 45));
        btnSiparisVer.setBackground(new Color(46, 204, 113));
        btnSiparisVer.setForeground(Color.WHITE);
        btnSiparisVer.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnSiparisVer.setFocusPainted(false);
        btnSiparisVer.setBorderPainted(false);
        btnSiparisVer.addActionListener(e -> siparisVer());
        
        JButton btnKapat = new JButton("Kapat");
        btnKapat.setPreferredSize(new Dimension(120, 45));
        btnKapat.setBackground(new Color(127, 140, 141));
        btnKapat.setForeground(Color.WHITE);
        btnKapat.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnKapat.setFocusPainted(false);
        btnKapat.setBorderPainted(false);
        btnKapat.addActionListener(e -> dispose());
        
        butonPanel.add(btnAdetArtir);
        butonPanel.add(btnAdetAzalt);
        butonPanel.add(btnSil);
        butonPanel.add(btnTemizle);
        butonPanel.add(btnSiparisVer);
        butonPanel.add(btnKapat);
        
        altPanel.add(toplamPanel, BorderLayout.NORTH);
        altPanel.add(butonPanel, BorderLayout.CENTER);
        
        add(altPanel, BorderLayout.SOUTH);
        
        
        sepetiYukle();
    }
    
    private void sepetiYukle() {
        model.setRowCount(0);
        
        if (Sepet.bosMu()) {
            model.addRow(new Object[]{"Sepetiniz boş", "", "", "", "", "", ""});
            return;
        }
        
        ArrayList<Sepet.SepetItem> items = Sepet.tumUrunleriGetir();
        for (Sepet.SepetItem item : items) {
            Product p = item.getUrun();
            model.addRow(new Object[]{
                p.getKod(),
                p.getMarka(),
                p.getModel(),
                p.getNumara(),
                String.format("%.2f TL", p.getFiyat()),
                item.getAdet(),
                String.format("%.2f TL", item.getToplamFiyat())
            });
        }
        
        toplamTutarGuncelle();
    }
    
    private void toplamTutarGuncelle() {
        lblToplamTutar.setText(String.format("%.2f TL", Sepet.toplamTutar()));
    }
    
    private void adetDegistir(int degisim) {
        int seciliSatir = tablo.getSelectedRow();
        if (seciliSatir == -1 || Sepet.bosMu()) {
            JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String urunKodu = (String) model.getValueAt(seciliSatir, 0);
        int mevcutAdet = (int) model.getValueAt(seciliSatir, 5);
        int yeniAdet = mevcutAdet + degisim;
        
        if (yeniAdet <= 0) {
            sepettenCikar();
            return;
        }
        
        if (!Sepet.adetGuncelle(urunKodu, yeniAdet)) {
            JOptionPane.showMessageDialog(this, "Yetersiz stok!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        sepetiYukle();
    }
    
    private void sepettenCikar() {
        int seciliSatir = tablo.getSelectedRow();
        if (seciliSatir == -1 || Sepet.bosMu()) {
            JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String urunKodu = (String) model.getValueAt(seciliSatir, 0);
        Sepet.urunCikar(urunKodu);
        sepetiYukle();
        
        JOptionPane.showMessageDialog(this, "Ürün sepetten çıkarıldı!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void sepetiTemizle() {
        if (Sepet.bosMu()) {
            JOptionPane.showMessageDialog(this, "Sepet zaten boş!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Object[] secenekler = {"Evet", "Hayır"};
        int onay = JOptionPane.showOptionDialog(this,
            "Sepetteki tüm ürünleri silmek istediğinize emin misiniz?",
            "Sepeti Temizle",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            secenekler,
            secenekler[1]);
        
        if (onay == JOptionPane.YES_OPTION) {
            Sepet.temizle();
            sepetiYukle();
            JOptionPane.showMessageDialog(this, "Sepet temizlendi!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void siparisVer() {
        if (Sepet.bosMu()) {
            JOptionPane.showMessageDialog(this, "Sepetiniz boş!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
  
        String mesaj = String.format(
            "Toplam %d ürün\nToplam Tutar: %.2f TL\n\nSiparişi onaylıyor musunuz?",
            Sepet.toplamUrunSayisi(),
            Sepet.toplamTutar()
        );
        
        Object[] secenekler = {"Sipariş Ver", "İptal"};
        int onay = JOptionPane.showOptionDialog(this,
            mesaj,
            "Sipariş Onayı",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            secenekler,
            secenekler[0]);
        
        if (onay == JOptionPane.YES_OPTION) {
   
            ArrayList<Sepet.SepetItem> items = Sepet.tumUrunleriGetir();
            for (Sepet.SepetItem item : items) {
                ProductManager.siparisKaydet(kullaniciAdi, item.getUrun(), item.getAdet());
            }
            
            
            Sepet.temizle();
            
            JOptionPane.showMessageDialog(this,
                "Siparişleriniz başarıyla oluşturuldu!\n\nSipariş geçmişinden kontrol edebilirsiniz.",
                "Sipariş Başarılı",
                JOptionPane.INFORMATION_MESSAGE);
            
            dispose();
        }
    }

}

