import javax.swing.*;
import java.awt.*;

public class UrunDetayDialog extends JDialog {
    
    public UrunDetayDialog(JFrame parent, Product urun, String kullaniciAdi) {
        super(parent, "Ürün Detayları", true);
        setSize(500, 600);
        setLocationRelativeTo(parent);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
   
        JPanel baslikPanel = new JPanel();
        baslikPanel.setBackground(new Color(155, 89, 182));
        baslikPanel.setPreferredSize(new Dimension(500, 80));
        
        JLabel lblBaslik = new JLabel("ÜRÜN DETAYLARI");
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblBaslik.setForeground(Color.WHITE);
        baslikPanel.add(lblBaslik);
        
        add(baslikPanel, BorderLayout.NORTH);
        
    
        JPanel detayPanel = new JPanel();
        detayPanel.setLayout(new BoxLayout(detayPanel, BoxLayout.Y_AXIS));
        detayPanel.setBackground(Color.WHITE);
        detayPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        
        detayPanel.add(bilgiSatiriOlustur("Ürün Kodu:", urun.getKod()));
        detayPanel.add(Box.createVerticalStrut(15));
        
        detayPanel.add(bilgiSatiriOlustur("Marka:", urun.getMarka()));
        detayPanel.add(Box.createVerticalStrut(15));
        
        detayPanel.add(bilgiSatiriOlustur("Model:", urun.getModel()));
        detayPanel.add(Box.createVerticalStrut(15));
        
        detayPanel.add(bilgiSatiriOlustur("Kategori:", urun.getKategori()));
        detayPanel.add(Box.createVerticalStrut(15));
        
        detayPanel.add(bilgiSatiriOlustur("Numara:", String.valueOf(urun.getNumara())));
        detayPanel.add(Box.createVerticalStrut(15));
        
        detayPanel.add(bilgiSatiriOlustur("Fiyat:", String.format("%.2f TL", urun.getFiyat())));
        detayPanel.add(Box.createVerticalStrut(15));
        
    

        String stokDurum;
        if (urun.getStok() == 0) {
            stokDurum = "STOKTA YOK!";
        } else if (urun.getStok() < 10) {
            stokDurum = urun.getStok() + " adet ( KRİTİK STOK!)";
        } else {
            stokDurum = urun.getStok() + " adet ";
        }
        detayPanel.add(bilgiSatiriOlustur("Stok:", stokDurum));
        
       
        JLabel lblAciklamaBaslik = new JLabel("Açıklama:");
        lblAciklamaBaslik.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblAciklamaBaslik.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextArea txtAciklama = new JTextArea(urun.getAciklama());
        txtAciklama.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtAciklama.setLineWrap(true);
        txtAciklama.setWrapStyleWord(true);
        txtAciklama.setEditable(false);
        txtAciklama.setBackground(new Color(240, 240, 240));
        txtAciklama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        txtAciklama.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        detayPanel.add(lblAciklamaBaslik);
        detayPanel.add(Box.createVerticalStrut(10));
        detayPanel.add(txtAciklama);
        
        add(detayPanel, BorderLayout.CENTER);
        
        
        JPanel altPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        altPanel.setBackground(Color.WHITE);
        
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
    
    private JPanel bilgiSatiriOlustur(String etiket, String deger) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(400, 30));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblEtiket = new JLabel(etiket);
        lblEtiket.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblEtiket.setPreferredSize(new Dimension(120, 25));
        
        JLabel lblDeger = new JLabel(deger);
        lblDeger.setFont(new Font("Tahoma", Font.PLAIN, 14));
        
        panel.add(lblEtiket, BorderLayout.WEST);
        panel.add(lblDeger, BorderLayout.CENTER);
        
        return panel;
    }
}