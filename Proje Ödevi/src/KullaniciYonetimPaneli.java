import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class KullaniciYonetimPaneli extends JFrame {
    private JTable tablo;
    private DefaultTableModel model;
    
    public KullaniciYonetimPaneli() {
        setTitle("Kullanıcı Yönetimi");
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 215, 215));
        
        // ÜST PANEL
        JPanel ustPanel = new JPanel();
        ustPanel.setBackground(new Color(0, 215, 215));
        ustPanel.setPreferredSize(new Dimension(800, 80));
        
        JLabel lblBaslik = new JLabel("KULLANICI YÖNETİMİ");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);
        ustPanel.add(lblBaslik);
        
        add(ustPanel, BorderLayout.NORTH);
        
        // TABLO
        String[] sutunlar = {"Kullanıcı Adı", "Şifre", "Rol"};
        model = new DefaultTableModel(sutunlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablo = new JTable(model);
        tablo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tablo.setRowHeight(30);
        tablo.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        tablo.getTableHeader().setBackground(new Color(52, 152, 219));
        tablo.getTableHeader().setForeground(Color.WHITE);
        tablo.setSelectionBackground(new Color(41, 128, 185));
        tablo.setSelectionForeground(Color.WHITE);
        
        kullanicilariYukle();
        
        JScrollPane scrollPane = new JScrollPane(tablo);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);
        
        // ALT PANEL
        JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        altPanel.setBackground(new Color(0, 215, 215));
        altPanel.setPreferredSize(new Dimension(800, 80));
        
        JButton btnSil = new JButton("🗑️ Kullanıcı Sil");
        btnSil.setPreferredSize(new Dimension(180, 50));
        btnSil.setBackground(new Color(231, 76, 60));
        btnSil.setForeground(Color.WHITE);
        btnSil.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnSil.setFocusPainted(false);
        btnSil.setBorderPainted(false);
        btnSil.addActionListener(e -> kullaniciSil());
        
        JButton btnKapat = new JButton("Kapat");
        btnKapat.setPreferredSize(new Dimension(150, 50));
        btnKapat.setBackground(new Color(127, 140, 141));
        btnKapat.setForeground(Color.WHITE);
        btnKapat.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnKapat.setFocusPainted(false);
        btnKapat.setBorderPainted(false);
        btnKapat.addActionListener(e -> dispose());
        
        altPanel.add(btnSil);
        altPanel.add(btnKapat);
        
        add(altPanel, BorderLayout.SOUTH);
    }
    
    private void kullanicilariYukle() {
        model.setRowCount(0);
        
        File dosya = new File("user.txt");
        if (!dosya.exists()) return;
        
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] parca = satir.split(",");
                if (parca.length == 3) {
                    model.addRow(new Object[]{parca[0], parca[1], parca[2]});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void kullaniciSil() {
        int seciliSatir = tablo.getSelectedRow();
        if (seciliSatir == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir kullanıcı seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String kullaniciAdi = (String) model.getValueAt(seciliSatir, 0);
        
        int onay = JOptionPane.showConfirmDialog(this, 
            "'" + kullaniciAdi + "' kullanıcısını silmek istediğinize emin misiniz?", 
            "Silme Onayı", 
            JOptionPane.YES_NO_OPTION);
            
        if (onay == JOptionPane.YES_OPTION) {
            try {
                ArrayList<String> satirlar = new ArrayList<>();
                BufferedReader br = new BufferedReader(new FileReader("user.txt"));
                String satir;
                while ((satir = br.readLine()) != null) {
                    if (!satir.startsWith(kullaniciAdi + ",")) {
                        satirlar.add(satir);
                    }
                }
                br.close();
                
                BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt"));
                for (String s : satirlar) {
                    bw.write(s);
                    bw.newLine();
                }
                bw.close();
                
                JOptionPane.showMessageDialog(this, "Kullanıcı silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                kullanicilariYukle();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}