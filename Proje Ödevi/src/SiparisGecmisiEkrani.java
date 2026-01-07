import javax.swing.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SiparisGecmisiEkrani extends JFrame {
    
    public SiparisGecmisiEkrani(String kullaniciAdi) {
        setTitle("Siparişlerim - " + kullaniciAdi);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 215, 215));
        
       
        JPanel ustPanel = new JPanel();
        ustPanel.setBackground(new Color(0, 215, 215));
        ustPanel.setPreferredSize(new Dimension(800, 80));
        
        JLabel lblBaslik = new JLabel("SİPARİŞ GEÇMİŞİM");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);
        ustPanel.add(lblBaslik);
        
        add(ustPanel, BorderLayout.NORTH);
        
        
        String[] sutunlar = {"Ürün Kodu", "Model", "Adet", "Toplam Fiyat", "Tarih"};
        DefaultTableModel model = new DefaultTableModel(sutunlar, 0) {
            
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable tablo = new JTable(model);
        tablo.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tablo.setRowHeight(30);
        tablo.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
        tablo.getTableHeader().setBackground(new Color(155, 89, 182));
        tablo.getTableHeader().setForeground(Color.WHITE);
        tablo.setSelectionBackground(new Color(41, 128, 185));
        tablo.setSelectionForeground(Color.WHITE);
        
       
        ArrayList<String> siparisler = ProductManager.kullaniciSiparisleriGetir(kullaniciAdi);
        
        if (siparisler.isEmpty()) {
            model.addRow(new Object[]{"Henüz sipariş yok", "", "", "", ""});
        } else {
            for (String siparis : siparisler) {
                String[] parca = siparis.split(",");
               
                if (parca.length == 6) {
                    model.addRow(new Object[]{
                        parca[1], 
                        parca[2], 
                        parca[3], 
                        String.format("%.2f TL", Double.parseDouble(parca[4])), 
                        parca[5]  
                    });
                }
            }
        }
        
        JScrollPane scrollPane = new JScrollPane(tablo);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scrollPane, BorderLayout.CENTER);
        
      
        JPanel altPanel = new JPanel();
        altPanel.setBackground(new Color(0, 215, 215));
        altPanel.setPreferredSize(new Dimension(800, 70));
        
        JButton btnKapat = new JButton("Kapat");
        btnKapat.setPreferredSize(new Dimension(150, 45));
        btnKapat.setBackground(new Color(41, 128, 185));
        btnKapat.setForeground(Color.WHITE);
        btnKapat.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnKapat.setFocusPainted(false);
        btnKapat.setBorderPainted(false);
        btnKapat.addActionListener(e -> dispose());
        
        altPanel.add(btnKapat);
        add(altPanel, BorderLayout.SOUTH);
    }
}