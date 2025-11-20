import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.*;
import java.util.List;

public class StatusOverviewPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    
    public StatusOverviewPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        // Inisialisasi awal, layout akan diset di method refresh()
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
    }

    public void refresh(){ // Tambahkan method refresh untuk memuat ulang
        this.removeAll();
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
        
        JLabel titleLabel = new JLabel("📊 Status Pendaftaran");
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        top.add(back, BorderLayout.WEST);
        top.add(titleLabel, BorderLayout.CENTER);
        this.add(top, BorderLayout.NORTH);

        // Main content
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainContent.setBackground(new Color(240, 253, 244));
        
        // Get current user's applications
        String currentNim = "";
        try {
            currentNim = new String(Files.readAllBytes(Paths.get("current_nim.txt"))).trim();
        } catch(Exception e){}
        
        List<Application> apps = store.loadApplications();
        boolean hasApplications = false;
        
        for(Application a : apps){
            if(a.nim.equals(currentNim)){
                hasApplications = true;
                // Kirim objek Application ke createApplicationCard
                JPanel appCard = createApplicationCard(a);
                mainContent.add(appCard);
                mainContent.add(Box.createRigidArea(new Dimension(0, 15)));
            }
        }
        
        if(!hasApplications){
            JPanel emptyState = createEmptyState();
            mainContent.add(emptyState);
        }
        
        this.add(new JScrollPane(mainContent), BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
    
    private JPanel createApplicationCard(Application app){
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // --- TAMBAHAN: MOUSE LISTENER UNTUK KLIK KARTU ---
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Panggil method untuk menampilkan detail aplikasi
                ((StatusDetailPanel)win.getPanel("statusDetail")).loadApplication(app);
                win.show("statusDetail");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                 card.setBackground(new Color(240, 253, 244)); // Highlight on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                 card.setBackground(Color.WHITE);
            }
        });
        // --------------------------------------------------
        
        // Left: Icon
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        
        JLabel icon = new JLabel("📋");
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 48));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        leftPanel.add(icon);
        
        // Center: Details
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(app.scholarshipName);
        nameLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        nameLabel.setForeground(new Color(6, 78, 59));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel nimLabel = new JLabel("NIM: " + app.nim);
        nimLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        nimLabel.setForeground(new Color(107, 114, 128));
        nimLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel fullNameLabel = new JLabel("Nama: " + app.fullName);
        fullNameLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        fullNameLabel.setForeground(new Color(107, 114, 128));
        fullNameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        centerPanel.add(nameLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(nimLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 3)));
        centerPanel.add(fullNameLabel);
        
        // Right: Status badge
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        
        JLabel statusLabel = new JLabel("  " + app.status + "  ");
        statusLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Set color based on status
        if(app.status.equals("Pending")){
            statusLabel.setBackground(new Color(245, 158, 11)); // Orange
        } else if(app.status.equals("Verified")){
            statusLabel.setBackground(new Color(59, 130, 246)); // Blue
        } else if(app.status.equals("Accepted")){
            statusLabel.setBackground(new Color(34, 197, 94)); // Green
        } else if(app.status.equals("Rejected")){
            statusLabel.setBackground(new Color(239, 68, 68)); // Red
        } else {
            statusLabel.setBackground(new Color(107, 114, 128)); // Gray
        }
        
        rightPanel.add(statusLabel);
        
        // Status description
        JLabel statusDesc = new JLabel(getStatusDescription(app.status));
        statusDesc.setFont(new Font("Segoe UI Symbol", Font.ITALIC, 11));
        statusDesc.setForeground(new Color(107, 114, 128));
        statusDesc.setAlignmentX(Component.CENTER_ALIGNMENT);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        rightPanel.add(statusDesc);
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(centerPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);
        
        return card;
    }
    
    private String getStatusDescription(String status){
        switch(status){
            case "Pending": return "Menunggu verifikasi";
            case "Verified": return "Sedang diproses";
            case "Accepted": return "Selamat! Anda diterima";
            case "Rejected": return "Mohon maaf";
            default: return "";
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
        
        JLabel icon = new JLabel("📝");
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 64));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Belum Ada Pendaftaran");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        title.setForeground(new Color(6, 78, 59));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel desc = new JLabel("Anda belum mendaftar beasiswa apapun");
        desc.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        desc.setForeground(new Color(107, 114, 128));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton applyBtn = new JButton("📋 Lihat Beasiswa Tersedia");
        applyBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
        applyBtn.setForeground(Color.WHITE);
        applyBtn.setBackground(new Color(16, 185, 129));
        applyBtn.setBorderPainted(false);
        applyBtn.setFocusPainted(false);
        applyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        applyBtn.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        applyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        applyBtn.addActionListener(e -> win.show("list"));
        
        applyBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                applyBtn.setBackground(new Color(5, 150, 105));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                applyBtn.setBackground(new Color(16, 185, 129));
            }
        });
        
        empty.add(icon);
        empty.add(Box.createRigidArea(new Dimension(0, 20)));
        empty.add(title);
        empty.add(Box.createRigidArea(new Dimension(0, 10)));
        empty.add(desc);
        empty.add(Box.createRigidArea(new Dimension(0, 25)));
        empty.add(applyBtn);
        
        return empty;
    }
}