import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    CardLayout cardLayout = new CardLayout();
    JPanel cards = new JPanel(cardLayout);
    DataStore store;

    public MainWindow() {
        setTitle("Sistem Beasiswa");
        setSize(900,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        store = new DataStore();

        // create panels
        LoginPanel login = new LoginPanel(this, store);
        SignupPanel signup = new SignupPanel(this, store);
        LoadingPanel loading = new LoadingPanel(this);
        HomePanel home = new HomePanel(this, store);
        HelpPanel help = new HelpPanel(this, store);
        ScholarshipListPanel list = new ScholarshipListPanel(this, store);
        ScholarshipDetailPanel detail = new ScholarshipDetailPanel(this, store);
        ApplicationFormPanel apply = new ApplicationFormPanel(this, store);
        ThankYouPanel thanks = new ThankYouPanel(this, store);
        StatusOverviewPanel status = new StatusOverviewPanel(this, store);
        StatusDetailPanel statusDetail = new StatusDetailPanel(this, store);
        CongratulationsPanel congrats = new CongratulationsPanel(this, store);

        // add to cards
        cards.add(login, "login");
        cards.add(signup, "signup");
        cards.add(loading, "loading");
        cards.add(home, "home");
        cards.add(help, "help");
        cards.add(list, "list");
        cards.add(detail, "detail");
        cards.add(apply, "apply");
        cards.add(thanks, "thanks");
        cards.add(status, "status");
        cards.add(statusDetail, "statusDetail");
        cards.add(congrats, "congrats");

        add(cards);
        // start at login
        show("login");
    }

    public void show(String name) {
        cardLayout.show(cards, name);
    }
}