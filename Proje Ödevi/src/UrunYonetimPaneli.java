import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.event.*;

public class UrunYonetimPaneli extends JFrame {
    private JTable tablo;
    private DefaultTableModel model;
    private JTextField txtArama;
    private JComboBox<String> cmbMarka, cmbKategori, cmbStok;
    private ArrayList<Product> tumUrunler;
    
    public UrunYonetimPaneli() {
        setTitle("Ürün Yönetimi");
        setSize(1300, 750);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 215, 215));
        
        // ÜST PANEL - BAŞLIK
        JPanel ustPanel = new JPanel();
        ustPanel.setBackground(new Color(0, 215, 215));
        ustPanel.setPreferredSize(new Dimension(1300, 70));
        
        JLabel lblBaslik = new JLabel("ÜRÜN YÖNETİMİ");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);
        ustPanel.add(lblBaslik);
        
        // ARAMA VE FİLTRELEME PANELİ
        JPanel aramaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        aramaPanel.setBackground(new Color(236, 240, 241));
        aramaPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Arama kutusu
        JLabel lblArama = new JLabel("🔍 Ara:");
        lblArama.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        txtArama = new JTextField(20);
        txtArama.setFont(new Font("Tahoma", Font.PLAIN, 13));
        txtArama.setToolTipText("Ürün kodu, marka, model veya kategori ile arayın");
        
        // Marka filtresi
        JLabel lblMarkaFiltre = new JLabel("Marka:");
        lblMarkaFiltre.setFont(new Font("Tahoma", Font.BOLD, 13));
        
        cmbMarka = new JComboBox<>();
        cmbMarka.addItem("Tümü");
        cmbMarka.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cmbMarka.setPreferredSize(new Dimension(120, 30));
        
        // Kategori filtresi
        JLabel lblKategoriFiltre = new JLabel("Kategori:");
        lblKategoriFiltre.setFont(new Font("Tahoma", Font.BOLD, 13));
        
        cmbKategori = new JComboBox<>();
        cmbKategori.addItem("Tümü");
        cmbKategori.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cmbKategori.setPreferredSize(new Dimension(120, 30));
        
        // Stok durumu filtresi
        JLabel lblStokFiltre = new JLabel("Stok:");
        lblStokFiltre.setFont(new Font("Tahoma", Font.BOLD, 13));
        
        cmbStok = new JComboBox<>();
        cmbStok.addItem("Tümü");
        cmbStok.addItem("Stokta Var");
        cmbStok.addItem("Kritik Stok (<10)");
        cmbStok.addItem("Stokta Yok");
        cmbStok.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cmbStok.setPreferredSize(new Dimension(140, 30));
        
        // Temizle butonu
        JButton btnTemizle = new JButton("🔄 Temizle");
        btnTemizle.setBackground(new Color(149, 165, 166));
        btnTemizle.setForeground(Color.WHITE);
        btnTemizle.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnTemizle.setFocusPainted(false);
        btnTemizle.setBorderPainted(false);
        btnTemizle.setPreferredSize(new Dimension(110, 35));
        btnTemizle.addActionListener(e -> {
            txtArama.setText("");
            cmbMarka.setSelectedIndex(0);
            cmbKategori.setSelectedIndex(0);
            cmbStok.setSelectedIndex(0);
            urunleriYukle();
            filtreleriDoldur();
        });
        
        aramaPanel.add(lblArama);
        aramaPanel.add(txtArama);
        aramaPanel.add(Box.createHorizontalStrut(10));
        aramaPanel.add(lblMarkaFiltre);
        aramaPanel.add(cmbMarka);
        aramaPanel.add(lblKategoriFiltre);
        aramaPanel.add(cmbKategori);
        aramaPanel.add(lblStokFiltre);
        aramaPanel.add(cmbStok);
        aramaPanel.add(btnTemizle);
        
        // Panel container
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(ustPanel, BorderLayout.NORTH);
        topContainer.add(aramaPanel, BorderLayout.CENTER);
        add(topContainer, BorderLayout.NORTH);
        
        // TABLO
        String[] sutunlar = {"Kod", "Marka", "Kategori", "Model", "Numara", "Stok", "Fiyat", "Açıklama"};
        model = new DefaultTableModel(sutunlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablo = new JTable(model);
        tablo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tablo.setRowHeight(25);
        tablo.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        tablo.getTableHeader().setBackground(new Color(155, 89, 182));
        tablo.getTableHeader().setForeground(Color.WHITE);
        tablo.setSelectionBackground(new Color(41, 128, 185));
        tablo.setSelectionForeground(Color.WHITE);
        
        // Arama ve filtre event listeners
        txtArama.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { filtrele(); }
            public void removeUpdate(DocumentEvent e) { filtrele(); }
            public void insertUpdate(DocumentEvent e) { filtrele(); }
        });
        
        cmbMarka.addActionListener(e -> filtrele());
        cmbKategori.addActionListener(e -> filtrele());
        cmbStok.addActionListener(e -> filtrele());
        
        urunleriYukle();
        filtreleriDoldur();
        
        JScrollPane scrollPane = new JScrollPane(tablo);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);
        
        // ALT PANEL - BUTONLAR
        JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        altPanel.setBackground(new Color(0, 215, 215));
        altPanel.setPreferredSize(new Dimension(1300, 80));
        
        JButton btnEkle = new JButton("➕ Yeni Ürün");
        btnEkle.setPreferredSize(new Dimension(150, 50));
        btnEkle.setBackground(new Color(46, 204, 113));
        btnEkle.setForeground(Color.WHITE);
        btnEkle.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnEkle.setFocusPainted(false);
        btnEkle.setBorderPainted(false);
        btnEkle.addActionListener(e -> yeniUrunEkle());
        
        JButton btnDuzenle = new JButton("✏️ Düzenle");
        btnDuzenle.setPreferredSize(new Dimension(150, 50));
        btnDuzenle.setBackground(new Color(52, 152, 219));
        btnDuzenle.setForeground(Color.WHITE);
        btnDuzenle.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnDuzenle.setFocusPainted(false);
        btnDuzenle.setBorderPainted(false);
        btnDuzenle.addActionListener(e -> urunDuzenle());
        
        JButton btnSil = new JButton("🗑️ Sil");
        btnSil.setPreferredSize(new Dimension(150, 50));
        btnSil.setBackground(new Color(231, 76, 60));
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnSil.setFocusPainted(false);
        btnSil.setBorderPainted(false);
        btnSil.addActionListener(e -> urunSil());
        
        JButton btnGeriAl = new JButton("♻️ Geri Al");
        btnGeriAl.setPreferredSize(new Dimension(150, 50));
        btnGeriAl.setBackground(new Color(241, 196, 15));
        btnGeriAl.setForeground(Color.WHITE);
        btnGeriAl.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnGeriAl.setFocusPainted(false);
        btnGeriAl.setBorderPainted(false);
        btnGeriAl.addActionListener(e -> silinenleriGoster());
        
        JButton btnKapat = new JButton("Kapat");
        btnKapat.setPreferredSize(new Dimension(150, 50));
        btnKapat.setBackground(new Color(127, 140, 141));
        btnKapat.setForeground(Color.WHITE);
        btnKapat.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnKapat.setFocusPainted(false);
        btnKapat.setBorderPainted(false);
        btnKapat.addActionListener(e -> dispose());
        
        altPanel.add(btnEkle);
        altPanel.add(btnDuzenle);
        altPanel.add(btnSil);
        altPanel.add(btnGeriAl);
        altPanel.add(btnKapat);
        
        add(altPanel, BorderLayout.SOUTH);
    }
    
    private void filtreleriDoldur() {
        // Mevcut seçimi koru
        String secilenMarka = (String) cmbMarka.getSelectedItem();
        String secilenKategori = (String) cmbKategori.getSelectedItem();
        
        // Temizle
        cmbMarka.removeAllItems();
        cmbKategori.removeAllItems();
        
        cmbMarka.addItem("Tümü");
        cmbKategori.addItem("Tümü");
        
        // Unique markalar ve kategoriler
        ArrayList<String> markalar = new ArrayList<>();
        ArrayList<String> kategoriler = new ArrayList<>();
        
        for (Product p : tumUrunler) {
            if (!markalar.contains(p.getMarka())) {
                markalar.add(p.getMarka());
                cmbMarka.addItem(p.getMarka());
            }
            if (!kategoriler.contains(p.getKategori())) {
                kategoriler.add(p.getKategori());
                cmbKategori.addItem(p.getKategori());
            }
        }
        
        // Önceki seçimi geri yükle
        if (secilenMarka != null && markalar.contains(secilenMarka)) {
            cmbMarka.setSelectedItem(secilenMarka);
        }
        if (secilenKategori != null && kategoriler.contains(secilenKategori)) {
            cmbKategori.setSelectedItem(secilenKategori);
        }
    }
    
    private void filtrele() {
        String aramaMetni = txtArama.getText().toLowerCase().trim();
        String secilenMarka = (String) cmbMarka.getSelectedItem();
        String secilenKategori = (String) cmbKategori.getSelectedItem();
        String secilenStok = (String) cmbStok.getSelectedItem();
        
        model.setRowCount(0);
        
        for (Product p : tumUrunler) {
            // Arama filtresi
            if (!aramaMetni.isEmpty()) {
                boolean eslesme = p.getKod().toLowerCase().contains(aramaMetni) ||
                                 p.getMarka().toLowerCase().contains(aramaMetni) ||
                                 p.getKategori().toLowerCase().contains(aramaMetni) ||
                                 p.getModel().toLowerCase().contains(aramaMetni);
                if (!eslesme) continue;
            }
            
            // Marka filtresi
            if (secilenMarka != null && !secilenMarka.equals("Tümü")) {
                if (!p.getMarka().equals(secilenMarka)) continue;
            }
            
            // Kategori filtresi
            if (secilenKategori != null && !secilenKategori.equals("Tümü")) {
                if (!p.getKategori().equals(secilenKategori)) continue;
            }
            
            // Stok filtresi
            if (secilenStok != null && !secilenStok.equals("Tümü")) {
                if (secilenStok.equals("Stokta Var") && p.getStok() == 0) continue;
                if (secilenStok.equals("Kritik Stok (<10)") && p.getStok() >= 10) continue;
                if (secilenStok.equals("Stokta Yok") && p.getStok() > 0) continue;
            }
            
            // Tabloya ekle
            model.addRow(new Object[]{
                p.getKod(),
                p.getMarka(),
                p.getKategori(),
                p.getModel(),
                p.getNumara(),
                p.getStok(),
                String.format("%.2f TL", p.getFiyat()),
                p.getAciklama()
            });
        }
        
        // Sonuç sayısını başlıkta göster
        setTitle("Ürün Yönetimi - " + model.getRowCount() + " ürün gösteriliyor");
    }
    
    private void urunleriYukle() {
        model.setRowCount(0);
        tumUrunler = ProductManager.tumUrunleriGetir();
        
        for (Product p : tumUrunler) {
            model.addRow(new Object[]{
                p.getKod(),
                p.getMarka(),
                p.getKategori(),
                p.getModel(),
                p.getNumara(),
                p.getStok(),
                String.format("%.2f TL", p.getFiyat()),
                p.getAciklama()
            });
        }
        
        setTitle("Ürün Yönetimi - " + model.getRowCount() + " ürün");
    }
    private void yeniUrunEkle() {
        JTextField txtKod = new JTextField(15);
        JTextField txtMarka = new JTextField(15);
        JTextField txtKategori = new JTextField(15);
        JTextField txtModel = new JTextField(15);
        JTextField txtNumara = new JTextField(15);
        JTextField txtStok = new JTextField(15);
        JTextField txtFiyat = new JTextField(15);
        JTextField txtAciklama = new JTextField(15);
        
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Ürün Kodu:"));
        panel.add(txtKod);
        panel.add(new JLabel("Marka:"));
        panel.add(txtMarka);
        panel.add(new JLabel("Kategori:"));
        panel.add(txtKategori);
        panel.add(new JLabel("Model:"));
        panel.add(txtModel);
        panel.add(new JLabel("Numara:"));
        panel.add(txtNumara);
        panel.add(new JLabel("Stok:"));
        panel.add(txtStok);
        panel.add(new JLabel("Fiyat:"));
        panel.add(txtFiyat);
        panel.add(new JLabel("Açıklama:"));
        panel.add(txtAciklama);
        
        Object[] secenekler = {"Ekle", "İptal"};
        int result = JOptionPane.showOptionDialog(this, panel, "Yeni Ürün Ekle",
            JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
            null, secenekler, secenekler[0]);
        
        if (result == JOptionPane.YES_OPTION) {
            try {
                String kod = txtKod.getText().trim();
                String marka = txtMarka.getText().trim();
                String kategori = txtKategori.getText().trim();
                String modelAdi = txtModel.getText().trim();
                int numara = Integer.parseInt(txtNumara.getText().trim());
                int stok = Integer.parseInt(txtStok.getText().trim());
                double fiyat = Double.parseDouble(txtFiyat.getText().trim());
                String aciklama = txtAciklama.getText().trim();
                
                if (kod.isEmpty() || marka.isEmpty() || kategori.isEmpty() || modelAdi.isEmpty() || aciklama.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Dosyaya ekle - doğru format
                java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter("urunler.txt", true));
                bw.write(kod + "," + marka + "," + kategori + "," + modelAdi + "," + numara + "," + stok + "," + fiyat + "," + aciklama);
                bw.newLine();
                bw.close();
                
                JOptionPane.showMessageDialog(this, "Ürün başarıyla eklendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                urunleriYukle();
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Numara, Stok ve Fiyat sayısal değer olmalıdır!", "Hata", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void urunDuzenle() {
        int seciliSatir = tablo.getSelectedRow();
        if (seciliSatir == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen düzenlemek için bir ürün seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String kod = (String) model.getValueAt(seciliSatir, 0);
        int stok = (int) model.getValueAt(seciliSatir, 5);
        
        String yeniStok = JOptionPane.showInputDialog(this, "Yeni Stok Miktarı:", stok);
        if (yeniStok != null) {
            try {
                int yeniStokInt = Integer.parseInt(yeniStok);
                
                ArrayList<Product> urunler = ProductManager.tumUrunleriGetir();
                java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter("urunler.txt"));
                
                for (Product p : urunler) {
                    if (p.getKod().equals(kod)) {
                        p.setStok(yeniStokInt);
                    }
                    bw.write(p.getKod() + "," + p.getMarka() + "," + p.getKategori() + "," + p.getModel() + "," + 
                             p.getNumara() + "," + p.getStok() + "," + p.getFiyat() + "," + p.getAciklama());
                    bw.newLine();
                }
                bw.close();
                
                JOptionPane.showMessageDialog(this, "Stok güncellendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                urunleriYukle();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void urunSil() {
        int seciliSatir = tablo.getSelectedRow();
        if (seciliSatir == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir ürün seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String kod = (String) model.getValueAt(seciliSatir, 0);
        
        int onay = JOptionPane.showConfirmDialog(this, 
            "Bu ürünü silmek istediğinize emin misiniz?\n(Silinen ürünler geri alınabilir)", 
            "Silme Onayı", 
            JOptionPane.YES_NO_OPTION);
            
        if (onay == JOptionPane.YES_OPTION) {
            try {
                ArrayList<Product> urunler = ProductManager.tumUrunleriGetir();
                
                // Silinen ürünü kaydet
                for (Product p : urunler) {
                    if (p.getKod().equals(kod)) {
                        java.io.BufferedWriter bwSilinen = new java.io.BufferedWriter(
                            new java.io.FileWriter("silinen_urunler.txt", true));
                        bwSilinen.write(p.getKod() + "," + p.getMarka() + "," + p.getKategori() + "," + p.getModel() + "," + 
                                       p.getNumara() + "," + p.getStok() + "," + p.getFiyat() + "," + p.getAciklama());
                        bwSilinen.newLine();
                        bwSilinen.close();
                        break;
                    }
                }
                
                // Ana dosyadan sil
                java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter("urunler.txt"));
                for (Product p : urunler) {
                    if (!p.getKod().equals(kod)) {
                        bw.write(p.getKod() + "," + p.getMarka() + "," + p.getKategori() + "," + p.getModel() + "," + 
                                p.getNumara() + "," + p.getStok() + "," + p.getFiyat() + "," + p.getAciklama());
                        bw.newLine();
                    }
                }
                bw.close();
                
                JOptionPane.showMessageDialog(this, "Ürün silindi! Geri Al butonundan geri alabilirsiniz.", 
                    "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                urunleriYukle();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void silinenleriGoster() {
        new SilinenUrunlerDialog(this).setVisible(true);
        urunleriYukle(); // Geri alındıysa listeyi yenile
    }
}

// Silinen ürünleri gösteren dialog
class SilinenUrunlerDialog extends JDialog {
    private JTable tablo;
    private DefaultTableModel model;
    private JFrame parent;
    
    public SilinenUrunlerDialog(JFrame parent) {
        super(parent, "Silinen Ürünler", true);
        this.parent = parent;
        
        setSize(1000, 500);
        setLocationRelativeTo(parent);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        // Başlık
        JPanel ustPanel = new JPanel();
        ustPanel.setBackground(new Color(241, 196, 15));
        ustPanel.setPreferredSize(new Dimension(1000, 60));
        
        JLabel lblBaslik = new JLabel("♻️ SİLİNEN ÜRÜNLER");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblBaslik.setForeground(Color.WHITE);
        ustPanel.add(lblBaslik);
        
        add(ustPanel, BorderLayout.NORTH);
        
        // Tablo
        String[] sutunlar = {"Kod", "Marka", "Kategori", "Model", "Numara", "Stok", "Fiyat", "Açıklama"};
        model = new DefaultTableModel(sutunlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablo = new JTable(model);
        tablo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tablo.setRowHeight(25);
        tablo.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        tablo.getTableHeader().setBackground(new Color(241, 196, 15));
        tablo.getTableHeader().setForeground(Color.WHITE);
        
        silinenleriYukle();
        
        JScrollPane scrollPane = new JScrollPane(tablo);
        add(scrollPane, BorderLayout.CENTER);
        
        // Alt panel
        JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        altPanel.setBackground(Color.WHITE);
        altPanel.setPreferredSize(new Dimension(1000, 70));
        
        JButton btnGeriAl = new JButton("✓ Geri Al");
        btnGeriAl.setPreferredSize(new Dimension(150, 45));
        btnGeriAl.setBackground(new Color(46, 204, 113));
        btnGeriAl.setForeground(Color.WHITE);
        btnGeriAl.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnGeriAl.setFocusPainted(false);
        btnGeriAl.setBorderPainted(false);
        btnGeriAl.addActionListener(e -> geriAl());
        
        JButton btnKaliciSil = new JButton("🗑️ Kalıcı Sil");
        btnKaliciSil.setPreferredSize(new Dimension(150, 45));
        btnKaliciSil.setBackground(new Color(231, 76, 60));
        btnKaliciSil.setForeground(Color.WHITE);
        btnKaliciSil.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnKaliciSil.setFocusPainted(false);
        btnKaliciSil.setBorderPainted(false);
        btnKaliciSil.addActionListener(e -> kaliciSil());
        
        JButton btnKapat = new JButton("Kapat");
        btnKapat.setPreferredSize(new Dimension(150, 45));
        btnKapat.setBackground(new Color(127, 140, 141));
        btnKapat.setForeground(Color.WHITE);
        btnKapat.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnKapat.setFocusPainted(false);
        btnKapat.setBorderPainted(false);
        btnKapat.addActionListener(e -> dispose());
        
        altPanel.add(btnGeriAl);
        altPanel.add(btnKaliciSil);
        altPanel.add(btnKapat);
        
        add(altPanel, BorderLayout.SOUTH);
    }
    
    private void silinenleriYukle() {
        model.setRowCount(0);
        
        java.io.File dosya = new java.io.File("silinen_urunler.txt");
        if (!dosya.exists()) {
            model.addRow(new Object[]{"Silinen ürün yok", "", "", "", "", "", "", ""});
            return;
        }
        
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] parca = satir.split(",");
                if (parca.length == 8) {
                    model.addRow(new Object[]{
                        parca[0], parca[1], parca[2], parca[3],
                        Integer.parseInt(parca[4]),
                        Integer.parseInt(parca[5]),
                        String.format("%.2f TL", Double.parseDouble(parca[6])),
                        parca[7]
                    });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void geriAl() {
        int seciliSatir = tablo.getSelectedRow();
        if (seciliSatir == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen geri almak için bir ürün seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String kod = (String) model.getValueAt(seciliSatir, 0);
        
        try {
            // Silinen ürünleri oku
            ArrayList<String> satirlar = new ArrayList<>();
            String geriAlinacak = "";
            
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("silinen_urunler.txt"));
            String satir;
            while ((satir = br.readLine()) != null) {
                if (satir.startsWith(kod + ",")) {
                    geriAlinacak = satir;
                } else {
                    satirlar.add(satir);
                }
            }
            br.close();
            
            if (geriAlinacak.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ürün bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Ana dosyaya ekle
            java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter("urunler.txt", true));
            bw.write(geriAlinacak);
            bw.newLine();
            bw.close();
            
            // Silinen ürünler dosyasını güncelle
            bw = new java.io.BufferedWriter(new java.io.FileWriter("silinen_urunler.txt"));
            for (String s : satirlar) {
                bw.write(s);
                bw.newLine();
            }
            bw.close();
            
            JOptionPane.showMessageDialog(this, "Ürün geri alındı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            silinenleriYukle();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void kaliciSil() {
        int seciliSatir = tablo.getSelectedRow();
        if (seciliSatir == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir ürün seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String kod = (String) model.getValueAt(seciliSatir, 0);
        
        int onay = JOptionPane.showConfirmDialog(this, 
            "Bu ürünü kalıcı olarak silmek istediğinize emin misiniz?\nBu işlem geri alınamaz!", 
            "Kalıcı Silme Onayı", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (onay == JOptionPane.YES_OPTION) {
            try {
                ArrayList<String> satirlar = new ArrayList<>();
                
                java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader("silinen_urunler.txt"));
                String satir;
                while ((satir = br.readLine()) != null) {
                    if (!satir.startsWith(kod + ",")) {
                        satirlar.add(satir);
                    }
                }
                br.close();
                
                java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter("silinen_urunler.txt"));
                for (String s : satirlar) {
                    bw.write(s);
                    bw.newLine();
                }
                bw.close();
                
                JOptionPane.showMessageDialog(this, "Ürün kalıcı olarak silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                silinenleriYukle();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}