import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class ManageScholarshipsPanel extends JPanel {
    MainWindow win;
    DataStore store;
    JTable table;
    DefaultTableModel model;

    public ManageScholarshipsPanel(MainWindow win, DataStore store){
        this.win = win;
        this.store = store;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Top panel
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
        back.addActionListener(e -> win.show("adminHome"));

        JLabel title = new JLabel("📚 Kelola Beasiswa");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        top.add(back, BorderLayout.WEST);
        top.add(title, BorderLayout.CENTER);

        // Center panel with table
        JPanel center = new JPanel(new BorderLayout());
        center.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        center.setBackground(Color.WHITE);

        // Action buttons
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionPanel.setBackground(Color.WHITE);

        JButton addBtn = createActionButton("Tambah Beasiswa");
        addBtn.addActionListener(e -> addScholarship());

        JButton editBtn = createActionButton("Edit");
        editBtn.addActionListener(e -> editScholarship());

        JButton deleteBtn = createActionButton("Hapus");
        deleteBtn.setBackground(new Color(239, 68, 68));
        deleteBtn.addActionListener(e -> deleteScholarship());

        JButton refreshBtn = createActionButton("Refresh");
        refreshBtn.addActionListener(e -> loadData());

        actionPanel.add(addBtn);
        actionPanel.add(editBtn);
        actionPanel.add(deleteBtn);
        actionPanel.add(refreshBtn);

        // Table
        String[] columns = {"ID", "Nama Beasiswa", "Kuota", "Deadline", "Status"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
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
        List<Scholarship> scholarships = store.loadScholarships();
        for(Scholarship s : scholarships){
            model.addRow(new Object[]{s.id, s.name, s.quota, s.deadline, s.status});
        }
    }

    private void addScholarship(){
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Tambah Beasiswa", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 550);
        dialog.setLocationRelativeTo(this);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField idField = createField();
        JTextField nameField = createField();
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JTextArea reqArea = new JTextArea(4, 20);
        reqArea.setLineWrap(true);
        reqArea.setWrapStyleWord(true);
        JTextField quotaField = createField();
        JTextField deadlineField = createField();
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Active", "Inactive"});

        form.add(new JLabel("ID Beasiswa:"));
        form.add(idField);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Nama Beasiswa:"));
        form.add(nameField);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Deskripsi:"));
        form.add(new JScrollPane(descArea));
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Persyaratan:"));
        form.add(new JScrollPane(reqArea));
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Kuota:"));
        form.add(quotaField);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Deadline (dd/mm/yyyy):"));
        form.add(deadlineField);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Status:"));
        form.add(statusBox);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Simpan");
        saveBtn.setBackground(new Color(16, 185, 129));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String desc = descArea.getText().trim();
            String req = reqArea.getText().trim();
            String quota = quotaField.getText().trim();
            String deadline = deadlineField.getText().trim();
            String status = (String) statusBox.getSelectedItem();

            if(id.isEmpty() || name.isEmpty()){
                JOptionPane.showMessageDialog(dialog, "ID dan Nama harus diisi!");
                return;
            }

            List<Scholarship> list = store.loadScholarships();
            list.add(new Scholarship(id, name, desc, req, quota, deadline, status));
            store.saveScholarships(list);
            loadData();
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Beasiswa berhasil ditambahkan!");
        });

        JButton cancelBtn = new JButton("Batal");
        cancelBtn.addActionListener(e -> dialog.dispose());

        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);

        dialog.add(new JScrollPane(form), BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void editScholarship(){
        int row = table.getSelectedRow();
        if(row < 0){
            JOptionPane.showMessageDialog(this, "Pilih beasiswa yang akan diedit!");
            return;
        }

        String id = (String) model.getValueAt(row, 0);
        List<Scholarship> list = store.loadScholarships();
        Scholarship selected = list.stream().filter(s -> s.id.equals(id)).findFirst().orElse(null);
        if(selected == null) return;

        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Edit Beasiswa", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(500, 550);
        dialog.setLocationRelativeTo(this);

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField idField = createField();
        idField.setText(selected.id);
        idField.setEditable(false);
        JTextField nameField = createField();
        nameField.setText(selected.name);
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setText(selected.description);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JTextArea reqArea = new JTextArea(4, 20);
        reqArea.setText(selected.requirements);
        reqArea.setLineWrap(true);
        reqArea.setWrapStyleWord(true);
        JTextField quotaField = createField();
        quotaField.setText(selected.quota);
        JTextField deadlineField = createField();
        deadlineField.setText(selected.deadline);
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Active", "Inactive"});
        statusBox.setSelectedItem(selected.status);

        form.add(new JLabel("ID Beasiswa:"));
        form.add(idField);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Nama Beasiswa:"));
        form.add(nameField);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Deskripsi:"));
        form.add(new JScrollPane(descArea));
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Persyaratan:"));
        form.add(new JScrollPane(reqArea));
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Kuota:"));
        form.add(quotaField);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Deadline:"));
        form.add(deadlineField);
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(new JLabel("Status:"));
        form.add(statusBox);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Update");
        saveBtn.setBackground(new Color(16, 185, 129));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.addActionListener(e -> {
            selected.name = nameField.getText().trim();
            selected.description = descArea.getText().trim();
            selected.requirements = reqArea.getText().trim();
            selected.quota = quotaField.getText().trim();
            selected.deadline = deadlineField.getText().trim();
            selected.status = (String) statusBox.getSelectedItem();

            store.saveScholarships(list);
            loadData();
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Beasiswa berhasil diupdate!");
        });

        JButton cancelBtn = new JButton("Batal");
        cancelBtn.addActionListener(e -> dialog.dispose());

        btnPanel.add(saveBtn);
        btnPanel.add(cancelBtn);

        dialog.add(new JScrollPane(form), BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void deleteScholarship(){
        int row = table.getSelectedRow();
        if(row < 0){
            JOptionPane.showMessageDialog(this, "Pilih beasiswa yang akan dihapus!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus beasiswa ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            String id = (String) model.getValueAt(row, 0);
            List<Scholarship> list = store.loadScholarships();
            list.removeIf(s -> s.id.equals(id));
            store.saveScholarships(list);
            loadData();
            JOptionPane.showMessageDialog(this, "Beasiswa berhasil dihapus!");
        }
    }

    private JTextField createField(){
        JTextField f = new JTextField();
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return f;
    }
}