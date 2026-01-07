import javax.swing.*;
import java.awt.*;
import java.util.*;

public class KatagoriPaneli extends JFrame {
    private String kullaniciAdi;
    private String marka;
    
    public KatagoriPaneli(String kullaniciAdi, String marka) {
        this.kullaniciAdi = kullaniciAdi;
        this.marka = marka;
        
        setTitle("ShoeStock - " + marka.toUpperCase() + " Kategorileri");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0, 215, 215));
        
        
        JPanel ustPanel = new JPanel(new BorderLayout());
        ustPanel.setBackground(new Color(0, 215, 215));
        ustPanel.setPreferredSize(new Dimension(800, 80));
        
        JButton btnGeri = new JButton("← Geri");
        btnGeri.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnGeri.setBackground(new Color(41, 128, 185));
        btnGeri.setForeground(Color.WHITE);
        btnGeri.setFocusPainted(false);
        btnGeri.setBorderPainted(false);
        btnGeri.setPreferredSize(new Dimension(100, 40));
        btnGeri.addActionListener(e -> {
            dispose();
            new MarkaPaneli(kullaniciAdi);
        });
        
        JLabel lblBaslik = new JLabel(marka.toUpperCase() + " - KATEGORİLER");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 26));
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        
        ustPanel.add(btnGeri, BorderLayout.WEST);
        ustPanel.add(lblBaslik, BorderLayout.CENTER);
        ustPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        add(ustPanel, BorderLayout.NORTH);
        
        
        JPanel ortaPanel = new JPanel();
        ortaPanel.setBackground(new Color(0, 215, 215));
        ortaPanel.setLayout(new GridLayout(2, 2, 20, 20));
        ortaPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        Map<String, ArrayList<Product>> kategoriMap = ProductManager.kategorilereGrupla(marka);
        
       
        Color[] renkler = {
            new Color(155, 89, 182),  
            new Color(52, 152, 219), 
            new Color(46, 204, 113), 
            new Color(241, 196, 15)   
        };
        
        int renkIndex = 0;
        for (String kategori : kategoriMap.keySet()) {
            JButton btnKategori = new JButton(kategori.toUpperCase());
            btnKategori.setFont(new Font("Tahoma", Font.BOLD, 22));
            btnKategori.setBackground(renkler[renkIndex % renkler.length]);
            btnKategori.setForeground(Color.WHITE);
            btnKategori.setFocusPainted(false);
            btnKategori.setBorderPainted(false);
            btnKategori.setPreferredSize(new Dimension(300, 150));
            
            int urunSayisi = kategoriMap.get(kategori).size();
            btnKategori.setToolTipText(urunSayisi + " ürün mevcut ");
            
            btnKategori.addActionListener(e -> {
                dispose();
                new UrunListesiPaneli(kullaniciAdi, marka, kategori);
            });
            
            ortaPanel.add(btnKategori);
            renkIndex++;
        }
        
        add(ortaPanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
}