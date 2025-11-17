import java.awt.*;
import javax.swing.*;

public class StatusDetailPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    
    public StatusDetailPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Kembali");
        back.addActionListener(e -> win.show("status"));
        top.add(back);
        add(top, BorderLayout.NORTH);
        
        JLabel label = new JLabel("Status detail (placeholder)", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        add(label, BorderLayout.CENTER);
    }
}