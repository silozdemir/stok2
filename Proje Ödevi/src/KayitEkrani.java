import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.io.*;

public class KayitEkrani extends JFrame {
    private JTextField txtYeniAd;
    private JPasswordField txtYeniSifre;
    private static final String ADMIN_KODU = "SHOESTOCK"; 

    public KayitEkrani() {
        setTitle("Yeni Kullanıcı Kaydı");
        setSize(400, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(0, 215, 215));

        JLabel lblTitle = new JLabel("KAYIT OL");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(150, 30, 100, 30);
        getContentPane().add(lblTitle);

        JLabel lblAd = new JLabel("Kullanıcı Adı:");
        lblAd.setForeground(Color.WHITE);
        lblAd.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblAd.setBounds(50, 100, 100, 40);
        getContentPane().add(lblAd);

        txtYeniAd = new JTextField();
        txtYeniAd.setBounds(150, 100, 180, 40);
        txtYeniAd.setFont(new Font("Tahoma", Font.PLAIN, 13));
        getContentPane().add(txtYeniAd);

        JLabel lblSifre = new JLabel("Şifre:");
        lblSifre.setForeground(Color.WHITE);
        lblSifre.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblSifre.setBounds(50, 160, 100, 40);
        getContentPane().add(lblSifre);

        txtYeniSifre = new JPasswordField();
        txtYeniSifre.setBounds(150, 160, 180, 40);
        getContentPane().add(txtYeniSifre);

        JLabel lblRol = new JLabel("Rol:");
        lblRol.setForeground(Color.WHITE);
        lblRol.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblRol.setBounds(50, 220, 100, 40);
        getContentPane().add(lblRol);

        String[] roller = {"Müşteri", "Admin"};
        JComboBox<String> comboRol = new JComboBox<>(roller);
        comboRol.setBounds(150, 220, 180, 40);
        comboRol.setFont(new Font("Tahoma", Font.PLAIN, 13));

        comboRol.setBackground(Color.WHITE);
        comboRol.setForeground(new Color(44, 62, 80));
        
        comboRol.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Tahoma", Font.BOLD, 13));
                
                if (value.equals("Müşteri")) {
                    if (isSelected) {
                        label.setBackground(new Color(52, 152, 219));
                        label.setForeground(Color.WHITE);
                    } else {
                        label.setBackground(new Color(174, 214, 241));
                        label.setForeground(new Color(44, 62, 80));
                    }
                } else if (value.equals("Admin")) {
                    if (isSelected) {
                        label.setBackground(new Color(231, 76, 60));
                        label.setForeground(Color.WHITE);
                    } else {
                        label.setBackground(new Color(245, 183, 177));
                        label.setForeground(new Color(44, 62, 80));
                    }
                }
                
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return label;
            }
        });
        
        getContentPane().add(comboRol);

        JButton btnKaydet = new JButton("KAYDET");
        btnKaydet.setBounds(100, 330, 200, 50);
        btnKaydet.setBackground(new Color(46, 204, 113));
        btnKaydet.setForeground(Color.WHITE);
        btnKaydet.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnKaydet.setFocusPainted(false);
        btnKaydet.setBorderPainted(false);
        getContentPane().add(btnKaydet);

        JButton btnIptal = new JButton("İPTAL");
        btnIptal.setBounds(100, 390, 200, 40);
        btnIptal.setBackground(new Color(231, 76, 60));
        btnIptal.setForeground(Color.WHITE);
        btnIptal.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnIptal.setFocusPainted(false);
        btnIptal.setBorderPainted(false);
        btnIptal.addActionListener(e -> dispose());
        getContentPane().add(btnIptal);

        btnKaydet.addActionListener(e -> {
            String ad = txtYeniAd.getText().trim();
            String sifre = new String(txtYeniSifre.getPassword());
            String rol = (String) comboRol.getSelectedItem();

            if (ad.isEmpty() || sifre.isEmpty()) {
                JPanel uyariPanel = new JPanel();
                uyariPanel.setLayout(new BoxLayout(uyariPanel, BoxLayout.Y_AXIS));
                uyariPanel.setBackground(Color.WHITE);
                uyariPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                JLabel lblUyariBaslik = new JLabel("Uyarı");
                lblUyariBaslik.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblUyariBaslik.setForeground(new Color(230, 126, 34));
                lblUyariBaslik.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel lblUyariMesaj = new JLabel("Lütfen tüm alanları doldurun!");
                lblUyariMesaj.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblUyariMesaj.setForeground(new Color(44, 62, 80));
                lblUyariMesaj.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                uyariPanel.add(lblUyariBaslik);
                uyariPanel.add(Box.createVerticalStrut(10));
                uyariPanel.add(lblUyariMesaj);
                
                Object[] secenekUyari = {"Tamam"};
                JOptionPane.showOptionDialog(this, uyariPanel, "Uyarı", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
                    null, secenekUyari, secenekUyari[0]);
                return;
            }

            if (kullaniciVarMi(ad)) {
                JPanel hataPanel = new JPanel();
                hataPanel.setLayout(new BoxLayout(hataPanel, BoxLayout.Y_AXIS));
                hataPanel.setBackground(Color.WHITE);
                hataPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                JLabel lblHataBaslik = new JLabel("Hata");
                lblHataBaslik.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblHataBaslik.setForeground(new Color(231, 76, 60));
                lblHataBaslik.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel lblHataMesaj = new JLabel("Bu kullanıcı adı zaten kullanılıyor!");
                lblHataMesaj.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblHataMesaj.setForeground(new Color(44, 62, 80));
                lblHataMesaj.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                hataPanel.add(lblHataBaslik);
                hataPanel.add(Box.createVerticalStrut(10));
                hataPanel.add(lblHataMesaj);
                
                Object[] secenekHata = {"Tamam"};
                JOptionPane.showOptionDialog(this, hataPanel, "Hata", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
                    null, secenekHata, secenekHata[0]);
                return;
            }

            if (rol.equalsIgnoreCase("Admin")) {
                JPanel kodPanel = new JPanel();
                kodPanel.setLayout(new BoxLayout(kodPanel, BoxLayout.Y_AXIS));
                kodPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                JLabel lblMesaj = new JLabel("Admin kaydı için özel kod gerekmektedir.");
                lblMesaj.setFont(new Font("Tahoma", Font.BOLD, 13));
                lblMesaj.setForeground(new Color(231, 76, 60));
                lblMesaj.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel lblAciklama = new JLabel("Lütfen admin kodunu girin:");
                lblAciklama.setFont(new Font("Tahoma", Font.PLAIN, 12));
                lblAciklama.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JPasswordField txtKod = new JPasswordField(20);
                txtKod.setMaximumSize(new Dimension(200, 30));
                txtKod.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                kodPanel.add(lblMesaj);
                kodPanel.add(Box.createVerticalStrut(10));
                kodPanel.add(lblAciklama);
                kodPanel.add(Box.createVerticalStrut(10));
                kodPanel.add(txtKod);

                Object[] secenekler = {"Tamam", "İptal"};
                int result = JOptionPane.showOptionDialog(this,
                    kodPanel,
                    "Admin Kod Doğrulama",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    secenekler,
                    secenekler[0]);
                
                if (result == JOptionPane.YES_OPTION) {
                    String girilenKod = new String(txtKod.getPassword());
                    
                    if (!girilenKod.equals(ADMIN_KODU)) {
                        JPanel hataPanel2 = new JPanel();
                        hataPanel2.setLayout(new BoxLayout(hataPanel2, BoxLayout.Y_AXIS));
                        hataPanel2.setBackground(Color.WHITE);
                        hataPanel2.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                        
                        JLabel lblAdminHata = new JLabel("Yetkisiz Erişim");
                        lblAdminHata.setFont(new Font("Tahoma", Font.BOLD, 20));
                        lblAdminHata.setForeground(new Color(231, 76, 60));
                        lblAdminHata.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        JLabel lblAdminMesaj = new JLabel("Hatalı Admin Kodu!");
                        lblAdminMesaj.setFont(new Font("Tahoma", Font.BOLD, 14));
                        lblAdminMesaj.setForeground(new Color(44, 62, 80));
                        lblAdminMesaj.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        JLabel lblAdminAciklama = new JLabel("Kayıt işlemi iptal edildi.");
                        lblAdminAciklama.setFont(new Font("Tahoma", Font.PLAIN, 13));
                        lblAdminAciklama.setForeground(new Color(127, 140, 141));
                        lblAdminAciklama.setAlignmentX(Component.CENTER_ALIGNMENT);
                        
                        hataPanel2.add(lblAdminHata);
                        hataPanel2.add(Box.createVerticalStrut(10));
                        hataPanel2.add(lblAdminMesaj);
                        hataPanel2.add(Box.createVerticalStrut(5));
                        hataPanel2.add(lblAdminAciklama);
                        
                        Object[] secenekAdminHata = {"Tamam"};
                        JOptionPane.showOptionDialog(this, hataPanel2, "Yetkisiz Erişim", 
                            JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
                            null, secenekAdminHata, secenekAdminHata[0]);
                        return;
                    }
                } else {
                    JPanel iptalPanel = new JPanel();
                    iptalPanel.setLayout(new BoxLayout(iptalPanel, BoxLayout.Y_AXIS));
                    iptalPanel.setBackground(Color.WHITE);
                    iptalPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                    
                    JLabel lblIptalBaslik = new JLabel("Bilgi");
                    lblIptalBaslik.setFont(new Font("Tahoma", Font.BOLD, 18));
                    lblIptalBaslik.setForeground(new Color(52, 152, 219));
                    lblIptalBaslik.setAlignmentX(Component.CENTER_ALIGNMENT);
                    
                    JLabel lblIptalMesaj = new JLabel("Kayıt işlemi iptal edildi.");
                    lblIptalMesaj.setFont(new Font("Tahoma", Font.PLAIN, 14));
                    lblIptalMesaj.setForeground(new Color(44, 62, 80));
                    lblIptalMesaj.setAlignmentX(Component.CENTER_ALIGNMENT);
                    
                    iptalPanel.add(lblIptalBaslik);
                    iptalPanel.add(Box.createVerticalStrut(10));
                    iptalPanel.add(lblIptalMesaj);
                    
                    Object[] secenekIptal = {"Tamam"};
                    JOptionPane.showOptionDialog(this, iptalPanel, "İptal", 
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
                        null, secenekIptal, secenekIptal[0]);
                    return;
                }
            }

            rol = rol.equalsIgnoreCase("Admin") ? "admin" : "musteri";
            
            if (kaydet(ad, sifre, rol)) {
                JPanel basariPanel = new JPanel();
                basariPanel.setLayout(new BoxLayout(basariPanel, BoxLayout.Y_AXIS));
                basariPanel.setBackground(Color.WHITE);
                basariPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                JLabel lblBasariBaslik = new JLabel("Başarılı");
                lblBasariBaslik.setFont(new Font("Tahoma", Font.BOLD, 22));
                lblBasariBaslik.setForeground(new Color(46, 204, 113));
                lblBasariBaslik.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel lblBasariMesaj1 = new JLabel(rol.equals("admin") ? "Admin kaydınız başarıyla oluşturuldu!" : "Kayıt başarılı!");
                lblBasariMesaj1.setFont(new Font("Tahoma", Font.BOLD, 14));
                lblBasariMesaj1.setForeground(new Color(44, 62, 80));
                lblBasariMesaj1.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel lblBasariMesaj2 = new JLabel(rol.equals("admin") ? "Artık admin paneline erişebilirsiniz." : "Artık giriş yapabilirsiniz.");
                lblBasariMesaj2.setFont(new Font("Tahoma", Font.PLAIN, 13));
                lblBasariMesaj2.setForeground(new Color(127, 140, 141));
                lblBasariMesaj2.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                basariPanel.add(lblBasariBaslik);
                basariPanel.add(Box.createVerticalStrut(10));
                basariPanel.add(lblBasariMesaj1);
                basariPanel.add(Box.createVerticalStrut(5));
                basariPanel.add(lblBasariMesaj2);
                
                Object[] secenekBasari = {"Tamam"};
                JOptionPane.showOptionDialog(this, basariPanel, "Başarılı", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
                    null, secenekBasari, secenekBasari[0]);
                dispose();
            } else {
                JPanel kayitHataPanel = new JPanel();
                kayitHataPanel.setLayout(new BoxLayout(kayitHataPanel, BoxLayout.Y_AXIS));
                kayitHataPanel.setBackground(Color.WHITE);
                kayitHataPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                JLabel lblKayitHata = new JLabel("Hata");
                lblKayitHata.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblKayitHata.setForeground(new Color(231, 76, 60));
                lblKayitHata.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel lblKayitMesaj = new JLabel("Kayıt sırasında bir hata oluştu!");
                lblKayitMesaj.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblKayitMesaj.setForeground(new Color(44, 62, 80));
                lblKayitMesaj.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                kayitHataPanel.add(lblKayitHata);
                kayitHataPanel.add(Box.createVerticalStrut(10));
                kayitHataPanel.add(lblKayitMesaj);
                
                Object[] secenekKayitHata = {"Tamam"};
                JOptionPane.showOptionDialog(this, kayitHataPanel, "Hata", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
                    null, secenekKayitHata, secenekKayitHata[0]);
            }
        });
    }

    private boolean kullaniciVarMi(String kullaniciAdi) {
        File dosya = new File("user.txt");
        if (!dosya.exists()) return false;
        
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] parca = satir.split(",");
                if (parca.length >= 1 && parca[0].equals(kullaniciAdi)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean kaydet(String ad, String sifre, String rol) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("user.txt", true))) {
            bw.write(ad + "," + sifre + "," + rol);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
