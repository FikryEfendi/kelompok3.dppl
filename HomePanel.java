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
        
        // Top navigation bar with green theme
        JPanel topNav = new JPanel(new BorderLayout());
        topNav.setBackground(new Color(16, 185, 129)); // Green primary
        topNav.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        JLabel logo = new JLabel("🎓 Sistem Beasiswa");
        logo.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        logo.setForeground(Color.WHITE);
        
        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navButtons.setBackground(new Color(16, 185, 129));
        
        String[] items = {"Beranda", "Daftar Beasiswa", "Pengumuman", "Bantuan"};
        String[] icons = {"🏠", "📋", "📢", "💬"};
        for(int i = 0; i < items.length; i++){
            final String item = items[i];
            JButton btn = createNavButton(icons[i] + " " + items[i]);
            btn.addActionListener(e -> nav(item));
            navButtons.add(btn);
        }
        
        // ===== TAMBAHAN: BUTTON LOGOUT =====
        JButton logoutBtn = createLogoutButton("🚪 Logout");
        logoutBtn.addActionListener(e -> handleLogout());
        navButtons.add(logoutBtn);
        // ===================================
        
        topNav.add(logo, BorderLayout.WEST);
        topNav.add(navButtons, BorderLayout.EAST);
        
        // Main content
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(240, 253, 244)); // Light green background
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Welcome section with green accent
        JPanel welcomeSection = new JPanel();
        welcomeSection.setLayout(new BoxLayout(welcomeSection, BoxLayout.Y_AXIS));
        welcomeSection.setBackground(Color.WHITE);
        welcomeSection.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true), // Green border
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        
        welcomeLabel = new JLabel("Selamat Datang");
        welcomeLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(31, 41, 55));
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitle = new JLabel("NIM: " + currentNim);
        subtitle.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        subtitle.setForeground(new Color(5, 150, 105)); // Dark green
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        welcomeSection.add(welcomeLabel);
        welcomeSection.add(Box.createRigidArea(new Dimension(0, 5)));
        welcomeSection.add(subtitle);
        
        // Quick actions with green theme
        JPanel quickActions = new JPanel(new GridLayout(1, 3, 20, 0));
        quickActions.setBackground(new Color(240, 253, 244));
        quickActions.setBorder(BorderFactory.createEmptyBorder(25, 0, 25, 0));
        
        quickActions.add(createActionCard("📝", "Daftar Beasiswa", "Lihat dan daftar beasiswa tersedia", () -> win.show("list")));
        quickActions.add(createActionCard("📊", "Cek Status", "Pantau status pendaftaran Anda", () -> win.show("status")));
        quickActions.add(createActionCard("💡", "Bantuan", "Butuh bantuan? Hubungi kami", () -> win.show("help")));
        
        // Scholarship recommendations with green accent
        JPanel recommendSection = new JPanel();
        recommendSection.setLayout(new BorderLayout());
        recommendSection.setBackground(Color.WHITE);
        recommendSection.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        JLabel recTitle = new JLabel("🌟 Rekomendasi Beasiswa");
        recTitle.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        recTitle.setForeground(new Color(6, 78, 59)); // Dark green
        
        JPanel scholarshipList = new JPanel();
        scholarshipList.setLayout(new BoxLayout(scholarshipList, BoxLayout.Y_AXIS));
        scholarshipList.setBackground(Color.WHITE);
        scholarshipList.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        // Get real scholarships from database
        java.util.List<Scholarship> scholarships = store.loadScholarships();
        for(Scholarship sch : scholarships){
            if(sch.status.equals("Active")){
                JPanel item = createScholarshipItem(sch.name + " - " + sch.description);
                scholarshipList.add(item);
                scholarshipList.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }
        
        recommendSection.add(recTitle, BorderLayout.NORTH);
        recommendSection.add(scholarshipList, BorderLayout.CENTER);
        
        // Assemble main content
        JPanel contentContainer = new JPanel();
        contentContainer.setLayout(new BoxLayout(contentContainer, BoxLayout.Y_AXIS));
        contentContainer.setBackground(new Color(240, 253, 244));
        
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
        btn.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(16, 185, 129));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(5, 150, 105)); // Darker green on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(16, 185, 129));
            }
        });
        
        return btn;
    }
    
    // ===== TAMBAHAN: METHOD UNTUK BUTTON LOGOUT =====
    private JButton createLogoutButton(String text){
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(220, 38, 38)); // Red color for logout
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(185, 28, 28)); // Darker red on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(220, 38, 38));
            }
        });
        
        return btn;
    }
    
    // ===== TAMBAHAN: METHOD HANDLE LOGOUT =====
    private void handleLogout(){
        int confirm = JOptionPane.showConfirmDialog(
            this, 
            "Apakah Anda yakin ingin logout?", 
            "Konfirmasi Logout", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if(confirm == JOptionPane.YES_OPTION){
            try {
                // Hapus file current_nim.txt
                Path nimPath = Paths.get("current_nim.txt");
                if(Files.exists(nimPath)){
                    Files.delete(nimPath);
                }
                
                // Hapus file selected_scholarship.txt jika ada
                Path selectedPath = Paths.get("selected_scholarship.txt");
                if(Files.exists(selectedPath)){
                    Files.delete(selectedPath);
                }
                
                // Kembali ke halaman login
                win.show("login");
                
                // Optional: Tampilkan pesan logout berhasil
                JOptionPane.showMessageDialog(
                    this, 
                    "Anda telah berhasil logout!", 
                    "Logout Berhasil", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch(Exception e){
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                    this, 
                    "Error saat logout: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    // ================================================
    
    private JPanel createActionCard(String icon, String title, String desc, Runnable action){
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 1, true),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 36));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        titleLabel.setForeground(new Color(6, 78, 59)); // Dark green
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html><center>" + desc + "</center></html>");
        descLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
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
                card.setBackground(new Color(240, 253, 244)); // Light green
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(16, 185, 129), 2, true),
                    BorderFactory.createEmptyBorder(24, 19, 24, 19)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                card.setBackground(Color.WHITE);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(167, 243, 208), 1, true),
                    BorderFactory.createEmptyBorder(25, 20, 25, 20)
                ));
            }
        });
        
        return card;
    }
    
    private JPanel createScholarshipItem(String text){
        JPanel item = new JPanel(new BorderLayout(15, 0));
        item.setBackground(new Color(240, 253, 244));
        item.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        
        JLabel bullet = new JLabel("✓");
        bullet.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        bullet.setForeground(new Color(16, 185, 129)); // Green checkmark
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        label.setForeground(new Color(55, 65, 81));
        
        item.add(bullet, BorderLayout.WEST);
        item.add(label, BorderLayout.CENTER);
        
        return item;
    }

    void nav(String s){
        if(s.equals("Bantuan")) win.show("help");
        else if(s.equals("Beranda")) win.show("home");
        else if(s.equals("Daftar Beasiswa")) win.show("list");
        else if(s.equals("Pengumuman")) win.show("announcement"); // Panggil method baru
    }

    // Method baru untuk membaca dan menampilkan pengumuman dari file
    private void showAnnouncements(){
        try {
            java.nio.file.Path path = java.nio.file.Paths.get("announcements.txt");
            
            if(java.nio.file.Files.exists(path) && java.nio.file.Files.size(path) > 0){
                // Membaca semua baris
                java.util.List<String> lines = java.nio.file.Files.readAllLines(path);
                
                if (lines.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Belum ada pengumuman saat ini.", "Pengumuman", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                StringBuilder sb = new StringBuilder();
                sb.append("PENGUMUMAN TERBARU:\n");
                sb.append("========================\n\n");
                
                // Tampilkan 3 pengumuman terbaru
                int limit = Math.min(3, lines.size());
                for(int i = lines.size() - 1; i >= lines.size() - limit; i--){
                    String line = lines.get(i);
                    String[] parts = line.split("\\|");
                    if(parts.length >= 4){
                        sb.append("Tanggal: ").append(parts[0]).append("\n");
                        sb.append("Judul: ").append(parts[1]).append("\n");
                        sb.append("Status: ").append(parts[3]).append("\n");
                        sb.append("------------------------\n");
                    }
                }
                sb.append("\nHubungi Admin untuk detail lengkap.");
                
                JOptionPane.showMessageDialog(this, sb.toString(), "Pengumuman Sistem", JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                JOptionPane.showMessageDialog(this, "Belum ada pengumuman saat ini.", "Pengumuman", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error saat memuat pengumuman: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}