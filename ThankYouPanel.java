import java.awt.*;
import javax.swing.*;

public class ThankYouPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    
    public ThankYouPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        
        JPanel c = new JPanel(); 
        c.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        
        JLabel message = new JLabel("Terima kasih telah mendaftar ke [beasiswa], mohon tunggu pengumumannya");
        message.setFont(new Font("Arial", Font.PLAIN, 14));
        c.add(message);
        c.add(Box.createRigidArea(new Dimension(0,20)));
        
        JButton home = new JButton("Kembali ke Beranda"); 
        home.setAlignmentX(Component.CENTER_ALIGNMENT);
        home.addActionListener(e -> win.show("home"));
        c.add(home);
        
        add(c, BorderLayout.CENTER);
    }
}