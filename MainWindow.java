import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    CardLayout cardLayout = new CardLayout();
    JPanel cards = new JPanel(cardLayout);
    DataStore store;

    private ScholarshipDetailPanel detailPanel;
    private ApplicationFormPanel applyPanel;
    private StatusOverviewPanel statusPanel;

    public MainWindow() {
        setTitle("Sistem Beasiswa Universitas Riau");
        setSize(1100,700);
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
        AnnouncementPanel announcement = new AnnouncementPanel(this, store);
        
        // Admin panels
        AdminHomePanel adminHome = new AdminHomePanel(this, store);
        ManageScholarshipsPanel manageSchol = new ManageScholarshipsPanel(this, store);
        ManageApplicantsPanel manageApps = new ManageApplicantsPanel(this, store);
        ManageAnnouncementsPanel manageAnnounce = new ManageAnnouncementsPanel(this, store);
        ReportsPanel reports = new ReportsPanel(this, store);

        detailPanel = detail;
        applyPanel = apply;
        statusPanel = status;

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
        cards.add(announcement, "announcement");
        
        // Admin panels
        cards.add(adminHome, "adminHome");
        cards.add(manageSchol, "manageScholarships");
        cards.add(manageApps, "manageApplicants");
        cards.add(manageAnnounce, "manageAnnouncements");
        cards.add(reports, "reports");
        
        add(cards);
        show("login");
    }

    public ScholarshipDetailPanel getDetailPanel() {
        return detailPanel;
    }
    
    public ApplicationFormPanel getApplyPanel() {
        return applyPanel;
    }

    public void show(String name) {
        if (name.equals("status") && statusPanel != null) {
            statusPanel.refresh();
        }
        
        cardLayout.show(cards, name);
    }
    
    public JPanel getPanel(String name) {
        for(Component comp : cards.getComponents()){
            // Check by class name
            if(comp instanceof LoginPanel && name.equals("login")) return (JPanel)comp;
            if(comp instanceof SignupPanel && name.equals("signup")) return (JPanel)comp;
            if(comp instanceof LoadingPanel && name.equals("loading")) return (JPanel)comp;
            if(comp instanceof HomePanel && name.equals("home")) return (JPanel)comp;
            if(comp instanceof HelpPanel && name.equals("help")) return (JPanel)comp;
            if(comp instanceof ScholarshipListPanel && name.equals("list")) return (JPanel)comp;
            if(comp instanceof ScholarshipDetailPanel && name.equals("detail")) return (JPanel)comp;
            if(comp instanceof ApplicationFormPanel && name.equals("apply")) return (JPanel)comp;
            if(comp instanceof ThankYouPanel && name.equals("thanks")) return (JPanel)comp;
            if(comp instanceof StatusOverviewPanel && name.equals("status")) return (JPanel)comp;
            if(comp instanceof StatusDetailPanel && name.equals("statusDetail")) return (JPanel)comp;
            if(comp instanceof CongratulationsPanel && name.equals("congrats")) return (JPanel)comp;
            if(comp instanceof AnnouncementPanel && name.equals("announcement")) return (JPanel)comp;
            if(comp instanceof AdminHomePanel && name.equals("adminHome")) return (JPanel)comp;
            if(comp instanceof ManageScholarshipsPanel && name.equals("manageScholarships")) return (JPanel)comp;
            if(comp instanceof ManageApplicantsPanel && name.equals("manageApplicants")) return (JPanel)comp;
            if(comp instanceof ManageAnnouncementsPanel && name.equals("manageAnnouncements")) return (JPanel)comp;
            if(comp instanceof ReportsPanel && name.equals("reports")) return (JPanel)comp;
        }
        return null;
    }
}