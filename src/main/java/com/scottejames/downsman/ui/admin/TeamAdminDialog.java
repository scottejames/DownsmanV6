package com.scottejames.downsman.ui.admin;

import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.services.LogService;
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
        this.setWidthFull();
        this.setHeightFull();

        // Add table of teams
        teamGrid.addColumn(TeamModel::getLeaderName).setHeader("Leader").setAutoWidth(true);
        teamGrid.addColumn(TeamModel::getTeamName).setHeader("TeamName").setAutoWidth(true);
        teamGrid.addColumn(TeamModel::getHikeClass).setHeader("HikeClass").setAutoWidth(true);
        teamGrid.addColumn(TeamModel::getPaymentStatus).setHeader("Paid").setAutoWidth(true);
        teamGrid.addColumn(TeamModel::getPaymentAmount).setHeader("Payment Amount").setAutoWidth(true);
        teamGrid.addColumn(TeamModel::getEntranceFee).setHeader("Entrance Fee").setAutoWidth(true);
        teamGrid.addColumn(TeamModel::isTeamSubmitted).setHeader("Entered").setAutoWidth(true);

        this.setSizeFull();



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
        this.setWidthFull();
        this.setHeightFull();

        updateTeamGrid();
    }

    private void toggleSubmitted() {
        LogService.logEvent("Admin submitted team " + selectedTeam.getTeamName());

        if (selectedTeam.isTeamSubmitted()){
            selectedTeam.setTeamSubmitted(false);
        } else{
            selectedTeam.setTeamSubmitted(true);
        }
        service.add(selectedTeam);
        updateTeamGrid();
    }

    private void togglePaid() {
        LogService.logEvent("Admin marked team paid " + selectedTeam.getTeamName());

        if (selectedTeam.isPaymentRecieved()){
            selectedTeam.setPaymentRecieved(false);
        } else{
            selectedTeam.setPaymentRecieved(true);
        }
        service.add(selectedTeam);
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
        LogService.logEvent("Admin edit team " + selectedTeam.getTeamName());

        teamDialog = new TeamDialog(selectedTeam,this::onTeamSave);
        teamDialog.open();
    }

    private void deleteTeam(ClickEvent<Button> e) {
        LogService.logEvent("Admin delete team " + selectedTeam.getTeamName());

        if (selectedTeam!=null)
            service.remove(selectedTeam);
        updateTeamGrid();
    }
    public void onTeamSave(){
        updateTeamGrid();
    }
}
