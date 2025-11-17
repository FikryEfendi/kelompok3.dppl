import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.nio.file.*;
import java.util.List;
import javax.swing.*;

public class ApplicationFormPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    JTextField fullName, ttl, nik, address;
    JLabel photoLabel;
    String photoPath = "";
    
    public ApplicationFormPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        JButton back = new JButton("Kembali"); 
        back.addActionListener(e -> win.show("detail")); 
        top.add(back);
        add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(); 
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS)); 
        form.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        JLabel formTitle = new JLabel("Form Pendaftaran Beasiswa");
        formTitle.setFont(new Font("Arial", Font.BOLD, 16));
        form.add(formTitle);
        form.add(Box.createRigidArea(new Dimension(0,10)));
        
        form.add(new JLabel("Nama")); 
        fullName = new JTextField(); 
        fullName.setMaximumSize(new Dimension(600,28)); 
        form.add(fullName);
        
        form.add(new JLabel("TTL (mm/dd/yyyy)")); 
        ttl = new JTextField(); 
        ttl.setMaximumSize(new Dimension(600,28)); 
        form.add(ttl);
        
        form.add(new JLabel("NIK")); 
        nik = new JTextField(); 
        nik.setMaximumSize(new Dimension(600,28)); 
        form.add(nik);
        
        form.add(new JLabel("Alamat")); 
        address = new JTextField(); 
        address.setMaximumSize(new Dimension(600,28)); 
        form.add(address);
        
        photoLabel = new JLabel("[Pas Foto]"); 
        form.add(photoLabel);
        
        JButton upload = new JButton("Upload Foto (pilih file path)"); 
        upload.addActionListener(e -> {
            String p = JOptionPane.showInputDialog(this, "Masukkan path file foto (contoh: C:/folder/foto.jpg)");
            if(p!=null && !p.trim().isEmpty()){ 
                photoPath = p.trim(); 
                photoLabel.setText("Foto: "+photoPath); 
            }
        });
        form.add(upload);
        
        JButton submit = new JButton("Kirim Pendaftaran"); 
        submit.addActionListener(e -> submit());
        form.add(submit);
        
        add(new JScrollPane(form), BorderLayout.CENTER);
    }

    void submit(){
        try {
            String nim = new String(Files.readAllBytes(Paths.get("current_nim.txt"))).trim();
            Application a = new Application();
            a.nim = nim;
            a.scholarshipName = "Beasiswa Contoh";
            a.fullName = fullName.getText().trim();
            a.ttl = ttl.getText().trim();
            a.nik = nik.getText().trim();
            a.address = address.getText().trim();
            a.photoPath = photoPath;
            a.status = "Pending";
            
            List<Application> apps = store.loadApplications();
            apps.add(a);
            store.saveApplications(apps);
            win.show("thanks");
        } catch(Exception e){ 
            JOptionPane.showMessageDialog(this, "Gagal submit: " + e.getMessage()); 
        }
    }
}