import java.awt.*;
import javax.swing.*;

public class StatusDetailPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    
    // Properti untuk menyimpan aplikasi yang sedang ditampilkan
    private Application currentApplication; 
    
    private JPanel detailContent;

    public StatusDetailPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
    }
    
    // Method baru untuk memuat dan menampilkan detail aplikasi
    public void loadApplication(Application app){
        this.currentApplication = app;
        
        // Membersihkan dan menyusun ulang layout setiap kali dipanggil
        removeAll();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Top bar with green theme
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(16, 185, 129));
        top.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        
        JButton back = new JButton("← Kembali ke Status"); 
        back.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(5, 150, 105));
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        back.addActionListener(e -> {
            ((StatusOverviewPanel)win.getPanel("status")).refresh(); // Refresh overview sebelum kembali
            win.show("status");
        }); 
        
        JLabel titleLabel = new JLabel("🔍 Detail Pendaftaran");
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
        
        // Card Detail
        detailContent = new JPanel();
        detailContent.setLayout(new BoxLayout(detailContent, BoxLayout.Y_AXIS));
        detailContent.setBackground(Color.WHITE);
        detailContent.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        detailContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel header = new JLabel("Pendaftaran Beasiswa: " + app.scholarshipName);
        header.setFont(new Font("Segoe UI Symbol", Font.BOLD, 22));
        header.setForeground(new Color(6, 78, 59));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailContent.add(header);
        detailContent.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Tampilkan Status Saat Ini
        JLabel statusLabel = createStatusBadge(app.status);
        detailContent.add(statusLabel);
        detailContent.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Tabel Data Diri
        detailContent.add(createTitle("Data Diri Pendaftar"));
        detailContent.add(createDetailRow("NIM:", app.nim));
        detailContent.add(createDetailRow("Nama Lengkap:", app.fullName));
        detailContent.add(createDetailRow("Tempat/Tgl Lahir:", app.ttl));
        detailContent.add(createDetailRow("NIK:", app.nik));
        detailContent.add(createDetailRow("Alamat:", app.address));
        
        detailContent.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Detail Dokumen
        detailContent.add(createTitle("Dokumen & Kelengkapan"));
        detailContent.add(createDetailRow("Pas Foto:", app.photoPath));

        mainContent.add(detailContent);
        add(new JScrollPane(mainContent), BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }
    
    private JLabel createTitle(String text){
         JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        label.setForeground(new Color(55, 65, 81));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    private JPanel createDetailRow(String label, String value){
        JPanel row = new JPanel(new BorderLayout(15, 0));
        row.setBackground(Color.WHITE);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JLabel lblLabel = new JLabel("  " + label);
        lblLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        lblLabel.setForeground(new Color(107, 114, 128));
        lblLabel.setPreferredSize(new Dimension(150, 20));
        
        JLabel lblValue = new JLabel("<html><b>" + value + "</b></html>");
        lblValue.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        lblValue.setForeground(new Color(31, 41, 55));
        
        row.add(lblLabel, BorderLayout.WEST);
        row.add(lblValue, BorderLayout.CENTER);
        
        return row;
    }
    
    private JLabel createStatusBadge(String status){
        JLabel statusLabel = new JLabel("STATUS SAAT INI: " + status.toUpperCase());
        statusLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Set color based on status
        if(status.equals("Pending")){
            statusLabel.setBackground(new Color(245, 158, 11)); // Orange
        } else if(status.equals("Verified")){
            statusLabel.setBackground(new Color(59, 130, 246)); // Blue
        } else if(status.equals("Accepted")){
            statusLabel.setBackground(new Color(34, 197, 94)); // Green
        } else if(status.equals("Rejected")){
            statusLabel.setBackground(new Color(239, 68, 68)); // Red
        } else {
            statusLabel.setBackground(new Color(107, 114, 128)); // Gray
        }
        return statusLabel;
    }
}