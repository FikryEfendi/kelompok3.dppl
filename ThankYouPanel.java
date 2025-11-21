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
        center.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JPanel card = new JPanel(); 
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(60, 70, 60, 70)
        ));
        
        // Success icon
        JLabel icon = new JLabel("✅");
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 90));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Pendaftaran Berhasil!");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 30));
        title.setForeground(new Color(6, 78, 59));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);
        messagePanel.setMaximumSize(new Dimension(500, 150));
        
        JLabel msg1 = new JLabel("Terima kasih telah mendaftar beasiswa.");
        msg1.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
        msg1.setForeground(new Color(75, 85, 99));
        msg1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel msg2 = new JLabel("Data Anda telah kami terima dan akan segera");
        msg2.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
        msg2.setForeground(new Color(75, 85, 99));
        msg2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel msg3 = new JLabel("diverifikasi oleh tim admin.");
        msg3.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
        msg3.setForeground(new Color(75, 85, 99));
        msg3.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel msg4 = new JLabel("Silakan pantau status pendaftaran Anda secara berkala.");
        msg4.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
        msg4.setForeground(new Color(75, 85, 99));
        msg4.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        messagePanel.add(msg1);
        messagePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        messagePanel.add(msg2);
        messagePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        messagePanel.add(msg3);
        messagePanel.add(Box.createRigidArea(new Dimension(0, 15)));
        messagePanel.add(msg4);
        
        JButton homeBtn = new JButton("🏠 Kembali ke Beranda"); 
        homeBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setBackground(new Color(16, 185, 129));
        homeBtn.setBorderPainted(false);
        homeBtn.setFocusPainted(false);
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.setBorder(BorderFactory.createEmptyBorder(16, 45, 16, 45));
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
        statusBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        statusBtn.setForeground(new Color(16, 185, 129));
        statusBtn.setBackground(Color.WHITE);
        statusBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(16, 185, 129), 2, true),
            BorderFactory.createEmptyBorder(14, 35, 14, 35)
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
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(title);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(messagePanel);
        card.add(Box.createRigidArea(new Dimension(0, 35)));
        card.add(homeBtn);
        card.add(Box.createRigidArea(new Dimension(0, 18)));
        card.add(statusBtn);
        
        center.add(card);
        add(center, BorderLayout.CENTER);
    }
}