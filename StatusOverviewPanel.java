import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.nio.file.*;
import java.util.List;

public class StatusOverviewPanel extends JPanel {
    MainWindow win; 
    DataStore store;
    
    public StatusOverviewPanel(MainWindow win, DataStore store){
        this.win=win; 
        this.store=store;
        setLayout(new BorderLayout());
        
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
        JButton back = new JButton("Kembali"); 
        back.addActionListener(e -> win.show("home")); 
        top.add(back);
        add(top, BorderLayout.NORTH);

        JTextArea ta = new JTextArea();
        ta.setEditable(false);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        List<Application> apps = store.loadApplications();
        StringBuilder sb = new StringBuilder();
        sb.append("Status Pendaftaran Beasiswa\n");
        sb.append("===================================\n\n");
        
        if(apps.isEmpty()){
            sb.append("Belum ada pendaftaran.\n");
        } else {
            for(Application a: apps){
                sb.append("NIM: ").append(a.nim).append("\n");
                sb.append("Beasiswa: ").append(a.scholarshipName).append("\n");
                sb.append("Status: ").append(a.status).append("\n");
                sb.append("-----------------------------------\n");
            }
        }
        
        ta.setText(sb.toString());
        add(new JScrollPane(ta), BorderLayout.CENTER);
    }
}