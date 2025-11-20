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
                
                // Tampilkan dari yang terbaru (reverse loop)
                for(int i = lines.size() - 1; i >= 0; i--){
                    String line = lines.get(i);
                    String[] parts = line.split("\\|");
                    if(parts.length >= 4){
                        // parts[0]: Tanggal, parts[1]: Judul, parts[2]: Tipe, parts[3]: Status, parts[4]: Path Detail (jika ada)
                        String detailPath = parts.length > 4 ? parts[4] : ""; 
                        
                        JPanel item = createAnnouncementCard(parts[1], parts[0], detailPath);
                        listPanel.add(item);
                        listPanel.add(Box.createRigidArea(new Dimension(0, 15)));
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
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        // Left Content (Title and Date)
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("📢 " + title);
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        titleLabel.setForeground(new Color(6, 78, 59));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel dateLabel = new JLabel("Dipublikasikan: " + date);
        dateLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
        dateLabel.setForeground(new Color(107, 114, 128));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(dateLabel);
        
        // Right Content (Detail Button)
        JButton detailBtn = new JButton("Lihat Detail");
        detailBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        detailBtn.setForeground(Color.WHITE);
        detailBtn.setBackground(new Color(59, 130, 246)); // Blue
        detailBtn.setBorderPainted(false);
        detailBtn.setFocusPainted(false);
        detailBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        detailBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        if(!detailPath.isEmpty()){
            detailBtn.addActionListener(e -> showDetailDialog(title, detailPath));
            detailBtn.setEnabled(true);
        } else {
            detailBtn.setText("Tidak Ada Detail");
            detailBtn.setBackground(new Color(107, 114, 128));
            detailBtn.setEnabled(false);
        }

        card.add(textPanel, BorderLayout.CENTER);
        card.add(detailBtn, BorderLayout.EAST);
        
        return card;
    }
    
    private void showDetailDialog(String title, String detailPath){
        try {
            String content = new String(Files.readAllBytes(Paths.get(detailPath)));
            
            JDialog dialog = new JDialog(win, "Detail Pengumuman: " + title, true);
            dialog.setSize(600, 500);
            dialog.setLocationRelativeTo(win);
            dialog.setLayout(new BorderLayout());
            
            JTextArea textArea = new JTextArea(content);
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
            textArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            
            dialog.add(scrollPane, BorderLayout.CENTER);
            
            JButton closeBtn = new JButton("Tutup");
            closeBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
            closeBtn.setBackground(new Color(16, 185, 129));
            closeBtn.setForeground(Color.WHITE);
            closeBtn.addActionListener(e -> dialog.dispose());
            
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btnPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
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
            BorderFactory.createEmptyBorder(60, 40, 60, 40)
        ));
        empty.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel icon = new JLabel("❌");
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 64));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Belum Ada Pengumuman");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        title.setForeground(new Color(6, 78, 59));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel desc = new JLabel("Admin belum mempublikasikan pengumuman apapun.");
        desc.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        desc.setForeground(new Color(107, 114, 128));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        empty.add(icon);
        empty.add(Box.createRigidArea(new Dimension(0, 20)));
        empty.add(title);
        empty.add(Box.createRigidArea(new Dimension(0, 10)));
        empty.add(desc);
        
        return empty;
    }
    
    private JPanel createErrorState(String message) {
        // Sama seperti createEmptyState, namun untuk pesan error
        JPanel error = createEmptyState();
        ((JLabel)error.getComponent(0)).setText("⚠️");
        ((JLabel)error.getComponent(2)).setText("Gagal Memuat Data");
        ((JLabel)error.getComponent(4)).setText(message);
        return error;
    }
}