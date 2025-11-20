import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.nio.file.*;

public class ManageAnnouncementsPanel extends JPanel {
    MainWindow win;
    DataStore store;
    JTable table;
    DefaultTableModel model;

    public ManageAnnouncementsPanel(MainWindow win, DataStore store){
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

        JLabel title = new JLabel("📢 Kelola Pengumuman");
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

        JButton publishBtn = createActionButton("Publikasikan Hasil Seleksi");
        publishBtn.addActionListener(e -> publishResults());

        JButton customBtn = createActionButton("Buat Pengumuman Custom");
        customBtn.addActionListener(e -> createCustomAnnouncement());

        JButton viewBtn = createActionButton("Lihat Detail");
        viewBtn.addActionListener(e -> viewAnnouncement());

        JButton deleteBtn = createActionButton("Hapus");
        deleteBtn.setBackground(new Color(239, 68, 68));
        deleteBtn.addActionListener(e -> deleteAnnouncement());

        JButton refreshBtn = createActionButton("Refresh");
        refreshBtn.addActionListener(e -> loadData());

        actionPanel.add(publishBtn);
        actionPanel.add(customBtn);
        actionPanel.add(viewBtn);
        actionPanel.add(deleteBtn);
        actionPanel.add(refreshBtn);

        // Table
        String[] columns = {"Tanggal", "Judul", "Tipe", "Status"};
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
                if(btn.getBackground().getRed() == 239){
                    btn.setBackground(new Color(220, 38, 38));
                } else {
                    btn.setBackground(new Color(5, 150, 105));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(btn.getText().equals("Hapus")){
                    btn.setBackground(new Color(239, 68, 68));
                } else {
                    btn.setBackground(new Color(16, 185, 129));
                }
            }
        });

        return btn;
    }

    private void loadData(){
        model.setRowCount(0);
        try {
            Path announcePath = Paths.get("announcements.txt");
            if(Files.exists(announcePath)){
                List<String> lines = Files.readAllLines(announcePath);
                for(String line : lines){
                    String[] parts = line.split("\\|");
                    // PERUBAHAN: Memperbarui pengecekan panjang array
                    if(parts.length >= 4){ // Cukup 4 untuk data dasar
                        // Kolom baru untuk Path Detail tidak perlu ditampilkan di tabel, hanya disimpan di file
                        model.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3]}); 
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void publishResults(){
        List<Application> apps = store.loadApplications();
        List<Application> accepted = new ArrayList<>();
        for(Application a : apps){
            if(a.status.equals("Accepted")){
                accepted.add(a);
            }
        }

        if(accepted.isEmpty()){
            JOptionPane.showMessageDialog(this, "Belum ada pendaftar yang diterima!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("PENGUMUMAN HASIL SELEKSI BEASISWA\n");
        sb.append("="+ "=".repeat(50) + "\n\n");
        sb.append("Selamat kepada mahasiswa berikut yang telah lolos seleksi beasiswa:\n\n");

        for(Application a : accepted){
            sb.append(String.format("- %s (%s) - %s\n", a.fullName, a.nim, a.scholarshipName));
        }

        sb.append("\n" + "=".repeat(50) + "\n");
        sb.append("Silakan konfirmasi penerimaan beasiswa melalui sistem.\n");

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Publikasikan hasil seleksi untuk " + accepted.size() + " mahasiswa?", 
            "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if(confirm == JOptionPane.YES_OPTION){
            try {
                String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
                String detailFilename = "announcement_detail_" + System.currentTimeMillis() + ".txt"; // Simpan nama file detail
                
                // PERUBAHAN: Menyimpan path detail di baris pengumuman
                String announcement = date + "|Pengumuman Hasil Seleksi Beasiswa|Hasil Seleksi|Published|" + detailFilename + "\n";
                
                Path path = Paths.get("announcements.txt");
                if(!Files.exists(path)){
                    Files.createFile(path);
                }
                Files.write(path, announcement.getBytes(), java.nio.file.StandardOpenOption.APPEND);
                
                // Save detail
                Files.write(Paths.get(detailFilename), sb.toString().getBytes());

                loadData();
                JOptionPane.showMessageDialog(this, "Pengumuman berhasil dipublikasikan!");
            } catch(Exception e){
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }

    private void createCustomAnnouncement(){
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Buat Pengumuman", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(550, 400);
        dialog.setLocationRelativeTo(this);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        form.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Judul Pengumuman:");
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        JTextField titleField = new JTextField();
        titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

        JLabel contentLabel = new JLabel("Isi Pengumuman:");
        contentLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        JTextArea contentArea = new JTextArea(8, 40);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(contentArea);

        form.add(titleLabel);
        form.add(Box.createRigidArea(new Dimension(0, 5)));
        form.add(titleField);
        form.add(Box.createRigidArea(new Dimension(0, 15)));
        form.add(contentLabel);
        form.add(Box.createRigidArea(new Dimension(0, 5)));
        form.add(scrollPane);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(Color.WHITE);

        JButton publishBtn = new JButton("Publikasikan");
        publishBtn.setBackground(new Color(16, 185, 129));
        publishBtn.setForeground(Color.WHITE);
        publishBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        publishBtn.addActionListener(e -> {
            String title = titleField.getText().trim();
            String content = contentArea.getText().trim();

            if(title.isEmpty() || content.isEmpty()){
                JOptionPane.showMessageDialog(dialog, "Judul dan isi harus diisi!");
                return;
            }

            try {
                String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
                String detailFilename = "announcement_detail_" + System.currentTimeMillis() + ".txt";
                
                // PERUBAHAN: Menyimpan path detail di baris pengumuman
                String announcement = date + "|" + title + "|Custom|Published|" + detailFilename + "\n";
                
                Path path = Paths.get("announcements.txt");
                if(!Files.exists(path)){
                    Files.createFile(path);
                }
                Files.write(path, announcement.getBytes(), java.nio.file.StandardOpenOption.APPEND);
                
                Files.write(Paths.get(detailFilename), content.getBytes());

                loadData();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Pengumuman berhasil dipublikasikan!");
            } catch(Exception ex){
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage());
            }
        });

        JButton cancelBtn = new JButton("Batal");
        cancelBtn.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        cancelBtn.addActionListener(e -> dialog.dispose());

        btnPanel.add(publishBtn);
        btnPanel.add(cancelBtn);

        dialog.add(form, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void viewAnnouncement(){
        int row = table.getSelectedRow();
        if(row < 0){
            JOptionPane.showMessageDialog(this, "Pilih pengumuman untuk melihat detail!");
            return;
        }

        String title = (String) model.getValueAt(row, 1);
        JOptionPane.showMessageDialog(this, "Detail pengumuman: " + title + "\nSilakan cek file 'announcement_detail_[timestamp].txt' di folder proyek.");
    }

    private void deleteAnnouncement(){
        int row = table.getSelectedRow();
        if(row < 0){
            JOptionPane.showMessageDialog(this, "Pilih pengumuman yang akan dihapus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Yakin ingin menghapus pengumuman ini?", 
            "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if(confirm == JOptionPane.YES_OPTION){
            try {
                Path path = Paths.get("announcements.txt");
                List<String> lines = Files.readAllLines(path);
                lines.remove(row);
                Files.write(path, lines);
                loadData();
                JOptionPane.showMessageDialog(this, "Pengumuman berhasil dihapus!");
            } catch(Exception e){
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
}