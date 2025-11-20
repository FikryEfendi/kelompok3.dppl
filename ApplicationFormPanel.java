import java.awt.*;
import java.nio.file.*;
import java.util.List;
import javax.swing.*;

public class ApplicationFormPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    JTextField fullName, ttl, nik, address;
    JComboBox<String> scholarshipCombo;
    JLabel photoLabel;
    String photoPath = "";
    
    public ApplicationFormPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Top bar with green theme
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(16, 185, 129));
        top.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        
        JButton back = new JButton("← Kembali"); 
        back.setFont(new Font("Segoe UI", Font.BOLD, 13));
        back.setForeground(Color.WHITE);
        back.setBackground(new Color(5, 150, 105));
        back.setBorderPainted(false);
        back.setFocusPainted(false);
        back.setCursor(new Cursor(Cursor.HAND_CURSOR));
        back.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        back.addActionListener(e -> win.show("detail")); 
        
        JLabel titleLabel = new JLabel("📝 Form Pendaftaran Beasiswa");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        top.add(back, BorderLayout.WEST);
        top.add(titleLabel, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        // Main form
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        mainContent.setBackground(new Color(240, 253, 244));
        
        JPanel form = new JPanel(); 
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS)); 
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(167, 243, 208), 2, true),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        form.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel formTitle = new JLabel("Lengkapi Data Pendaftaran");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        formTitle.setForeground(new Color(6, 78, 59));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(formTitle);
        form.add(Box.createRigidArea(new Dimension(0,5)));
        
        JLabel formDesc = new JLabel("Pastikan semua data yang Anda masukkan benar dan lengkap.");
        formDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formDesc.setForeground(new Color(107, 114, 128));
        formDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(formDesc);
        form.add(Box.createRigidArea(new Dimension(0,25)));
        
        // Beasiswa dropdown
        form.add(createLabel("Pilih Beasiswa")); 
        scholarshipCombo = new JComboBox<>();
        scholarshipCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        scholarshipCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        scholarshipCombo.setBackground(Color.WHITE);
        scholarshipCombo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Load scholarships from database
        loadScholarships();
        
        // Try to pre-select from file
        try {
            Path selectedPath = Paths.get("selected_scholarship.txt");
            if(Files.exists(selectedPath)){
                String selected = new String(Files.readAllBytes(selectedPath)).trim();
                String[] parts = selected.split("\\|");
                if(parts.length >= 2){
                    scholarshipCombo.setSelectedItem(parts[1]);
                }
            }
        } catch(Exception e){}
        
        form.add(scholarshipCombo);
        form.add(Box.createRigidArea(new Dimension(0,15)));
        
        // Nama
        form.add(createLabel("Nama Lengkap")); 
        fullName = createTextField(); 
        form.add(fullName);
        form.add(Box.createRigidArea(new Dimension(0,15)));
        
        // TTL
        form.add(createLabel("Tempat/Tanggal Lahir (contoh: Jakarta, 15 Agustus 2000)")); 
        ttl = createTextField(); 
        form.add(ttl);
        form.add(Box.createRigidArea(new Dimension(0,15)));
        
        // NIK
        form.add(createLabel("NIK (Nomor Induk Kependudukan)")); 
        nik = createTextField(); 
        form.add(nik);
        form.add(Box.createRigidArea(new Dimension(0,15)));
        
        // Alamat
        form.add(createLabel("Alamat Lengkap")); 
        address = createTextField(); 
        form.add(address);
        form.add(Box.createRigidArea(new Dimension(0,15)));
        
        // Photo
        form.add(createLabel("Pas Foto"));
        photoLabel = new JLabel("Belum ada foto dipilih"); 
        photoLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        photoLabel.setForeground(new Color(107, 114, 128));
        photoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(photoLabel);
        form.add(Box.createRigidArea(new Dimension(0,8)));
        
        JButton upload = new JButton("📁 Pilih File Foto"); 
        upload.setFont(new Font("Segoe UI", Font.BOLD, 13));
        upload.setForeground(new Color(16, 185, 129));
        upload.setBackground(Color.WHITE);
        upload.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(16, 185, 129), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        upload.setFocusPainted(false);
        upload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        upload.setAlignmentX(Component.LEFT_ALIGNMENT);
        upload.addActionListener(e -> {
            String p = JOptionPane.showInputDialog(this, "Masukkan path file foto (contoh: C:/Users/nama/Documents/foto.jpg):");
            if(p!=null && !p.trim().isEmpty()){ 
                photoPath = p.trim(); 
                photoLabel.setText("✅ " + photoPath); 
                photoLabel.setForeground(new Color(16, 185, 129));
            }
        });
        form.add(upload);
        form.add(Box.createRigidArea(new Dimension(0,25)));
        
        // Submit button
        JButton submit = new JButton("📤 Kirim Pendaftaran"); 
        submit.setFont(new Font("Segoe UI", Font.BOLD, 15));
        submit.setForeground(Color.WHITE);
        submit.setBackground(new Color(16, 185, 129));
        submit.setBorderPainted(false);
        submit.setFocusPainted(false);
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submit.setBorder(BorderFactory.createEmptyBorder(15, 40, 15, 40));
        submit.setAlignmentX(Component.LEFT_ALIGNMENT);
        submit.addActionListener(e -> submit());
        
        submit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                submit.setBackground(new Color(5, 150, 105));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                submit.setBackground(new Color(16, 185, 129));
            }
        });
        
        form.add(submit);
        
        mainContent.add(form);
        add(new JScrollPane(mainContent), BorderLayout.CENTER);
    }
    
    private JLabel createLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(55, 65, 81));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    private JTextField createTextField(){
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }
    
    private void loadScholarships(){
        scholarshipCombo.removeAllItems();
        List<Scholarship> scholarships = store.loadScholarships();
        for(Scholarship s : scholarships){
            if(s.status.equals("Active")){
                scholarshipCombo.addItem(s.name);
            }
        }
    }

    void submit(){
        try {
            String nim = new String(Files.readAllBytes(Paths.get("current_nim.txt"))).trim();
            
            String selectedScholarship = (String) scholarshipCombo.getSelectedItem();
            String fullNameText = fullName.getText().trim();
            String ttlText = ttl.getText().trim();
            String nikText = nik.getText().trim();
            String addressText = address.getText().trim();
            
            // Validation
            if(selectedScholarship == null || selectedScholarship.isEmpty()){
                JOptionPane.showMessageDialog(this, "Pilih beasiswa terlebih dahulu!");
                return;
            }
            
            if(fullNameText.isEmpty() || ttlText.isEmpty() || nikText.isEmpty() || addressText.isEmpty()){
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }
            
            if(photoPath.isEmpty()){
                JOptionPane.showMessageDialog(this, "Upload pas foto terlebih dahulu!");
                return;
            }
            
            Application a = new Application();
            a.nim = nim;
            a.scholarshipName = selectedScholarship;
            a.fullName = fullNameText;
            a.ttl = ttlText;
            a.nik = nikText;
            a.address = addressText;
            a.photoPath = photoPath;
            a.status = "Pending";
            
            List<Application> apps = store.loadApplications();
            apps.add(a);
            store.saveApplications(apps);
            
            // Clear form
            scholarshipCombo.setSelectedIndex(0);
            fullName.setText("");
            ttl.setText("");
            nik.setText("");
            address.setText("");
            photoPath = "";
            photoLabel.setText("Belum ada foto dipilih");
            photoLabel.setForeground(new Color(107, 114, 128));
            
            win.show("thanks");
        } catch(Exception e){ 
            JOptionPane.showMessageDialog(this, "Gagal submit: " + e.getMessage()); 
        }
    }
}