import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

public class SignupPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    JTextField nameF, nimF, emailF;
    JPasswordField passF, pass2F;

    public SignupPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        
        JPanel left = new JPanel(); 
        left.setPreferredSize(new Dimension(300,0)); 
        left.setBackground(Color.decode("#f0f0f0")); 
        left.add(new JLabel("[Logo]")); 
        
        JPanel form = new JPanel(); 
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS)); 
        form.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
        
        form.add(new JLabel("Welcome newcomer")); 
        form.add(Box.createRigidArea(new Dimension(0,8)));
        form.add(new JLabel("Nama Lengkap")); 
        nameF=new JTextField(); 
        nameF.setMaximumSize(new Dimension(300,28)); 
        form.add(nameF);
        
        form.add(new JLabel("NIM")); 
        nimF=new JTextField(); 
        nimF.setMaximumSize(new Dimension(300,28)); 
        form.add(nimF);
        
        form.add(new JLabel("Email")); 
        emailF=new JTextField(); 
        emailF.setMaximumSize(new Dimension(300,28)); 
        form.add(emailF);
        
        form.add(new JLabel("Password")); 
        passF=new JPasswordField(); 
        passF.setMaximumSize(new Dimension(300,28)); 
        form.add(passF);
        
        form.add(new JLabel("Konfirmasi password")); 
        pass2F=new JPasswordField(); 
        pass2F.setMaximumSize(new Dimension(300,28)); 
        form.add(pass2F);
        
        form.add(Box.createRigidArea(new Dimension(0,8)));
        
        JButton signup = new JButton("Sign up"); 
        signup.setAlignmentX(Component.LEFT_ALIGNMENT);
        signup.addActionListener(e -> doSignup());
        form.add(signup);
        
        JButton back = new JButton("Log in jika anda sudah mempunyai akun"); 
        back.setAlignmentX(Component.LEFT_ALIGNMENT);
        back.addActionListener(e -> win.show("login")); 
        form.add(back);

        add(left, BorderLayout.WEST); 
        add(form, BorderLayout.CENTER);
    }

    void doSignup(){
        String name=nameF.getText().trim();
        String nim=nimF.getText().trim();
        String email=emailF.getText().trim();
        String p=new String(passF.getPassword());
        String p2=new String(pass2F.getPassword());
        
        if(name.isEmpty()||nim.isEmpty()||p.isEmpty()){ 
            JOptionPane.showMessageDialog(this, "Isi semua field penting"); 
            return; 
        }
        if(!p.equals(p2)){ 
            JOptionPane.showMessageDialog(this, "Password dan konfirmasi tidak sama"); 
            return; 
        }
        
        List<User> users = store.loadUsers();
        for(User u: users) {
            if(u.nim.equals(nim)){ 
                JOptionPane.showMessageDialog(this, "NIM sudah terdaftar"); 
                return; 
            }
        }
        
        users.add(new User(name,nim,email,p));
        store.saveUsers(users);
        JOptionPane.showMessageDialog(this, "Akun berhasil dibuat. Silakan login.");
        win.show("login");
    }
}