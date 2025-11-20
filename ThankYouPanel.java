import java.awt.*;
import javax.swing.*;

public class ThankYouPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    
    public ThankYouPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        setBackground(new Color(240, 253, 244));
        
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(240, 253, 244));
        
        JPanel card = new JPanel(); 
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(50, 60, 50, 60)
        ));
        
        // Success icon
        JLabel icon = new JLabel("✅");
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 80));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Pendaftaran Berhasil!");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 26));
        title.setForeground(new Color(6, 78, 59));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea message = new JTextArea("Terima kasih telah mendaftar beasiswa.\n\nData Anda telah kami terima dan akan segera diverifikasi oleh tim admin.\n\nSilakan pantau status pendaftaran Anda secara berkala.");
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        message.setForeground(new Color(75, 85, 99));
        message.setBackground(Color.WHITE);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        message.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton homeBtn = new JButton("🏠 Kembali ke Beranda"); 
        homeBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setBackground(new Color(16, 185, 129));
        homeBtn.setBorderPainted(false);
        homeBtn.setFocusPainted(false);
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        homeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeBtn.addActionListener(e -> win.show("home"));
        
        homeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeBtn.setBackground(new Color(5, 150, 105));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeBtn.setBackground(new Color(16, 185, 129));
            }
        });
        
        JButton statusBtn = new JButton("📊 Lihat Status Pendaftaran");
        statusBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
        statusBtn.setForeground(new Color(16, 185, 129));
        statusBtn.setBackground(Color.WHITE);
        statusBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(16, 185, 129), 2, true),
            BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));
        statusBtn.setFocusPainted(false);
        statusBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        statusBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBtn.addActionListener(e -> win.show("status"));
        
        statusBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                statusBtn.setBackground(new Color(240, 253, 244));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                statusBtn.setBackground(Color.WHITE);
            }
        });
        
        card.add(icon);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(message);
        card.add(Box.createRigidArea(new Dimension(0, 30)));
        card.add(homeBtn);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(statusBtn);
        
        center.add(card);
        add(center, BorderLayout.CENTER);
    }
}