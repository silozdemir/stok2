import javax.swing.*;
import java.awt.*;
import java.io.*;

public class GirisEkrani {
    private static JPasswordField textSifre;
    private static JTextField textKullanici;

    public static void main(String[] args) {
        JFrame jf = new JFrame("ShoeStock - Giriş");
        jf.setSize(500, 600);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().setLayout(null);
        jf.getContentPane().setBackground(new Color(0, 215, 215));
        

        JLabel lblLogo = new JLabel("");
        lblLogo.setBackground(new Color(255, 140, 85));
        lblLogo.setBounds(95, 30, 302, 172);
        
        try {
            ImageIcon icon = new ImageIcon(GirisEkrani.class.getResource("/logo.png"));
            Image img = icon.getImage().getScaledInstance(302, 172, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo.setText("Logo Bulunamadı (logo.png)");
            lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
            lblLogo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        jf.getContentPane().add(lblLogo);

  
        JLabel lblBaslik = new JLabel("ShoeStock Uygulaması");
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblBaslik.setHorizontalAlignment(SwingConstants.CENTER);
        lblBaslik.setBounds(60, 213, 381, 56);
        jf.getContentPane().add(lblBaslik);

 
        JLabel lblKullanici = new JLabel("Kullanıcı Adı:");
        lblKullanici.setForeground(Color.WHITE);
        lblKullanici.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblKullanici.setBounds(29, 298, 185, 56);
        jf.getContentPane().add(lblKullanici);

        textKullanici = new JTextField();
        textKullanici.setBackground(new Color(212, 212, 212));
        textKullanici.setBounds(270, 298, 185, 56);
        textKullanici.setFont(new Font("Tahoma", Font.PLAIN, 15));
        jf.getContentPane().add(textKullanici);

        JLabel lblSifre = new JLabel("Şifre:");
        lblSifre.setForeground(Color.WHITE);
        lblSifre.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblSifre.setBounds(29, 383, 185, 56);
        jf.getContentPane().add(lblSifre);

        textSifre = new JPasswordField();
        textSifre.setBackground(new Color(212, 212, 212));
        textSifre.setBounds(270, 383, 185, 56);
        jf.getContentPane().add(textSifre);

   
        JButton butonGiris = new JButton("Giriş Yap");
        butonGiris.setBounds(44, 479, 185, 56);
        butonGiris.setBackground(new Color(155, 89, 182)); 
        butonGiris.setForeground(Color.WHITE);
        butonGiris.setOpaque(true);
        butonGiris.setContentAreaFilled(true);
        butonGiris.setBorderPainted(false);
        butonGiris.setFont(new Font("Tahoma", Font.BOLD, 14));
        jf.getContentPane().add(butonGiris);

        JButton butonKaydol = new JButton("Kayıt Ol");
        butonKaydol.setBounds(270, 479, 185, 56);
        butonKaydol.setBackground(new Color(41, 128, 185)); 
        butonKaydol.setForeground(Color.WHITE);
        butonKaydol.setOpaque(true);
        butonKaydol.setContentAreaFilled(true);
        butonKaydol.setBorderPainted(false);
        butonKaydol.setFont(new Font("Tahoma", Font.BOLD, 14));
        jf.getContentPane().add(butonKaydol);

      
        butonGiris.addActionListener(e -> {
            String ad = textKullanici.getText().trim();
            String sifre = new String(textSifre.getPassword());
            String rol = girisKontrol(ad, sifre);

            if (rol != null) {
 
                JPanel mesajPanel = new JPanel();
                mesajPanel.setLayout(new BoxLayout(mesajPanel, BoxLayout.Y_AXIS));
                mesajPanel.setBackground(Color.WHITE);
                mesajPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                JLabel lblBasarili = new JLabel("Giriş Başarılı!");
                lblBasarili.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblBasarili.setForeground(new Color(46, 204, 113));
                lblBasarili.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel lblHosgeldin = new JLabel("Hoş Geldiniz, " + ad);
                lblHosgeldin.setFont(new Font("Tahoma", Font.PLAIN, 16));
                lblHosgeldin.setForeground(new Color(44, 62, 80));
                lblHosgeldin.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                mesajPanel.add(lblBasarili);
                mesajPanel.add(Box.createVerticalStrut(10));
                mesajPanel.add(lblHosgeldin);
                
                Object[] secenekBasari = {"Tamam"};
                JOptionPane.showOptionDialog(null, mesajPanel, "Başarılı", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
                    null, secenekBasari, secenekBasari[0]);
                jf.dispose();
                
               
                if (rol.equalsIgnoreCase("admin")) {
                    new AdminPaneli(ad);
                } else {
                    new MarkaPaneli(ad);
                }
            } else {
        
                JPanel hataPanel = new JPanel();
                hataPanel.setLayout(new BoxLayout(hataPanel, BoxLayout.Y_AXIS));
                hataPanel.setBackground(Color.WHITE);
                hataPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                JLabel lblHataMesaj = new JLabel("Hatalı Giriş!");
                lblHataMesaj.setFont(new Font("Tahoma", Font.BOLD, 20));
                lblHataMesaj.setForeground(new Color(231, 76, 60));
                lblHataMesaj.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel lblAciklama = new JLabel("Kullanıcı adı veya şifre yanlış.");
                lblAciklama.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblAciklama.setForeground(new Color(44, 62, 80));
                lblAciklama.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                hataPanel.add(lblHataMesaj);
                hataPanel.add(Box.createVerticalStrut(10));
                hataPanel.add(lblAciklama);
                
                Object[] secenekGirisHata = {"Tamam"};
                JOptionPane.showOptionDialog(null, hataPanel, "Hata", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
                    null, secenekGirisHata, secenekGirisHata[0]);
            }
        });

        butonKaydol.addActionListener(e -> {
            try {
                new KayitEkrani().setVisible(true);
            } catch (Exception ex) {
                JPanel hataPanel = new JPanel();
                hataPanel.setLayout(new BoxLayout(hataPanel, BoxLayout.Y_AXIS));
                hataPanel.setBackground(Color.WHITE);
                hataPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
                JLabel lblHataMesaj = new JLabel("Hata");
                lblHataMesaj.setFont(new Font("Tahoma", Font.BOLD, 18));
                lblHataMesaj.setForeground(new Color(230, 126, 34));
                lblHataMesaj.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                JLabel lblAciklama = new JLabel("Kayıt ekranı yüklenemedi!");
                lblAciklama.setFont(new Font("Tahoma", Font.PLAIN, 14));
                lblAciklama.setForeground(new Color(44, 62, 80));
                lblAciklama.setAlignmentX(Component.CENTER_ALIGNMENT);
                
                hataPanel.add(lblHataMesaj);
                hataPanel.add(Box.createVerticalStrut(10));
                hataPanel.add(lblAciklama);
                
                Object[] secenekKayitHata = {"Tamam"};
                JOptionPane.showOptionDialog(null, hataPanel, "Hata", 
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
                    null, secenekKayitHata, secenekKayitHata[0]);
            }
        });

        jf.setVisible(true);
    }

    public static String girisKontrol(String ad, String sifre) {
        File dosya = new File("user.txt");
        if (!dosya.exists()) return null;
        try (BufferedReader br = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = br.readLine()) != null) {
                String[] parca = satir.split(",");
                if (parca.length == 3 && parca[0].equals(ad) && parca[1].equals(sifre)) return parca[2];
            }
        } catch (IOException e) { }
        return null;
    }

}
