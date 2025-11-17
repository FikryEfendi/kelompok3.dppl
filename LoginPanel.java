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
<<<<<<< HEAD
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Left illustration area with gradient
        JPanel left = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(99, 102, 241), 0, getHeight(), new Color(139, 92, 246));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        left.setPreferredSize(new Dimension(400, 0));
        left.setLayout(new GridBagLayout());
        
        JLabel logoLabel = new JLabel("<html><div style='text-align: center;'><span style='font-size: 48px; color: white;'>🎓</span><br><br><span style='font-size: 24px; color: white; font-weight: bold;'>Sistem Beasiswa</span><br><span style='font-size: 14px; color: rgba(255,255,255,0.8);'>Wujudkan Mimpimu</span></div></html>");
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        left.add(logoLabel);

        // Right form with modern design
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);
        
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));

        JLabel title = new JLabel("Selamat Datang");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(31, 41, 55));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Silakan login ke akun Anda");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(107, 114, 128));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        form.add(title);
        form.add(Box.createRigidArea(new Dimension(0, 8)));
        form.add(subtitle);
        form.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // NIM Field
        JLabel nimLabel = new JLabel("NIM");
        nimLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nimLabel.setForeground(new Color(55, 65, 81));
        nimLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(nimLabel);
        form.add(Box.createRigidArea(new Dimension(0, 8)));
        
        nimField = new JTextField();
        nimField.setMaximumSize(new Dimension(350, 45));
        nimField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nimField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        nimField.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(nimField);
        form.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Password Field
        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        passLabel.setForeground(new Color(55, 65, 81));
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(passLabel);
        form.add(Box.createRigidArea(new Dimension(0, 8)));
        
        passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(350, 45));
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1, true),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        passField.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(passField);
        form.add(Box.createRigidArea(new Dimension(0, 30)));
        
        // Login Button
        JButton loginBtn = createStyledButton("Login", new Color(99, 102, 241), Color.WHITE);
        loginBtn.setMaximumSize(new Dimension(350, 45));
=======

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
>>>>>>> 122dd4f14bfe0cbfedafbc5bac22087be26b7bad
        loginBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginBtn.addActionListener(e -> doLogin());

        form.add(loginBtn);
<<<<<<< HEAD
        
        form.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Divider
        JPanel divider = new JPanel();
        divider.setLayout(new BoxLayout(divider, BoxLayout.X_AXIS));
        divider.setMaximumSize(new Dimension(350, 20));
        divider.setBackground(Color.WHITE);
        divider.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JSeparator sep1 = new JSeparator();
        JSeparator sep2 = new JSeparator();
        JLabel orLabel = new JLabel("atau");
        orLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        orLabel.setForeground(new Color(107, 114, 128));
        
        divider.add(sep1);
        divider.add(Box.createRigidArea(new Dimension(10, 0)));
        divider.add(orLabel);
        divider.add(Box.createRigidArea(new Dimension(10, 0)));
        divider.add(sep2);
        form.add(divider);
        
        form.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Signup Button
        JButton signupBtn = createStyledButton("Buat Akun Baru", Color.WHITE, new Color(99, 102, 241));
        signupBtn.setMaximumSize(new Dimension(350, 45));
        signupBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(99, 102, 241), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
=======
        form.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton signupBtn = new JButton("Belum punya akun? Daftar");
        signupBtn.setBorder(null);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setForeground(Color.decode("#3A7BFF"));
        signupBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
>>>>>>> 122dd4f14bfe0cbfedafbc5bac22087be26b7bad
        signupBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        signupBtn.addActionListener(e -> win.show("signup"));

        form.add(signupBtn);

<<<<<<< HEAD
        formContainer.add(form);
=======
        // ADD TO MAIN LAYOUT
>>>>>>> 122dd4f14bfe0cbfedafbc5bac22087be26b7bad
        add(left, BorderLayout.WEST);
        add(formContainer, BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (bg.equals(Color.WHITE)) {
                    btn.setBackground(new Color(243, 244, 246));
                } else {
                    btn.setBackground(bg.darker());
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
        
        return btn;
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
<<<<<<< HEAD
        String pass = new String(passField.getPassword());
        
        if (nim.isEmpty() || pass.isEmpty()) {
            showError("Mohon isi semua field");
            return;
        }
        
        List<User> users = store.loadUsers();
        for(User u: users){
            if(u.nim.equals(nim) && u.password.equals(pass)){
                try { 
                    java.nio.file.Files.write(java.nio.file.Paths.get("current_nim.txt"), nim.getBytes()); 
                } catch(Exception ex){
=======
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
>>>>>>> 122dd4f14bfe0cbfedafbc5bac22087be26b7bad
                    ex.printStackTrace();
                }

                win.show("loading");
<<<<<<< HEAD
                Timer timer = new Timer(1200, evt -> win.show("home"));
                timer.setRepeats(false);
                timer.start();
                return;
            }
        }
        showError("Login gagal. Periksa kembali NIM dan password Anda.");
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
=======

                new Timer(1200, e -> win.show("home")) {{
                    setRepeats(false);
                }}.start();

                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Login gagal: NIM atau password salah");
>>>>>>> 122dd4f14bfe0cbfedafbc5bac22087be26b7bad
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
