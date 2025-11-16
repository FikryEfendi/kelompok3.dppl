import java.awt.*;
import javax.swing.*;

public class ScholarshipListPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    
    public ScholarshipListPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton back = new JButton("Kembali"); 
        back.addActionListener(e -> win.show("home")); 
        top.add(back);
        add(top, BorderLayout.NORTH);

        JPanel list = new JPanel(); 
        list.setLayout(new GridLayout(3,1,10,10)); 
        list.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        
        for(int i=1;i<=3;i++){
            JPanel item = new JPanel(new BorderLayout());
            item.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            JLabel title = new JLabel("NAMA BEASISWA " + i);
            title.setFont(new Font("Arial", Font.BOLD, 14));
            
            JTextArea desc = new JTextArea("Beasiswa ini merupakan program bantuan pendidikan bagi pelajar atau mahasiswa...");
            desc.setEditable(false);
            desc.setLineWrap(true);
            desc.setWrapStyleWord(true);
            
            JPanel right = new JPanel(); 
            right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
            JButton detail = new JButton("Detail"); 
            detail.addActionListener(e -> win.show("detail"));
            right.add(detail);
            
            item.add(title, BorderLayout.NORTH);
            item.add(new JScrollPane(desc), BorderLayout.CENTER);
            item.add(right, BorderLayout.EAST);
            list.add(item);
        }
        add(new JScrollPane(list), BorderLayout.CENTER);
    }
}