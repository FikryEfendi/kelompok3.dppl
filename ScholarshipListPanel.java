import java.awt.*;
import java.util.List;
import javax.swing.*;

public class ScholarshipListPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    String selectedScholarshipId = "";
    
    public ScholarshipListPanel(MainWindow win, DataStore store){
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
        
        JLabel title = new JLabel("📋 Daftar Beasiswa Tersedia");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        
        top.add(back, BorderLayout.WEST);
        top.add(title, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        // Main content area
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        mainContent.setBackground(new Color(240, 253, 244));
        
        // Load scholarships from database
        List<Scholarship> scholarships = store.loadScholarships();
        
        for(Scholarship sch : scholarships){
            if(!sch.status.equals("Active")) continue; // Only show active scholarships
            
            JPanel item = new JPanel(new BorderLayout(15, 10));
            item.setBackground(Color.WHITE);
            item.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ));
            item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
            
            // Left panel with icon
            JPanel leftPanel = new JPanel();
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
            leftPanel.setBackground(Color.WHITE);
            
            JLabel icon = new JLabel("🎓");
            icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 48));
            icon.setAlignmentX(Component.CENTER_ALIGNMENT);
            leftPanel.add(icon);
            
            // Main content panel
            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setBackground(Color.WHITE);
            
            JLabel titleLabel = new JLabel(sch.name);
            titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
            titleLabel.setForeground(new Color(6, 78, 59));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JLabel descLabel = new JLabel("<html>" + sch.description + "</html>");
            descLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
            descLabel.setForeground(new Color(75, 85, 99));
            descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
            infoPanel.setBackground(Color.WHITE);
            
            JLabel quotaLabel = new JLabel("📊 Kuota: " + sch.quota);
            quotaLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
            quotaLabel.setForeground(new Color(107, 114, 128));
            
            JLabel deadlineLabel = new JLabel("📅 Deadline: " + sch.deadline);
            deadlineLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
            deadlineLabel.setForeground(new Color(107, 114, 128));
            
            infoPanel.add(quotaLabel);
            infoPanel.add(deadlineLabel);
            
            contentPanel.add(titleLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            contentPanel.add(descLabel);
            contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            contentPanel.add(infoPanel);
            
            // Right panel with button
            JPanel rightPanel = new JPanel();
            rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
            rightPanel.setBackground(Color.WHITE);
            
            JButton detailBtn = new JButton("Lihat Detail"); 
            detailBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
            detailBtn.setForeground(Color.WHITE);
            detailBtn.setBackground(new Color(16, 185, 129));
            detailBtn.setBorderPainted(false);
            detailBtn.setFocusPainted(false);
            detailBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            detailBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            detailBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            final String scholarshipId = sch.id;
            detailBtn.addActionListener(e -> {
                selectedScholarshipId = scholarshipId;
                win.getDetailPanel().loadScholarship(scholarshipId);
                win.show("detail");
            });
            
            detailBtn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    detailBtn.setBackground(new Color(5, 150, 105));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    detailBtn.setBackground(new Color(16, 185, 129));
                }
            });
            
            rightPanel.add(detailBtn);
            
            item.add(leftPanel, BorderLayout.WEST);
            item.add(contentPanel, BorderLayout.CENTER);
            item.add(rightPanel, BorderLayout.EAST);
            
            mainContent.add(item);
            mainContent.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        
        if(scholarships.isEmpty() || scholarships.stream().noneMatch(s -> s.status.equals("Active"))){
            JLabel noData = new JLabel("Belum ada beasiswa tersedia saat ini");
            noData.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
            noData.setForeground(new Color(107, 114, 128));
            noData.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainContent.add(Box.createVerticalGlue());
            mainContent.add(noData);
            mainContent.add(Box.createVerticalGlue());
        }
        
        add(new JScrollPane(mainContent), BorderLayout.CENTER);
    }
    
    public String getSelectedScholarshipId(){
        return selectedScholarshipId;
    }
}