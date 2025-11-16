import java.awt.*;
import javax.swing.*;

public class HelpPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    JTextField subject; 
    JTextArea body;
    
    public HelpPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Kembali"); 
        back.addActionListener(e -> win.show("home")); 
        top.add(back);
        add(top, BorderLayout.NORTH);

        JPanel form = new JPanel(); 
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS)); 
        form.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        JLabel title = new JLabel("Formulir Bantuan / Keluhan");
        title.setFont(new Font("Arial", Font.BOLD, 16));
        form.add(title);
        form.add(Box.createRigidArea(new Dimension(0,10)));
        
        form.add(new JLabel("Subject keluhan")); 
        subject = new JTextField(); 
        subject.setMaximumSize(new Dimension(600,28)); 
        form.add(subject);
        form.add(Box.createRigidArea(new Dimension(0,10)));
        
        form.add(new JLabel("Keluhan")); 
        body = new JTextArea(8,40); 
        body.setLineWrap(true);
        body.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(body);
        scrollPane.setMaximumSize(new Dimension(600, 200));
        form.add(scrollPane);
        form.add(Box.createRigidArea(new Dimension(0,10)));
        
        JButton send = new JButton("Kirim"); 
        send.setAlignmentX(Component.LEFT_ALIGNMENT);
        send.addActionListener(e -> {
            if(subject.getText().trim().isEmpty() || body.getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(this, "Mohon isi semua field.");
                return;
            }
            JOptionPane.showMessageDialog(this, "Keluhan terkirim. Terima kasih.");
            subject.setText("");
            body.setText("");
            win.show("home");
        });
        form.add(send);
        
        add(form, BorderLayout.CENTER);
    }
}