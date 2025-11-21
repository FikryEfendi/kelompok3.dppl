import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class ManageApplicantsPanel extends JPanel {
    MainWindow win;
    DataStore store;
    JTable table;
    DefaultTableModel model;

    public ManageApplicantsPanel(MainWindow win, DataStore store){
        this.win = win;
        this.store = store;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Top panel
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
        back.addActionListener(e -> win.show("adminHome"));

        JLabel title = new JLabel("👥 Kelola Pendaftar");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        top.add(back, BorderLayout.WEST);
        top.add(title, BorderLayout.CENTER);

        // Center panel
        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        center.setBackground(Color.WHITE);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionPanel.setBackground(Color.WHITE);

        JButton verifyBtn = createActionButton("Verifikasi");
        verifyBtn.addActionListener(e -> verifyApplicant());

        JButton acceptBtn = createActionButton("Terima");
        acceptBtn.setBackground(new Color(34, 197, 94));
        acceptBtn.addActionListener(e -> updateStatus("Accepted"));

        JButton rejectBtn = createActionButton("Tolak");
        rejectBtn.setBackground(new Color(239, 68, 68));
        rejectBtn.addActionListener(e -> updateStatus("Rejected"));

        JButton viewBtn = createActionButton("Lihat Detail");
        viewBtn.addActionListener(e -> viewDetail());

        JButton refreshBtn = createActionButton("Refresh");
        refreshBtn.addActionListener(e -> loadData());

        actionPanel.add(verifyBtn);
        actionPanel.add(acceptBtn);
        actionPanel.add(rejectBtn);
        actionPanel.add(viewBtn);
        actionPanel.add(refreshBtn);

        // Table
        String[] columns = {"NIM", "Nama Lengkap", "Beasiswa", "NIK", "Status"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(240, 253, 244));
        table.getTableHeader().setForeground(new Color(31, 41, 55));
        table.setSelectionBackground(new Color(209, 250, 229));
        table.setSelectionForeground(new Color(31, 41, 55));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235)));

        center.add(actionPanel, BorderLayout.NORTH);
        center.add(scrollPane, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        loadData();
    }

    private JButton createActionButton(String text){
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(16, 185, 129));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Color bg = btn.getBackground();
                if(bg.getRed() == 239){
                    btn.setBackground(new Color(220, 38, 38));
                } else if(bg.getGreen() == 197){
                    btn.setBackground(new Color(22, 163, 74));
                } else {
                    btn.setBackground(new Color(5, 150, 105));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(btn.getText().equals("Tolak")){
                    btn.setBackground(new Color(239, 68, 68));
                } else if(btn.getText().equals("Terima")){
                    btn.setBackground(new Color(34, 197, 94));
                } else {
                    btn.setBackground(new Color(16, 185, 129));
                }
            }
        });

        return btn;
    }

    private void loadData(){
        model.setRowCount(0);
        List<Application> apps = store.loadApplications();
        for(Application a : apps){
            model.addRow(new Object[]{a.nim, a.fullName, a.scholarshipName, a.nik, a.status});
        }
    }

    private void verifyApplicant(){
        int row = table.getSelectedRow();
        if(row < 0){
            JOptionPane.showMessageDialog(this, "Pilih pendaftar yang akan diverifikasi!");
            return;
        }

        String nim = (String) model.getValueAt(row, 0);
        List<Application> list = store.loadApplications();
        Application selected = list.stream().filter(a -> a.nim.equals(nim)).findFirst().orElse(null);
        if(selected == null) return;

        selected.status = "Verified";
        store.saveApplications(list);
        loadData();
        JOptionPane.showMessageDialog(this, "Pendaftar berhasil diverifikasi!");
    }

    private void updateStatus(String newStatus){
        int row = table.getSelectedRow();
        if(row < 0){
            JOptionPane.showMessageDialog(this, "Pilih pendaftar terlebih dahulu!");
            return;
        }

        String nim = (String) model.getValueAt(row, 0);
        List<Application> list = store.loadApplications();
        Application selected = list.stream().filter(a -> a.nim.equals(nim)).findFirst().orElse(null);
        if(selected == null) return;

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Yakin ingin mengubah status menjadi " + newStatus + "?", 
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if(confirm == JOptionPane.YES_OPTION){
            selected.status = newStatus;
            store.saveApplications(list);
            loadData();
            JOptionPane.showMessageDialog(this, "Status berhasil diupdate menjadi " + newStatus + "!");
        }
    }

    private void viewDetail(){
        int row = table.getSelectedRow();
        if(row < 0){
            JOptionPane.showMessageDialog(this, "Pilih pendaftar untuk melihat detail!");
            return;
        }

        String nim = (String) model.getValueAt(row, 0);
        List<Application> list = store.loadApplications();
        Application app = list.stream().filter(a -> a.nim.equals(nim)).findFirst().orElse(null);
        if(app == null) return;

        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Detail Pendaftar", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 600);
        dialog.setLocationRelativeTo(this);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        content.setBackground(Color.WHITE);

        content.add(createDetailRow("NIM:", app.nim));
        content.add(createDetailRow("Nama Lengkap:", app.fullName));
        content.add(createDetailRow("Beasiswa:", app.scholarshipName));
        content.add(createDetailRow("Tempat/Tgl Lahir:", app.ttl));
        content.add(createDetailRow("NIK:", app.nik));
        content.add(createDetailRow("Alamat:", app.address));
        content.add(createDetailRow("Foto:", app.photoPath));
        content.add(createDetailRow("Status:", app.status));
        
        // TAMBAHAN: Tampilkan dokumen yang diupload
        if(app.documents != null && !app.documents.isEmpty()){
            content.add(Box.createRigidArea(new Dimension(0, 15)));
            
            JLabel docTitle = new JLabel("📎 Dokumen yang Diupload:");
            docTitle.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
            docTitle.setForeground(new Color(31, 41, 55));
            docTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
            content.add(docTitle);
            content.add(Box.createRigidArea(new Dimension(0, 10)));
            
            for(java.util.Map.Entry<String, String> entry : app.documents.entrySet()){
                content.add(createDocumentRow(entry.getKey(), entry.getValue()));
            }
        }

        JButton closeBtn = new JButton("Tutup");
        closeBtn.setBackground(new Color(16, 185, 129));
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        closeBtn.addActionListener(e -> dialog.dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setBackground(Color.WHITE);
        btnPanel.add(closeBtn);

        dialog.add(new JScrollPane(content), BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JPanel createDetailRow(String label, String value){
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        lblLabel.setPreferredSize(new Dimension(150, 25));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));

        row.add(lblLabel, BorderLayout.WEST);
        row.add(lblValue, BorderLayout.CENTER);

        return row;
    }
    
    // TAMBAHAN: Create row untuk dokumen dengan tombol buka
    private JPanel createDocumentRow(String docName, String filePath){
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(new Color(249, 250, 251));
        row.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblLabel = new JLabel(docName);
        lblLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        lblLabel.setPreferredSize(new Dimension(200, 25));

        JButton openBtn = new JButton("📄 Buka File");
        openBtn.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 12));
        openBtn.setForeground(new Color(59, 130, 246));
        openBtn.setBackground(Color.WHITE);
        openBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(59, 130, 246), 1, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        openBtn.setFocusPainted(false);
        openBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openBtn.addActionListener(e -> {
            try {
                Desktop.getDesktop().open(new java.io.File(filePath));
            } catch(Exception ex){
                JOptionPane.showMessageDialog(row, "Tidak dapat membuka file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        row.add(lblLabel, BorderLayout.WEST);
        row.add(openBtn, BorderLayout.EAST);

        return row;
    }
}