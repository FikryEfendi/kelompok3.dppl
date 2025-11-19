import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginPanel extends JPanel {
    MainWindow win;
    DataStore store;
    JTextField nimField;
    JPasswordField passField;

    public LoginPanel(MainWindow win, DataStore store){
        this.win = win; 
        this.store = store;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        // Left illustration area with green gradient
        JPanel left = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(16, 185, 129), 0, getHeight(), new Color(5, 150, 105));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        left.setPreferredSize(new Dimension(400, 0));
        left.setLayout(new GridBagLayout());
        
        JLabel logoLabel = new JLabel("<html><div style='text-align: center;'><span style='font-size: 48px; color: white;'>🎓</span><br><br><span style='font-size: 24px; color: white; font-weight: bold;'>Sistem Beasiswa</span><br><span style='font-size: 14px; color: rgba(255,255,255,0.9);'>Wujudkan Mimpi Pendidikanmu</span></div></html>");
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        left.add(logoLabel);

        // Right form with modern design
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setBackground(Color.WHITE);
        
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));

        JLabel title = new JLabel("Selamat Datang Kembali");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(31, 41, 55));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Masuk ke akun Anda untuk melanjutkan");
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
        JButton loginBtn = createStyledButton("Masuk", new Color(16, 185, 129), Color.WHITE);
        loginBtn.setMaximumSize(new Dimension(350, 45));
        loginBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginBtn.addActionListener(e -> doLogin());
        form.add(loginBtn);
        
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
        JButton signupBtn = createStyledButton("Buat Akun Baru", Color.WHITE, new Color(16, 185, 129));
        signupBtn.setMaximumSize(new Dimension(350, 45));
        signupBtn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(16, 185, 129), 2, true),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        signupBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        signupBtn.addActionListener(e -> win.show("signup"));
        form.add(signupBtn);

        formContainer.add(form);
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
                    btn.setBackground(new Color(5, 150, 105));
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bg);
            }
        });
        
        return btn;
    }

    void doLogin(){
        String nim = nimField.getText().trim();
        String pass = new String(passField.getPassword());
        
        if (nim.isEmpty() || pass.isEmpty()) {
            showError("Mohon isi semua field");
            return;
        }
        
        // Check for admin login
        if(nim.equals("admin") && pass.equals("admin123")){
            try { 
                java.nio.file.Files.write(java.nio.file.Paths.get("current_nim.txt"), "admin".getBytes()); 
            } catch(Exception ex){
                ex.printStackTrace();
            }
            win.show("loading");
            Timer timer = new Timer(1200, evt -> win.show("adminHome"));
            timer.setRepeats(false);
            timer.start();
            return;
        }
        
        List<User> users = store.loadUsers();
        for(User u: users){
            if(u.nim.equals(nim) && u.password.equals(pass)){
                try { 
                    java.nio.file.Files.write(java.nio.file.Paths.get("current_nim.txt"), nim.getBytes()); 
                } catch(Exception ex){
                    ex.printStackTrace();
                }
                win.show("loading");
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
    }
}