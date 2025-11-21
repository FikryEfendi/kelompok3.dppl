import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.nio.file.*;
import java.text.SimpleDateFormat;

public class ReportsPanel extends JPanel {
    MainWindow win;
    DataStore store;

    public ReportsPanel(MainWindow win, DataStore store){
        this.win = win;
        this.store = store;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

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

        JLabel title = new JLabel("📊 Laporan dan Statistik");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        top.add(back, BorderLayout.WEST);
        top.add(title, BorderLayout.CENTER);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        center.setBackground(new Color(249, 250, 251));

        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        statsPanel.setBackground(new Color(249, 250, 251));
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        List<Application> apps = store.loadApplications();
        List<User> users = store.loadUsers();
        List<Scholarship> scholarships = store.loadScholarships();

        int totalApps = apps.size();
        int pending = (int) apps.stream().filter(a -> a.status.equals("Pending")).count();
        int accepted = (int) apps.stream().filter(a -> a.status.equals("Accepted")).count();
        int rejected = (int) apps.stream().filter(a -> a.status.equals("Rejected")).count();

        statsPanel.add(createStatCard("📝 Total Pendaftar", String.valueOf(totalApps), new Color(59, 130, 246)));
        statsPanel.add(createStatCard("⏳ Menunggu Verifikasi", String.valueOf(pending), new Color(245, 158, 11)));
        statsPanel.add(createStatCard("✅ Diterima", String.valueOf(accepted), new Color(34, 197, 94)));
        statsPanel.add(createStatCard("❌ Ditolak", String.valueOf(rejected), new Color(239, 68, 68)));

        JPanel reportButtons = new JPanel(new GridLayout(3, 2, 20, 20));
        reportButtons.setBackground(new Color(249, 250, 251));
        reportButtons.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        reportButtons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        reportButtons.setAlignmentX(Component.LEFT_ALIGNMENT);

        reportButtons.add(createReportButton("📄 Laporan Pendaftar", "Generate laporan semua pendaftar", () -> generateApplicantReport()));
        reportButtons.add(createReportButton("✅ Laporan Penerima", "Generate laporan yang diterima", () -> generateAcceptedReport()));
        reportButtons.add(createReportButton("📊 Statistik per Beasiswa", "Lihat statistik tiap beasiswa", () -> showScholarshipStats()));
        reportButtons.add(createReportButton("👥 Laporan User", "Generate laporan semua user", () -> generateUserReport()));
        reportButtons.add(createReportButton("📈 Export CSV", "Export data ke format CSV", () -> exportToCSV()));
        reportButtons.add(createReportButton("🖨️ Print Summary", "Cetak ringkasan lengkap", () -> printSummary()));

        center.add(statsPanel);
        center.add(reportButtons);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(center), BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String value, Color color){
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 15));
        titleLabel.setForeground(new Color(107, 114, 128));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 36));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(valueLabel);

        return card;
    }

    private JButton createReportButton(String title, String desc, Runnable action){
        JButton btn = new JButton();
        btn.setLayout(new BorderLayout(10, 10));
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.BOLD, 15));
        titleLabel.setForeground(new Color(31, 41, 55));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel("<html><p style='width: 200px;'>" + desc + "</p></html>"); 
        descLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        descLabel.setForeground(new Color(107, 114, 128));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        textPanel.add(descLabel);

        btn.add(textPanel, BorderLayout.CENTER);
        btn.addActionListener(e -> action.run());

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(240, 253, 244));
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(16, 185, 129), 2, true),
                    BorderFactory.createEmptyBorder(19, 19, 19, 19)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(Color.WHITE);
                btn.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(229, 231, 235), 1, true),
                    BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
        });

        return btn;
    }

    private void generateApplicantReport(){
        try {
            List<Application> apps = store.loadApplications();
            StringBuilder sb = new StringBuilder();
            sb.append("=".repeat(70)).append("\n");
            sb.append("LAPORAN PENDAFTAR BEASISWA\n");
            sb.append("=".repeat(70)).append("\n");
            sb.append("Tanggal: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())).append("\n\n");
            sb.append("Total Pendaftar: ").append(apps.size()).append("\n\n");
            sb.append(String.format("%-15s %-25s %-30s %-15s\n", "NIM", "Nama", "Beasiswa", "Status"));
            sb.append("-".repeat(70)).append("\n");

            for(Application a : apps){
                sb.append(String.format("%-15s %-25s %-30s %-15s\n", 
                    a.nim, a.fullName, a.scholarshipName, a.status));
            }

            sb.append("\n").append("=".repeat(70)).append("\n");

            String filename = "Laporan_Pendaftar_" + System.currentTimeMillis() + ".txt";
            Files.write(Paths.get(filename), sb.toString().getBytes());

            JOptionPane.showMessageDialog(this, "Laporan berhasil dibuat!\nFile: " + filename, "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateAcceptedReport(){
        try {
            List<Application> apps = store.loadApplications();
            List<Application> accepted = new ArrayList<>();
            for(Application a : apps){
                if(a.status.equals("Accepted")){
                    accepted.add(a);
                }
            }

            StringBuilder sb = new StringBuilder();
            sb.append("=".repeat(70)).append("\n");
            sb.append("LAPORAN PENERIMA BEASISWA\n");
            sb.append("=".repeat(70)).append("\n");
            sb.append("Tanggal: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())).append("\n\n");
            sb.append("Total Penerima: ").append(accepted.size()).append("\n\n");
            sb.append(String.format("%-15s %-25s %-30s\n", "NIM", "Nama", "Beasiswa"));
            sb.append("-".repeat(70)).append("\n");

            for(Application a : accepted){
                sb.append(String.format("%-15s %-25s %-30s\n", a.nim, a.fullName, a.scholarshipName));
            }

            sb.append("\n").append("=".repeat(70)).append("\n");

            String filename = "Laporan_Penerima_" + System.currentTimeMillis() + ".txt";
            Files.write(Paths.get(filename), sb.toString().getBytes());

            JOptionPane.showMessageDialog(this, "Laporan berhasil dibuat!\nFile: " + filename, "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showScholarshipStats(){
        List<Application> apps = store.loadApplications();
        List<Scholarship> scholarships = store.loadScholarships();
        
        Map<String, Integer> stats = new HashMap<>();
        for(Scholarship s : scholarships){
            stats.put(s.name, 0);
        }
        
        for(Application a : apps){
            stats.put(a.scholarshipName, stats.getOrDefault(a.scholarshipName, 0) + 1);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("STATISTIK PENDAFTAR PER BEASISWA\n");
        sb.append("=".repeat(50)).append("\n\n");
        for(Map.Entry<String, Integer> entry : stats.entrySet()){
            sb.append(String.format("%-35s : %d pendaftar\n", entry.getKey(), entry.getValue()));
        }

        JOptionPane.showMessageDialog(this, sb.toString(), "Statistik per Beasiswa", JOptionPane.INFORMATION_MESSAGE);
    }

    private void generateUserReport(){
        try {
            List<User> users = store.loadUsers();
            StringBuilder sb = new StringBuilder();
            sb.append("=".repeat(70)).append("\n");
            sb.append("LAPORAN USER TERDAFTAR\n");
            sb.append("=".repeat(70)).append("\n");
            sb.append("Tanggal: ").append(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date())).append("\n\n");
            sb.append("Total User: ").append(users.size()).append("\n\n");
            sb.append(String.format("%-15s %-25s %-30s\n", "NIM", "Nama", "Email"));
            sb.append("-".repeat(70)).append("\n");

            for(User u : users){
                sb.append(String.format("%-15s %-25s %-30s\n", u.nim, u.name, u.email));
            }

            sb.append("\n").append("=".repeat(70)).append("\n");

            String filename = "Laporan_User_" + System.currentTimeMillis() + ".txt";
            Files.write(Paths.get(filename), sb.toString().getBytes());

            JOptionPane.showMessageDialog(this, "Laporan berhasil dibuat!\nFile: " + filename, "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportToCSV(){
        try {
            List<Application> apps = store.loadApplications();
            StringBuilder sb = new StringBuilder();
            sb.append("NIM,Nama,Beasiswa,TTL,NIK,Alamat,Status\n");

            for(Application a : apps){
                sb.append(String.format("%s,%s,%s,%s,%s,%s,%s\n", 
                    a.nim, a.fullName, a.scholarshipName, a.ttl, a.nik, a.address, a.status));
            }

            String filename = "Export_Data_" + System.currentTimeMillis() + ".csv";
            Files.write(Paths.get(filename), sb.toString().getBytes());

            JOptionPane.showMessageDialog(this, "Data berhasil di-export!\nFile: " + filename, "Berhasil", JOptionPane.INFORMATION_MESSAGE);
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void printSummary(){
        List<Application> apps = store.loadApplications();
        List<User> users = store.loadUsers();
        List<Scholarship> scholarships = store.loadScholarships();

        int pending = (int) apps.stream().filter(a -> a.status.equals("Pending")).count();
        int verified = (int) apps.stream().filter(a -> a.status.equals("Verified")).count();
        int accepted = (int) apps.stream().filter(a -> a.status.equals("Accepted")).count();
        int rejected = (int) apps.stream().filter(a -> a.status.equals("Rejected")).count();

        String summary = String.format(
            "RINGKASAN SISTEM BEASISWA\n" +
            "=".repeat(40) + "\n\n" +
            "Total User Terdaftar    : %d\n" +
            "Total Beasiswa Tersedia : %d\n" +
            "Total Pendaftar         : %d\n\n" +
            "Status Pendaftar:\n" +
            "  - Pending             : %d\n" +
            "  - Verified            : %d\n" +
            "  - Accepted            : %d\n" +
            "  - Rejected            : %d\n",
            users.size(), scholarships.size(), apps.size(), pending, verified, accepted, rejected
        );

        JOptionPane.showMessageDialog(this, summary, "Ringkasan Sistem", JOptionPane.INFORMATION_MESSAGE);
    }
}