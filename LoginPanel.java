import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginPanel extends JPanel {

    private MainWindow win;
    private DataStore store;

    private PlaceholderField nimField;
    private PlaceholderPasswordField passField;

    public LoginPanel(MainWindow win, DataStore store) {
        this.win = win;
        this.store = store;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // =============================
        // LEFT IMAGE / ILLUSTRATION
        // =============================
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(330, 0));
        left.setBackground(Color.decode("#EAF3FF"));
        left.setLayout(new BorderLayout());

        JLabel img = new JLabel("<html><center><br><br><h1 style='font-size:22px'>[Logo]</h1></center></html>");
        img.setHorizontalAlignment(SwingConstants.CENTER);
        left.add(img, BorderLayout.CENTER);

        // =============================
        // RIGHT FORM PANEL
        // =============================
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));
        form.setBackground(Color.WHITE);

        JLabel title = new JLabel("Selamat Datang Kembali");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("Masuk untuk melanjutkan");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(title);
        form.add(Box.createRigidArea(new Dimension(0, 5)));
        form.add(subtitle);
        form.add(Box.createRigidArea(new Dimension(0, 25)));

        // =============================
        // INPUT FIELD MODERN
        // =============================
        nimField = new PlaceholderField("Masukkan NIM");
        passField = new PlaceholderPasswordField("Masukkan Password");

        form.add(makeLabeledField("NIM", nimField));
        form.add(Box.createRigidArea(new Dimension(0, 15)));
        form.add(makeLabeledField("Password", passField));

        form.add(Box.createRigidArea(new Dimension(0, 25)));

        // =============================
        // LOGIN BUTTON
        // =============================
        JButton loginBtn = new JButton("Masuk");
        loginBtn.setPreferredSize(new Dimension(200, 40));
        loginBtn.setMaximumSize(new Dimension(200, 40));
        loginBtn.setBackground(Color.decode("#3A7BFF"));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loginBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginBtn.addActionListener(e -> doLogin());

        form.add(loginBtn);
        form.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton signupBtn = new JButton("Belum punya akun? Daftar");
        signupBtn.setBorder(null);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setForeground(Color.decode("#3A7BFF"));
        signupBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        signupBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        signupBtn.addActionListener(e -> win.show("signup"));

        form.add(signupBtn);

        // ADD TO MAIN LAYOUT
        add(left, BorderLayout.WEST);
        add(form, BorderLayout.CENTER);
    }

    // ==================================================================================
    // COMPONENT HELPERS
    // ==================================================================================

    private JPanel makeLabeledField(String label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        field.setMaximumSize(new Dimension(320, 38));
        field.setPreferredSize(new Dimension(320, 38));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode("#D0D7E0"), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        panel.add(lbl);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(field);

        return panel;
    }

    // ==================================================================================
    // LOGIN LOGIC + VALIDATION
    // ==================================================================================

    private void doLogin() {
        String nim = nimField.getText().trim();
        String pass = new String(passField.getPassword()).trim();

        if (nim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM tidak boleh kosong");
            return;
        }
        if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password tidak boleh kosong");
            return;
        }

        List<User> users = store.loadUsers();
        for (User u : users) {
            if (u.nim.equals(nim) && u.password.equals(pass)) {
                try {
                    java.nio.file.Files.write(java.nio.file.Paths.get("current_nim.txt"), nim.getBytes());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                win.show("loading");

                new Timer(1200, e -> win.show("home")) {{
                    setRepeats(false);
                }}.start();

                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Login gagal: NIM atau password salah");
    }

    // ==================================================================================
    // CUSTOM PLACEHOLDER COMPONENTS
    // ==================================================================================
    class PlaceholderField extends JTextField {
        String placeholder;
        PlaceholderField(String placeholder) {
            this.placeholder = placeholder;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty() && !isFocusOwner()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.GRAY);
                g2.setFont(getFont().deriveFont(Font.ITALIC));
                g2.drawString(placeholder, 10, getHeight() / 2 + 5);
                g2.dispose();
            }
        }
    }

    class PlaceholderPasswordField extends JPasswordField {
        String placeholder;
        PlaceholderPasswordField(String placeholder) {
            this.placeholder = placeholder;
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getPassword().length == 0 && !isFocusOwner()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.GRAY);
                g2.setFont(getFont().deriveFont(Font.ITALIC));
                g2.drawString(placeholder, 10, getHeight() / 2 + 5);
                g2.dispose();
            }
        }
    }
}
