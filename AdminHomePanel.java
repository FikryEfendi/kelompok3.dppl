import java.awt.*;
import javax.swing.*;

public class AdminHomePanel extends JPanel {
    MainWindow win; 
    DataStore store;

    public AdminHomePanel(MainWindow win, DataStore store){
        this.win = win; 
        this.store = store;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Top navigation bar
        JPanel topNav = new JPanel(new BorderLayout());
        topNav.setBackground(new Color(16, 185, 129));
        topNav.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        JLabel logo = new JLabel("🎓 Admin Dashboard - Sistem Beasiswa");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(Color.WHITE);
        
        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navButtons.setBackground(new Color(16, 185, 129));
        
        JButton logoutBtn = createNavButton("Logout");
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION){
                win.show("login");
            }
        });
        navButtons.add(logoutBtn);
        
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
        
        JLabel welcomeLabel = new JLabel("Selamat Datang, Administrator");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(31, 41, 55));
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Kelola sistem beasiswa mahasiswa dengan efisien");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(107, 114, 128));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        welcomeSection.add(welcomeLabel);
        welcomeSection.add(Box.createRigidArea(new Dimension(0, 5)));
        welcomeSection.add(subtitle);
        
        // Admin actions
        JPanel adminActions = new JPanel(new GridLayout(2, 2, 20, 20));
        adminActions.setBackground(new Color(249, 250, 251));
        adminActions.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
        
        adminActions.add(createActionCard("📚", "Kelola Beasiswa", "Tambah, edit, dan hapus data beasiswa", () -> win.show("manageScholarships")));
        adminActions.add(createActionCard("👥", "Kelola Pendaftar", "Verifikasi dan seleksi pendaftar", () -> win.show("manageApplicants")));
        adminActions.add(createActionCard("📢", "Kelola Pengumuman", "Buat dan publikasikan pengumuman", () -> win.show("manageAnnouncements")));
        adminActions.add(createActionCard("📊", "Laporan", "Generate laporan dan statistik", () -> win.show("reports")));
        
        // Statistics
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        JLabel statsTitle = new JLabel("📈 Statistik Cepat");
        statsTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        statsTitle.setForeground(new Color(31, 41, 55));
        statsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        int totalApps = store.loadApplications().size();
        int totalUsers = store.loadUsers().size();
        int pendingApps = (int) store.loadApplications().stream().filter(a -> a.status.equals("Pending")).count();
        
        JLabel stat1 = new JLabel("Total Pendaftar: " + totalApps);
        stat1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stat1.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel stat2 = new JLabel("Total User Terdaftar: " + totalUsers);
        stat2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stat2.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel stat3 = new JLabel("Menunggu Verifikasi: " + pendingApps);
        stat3.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        stat3.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        statsPanel.add(statsTitle);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        statsPanel.add(stat1);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        statsPanel.add(stat2);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        statsPanel.add(stat3);
        
        // Assemble
        JPanel contentContainer = new JPanel();
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.Y_AXIS));
        contentContainer.setBackground(new Color(249, 250, 251));
        
        welcomeSection.setAlignmentX(Component.LEFT_ALIGNMENT);
        adminActions.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        contentContainer.add(welcomeSection);
        contentContainer.add(adminActions);
        contentContainer.add(statsPanel);
        
        mainContent.add(contentContainer, BorderLayout.NORTH);
        
        add(topNav, BorderLayout.NORTH);
        add(new JScrollPane(mainContent), BorderLayout.CENTER);
    }
    
    private JButton createNavButton(String text){
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(5, 150, 105));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(6, 78, 59));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(5, 150, 105));
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
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 40));
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
                card.setBackground(new Color(240, 253, 244));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(16, 185, 129), 2, true),
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
}