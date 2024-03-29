package com.scottejames.downsman.ui;

import com.scottejames.downsman.model.SessionState;
import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.LogService;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.ui.admin.AdminDialog;
import com.scottejames.downsman.utils.Config;
import com.scottejames.downsman.utils.LogUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@HtmlImport("styles/shared-styles.html")
@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends VerticalLayout implements HasDynamicTitle {

    private final TeamService service = ServiceManager.getInstance().getTeamService();
    private TeamModel selectedTeam = null;

    private LoginDialog loginDialog = null;
    private TeamDialog teamDialog = null;
    private RegisterUserDialog registerUserDialog = null;

    private final Grid<TeamModel> teamGrid = new Grid<>();
    private final HorizontalLayout gridButtons = new HorizontalLayout();

    private Button deleteTeam = null;
    private Button addTeam = null;
    private Button editTeam = null;
    private boolean locked = false;
    public MainView(){
        UserModel user = SessionState.getInstance().getCurrentUser();
        locked = Config.getInstance().isLocked();
        LogService.logEvent("Starting MainView and lock status is " + locked);

        this.loginDialog = new LoginDialog(this::onLogin);
        // Add table of teams
        teamGrid.addColumn(TeamModel::getTeamName).setHeader("TeamName");
        teamGrid.addColumn(TeamModel::getHikeClass).setHeader("HikeClass");
        teamGrid.addColumn(TeamModel::isTeamSubmitted).setHeader("Submitted");
        teamGrid.addColumn(TeamModel::getPaymentAmount).setHeader("AmountPaid");
        teamGrid.addColumn(TeamModel::isPaymentRecieved).setHeader("Payment Recieved");
        teamGrid.addColumn(TeamModel::isPaidInFull).setHeader("Paid In Full");
        teamGrid.setHeightByRows(true);

        deleteTeam = new Button("Delete Team", this::deleteTeam);

        if (locked == false)
            editTeam = new Button("Edit Team", e->editTeam());
        else
            editTeam = new Button("View Team", e->editTeam());
        addTeam = new Button("Add Team",e->addTeam());
        if (locked) addTeam.setEnabled(false);

        gridButtons.add(deleteTeam,editTeam,addTeam);

        teamGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null){
                editTeam.setEnabled(true);
                deleteTeam.setEnabled(true);
            } else {
                editTeam.setEnabled(false);
                deleteTeam.setEnabled(false);
            }
            if (locked) deleteTeam.setEnabled(false);

            selectedTeam = event.getValue();
        });

        buildUI();
    }

    private void buildUI(){
        teamGrid.getDataProvider().refreshAll();
        removeAll();

        HorizontalLayout userStrip = new HorizontalLayout();
        UserModel user = SessionState.getInstance().getCurrentUser();

        if (Config.getInstance().isDev() == true){
            userStrip.add(new Label("This is DEVELOPMENT"));
        }
        if (SessionState.getInstance().isAuthenticated()){
            // if we have a valid logged in user and they have break lock then let them make changes.
            if ((user != null)  && (user.getBreakLock() == true)) locked = false;
            if (locked) {
                userStrip.add(new Label("Entries have been CLOSED, and all teams are locked.  If you have to make changes please mail web@downsman.com"));
                editTeam.setText("View Team");
            } else {
                editTeam.setText("Edit Team");
            }
            userStrip.add(new Label("Logged in : " + user.getUsername()));
            userStrip.add(new Button("Logout", e -> logout()));
            
            if(user.isAdmin())
                userStrip.add( new Button("Admin", e-> admin()));
            updateTeamGrid();

        } else {

            userStrip.add(new Button("Login", e -> login()));
            userStrip.add(new Button ( "Register", e -> register()));
        }

        add(userStrip);
        if (SessionState.getInstance().isAuthenticated()) {

            add(teamGrid);
            add(gridButtons);
        }
        setClassName("main-layout");



    }



    private void admin() {
        AdminDialog adminDialog = new AdminDialog();
        adminDialog.open();
    }

    private void updateTeamGrid(){
        teamGrid.setItems(service.getAll());
        deleteTeam.setEnabled(false);
        editTeam.setEnabled(false);

    }

    private void register() {
        registerUserDialog = new RegisterUserDialog(this::onRegister);
        registerUserDialog.open();
    }

    private void addTeam() {
        teamDialog = new TeamDialog(null,this::onTeamSave);
        teamDialog.open();
    }

    private void editTeam() {
        teamDialog = new TeamDialog(selectedTeam,this::onTeamSave);
        teamDialog.open();
    }

    private void deleteTeam(ClickEvent<Button> e) {
        LogService.logEvent("Removed team " + selectedTeam.getTeamName());
        if (selectedTeam!=null)
            service.remove(selectedTeam);
        buildUI();
        
    }

    private void login(){
        LogUtil.logDebug("MainView : login()");
        loginDialog.open();
    }
    private void logout(){
        SessionState.getInstance().setCurrentUser(null);
        buildUI();
    }

    private void onLogin(){
        buildUI();
    }
    private void onTeamSave(){
        buildUI();
    }
    private void onRegister() { buildUI(); }

    @Override
    public String getPageTitle() {

        if (Config.getInstance().getConfigString("dev").equals("true"))
            return  "DEV Downsman";
        else
            return "Downsman";

    }
}
