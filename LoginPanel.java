import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
        
        // left illustration area
        JPanel left = new JPanel();
        left.setPreferredSize(new Dimension(300,0));
        left.setBackground(Color.decode("#f0f0f0"));
        JLabel logoLabel = new JLabel("<html><br><br>[Logo/Image]<br><br></html>");
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        left.add(logoLabel);

        // right form
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        JLabel title = new JLabel("Welcome Back");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        form.add(title);
        form.add(Box.createRigidArea(new Dimension(0,10)));
        form.add(new JLabel("NIM"));
        nimField = new JTextField();
        nimField.setMaximumSize(new Dimension(300,30));
        form.add(nimField);
        form.add(Box.createRigidArea(new Dimension(0,10)));
        form.add(new JLabel("Password"));
        passField = new JPasswordField();
        passField.setMaximumSize(new Dimension(300,30));
        form.add(passField);
        form.add(Box.createRigidArea(new Dimension(0,10)));
        JButton loginBtn = new JButton("Log in");
        loginBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginBtn.addActionListener(e -> doLogin());
        form.add(loginBtn);
        form.add(Box.createRigidArea(new Dimension(0,10)));
        JButton signupBtn = new JButton("Sign up jika anda belum mempunyai akun");
        signupBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        signupBtn.addActionListener(e -> win.show("signup"));
        form.add(signupBtn);

        add(left, BorderLayout.WEST);
        add(form, BorderLayout.CENTER);
    }

    void doLogin(){
        String nim = nimField.getText().trim();
        String pass = new String(passField.getPassword());
        List<User> users = store.loadUsers();
        for(User u: users){
            if(u.nim.equals(nim) && u.password.equals(pass)){
                // store current user by writing to a small temp file
                try { 
                    java.nio.file.Files.write(java.nio.file.Paths.get("current_nim.txt"), nim.getBytes()); 
                } catch(Exception ex){
                    ex.printStackTrace();
                }
                win.show("loading");
                // simulate loading with Timer
                Timer timer = new Timer(1200, evt -> win.show("home"));
                timer.setRepeats(false);
                timer.start();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Login gagal: cek NIM/password");
    }
}