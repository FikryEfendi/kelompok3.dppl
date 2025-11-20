import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoadingPanel extends JPanel {
    MainWindow win;
    
    public LoadingPanel(MainWindow win){
        this.win=win;
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        
        JLabel icon = new JLabel("🎓");
        icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 64));
        icon.setForeground(new Color(16, 185, 129));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel title = new JLabel("Memuat Sistem...");
        title.setFont(new Font("Segoe UI Symbol", Font.BOLD, 24));
        title.setForeground(new Color(31, 41, 55));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitle = new JLabel("Harap tunggu sementara kami mengautentikasi informasi Anda");
        subtitle.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 14));
        subtitle.setForeground(new Color(107, 114, 128));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setPreferredSize(new Dimension(300, 10));
        progressBar.setIndeterminate(true); // Indeterminate untuk simulasi loading
        progressBar.setBackground(new Color(240, 253, 244));
        progressBar.setForeground(new Color(16, 185, 129));
        progressBar.setBorderPainted(false);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        content.add(icon);
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(title);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(subtitle);
        content.add(Box.createRigidArea(new Dimension(0, 25)));
        content.add(progressBar);
        
        add(content);
    }
}