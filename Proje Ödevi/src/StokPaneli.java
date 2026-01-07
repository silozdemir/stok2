import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StokPaneli {
    public StokPaneli(String rol) {
        JFrame frame = new JFrame("Stok Yönetim Paneli - " + rol.toUpperCase());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        

        String[] sutunlar = {"Kod", "Marka", "Kategori", "Model", "Stok"};
        DefaultTableModel model = new DefaultTableModel(sutunlar, 0);
        JTable tablo = new JTable(model);
        
        frame.add(new JScrollPane(tablo), BorderLayout.CENTER);

     
        JPanel altPanel = new JPanel();
        if (rol.equals("admin")) {
            JButton btnEkle = new JButton("Yeni Ürün Ekle ");
            altPanel.add(btnEkle);
        } else {
            altPanel.add(new JLabel("Görüntüleme Modu (Müşteri)"));
        }

        frame.add(altPanel, BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}