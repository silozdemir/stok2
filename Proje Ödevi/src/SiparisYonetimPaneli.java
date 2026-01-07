import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class SiparisYonetimPaneli extends JFrame {
    private JTable tablo;
    private DefaultTableModel model;
    
    public SiparisYonetimPaneli() {
        setTitle("Sipariş Yönetimi");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 215, 215));
        
        // ÜST PANEL
        JPanel ustPanel = new JPanel();
        ustPanel.setBackground(new Color(0, 215, 215));
        ustPanel.setPreferredSize(new Dimension(1100, 80));
        
        JLabel lblBaslik = new JLabel("SİPARİŞ YÖNETİMİ");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);
        ustPanel.add(lblBaslik);
        
        add(ustPanel, BorderLayout.NORTH);
        
        // TABLO
        String[] sutunlar = {"Kullanıcı", "Ürün Kodu", "Model", "Adet", "Toplam Fiyat", "Tarih"};
        model = new DefaultTableModel(sutunlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablo = new JTable(model);
        tablo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tablo.setRowHeight(28);
        tablo.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
        tablo.getTableHeader().setBackground(new Color(46, 204, 113));
        tablo.getTableHeader().setForeground(Color.WHITE);
        tablo.setSelectionBackground(new Color(41, 128, 185));
        tablo.setSelectionForeground(Color.WHITE);
        
        siparisleriYukle();
        
        JScrollPane scrollPane = new JScrollPane(tablo);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);
        
        // ALT PANEL
        JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        altPanel.setBackground(new Color(0, 215, 215));
        altPanel.setPreferredSize(new Dimension(1100, 80));
        
        JButton btnYenile = new JButton("🔄 Yenile");
        btnYenile.setPreferredSize(new Dimension(150, 50));
        btnYenile.setBackground(new Color(52, 152, 219));
        btnYenile.setForeground(Color.WHITE);
        btnYenile.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnYenile.setFocusPainted(false);
        btnYenile.setBorderPainted(false);
        btnYenile.addActionListener(e -> siparisleriYukle());
        
        JButton btnSil = new JButton("🗑️ Sil");
        btnSil.setPreferredSize(new Dimension(150, 50));
        btnSil.setBackground(new Color(231, 76, 60));
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnSil.setFocusPainted(false);
        btnSil.setBorderPainted(false);
        btnSil.addActionListener(e -> siparisSil());
        
        JButton btnKapat = new JButton("Kapat");
        btnKapat.setPreferredSize(new Dimension(150, 50));
        btnKapat.setBackground(new Color(127, 140, 141));
        btnKapat.setForeground(Color.WHITE);
        btnKapat.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnKapat.setFocusPainted(false);
        btnKapat.setBorderPainted(false);
        btnKapat.addActionListener(e -> dispose());
        
        altPanel.add(btnYenile);
        altPanel.add(btnSil);
        altPanel.add(btnKapat);
        
        add(altPanel, BorderLayout.SOUTH);
    }
    
    private void siparisleriYukle() {
        model.setRowCount(0);
        
        File dosya = new File("siparisler.txt");
        if (!dosya.exists()) {
            model.addRow(new Object[]{"Henüz sipariş yok", "", "", "", "", ""});
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] parca = satir.split(",");
                if (parca.length == 6) {
                    model.addRow(new Object[]{
                        parca[0], // Kullanıcı
                        parca[1], // Ürün Kodu
                        parca[2], // Model
                        parca[3], // Adet
                        String.format("%.2f TL", Double.parseDouble(parca[4])), // Toplam Fiyat
                        parca[5]  // Tarih
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void siparisSil() {
        int seciliSatir = tablo.getSelectedRow();
        if (seciliSatir == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir sipariş seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int onay = JOptionPane.showConfirmDialog(this, 
            "Bu siparişi silmek istediğinize emin misiniz?", 
            "Silme Onayı", 
            JOptionPane.YES_NO_OPTION);
            
        if (onay == JOptionPane.YES_OPTION) {
            try {
                ArrayList<String> satirlar = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader("siparisler.txt"));
                String satir;
                int satirNo = 0;
                while ((satir = br.readLine()) != null) {
                    if (satirNo != seciliSatir) {
                        satirlar.add(satir);
                    }
                    satirNo++;
                }
                br.close();
                
                BufferedWriter bw = new BufferedWriter(new FileWriter("siparisler.txt"));
                for (String s : satirlar) {
                    bw.write(s);
                    bw.newLine();
                }
                bw.close();
                
                JOptionPane.showMessageDialog(this, "Sipariş silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                siparisleriYukle();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}