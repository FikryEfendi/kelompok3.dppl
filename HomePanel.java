import java.awt.*;
import java.nio.file.*;
import javax.swing.*;

public class HomePanel extends JPanel {
    MainWindow win; 
    DataStore store;
    JLabel welcomeLabel;
    String currentNim = "";

    public HomePanel(MainWindow win, DataStore store){
        this.win = win; 
        this.store = store;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Read current nim
        try {
            currentNim = new String(Files.readAllBytes(Paths.get("current_nim.txt"))).trim();
        } catch(Exception e){}
        
        // Top navigation bar
        JPanel topNav = new JPanel(new BorderLayout());
        topNav.setBackground(Color.WHITE);
        topNav.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)),
            BorderFactory.createEmptyBorder(15, 30, 15, 30)
        ));
        
        JLabel logo = new JLabel("🎓 Sistem Beasiswa");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(new Color(99, 102, 241));
        
        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navButtons.setBackground(Color.WHITE);
        
        String[] items = {"Beranda", "Daftar Beasiswa", "Pengumuman", "Bantuan"};
        String[] icons = {"🏠", "📋", "📢", "💬"};
        for(int i = 0; i < items.length; i++){
            final String item = items[i];
            JButton btn = createNavButton(icons[i] + " " + items[i]);
            btn.addActionListener(e -> nav(item));
            navButtons.add(btn);
        }
        
        topNav.add(logo, BorderLayout.WEST);
        topNav.add(navButtons, BorderLayout.EAST);
        
        // Main content
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(249, 250, 251));
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Welcome section
        JPanel welcomeSection = new JPanel();
        welcomeSection.setLayout(new BoxLayout(welcomeSection, BoxLayout.Y_AXIS));
        welcomeSection.setBackground(Color.WHITE);
        welcomeSection.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        welcomeLabel = new JLabel("Selamat Datang");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(31, 41, 55));
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitle = new JLabel("NIM: " + currentNim);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(107, 114, 128));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        welcomeSection.add(welcomeLabel);
        welcomeSection.add(Box.createRigidArea(new Dimension(0, 5)));
        welcomeSection.add(subtitle);
        
        // Quick actions
        JPanel quickActions = new JPanel(new GridLayout(1, 3, 20, 0));
        quickActions.setBackground(new Color(249, 250, 251));
        quickActions.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
        
        quickActions.add(createActionCard("📝", "Daftar Beasiswa", "Lihat dan daftar beasiswa tersedia", () -> win.show("list")));
        quickActions.add(createActionCard("📊", "Cek Status", "Pantau status pendaftaran Anda", () -> win.show("status")));
        quickActions.add(createActionCard("💡", "Bantuan", "Butuh bantuan? Hubungi kami", () -> win.show("help")));
        
        // Scholarship recommendations
        JPanel recommendSection = new JPanel();
        recommendSection.setLayout(new BorderLayout());
        recommendSection.setBackground(Color.WHITE);
        recommendSection.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        JLabel recTitle = new JLabel("🌟 Rekomendasi Beasiswa");
        recTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        recTitle.setForeground(new Color(31, 41, 55));
        
        JPanel scholarshipList = new JPanel();
        scholarshipList.setLayout(new BoxLayout(scholarshipList, BoxLayout.Y_AXIS));
        scholarshipList.setBackground(Color.WHITE);
        scholarshipList.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        String[] scholarships = {
            "Beasiswa Prestasi Akademik - Untuk mahasiswa berprestasi",
            "Beasiswa Penelitian - Untuk peneliti muda berbakat",
            "Beasiswa Ekonomi - Bantuan untuk mahasiswa kurang mampu"
        };
        
        for(String sch : scholarships){
            JPanel item = createScholarshipItem(sch);
            scholarshipList.add(item);
            scholarshipList.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        recommendSection.add(recTitle, BorderLayout.NORTH);
        recommendSection.add(scholarshipList, BorderLayout.CENTER);
        
        // Assemble main content
        JPanel contentContainer = new JPanel();
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.Y_AXIS));
        contentContainer.setBackground(new Color(249, 250, 251));
        
        welcomeSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        quickActions.setAlignmentX(Component.LEFT_ALIGNMENT);
        recommendSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        contentContainer.add(welcomeSection);
        contentContainer.add(quickActions);
        contentContainer.add(recommendSection);
        
        mainContent.add(contentContainer, BorderLayout.NORTH);
        
        add(topNav, BorderLayout.NORTH);
        add(new JScrollPane(mainContent), BorderLayout.CENTER);
    }
    
    private JButton createNavButton(String text){
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(75, 85, 99));
        btn.setBackground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(243, 244, 246));
                btn.setForeground(new Color(99, 102, 241));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(75, 85, 99));
            }
        });
        
        return btn;
    }
    
    private JPanel createActionCard(String icon, String title, String desc, Runnable action){
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(31, 41, 55));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html><center>" + desc + "</center></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(107, 114, 128));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        card.add(iconLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(descLabel);
        
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.run();
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                card.setBackground(new Color(249, 250, 251));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(99, 102, 241), 2, true),
                    BorderFactory.createEmptyBorder(24, 19, 24, 19)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
                    BorderFactory.createEmptyBorder(25, 20, 25, 20)
                ));
            }
        });
        
        return card;
    }
    
    private JPanel createScholarshipItem(String text){
        JPanel item = new JPanel(new BorderLayout(15, 0));
        item.setBackground(new Color(249, 250, 251));
        item.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        
        JLabel bullet = new JLabel("✓");
        bullet.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bullet.setForeground(new Color(16, 185, 129));
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(55, 65, 81));
        
        item.add(bullet, BorderLayout.WEST);
        item.add(label, BorderLayout.CENTER);
        
        return item;
    }

    void nav(String s){
        if(s.equals("Bantuan")) win.show("help");
        else if(s.equals("Beranda")) win.show("home");
        else if(s.equals("Daftar Beasiswa")) win.show("list");
        else if(s.equals("Pengumuman")) JOptionPane.showMessageDialog(this, "Belum ada pengumuman saat ini.", "Pengumuman", JOptionPane.INFORMATION_MESSAGE);
    }
}