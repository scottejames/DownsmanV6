package com.scottejames.downsman.ui;

import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@HtmlImport("styles/shared-styles.html")
@Route("")
public class MainView extends VerticalLayout {

    private TeamService service = ServiceManager.getInstance().getTeamService();
    TeamModel selectedTeam = null;

    LoginDialog loginDialog = null;
    TeamDialog teamDialog = null;

    Grid<TeamModel> teamGrid = new Grid<>();
    HorizontalLayout gridButtons = new HorizontalLayout();

    Button deleteTeam = null;
    Button addTeam = null;
    Button editTeam = null;

    public MainView(){
        loginDialog = new LoginDialog(this::onLogin);
        // Add table of teams
        teamGrid.addColumn(TeamModel::getTeamName).setHeader("TeamName");
        teamGrid.addColumn(TeamModel::getHikeClass).setHeader("HikeClass");
        teamGrid.addColumn(TeamModel::getStatus).setHeader("Status");
        teamGrid.setHeightByRows(true);

        deleteTeam = new Button("Delete Team",e-> deleteTeam(e));
        editTeam = new Button("Edit Team", e->editTeam());
        addTeam = new Button("Add Team",e->addTeam());
        gridButtons.add(deleteTeam,editTeam,addTeam);

        teamGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null){
                editTeam.setEnabled(true);
                deleteTeam.setEnabled(true);
            } else {
                editTeam.setEnabled(false);
                deleteTeam.setEnabled(false);
            }

            selectedTeam = event.getValue();
        });

        buildUI();
    }

    public void buildUI(){

        removeAll();

        HorizontalLayout userStrip = new HorizontalLayout();

        if (LoginDialog.isAuthenticated()){
            UserModel user = loginDialog.getLoggedInUser();

            userStrip.add(new Label("Logged in : " + user.getUsername()));
            userStrip.add(new Button("Logout", e -> logout()));

            updateTeamGrid();

        } else {
            userStrip.add(new Button("Login", e -> login()));
        }

        add(userStrip);
        if (LoginDialog.isAuthenticated()) {

            add(teamGrid);
            add(gridButtons);
        }
        setClassName("main-layout");



    }

    public void updateTeamGrid(){
        teamGrid.setItems(service.getAll());
        deleteTeam.setEnabled(false);
        editTeam.setEnabled(false);

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
        if (selectedTeam!=null)
            service.remove(selectedTeam);
        buildUI();
        
    }

    public void login(){
        loginDialog.open();
    }
    public void logout(){
        loginDialog.logout();
        buildUI();
    }

    public void onLogin(){
        buildUI();
    }
    public void onTeamSave(){
        buildUI();
    }
}
