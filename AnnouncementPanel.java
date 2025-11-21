import java.awt.*;
import java.nio.file.*;
import java.util.List;
import javax.swing.*;
import java.io.IOException;

public class AnnouncementPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    JPanel listPanel;

    public AnnouncementPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Top bar with green theme
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
        
        JLabel titleLabel = new JLabel("📢 Pengumuman Sistem");
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        top.add(back, BorderLayout.WEST);
        top.add(titleLabel, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        // Main content area
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        listPanel.setBackground(new Color(240, 253, 244));
        
        loadAnnouncements();

        add(new JScrollPane(listPanel), BorderLayout.CENTER);
    }
    
    private void loadAnnouncements(){
        listPanel.removeAll();
        try {
            Path path = Paths.get("announcements.txt");
            if(Files.exists(path) && Files.size(path) > 0){
                List<String> lines = Files.readAllLines(path);
                
                for(int i = lines.size() - 1; i >= 0; i--){
                    String line = lines.get(i);
                    String[] parts = line.split("\\|");
                    if(parts.length >= 4){
                        String detailPath = parts.length > 4 ? parts[4] : ""; 
                        
                        JPanel item = createAnnouncementCard(parts[1], parts[0], detailPath);
                        listPanel.add(item);
                        listPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                    }
                }
                
            } else {
                listPanel.add(createEmptyState());
            }
        } catch(IOException e){
            listPanel.add(createErrorState("Error saat memuat pengumuman: " + e.getMessage()));
        }
        listPanel.revalidate();
        listPanel.repaint();
    }
    
    private JPanel createAnnouncementCard(String title, String date, String detailPath){
        JPanel card = new JPanel(new BorderLayout(25, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        // Left Content (Icon + Text)
        JPanel leftContent = new JPanel(new BorderLayout(15, 0));
        leftContent.setBackground(Color.WHITE);
        
        // Icon Panel
        JPanel iconPanel = new JPanel(new GridBagLayout());
        iconPanel.setBackground(Color.WHITE);
        iconPanel.setPreferredSize(new Dimension(60, 60));
        
        JLabel icon = new JLabel("📢");
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 40));
        iconPanel.add(icon);
        
        // Text Panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        titleLabel.setForeground(new Color(6, 78, 59));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel dateLabel = new JLabel("📅 Dipublikasikan: " + date);
        dateLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        dateLabel.setForeground(new Color(107, 114, 128));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        textPanel.add(dateLabel);
        
        leftContent.add(iconPanel, BorderLayout.WEST);
        leftContent.add(textPanel, BorderLayout.CENTER);
        
        // Right Content (Detail Button)
        JButton detailBtn = new JButton("Lihat Detail");
        detailBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
        detailBtn.setForeground(Color.WHITE);
        detailBtn.setBackground(new Color(59, 130, 246));
        detailBtn.setBorderPainted(false);
        detailBtn.setFocusPainted(false);
        detailBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        detailBtn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        
        if(!detailPath.isEmpty()){
            detailBtn.addActionListener(e -> showDetailDialog(title, detailPath));
            detailBtn.setEnabled(true);
            
            detailBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    detailBtn.setBackground(new Color(37, 99, 235));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    detailBtn.setBackground(new Color(59, 130, 246));
                }
            });
        } else {
            detailBtn.setText("Tidak Ada Detail");
            detailBtn.setBackground(new Color(156, 163, 175));
            detailBtn.setEnabled(false);
        }

        card.add(leftContent, BorderLayout.CENTER);
        card.add(detailBtn, BorderLayout.EAST);
        
        return card;
    }
    
    private void showDetailDialog(String title, String detailPath){
        try {
            String content = new String(Files.readAllBytes(Paths.get(detailPath)));
            
            JDialog dialog = new JDialog(win, "Detail Pengumuman: " + title, true);
            dialog.setSize(650, 550);
            dialog.setLocationRelativeTo(win);
            dialog.setLayout(new BorderLayout());
            
            JTextArea textArea = new JTextArea(content);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
            textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            
            dialog.add(scrollPane, BorderLayout.CENTER);
            
            JButton closeBtn = new JButton("Tutup");
            closeBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
            closeBtn.setBackground(new Color(16, 185, 129));
            closeBtn.setForeground(Color.WHITE);
            closeBtn.setBorderPainted(false);
            closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            closeBtn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
            closeBtn.addActionListener(e -> dialog.dispose());
            
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            btnPanel.add(closeBtn);
            
            dialog.add(btnPanel, BorderLayout.SOUTH);
            dialog.setVisible(true);

        } catch(IOException e){
            JOptionPane.showMessageDialog(this, "Gagal memuat detail pengumuman: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JPanel createEmptyState(){
        JPanel empty = new JPanel();
        empty.setLayout(new BoxLayout(empty, BoxLayout.Y_AXIS));
        empty.setBackground(Color.WHITE);
        empty.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(80, 50, 80, 50)
        ));
        empty.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel icon = new JLabel("📭");
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 72));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Belum Ada Pengumuman");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 24));
        title.setForeground(new Color(6, 78, 59));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel desc = new JLabel("Admin belum mempublikasikan pengumuman apapun.");
        desc.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
        desc.setForeground(new Color(107, 114, 128));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        empty.add(icon);
        empty.add(Box.createRigidArea(new Dimension(0, 25)));
        empty.add(title);
        empty.add(Box.createRigidArea(new Dimension(0, 12)));
        empty.add(desc);
        
        return empty;
    }
    
    private JPanel createErrorState(String message) {
        JPanel error = new JPanel();
        error.setLayout(new BoxLayout(error, BoxLayout.Y_AXIS));
        error.setBackground(Color.WHITE);
        error.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(239, 68, 68), 2, true),
            BorderFactory.createEmptyBorder(80, 50, 80, 50)
        ));
        error.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel icon = new JLabel("⚠️");
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 72));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Gagal Memuat Data");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 24));
        title.setForeground(new Color(239, 68, 68));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel desc = new JLabel("<html><center>" + message + "</center></html>");
        desc.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        desc.setForeground(new Color(107, 114, 128));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        error.add(icon);
        error.add(Box.createRigidArea(new Dimension(0, 25)));
        error.add(title);
        error.add(Box.createRigidArea(new Dimension(0, 12)));
        error.add(desc);
        
        return error;
    }
}