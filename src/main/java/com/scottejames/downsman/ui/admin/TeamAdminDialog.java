package com.scottejames.downsman.ui.admin;

import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.ui.TeamDialog;
import com.scottejames.downsman.utils.LogUtil;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class TeamAdminDialog extends Dialog {
    private FormLayout form = new FormLayout();
    private final TeamService service = ServiceManager.getInstance().getTeamService();
    private TeamDialog teamDialog = null;

    private final Grid<TeamModel> teamGrid = new Grid<>();
    private Button deleteTeam = null;
    private Button editTeam = null;
    private Button togglePaid = null;
    private Button toggleSubmitted = null;

    private final HorizontalLayout gridButtonsLayout = new HorizontalLayout();
    private TeamModel selectedTeam = null;

    TeamAdminDialog(){
        // Add table of teams
        teamGrid.addColumn(TeamModel::getLeaderName).setHeader("Leader");
        teamGrid.addColumn(TeamModel::getTeamName).setHeader("TeamName");
        teamGrid.addColumn(TeamModel::getHikeClass).setHeader("HikeClass");
        teamGrid.addColumn(TeamModel::isPaymentRecieved).setHeader("Paid");
        teamGrid.addColumn(TeamModel::isPaymentSubmitted).setHeader("Pay Submitted");
        teamGrid.addColumn(TeamModel::isTeamSubmitted).setHeader("Entered");
        teamGrid.setHeightByRows(true);

        deleteTeam = new Button("Delete Team", this::deleteTeam);
        editTeam = new Button("Edit Team", e->editTeam());
        togglePaid = new Button("Toggle Paid", e->togglePaid());
        toggleSubmitted = new Button("Toggle Submitted", e->toggleSubmitted());
        gridButtonsLayout.add(deleteTeam,editTeam,togglePaid,toggleSubmitted);

        teamGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null){
                gridButtonsLayout.setEnabled(true);

            } else {
                gridButtonsLayout.setEnabled(false);

            }

            selectedTeam = event.getValue();

        });
        setSizeUndefined();


        updateTeamGrid();
    }

    private void toggleSubmitted() {
        if (selectedTeam.isTeamSubmitted()){
            selectedTeam.setTeamSubmitted(false);
        } else{
            selectedTeam.setTeamSubmitted(true);
        }
        updateTeamGrid();
    }

    private void togglePaid() {
        if (selectedTeam.isPaymentRecieved()){
            selectedTeam.setPaymentRecieved(false);
        } else{
            selectedTeam.setPaymentRecieved(true);
        }
        updateTeamGrid();
    }

    private void updateTeamGrid(){
        removeAll();
        teamGrid.setItems(service.getAllAll());
        gridButtonsLayout.setEnabled(false);
        form.add(teamGrid);
        form.add(gridButtonsLayout);
        add(form);
    }
    private void editTeam() {
        LogUtil.logDebug("MainView : editTeam()");

        teamDialog = new TeamDialog(selectedTeam,this::onTeamSave);
        teamDialog.open();
    }

    private void deleteTeam(ClickEvent<Button> e) {
        LogUtil.logDebug("MainView : deleteTeam()");

        if (selectedTeam!=null)
            service.remove(selectedTeam);
        updateTeamGrid();
    }
    public void onTeamSave(){
        updateTeamGrid();
    }
}
