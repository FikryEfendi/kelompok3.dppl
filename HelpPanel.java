import java.awt.*;
import javax.swing.*;

public class HelpPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    JTextField subject; 
    JTextArea body;
    
    public HelpPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(16, 185, 129));
        top.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        
        JButton back = new JButton("← Kembali"); 
        back.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(5, 150, 105));
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        back.addActionListener(e -> win.show("home")); 
        
        JLabel titleLabel = new JLabel("💬 Bantuan & Keluhan");
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        top.add(back, BorderLayout.WEST);
        top.add(titleLabel, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainContent.setBackground(new Color(240, 253, 244));
        
        JPanel form = new JPanel(); 
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS)); 
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(35, 35, 35, 35)
        ));
        form.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel title = new JLabel("Sampaikan Keluhan Anda");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 22));
        title.setForeground(new Color(6, 78, 59));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(title);
        
        JLabel subtitle = new JLabel("Kami siap membantu menyelesaikan masalah Anda");
        subtitle.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        subtitle.setForeground(new Color(107, 114, 128));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(subtitle);
        form.add(Box.createRigidArea(new Dimension(0,30)));
        
        JLabel subjectLabel = new JLabel("Subjek Keluhan");
        subjectLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        subjectLabel.setForeground(new Color(55, 65, 81));
        subjectLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(subjectLabel);
        form.add(Box.createRigidArea(new Dimension(0,10)));
        
        subject = new JTextField(); 
        subject.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45)); 
        subject.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        subject.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        subject.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(subject);
        form.add(Box.createRigidArea(new Dimension(0,25)));
        
        JLabel bodyLabel = new JLabel("Detail Keluhan");
        bodyLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        bodyLabel.setForeground(new Color(55, 65, 81));
        bodyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(bodyLabel);
        form.add(Box.createRigidArea(new Dimension(0,10)));
        
        body = new JTextArea(10,40); 
        body.setLineWrap(true);
        body.setWrapStyleWord(true);
        body.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        body.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));
        
        JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(scrollPane);
        form.add(Box.createRigidArea(new Dimension(0,30)));
        
        JButton send = new JButton("📤 Kirim Keluhan"); 
        send.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        send.setForeground(Color.WHITE);
        send.setBackground(new Color(16, 185, 129));
        send.setBorderPainted(false);
        send.setFocusPainted(false);
        send.setCursor(new Cursor(Cursor.HAND_CURSOR));
        send.setBorder(BorderFactory.createEmptyBorder(16, 45, 16, 45));
        send.setAlignmentX(Component.LEFT_ALIGNMENT);
        send.addActionListener(e -> {
            if(subject.getText().trim().isEmpty() || body.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Mohon isi semua field.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Keluhan terkirim. Terima kasih atas masukan Anda!", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
            subject.setText("");
            body.setText("");
            win.show("home");
        });
        
        send.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                send.setBackground(new Color(5, 150, 105));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                send.setBackground(new Color(16, 185, 129));
            }
        });
        
        form.add(send);
        mainContent.add(form);
        add(new JScrollPane(mainContent), BorderLayout.CENTER);
    }
}