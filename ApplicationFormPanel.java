import java.awt.*;
import java.io.File;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ApplicationFormPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    JTextField fullName, ttl, nik, address;
    JComboBox<String> scholarshipCombo;
    JLabel photoLabel;
    String photoPath = "";
    
    // TAMBAHAN: Untuk upload dokumen persyaratan
    Map<String, String> uploadedDocuments; // Key: nama dokumen, Value: path file
    Map<String, JLabel> documentLabels; // Untuk menampilkan status upload
    
    public ApplicationFormPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        uploadedDocuments = new HashMap<>();
        documentLabels = new HashMap<>();
        
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Top bar
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
        back.addActionListener(e -> win.show("detail")); 
        
        JLabel titleLabel = new JLabel("📝 Form Pendaftaran Beasiswa");
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
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
            BorderFactory.createEmptyBorder(35, 35, 35, 35)
        ));
        form.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel formTitle = new JLabel("Lengkapi Data Pendaftaran");
        formTitle.setFont(new Font("Segoe UI Symbol", Font.BOLD, 22));
        formTitle.setForeground(new Color(6, 78, 59));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(formTitle);
        form.add(Box.createRigidArea(new Dimension(0,8)));
        
        JLabel formDesc = new JLabel("Pastikan semua data dan dokumen yang Anda upload benar dan lengkap.");
        formDesc.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        formDesc.setForeground(new Color(107, 114, 128));
        formDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(formDesc);
        form.add(Box.createRigidArea(new Dimension(0,30)));
        
        // Beasiswa dropdown
        form.add(createLabel("Pilih Beasiswa")); 
        scholarshipCombo = new JComboBox<>();
        scholarshipCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        scholarshipCombo.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        scholarshipCombo.setBackground(Color.WHITE);
        scholarshipCombo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // TAMBAHAN: Listener untuk update dokumen yang diperlukan
        scholarshipCombo.addActionListener(e -> updateRequiredDocuments(form));
        
        loadScholarships();
        
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
        form.add(Box.createRigidArea(new Dimension(0,20)));
        
        // Nama
        form.add(createLabel("Nama Lengkap")); 
        fullName = createTextField(); 
        form.add(fullName);
        form.add(Box.createRigidArea(new Dimension(0,20)));
        
        // TTL
        form.add(createLabel("Tempat/Tanggal Lahir")); 
        JLabel ttlHint = new JLabel("Contoh: Jakarta, 15 Agustus 2000");
        ttlHint.setFont(new Font("Segoe UI Symbol", Font.ITALIC, 12));
        ttlHint.setForeground(new Color(156, 163, 175));
        ttlHint.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(ttlHint);
        form.add(Box.createRigidArea(new Dimension(0,5)));
        ttl = createTextField(); 
        form.add(ttl);
        form.add(Box.createRigidArea(new Dimension(0,20)));
        
        // NIK
        form.add(createLabel("NIK (Nomor Induk Kependudukan)")); 
        nik = createTextField(); 
        form.add(nik);
        form.add(Box.createRigidArea(new Dimension(0,20)));
        
        // Alamat
        form.add(createLabel("Alamat Lengkap")); 
        address = createTextField(); 
        form.add(address);
        form.add(Box.createRigidArea(new Dimension(0,20)));
        
        // Photo
        form.add(createLabel("Pas Foto"));
        form.add(Box.createRigidArea(new Dimension(0,8)));
        
        JPanel photoPanel = new JPanel();
        photoPanel.setLayout(new BoxLayout(photoPanel, BoxLayout.Y_AXIS));
        photoPanel.setBackground(Color.WHITE);
        photoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        photoLabel = new JLabel("Belum ada foto dipilih"); 
        photoLabel.setFont(new Font("Segoe UI Symbol", Font.ITALIC, 13));
        photoLabel.setForeground(new Color(107, 114, 128));
        photoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JButton upload = new JButton("📁 Pilih File Foto"); 
        upload.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
        upload.setForeground(new Color(16, 185, 129));
        upload.setBackground(Color.WHITE);
        upload.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(16, 185, 129), 2, true),
            BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        upload.setFocusPainted(false);
        upload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        upload.setAlignmentX(Component.LEFT_ALIGNMENT);
        upload.addActionListener(e -> choosePhotoFile());
        
        upload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                upload.setBackground(new Color(240, 253, 244));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                upload.setBackground(Color.WHITE);
            }
        });
        
        photoPanel.add(photoLabel);
        photoPanel.add(Box.createRigidArea(new Dimension(0,10)));
        photoPanel.add(upload);
        
        form.add(photoPanel);
        form.add(Box.createRigidArea(new Dimension(0,30)));
        
        // TAMBAHAN: Section untuk upload dokumen persyaratan
        // Akan di-populate saat beasiswa dipilih
        
        // Submit button
        JButton submit = new JButton("📤 Kirim Pendaftaran"); 
        submit.setFont(new Font("Segoe UI Symbol", Font.BOLD, 16));
        submit.setForeground(Color.WHITE);
        submit.setBackground(new Color(16, 185, 129));
        submit.setBorderPainted(false);
        submit.setFocusPainted(false);
        submit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submit.setBorder(BorderFactory.createEmptyBorder(16, 45, 16, 45));
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
        
        // Initial load dokumen
        updateRequiredDocuments(form);
    }
    
    // TAMBAHAN: Method untuk update dokumen yang diperlukan
    private void updateRequiredDocuments(JPanel form){
        // Hapus dokumen section yang lama (jika ada)
        Component[] components = form.getComponents();
        boolean startRemoving = false;
        java.util.List<Component> toRemove = new ArrayList<>();
        
        for(Component comp : components){
            if(comp instanceof JLabel && ((JLabel)comp).getText().equals("📎 Dokumen Persyaratan")){
                startRemoving = true;
            }
            if(startRemoving){
                toRemove.add(comp);
                if(comp instanceof JButton && ((JButton)comp).getText().startsWith("📤")){
                    break;
                }
            }
        }
        
        for(Component comp : toRemove){
            if(!(comp instanceof JButton && ((JButton)comp).getText().startsWith("📤"))){
                form.remove(comp);
            }
        }
        
        // Clear uploaded documents
        uploadedDocuments.clear();
        documentLabels.clear();
        
        // Dapatkan beasiswa yang dipilih
        String selectedScholarship = (String) scholarshipCombo.getSelectedItem();
        if(selectedScholarship == null) return;
        
        Scholarship scholarship = null;
        for(Scholarship s : store.loadScholarships()){
            if(s.name.equals(selectedScholarship)){
                scholarship = s;
                break;
            }
        }
        
        if(scholarship == null || scholarship.requiredDocuments.isEmpty()) return;
        
        // Tambahkan section dokumen baru
        int submitIndex = -1;
        for(int i = 0; i < form.getComponentCount(); i++){
            Component comp = form.getComponent(i);
            if(comp instanceof JButton && ((JButton)comp).getText().startsWith("📤")){
                submitIndex = i;
                break;
            }
        }
        
        if(submitIndex < 0) return;
        
        // Insert dokumen section sebelum submit button
        JLabel docTitle = createLabel("📎 Dokumen Persyaratan");
        form.add(docTitle, submitIndex);
        form.add(Box.createRigidArea(new Dimension(0,10)), submitIndex + 1);
        
        int currentIndex = submitIndex + 2;
        
        for(String docName : scholarship.requiredDocuments){
            JPanel docPanel = createDocumentUploadPanel(docName);
            form.add(docPanel, currentIndex++);
            form.add(Box.createRigidArea(new Dimension(0,15)), currentIndex++);
        }
        
        form.add(Box.createRigidArea(new Dimension(0,15)), currentIndex);
        
        form.revalidate();
        form.repaint();
    }
    
    // TAMBAHAN: Create panel untuk upload satu dokumen
    private JPanel createDocumentUploadPanel(String docName){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        JLabel nameLabel = new JLabel(docName);
        nameLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
        nameLabel.setForeground(new Color(75, 85, 99));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel statusLabel = new JLabel("Belum diupload");
        statusLabel.setFont(new Font("Segoe UI Symbol", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(156, 163, 175));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        documentLabels.put(docName, statusLabel);
        
        JButton uploadBtn = new JButton("📁 Upload " + docName);
        uploadBtn.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        uploadBtn.setForeground(new Color(59, 130, 246));
        uploadBtn.setBackground(Color.WHITE);
        uploadBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(59, 130, 246), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        uploadBtn.setFocusPainted(false);
        uploadBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        uploadBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        uploadBtn.addActionListener(e -> chooseDocumentFile(docName));
        
        uploadBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                uploadBtn.setBackground(new Color(239, 246, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                uploadBtn.setBackground(Color.WHITE);
            }
        });
        
        panel.add(nameLabel);
        panel.add(Box.createRigidArea(new Dimension(0,5)));
        panel.add(statusLabel);
        panel.add(Box.createRigidArea(new Dimension(0,8)));
        panel.add(uploadBtn);
        
        return panel;
    }
    
    // TAMBAHAN: Method untuk memilih file dokumen
    private void chooseDocumentFile(String docName){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Upload " + docName);
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Dokumen (PDF, DOC, DOCX, JPG, PNG)", "pdf", "doc", "docx", "jpg", "jpeg", "png");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            if(selectedFile.exists()){
                uploadedDocuments.put(docName, selectedFile.getAbsolutePath());
                JLabel label = documentLabels.get(docName);
                if(label != null){
                    label.setText("✅ " + selectedFile.getName());
                    label.setForeground(new Color(34, 197, 94));
                }
            }
        }
    }
    
    private void choosePhotoFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih Pas Foto");
        
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "File Gambar (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);
        
        int result = fileChooser.showOpenDialog(this);
        
        if(result == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            if(selectedFile.exists()){
                photoPath = selectedFile.getAbsolutePath();
                photoLabel.setText("✅ " + selectedFile.getName());
                photoLabel.setForeground(new Color(16, 185, 129));
            } else {
                JOptionPane.showMessageDialog(this, "File tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                photoPath = "";
                photoLabel.setText("Belum ada foto dipilih");
                photoLabel.setForeground(new Color(107, 114, 128));
            }
        }
    }
    
    private JLabel createLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        label.setForeground(new Color(55, 65, 81));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }
    
    private JTextField createTextField(){
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(10, 14, 10, 14)
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
            
            // TAMBAHAN: Validasi dokumen persyaratan
            Scholarship scholarship = null;
            for(Scholarship s : store.loadScholarships()){
                if(s.name.equals(selectedScholarship)){
                    scholarship = s;
                    break;
                }
            }
            
            if(scholarship != null && !scholarship.requiredDocuments.isEmpty()){
                for(String docName : scholarship.requiredDocuments){
                    if(!uploadedDocuments.containsKey(docName) || uploadedDocuments.get(docName).isEmpty()){
                        JOptionPane.showMessageDialog(this, "Mohon upload semua dokumen persyaratan!\nDokumen yang belum diupload: " + docName, "Peringatan", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }
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
            a.documents = new HashMap<>(uploadedDocuments); // Copy uploaded documents
            
            List<Application> apps = store.loadApplications();
            
            boolean exists = apps.stream().anyMatch(app -> app.nim.equals(nim) && app.scholarshipName.equals(selectedScholarship));
            if(exists){
                 JOptionPane.showMessageDialog(this, "Anda sudah mendaftar untuk beasiswa ini!");
                 return;
            }
            
            apps.add(a);
            store.saveApplications(apps);
            
            // Clear form
            if(scholarshipCombo.getItemCount() > 0) scholarshipCombo.setSelectedIndex(0);
            fullName.setText("");
            ttl.setText("");
            nik.setText("");
            address.setText("");
            photoPath = "";
            photoLabel.setText("Belum ada foto dipilih");
            photoLabel.setForeground(new Color(107, 114, 128));
            uploadedDocuments.clear();
            
            Path selectedPath = Paths.get("selected_scholarship.txt");
            if(Files.exists(selectedPath)){
                Files.delete(selectedPath);
            }
            
            win.show("thanks");
        } catch(Exception e){ 
            JOptionPane.showMessageDialog(this, "Gagal submit: " + e.getMessage()); 
            e.printStackTrace();
        }
    }
}