import java.awt.*;
import java.nio.file.*;
import javax.swing.*;

public class HomePanel extends JPanel {
    MainWindow win; 
    DataStore store;
    JLabel welcomeLabel;

    public HomePanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        
        // top nav
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT,20,10));
        String[] items = {"BERANDA","DAFTAR BEASISWA","PENGUMUMAN","HELP"};
        for(String s: items){
            JButton b = new JButton(s);
            b.addActionListener(e -> nav(s));
            top.add(b);
        }
        add(top, BorderLayout.NORTH);

        // center content
        JPanel center = new JPanel(new BorderLayout()); 
        center.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        welcomeLabel = new JLabel("Selamat datang", SwingConstants.LEFT);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        center.add(welcomeLabel, BorderLayout.NORTH);

        // simple recommendations area
        JTextArea ta = new JTextArea("Informasi Beasiswa\n\nRekomendasi Beasiswa:\n- Beasiswa A\n- Beasiswa B\n- Beasiswa C");
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        center.add(new JScrollPane(ta), BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
        
        // read current nim
        try {
            String nim = new String(Files.readAllBytes(Paths.get("current_nim.txt"))).trim();
            welcomeLabel.setText("Selamat datang, NIM: " + nim);
        } catch(Exception e){
            // file not found, keep default text
        }
    }

    void nav(String s){
        if(s.equals("HELP")) win.show("help");
        else if(s.equals("BERANDA")) win.show("home");
        else if(s.equals("DAFTAR BEASISWA")) win.show("list");
        else if(s.equals("PENGUMUMAN")) JOptionPane.showMessageDialog(this, "Belum ada pengumuman saat ini.");
    }
}