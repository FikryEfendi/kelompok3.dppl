import java.awt.*;
import javax.swing.*;

public class CongratulationsPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    
    public CongratulationsPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        
        JPanel center = new JPanel();
        center.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        
        JTextArea ta = new JTextArea("Selamat! Anda dinyatakan lulus seleksi penerima Beasiswa.\n\nSilakan konfirmasi penerimaan beasiswa ini.");
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(new Font("Arial", Font.PLAIN, 14));
        ta.setBackground(center.getBackground());
        
        center.add(ta);
        center.add(Box.createRigidArea(new Dimension(0,20)));
        
        JButton confirm = new JButton("Konfirmasi Penerimaan");
        confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirm.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Terima kasih! Konfirmasi Anda telah diterima.");
            win.show("home");
        });
        center.add(confirm);
        
        add(center, BorderLayout.CENTER);
    }
}