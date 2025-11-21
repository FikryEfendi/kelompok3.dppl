import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class StatusDetailPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    private Application currentApplication; 
    private JPanel detailContent;

    public StatusDetailPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
    }
    
    public void loadApplication(Application app){
        this.currentApplication = app;
        
        removeAll();
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Top bar
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
            StatusOverviewPanel overviewPanel = (StatusOverviewPanel)win.getPanel("status");
            if(overviewPanel != null) overviewPanel.refresh();
            win.show("status");
        }); 
        
        JLabel titleLabel = new JLabel("📝 Detail Pendaftaran");
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
            BorderFactory.createEmptyBorder(35, 35, 35, 35)
        ));
        detailContent.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel header = new JLabel("Pendaftaran Beasiswa: " + app.scholarshipName);
        header.setFont(new Font("Segoe UI Symbol", Font.BOLD, 24));
        header.setForeground(new Color(6, 78, 59));
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailContent.add(header);
        detailContent.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Status Badge
        JLabel statusLabel = createStatusBadge(app.status);
        detailContent.add(statusLabel);
        detailContent.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Data Diri Section
        detailContent.add(createSectionTitle("👤 Data Diri Pendaftar"));
        detailContent.add(Box.createRigidArea(new Dimension(0, 15)));
        detailContent.add(createDetailRow("NIM", app.nim));
        detailContent.add(createDetailRow("Nama Lengkap", app.fullName));
        detailContent.add(createDetailRow("Tempat/Tgl Lahir", app.ttl));
        detailContent.add(createDetailRow("NIK", app.nik));
        detailContent.add(createDetailRow("Alamat", app.address));
        
        detailContent.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Dokumen Section
        detailContent.add(createSectionTitle("📎 Dokumen & Kelengkapan"));
        detailContent.add(Box.createRigidArea(new Dimension(0, 15)));
        detailContent.add(createDetailRow("Pas Foto", app.photoPath));
        
        // TAMBAHAN: Tampilkan dokumen yang diupload
        if(app.documents != null && !app.documents.isEmpty()){
            detailContent.add(Box.createRigidArea(new Dimension(0, 10)));
            for(java.util.Map.Entry<String, String> entry : app.documents.entrySet()){
                detailContent.add(createDocumentRow(entry.getKey(), entry.getValue()));
            }
        }

        mainContent.add(detailContent);
        add(new JScrollPane(mainContent), BorderLayout.CENTER);
        
        revalidate();
        repaint();
    }
    
    private JLabel createSectionTitle(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
        label.setForeground(new Color(31, 41, 55));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    private JPanel createDetailRow(String label, String value){
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setBackground(new Color(249, 250, 251));
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
        lblLabel.setForeground(new Color(75, 85, 99));
        lblLabel.setPreferredSize(new Dimension(180, 25));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        lblValue.setForeground(new Color(31, 41, 55));
        
        row.add(lblLabel, BorderLayout.WEST);
        row.add(lblValue, BorderLayout.CENTER);
        
        return row;
    }
    
    // TAMBAHAN: Create row untuk dokumen dengan link
    private JPanel createDocumentRow(String docName, String filePath){
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setBackground(new Color(249, 250, 251));
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)),
            BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblLabel = new JLabel(docName);
        lblLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
        lblLabel.setForeground(new Color(75, 85, 99));
        lblLabel.setPreferredSize(new Dimension(180, 25));
        
        // Clickable label untuk membuka file
        JLabel lblValue = new JLabel("📄 " + new java.io.File(filePath).getName());
        lblValue.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        lblValue.setForeground(new Color(59, 130, 246));
        lblValue.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblValue.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().open(new java.io.File(filePath));
                } catch(Exception ex){
                    JOptionPane.showMessageDialog(row, "Tidak dapat membuka file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lblValue.setText("<html><u>📄 " + new java.io.File(filePath).getName() + "</u></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblValue.setText("📄 " + new java.io.File(filePath).getName());
            }
        });
        
        row.add(lblLabel, BorderLayout.WEST);
        row.add(lblValue, BorderLayout.CENTER);
        
        return row;
    }
    
    private JLabel createStatusBadge(String status){
        JLabel statusLabel = new JLabel("  STATUS: " + status.toUpperCase() + "  ");
        statusLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        if(status.equals("Pending")){
            statusLabel.setBackground(new Color(245, 158, 11));
        } else if(status.equals("Verified")){
            statusLabel.setBackground(new Color(59, 130, 246));
        } else if(status.equals("Accepted")){
            statusLabel.setBackground(new Color(34, 197, 94));
        } else if(status.equals("Rejected")){
            statusLabel.setBackground(new Color(239, 68, 68));
        } else {
            statusLabel.setBackground(new Color(107, 114, 128));
        }
        return statusLabel;
    }
}