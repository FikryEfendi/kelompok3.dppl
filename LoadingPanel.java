import javax.swing.*;
import java.awt.*;

public class LoadingPanel extends JPanel {
    MainWindow win;
    
    public LoadingPanel(MainWindow win){
        this.win=win;
        setLayout(new BorderLayout());
        JLabel l = new JLabel("Harap tunggu sementara kami mengautentikasi informasi Anda", SwingConstants.CENTER);
        l.setFont(new Font("Arial", Font.PLAIN, 14));
        add(l, BorderLayout.CENTER);
    }
}