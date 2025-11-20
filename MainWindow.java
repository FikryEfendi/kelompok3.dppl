import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    CardLayout cardLayout = new CardLayout();
    JPanel cards = new JPanel(cardLayout);
    DataStore store;

    private ScholarshipDetailPanel detailPanel;
    private ApplicationFormPanel applyPanel;

    public MainWindow() {
        setTitle("Sistem Beasiswa Universitas Riau");
        setSize(1000,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        store = new DataStore();

        // Create all panels
        LoginPanel login = new LoginPanel(this, store);
        SignupPanel signup = new SignupPanel(this, store);
        LoadingPanel loading = new LoadingPanel(this);
        
        // Student panels
        HomePanel home = new HomePanel(this, store);
        HelpPanel help = new HelpPanel(this, store);
        ScholarshipListPanel list = new ScholarshipListPanel(this, store);
        ScholarshipDetailPanel detail = new ScholarshipDetailPanel(this, store);
        ApplicationFormPanel apply = new ApplicationFormPanel(this, store);
        ThankYouPanel thanks = new ThankYouPanel(this, store);
        StatusOverviewPanel status = new StatusOverviewPanel(this, store);
        StatusDetailPanel statusDetail = new StatusDetailPanel(this, store);
        CongratulationsPanel congrats = new CongratulationsPanel(this, store);
        
        // Admin panels
        AdminHomePanel adminHome = new AdminHomePanel(this, store);
        ManageScholarshipsPanel manageSchol = new ManageScholarshipsPanel(this, store);
        ManageApplicantsPanel manageApps = new ManageApplicantsPanel(this, store);
        ManageAnnouncementsPanel manageAnnounce = new ManageAnnouncementsPanel(this, store);
        ReportsPanel reports = new ReportsPanel(this, store);

        detailPanel = new ScholarshipDetailPanel(this, store);
        applyPanel = new ApplicationFormPanel(this, store);

        // Add all panels to cards
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
        
        // Admin panels
        cards.add(adminHome, "adminHome");
        cards.add(manageSchol, "manageScholarships");
        cards.add(manageApps, "manageApplicants");
        cards.add(manageAnnounce, "manageAnnouncements");
        cards.add(reports, "reports");

        cards.add(detailPanel, "detail");
        cards.add(applyPanel, "apply");
        
        add(cards);
        // Start at login
        show("login");
    }

    public ScholarshipDetailPanel getDetailPanel() {
        return detailPanel;
    }
    
    public ApplicationFormPanel getApplyPanel() {
        return applyPanel;
    }

    public void show(String name) {
        cardLayout.show(cards, name);
    }
    
    public JPanel getPanel(String name) {
        for(Component comp : cards.getComponents()){
            if(comp.getName() != null && comp.getName().equals(name)){
                return (JPanel) comp;
            }
        }
        return null;
    }
}