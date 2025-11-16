import javax.swing.*;
import java.awt.*;

public class ScholarshipDetailPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    
    public ScholarshipDetailPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Kembali"); 
        back.addActionListener(e -> win.show("list")); 
        top.add(back);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout()); 
        center.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        JLabel title = new JLabel("[NAMA BEASISWA]");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        
        JTextArea desc = new JTextArea("Detail beasiswa...\n\nSpesifikasi/dokumen yang diperlukan untuk beasiswa ini:\n- Dokumen 1\n- Dokumen 2\n- Dokumen 3");
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        
        center.add(title, BorderLayout.NORTH);
        center.add(new JScrollPane(desc), BorderLayout.CENTER);
        
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton daftar = new JButton("Daftar"); 
        daftar.addActionListener(e -> win.show("apply"));
        bottom.add(daftar);
        center.add(bottom, BorderLayout.SOUTH);
        
        add(center, BorderLayout.CENTER);
    }
}