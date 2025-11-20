import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SignupPanel extends JPanel {
    MainWindow win;
    DataStore store;

    JTextField nameF, nimF, emailF;
    JPasswordField passF, pass2F;

    public SignupPanel(MainWindow win, DataStore store){
        this.win = win;
        this.store = store;

        setLayout(new BorderLayout());
        setBackground(Color.decode("#eef3f7")); // background lembut

        // LEFT PANEL (logo + warna)
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(280, 0));
        left.setBackground(Color.decode("#1e3a8a")); // navy modern

        JLabel logo = new JLabel("<html><font color='white' size='6'>LOGO</font></html>");
        left.add(logo);

        // FORM PANEL
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        form.setBackground(Color.white);

        JLabel title = new JLabel("Welcome newcomer");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 22));
        form.add(title);
        form.add(Box.createRigidArea(new Dimension(0, 20)));

        // NAMA
        form.add(label("Nama Lengkap"));
        nameF = field();
        nameF.putClientProperty("JTextField.placeholderText", "Masukkan nama lengkap");
        form.add(nameF);

        // NIM
        form.add(label("NIM"));
        nimF = field();
        nimF.putClientProperty("JTextField.placeholderText", "Masukkan NIM Anda");
        form.add(nimF);

        // EMAIL
        form.add(label("Email"));
        emailF = field();
        emailF.putClientProperty("JTextField.placeholderText", "contoh: nama@gmail.com");
        form.add(emailF);

        // PASSWORD
        form.add(label("Password"));
        passF = passField();
        passF.putClientProperty("JTextField.placeholderText", "Minimal 6 karakter");
        form.add(passF);

        // CONFIRM PASSWORD
        form.add(label("Konfirmasi Password"));
        pass2F = passField();
        pass2F.putClientProperty("JTextField.placeholderText", "Ulangi password");
        form.add(pass2F);

        form.add(Box.createRigidArea(new Dimension(0, 15)));

        // INFO PASSWORD
        JLabel info = new JLabel("<html><i>Password harus minimal 6 karakter & mengandung angka.</i></html>");
        info.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 11));
        info.setForeground(Color.gray);
        form.add(info);

        form.add(Box.createRigidArea(new Dimension(0, 25)));

        // BUTTON SIGNUP
        JButton signup = new JButton("Sign Up");
        signup.setAlignmentX(Component.LEFT_ALIGNMENT);
        signup.setPreferredSize(new Dimension(120, 32));
        signup.addActionListener(e -> doSignup());
        form.add(signup);

        // BUTTON BACK
        JButton back = new JButton("Sudah punya akun? Log in");
        back.setAlignmentX(Component.LEFT_ALIGNMENT);
        back.setBorderPainted(false);
        back.setForeground(Color.decode("#1e3a8a"));
        back.setContentAreaFilled(false);
        back.addActionListener(e -> win.show("login"));
        form.add(Box.createRigidArea(new Dimension(0, 10)));
        form.add(back);

        // FINAL ADD PANELS
        add(left, BorderLayout.WEST);
        add(form, BorderLayout.CENTER);

        // FOKUS OTOMATIS
        SwingUtilities.invokeLater(() -> nameF.requestFocus());
    }

    // LABEL STYLE
    private JLabel label(String text){
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI Symbol", Font.BOLD, 13));
        return l;
    }

    // TEXTFIELD TEMPLATE
    private JTextField field(){
        JTextField f = new JTextField();
        f.setMaximumSize(new Dimension(320, 28));
        f.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        return f;
    }

    // PASSFIELD TEMPLATE
    private JPasswordField passField(){
        JPasswordField f = new JPasswordField();
        f.setMaximumSize(new Dimension(320, 28));
        f.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 13));
        return f;
    }

    // SIGNUP LOGIC (validasi lengkap)
    void doSignup(){
        String name = nameF.getText().trim();
        String nim = nimF.getText().trim();
        String email = emailF.getText().trim();
        String p = new String(passF.getPassword());
        String p2 = new String(pass2F.getPassword());

        // Validasi dasar
        if(name.isEmpty() || nim.isEmpty() || email.isEmpty() || p.isEmpty()){
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return;
        }

        // Validasi NIM angka
        if(!nim.matches("\\d+")){
            JOptionPane.showMessageDialog(this, "NIM harus berupa angka.");
            return;
        }

        // Email valid
        if(!email.contains("@") || !email.contains(".")){
            JOptionPane.showMessageDialog(this, "Format email tidak valid.");
            return;
        }

        // Password kuat
        if(p.length() < 6 || !p.matches(".*\\d.*")){
            JOptionPane.showMessageDialog(this, "Password minimal 6 karakter & mengandung angka.");
            return;
        }

        // Confirm
        if(!p.equals(p2)){
            JOptionPane.showMessageDialog(this, "Konfirmasi password tidak sesuai.");
            return;
        }

        // Cek apakah NIM sudah ada
        List<User> users = store.loadUsers();
        for(User u : users){
            if(u.nim.equals(nim)){
                JOptionPane.showMessageDialog(this, "NIM sudah terdaftar.");
                return;
            }
        }

        // Simpan user baru
        users.add(new User(name, nim, email, p));
        store.saveUsers(users);

        JOptionPane.showMessageDialog(this, "Akun berhasil dibuat! Silakan login.");
        win.show("login");
    }
}
