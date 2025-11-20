import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScholarshipDetailPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    Scholarship currentScholarship;
    
    public ScholarshipDetailPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
    }
    
    public void loadScholarship(String scholarshipId){
        removeAll();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Find scholarship by ID
        List<Scholarship> scholarships = store.loadScholarships();
        currentScholarship = null;
        for(Scholarship s : scholarships){
            if(s.id.equals(scholarshipId)){
                currentScholarship = s;
                break;
            }
        }
        
        if(currentScholarship == null){
            // Fallback if not found
            currentScholarship = scholarships.get(0);
        }
        
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
        back.addActionListener(e -> win.show("list"));
        
        JLabel titleLabel = new JLabel("📋 Detail Beasiswa");
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        top.add(back, BorderLayout.WEST);
        top.add(titleLabel, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        // Main content
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainContent.setBackground(new Color(240, 253, 244));
        
        // Header section
        JPanel headerSection = new JPanel();
        headerSection.setLayout(new BoxLayout(headerSection, BoxLayout.Y_AXIS));
        headerSection.setBackground(Color.WHITE);
        headerSection.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        headerSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel scholarshipTitle = new JLabel(currentScholarship.name);
        scholarshipTitle.setFont(new Font("Segoe UI Symbol", Font.BOLD, 26));
        scholarshipTitle.setForeground(new Color(6, 78, 59));
        scholarshipTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel statusBadge = new JLabel("  " + currentScholarship.status + "  ");
        statusBadge.setFont(new Font("Segoe UI Symbol", Font.BOLD, 12));
        statusBadge.setForeground(Color.WHITE);
        statusBadge.setBackground(new Color(16, 185, 129));
        statusBadge.setOpaque(true);
        statusBadge.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusPanel.add(statusBadge);
        
        headerSection.add(scholarshipTitle);
        headerSection.add(Box.createRigidArea(new Dimension(0, 10)));
        headerSection.add(statusPanel);
        
        // Info cards
        JPanel infoCards = new JPanel(new GridLayout(1, 2, 20, 0));
        infoCards.setBackground(new Color(240, 253, 244));
        infoCards.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        infoCards.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoCards.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        infoCards.add(createInfoCard("📊 Kuota Tersedia", currentScholarship.quota + " Mahasiswa"));
        infoCards.add(createInfoCard("📅 Batas Pendaftaran", currentScholarship.deadline));
        
        // Description section
        JPanel descSection = createSection("📝 Deskripsi", currentScholarship.description);
        
        // Requirements section
        JPanel reqSection = createSection("✅ Persyaratan", currentScholarship.requirements);
        
        // Action section
        JPanel actionSection = new JPanel();
        actionSection.setLayout(new BoxLayout(actionSection, BoxLayout.Y_AXIS));
        actionSection.setBackground(Color.WHITE);
        actionSection.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        actionSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel actionTitle = new JLabel("Tertarik dengan beasiswa ini?");
        actionTitle.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        actionTitle.setForeground(new Color(6, 78, 59));
        actionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel actionDesc = new JLabel("Pastikan Anda memenuhi semua persyaratan sebelum mendaftar.");
        actionDesc.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        actionDesc.setForeground(new Color(107, 114, 128));
        actionDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton applyBtn = new JButton("📝 Daftar Sekarang");
        applyBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        applyBtn.setForeground(Color.WHITE);
        applyBtn.setBackground(new Color(16, 185, 129));
        applyBtn.setBorderPainted(false);
        applyBtn.setFocusPainted(false);
        applyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        applyBtn.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        applyBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        applyBtn.addActionListener(e -> {
            // Save selected scholarship to pass to application form
            try {
                java.nio.file.Files.write(java.nio.file.Paths.get("selected_scholarship.txt"), 
                    (currentScholarship.id + "|" + currentScholarship.name).getBytes());
            } catch(Exception ex){
                ex.printStackTrace();
            }
            win.show("apply");
        });
        
        applyBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                applyBtn.setBackground(new Color(5, 150, 105));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                applyBtn.setBackground(new Color(16, 185, 129));
            }
        });
        
        actionSection.add(actionTitle);
        actionSection.add(Box.createRigidArea(new Dimension(0, 5)));
        actionSection.add(actionDesc);
        actionSection.add(Box.createRigidArea(new Dimension(0, 20)));
        actionSection.add(applyBtn);
        
        // Add all sections
        mainContent.add(headerSection);
        mainContent.add(infoCards);
        mainContent.add(descSection);
        mainContent.add(reqSection);
        mainContent.add(actionSection);
        
        add(new JScrollPane(mainContent), BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }
    
    private JPanel createInfoCard(String title, String value){
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        titleLabel.setForeground(new Color(107, 114, 128));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        valueLabel.setForeground(new Color(6, 78, 59));
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(valueLabel);
        
        return card;
    }
    
    private JPanel createSection(String title, String content){
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(Color.WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        section.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        titleLabel.setForeground(new Color(6, 78, 59));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextArea contentArea = new JTextArea(content);
        contentArea.setEditable(false);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        contentArea.setForeground(new Color(55, 65, 81));
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        contentArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        section.add(titleLabel);
        section.add(Box.createRigidArea(new Dimension(0, 10)));
        section.add(contentArea);
        
        return section;
    }
}