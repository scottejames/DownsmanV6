package com.scottejames.downsman.ui;

import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.model.SupportModel;
import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.ArrayList;
import java.util.List;

public class TeamDialog extends Dialog {
    private TeamService         service             = ServiceManager.getInstance().getTeamService();
    private TeamModel           model               = null;
    private Binder<TeamModel>   binder              = new Binder<>(TeamModel.class);
    private FormLayout          teamDetailsForm     = new FormLayout();
    private Grid<SupportModel>  supportMembersGrid  = new Grid<>();
    private Button              addNewSupportButton = null;
    private Button              editSupportButton   = null;
    private Button              deleteSupportButton = null;
    private SupportModel        selectedSupport     = null;
    private SupportDialog       supportDialog       = null;

    private Grid<ScoutModel>    teamMembersGrid     = new Grid<>();
    private Button              addNewScoutButton   = null;
    private Button              editScoutButton     = null;
    private Button              deleteScoutButton   = null;
    private ScoutModel          selectedScout       = null;
    private ScoutDialog         scoutDialog         = null;

    private Button              saveFormButton;
    private Button              cancelFormButton;

    private Runnable            onSave;

    public TeamDialog(TeamModel team, Runnable onSave){
        if (team == null)
            model = new TeamModel();
        else
            model = team;
        this.onSave = onSave;

        setSizeUndefined();


        setupTeamDetails();
        setupScoutGrid();
        setupTeamPhone();
        setupSupportGrid();
        setupEmergencyContact();

        saveFormButton = new Button("Save");
        saveFormButton.addClickListener(e -> this.saveForm());

        cancelFormButton = new Button("Cancel");
        cancelFormButton.addClickListener(e -> this.cancelForm());
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(saveFormButton, cancelFormButton);
        add(horizontalLayout);

        binder.readBean(model);

    }

    private void setupTeamPhone(){
        add(new Label("Team Phones"));

        FormLayout teamPhoneForm = new FormLayout();
        addTextField(teamPhoneForm,"Active Phone","activeMobile");
        addTextField(teamPhoneForm,"Backup Phone","backupMobile");
        add(teamPhoneForm);
    }
    private void setupTeamDetails(){
        add(new Label("Team Details"));

        addTextField(teamDetailsForm,"Team Name", "teamName");
        addTextField(teamDetailsForm,"Section", "section");
        addTextField(teamDetailsForm,"District", "district");
        addTextField(teamDetailsForm,"County", "county");
        addTextField(teamDetailsForm,"Start Time","prefStart");
        addTextField(teamDetailsForm,"Hike Class", "hikeClass");
        add(teamDetailsForm);

    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    private void setupScoutGrid(){
        add(new Label("Hiking Team"));

        teamMembersGrid.addColumn(ScoutModel::getFullName).setHeader("Full Name");
        teamMembersGrid.addColumn(ScoutModel::getDob).setHeader("DOB");
        teamMembersGrid.addColumn(ScoutModel::getGender).setHeader("Gender");


        add(teamMembersGrid);
        HorizontalLayout scoutButtonsLayout = new HorizontalLayout();

        addNewScoutButton = new Button("Add");
        addNewScoutButton.addClickListener(e -> this.addNewScout());

        editScoutButton = new Button("Edit");
        editScoutButton.addClickListener(e -> this.editScout());
        editScoutButton.setEnabled(false);

        deleteScoutButton = new Button("Delete");
        deleteScoutButton.addClickListener(e -> this.deleteScout());
        deleteScoutButton.setEnabled(false);

        scoutButtonsLayout.add(deleteScoutButton,editScoutButton,addNewScoutButton);
        add(scoutButtonsLayout);

        teamMembersGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null){
                editScoutButton.setEnabled(true);
                deleteScoutButton.setEnabled(true);
            } else {
                editScoutButton.setEnabled(false);
                deleteScoutButton.setEnabled(false);
            }

            selectedScout = event.getValue();
        });
        updateScoutGrid();
    }

    public void updateScoutGrid(){
        if ((model.getScoutsTeam() != null)){
            teamMembersGrid.setItems(model.getScoutsTeam());

        }
        teamMembersGrid.setHeightByRows(true);
    }

    private void addNewScout(){
        scoutDialog = new ScoutDialog(null,this::onTeamChanged);
        scoutDialog.open();

    }
    private void editScout(){
        scoutDialog = new ScoutDialog(selectedScout,this::onTeamChanged);
        scoutDialog.open();
    }

    private void onTeamChanged(){
        ScoutModel scout = scoutDialog.getScout();
        model.addScoutMember(scout);
        updateScoutGrid();

    }

    private void deleteScout(){
        if (selectedScout!=null) {
            model.removeScoutMember(selectedScout);
        }
        updateScoutGrid();

    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    private void setupSupportGrid() {
        add(new Label("Support Team"));

        supportMembersGrid.addColumn(SupportModel::getFullName).setHeader("Full Name");
        supportMembersGrid.addColumn(SupportModel::getPhoneNumber).setHeader("Phone");
        supportMembersGrid.addColumn(SupportModel::getFrom).setHeader("From");
        supportMembersGrid.addColumn(SupportModel::getTo).setHeader("To");
        add(supportMembersGrid);

        HorizontalLayout supportButtonsLayout = new HorizontalLayout();

        addNewSupportButton = new Button("Add");
        addNewSupportButton.addClickListener(e -> this.addNewSupport());

        editSupportButton = new Button("Edit");
        editSupportButton.addClickListener(e -> this.editSupport());
        editSupportButton.setEnabled(false);
        deleteSupportButton = new Button("Delete");
        deleteSupportButton.addClickListener(e -> this.deleteSupport());
        deleteSupportButton.setEnabled(false);

        supportButtonsLayout.add(deleteSupportButton,editSupportButton,addNewSupportButton);
        add(supportButtonsLayout);

        supportMembersGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null){
                editSupportButton.setEnabled(true);
                deleteSupportButton.setEnabled(true);
            } else {
                editSupportButton.setEnabled(false);
                deleteSupportButton.setEnabled(false);
            }

            selectedSupport = event.getValue();
        });
        updateSupportGrid();
    }


    public void updateSupportGrid(){

        if ((model.getSupportTeam() != null))
            supportMembersGrid.setItems(model.getSupportTeam());
        supportMembersGrid.setHeightByRows(true);


    }
    private void addNewSupport() {
        supportDialog = new SupportDialog(null,this::onSupportTeamChanged);
        supportDialog.open();
    }
    private void editSupport() {
        supportDialog = new SupportDialog(selectedSupport,this::onSupportTeamChanged);
        supportDialog.open();
    }
    private void deleteSupport() {
        if (selectedSupport!=null) {
            model.removeSupportMember(selectedSupport);
        }
        updateSupportGrid();
    }
    private void onSupportTeamChanged(){
        SupportModel support = supportDialog.getSupport();
        model.addSupportMember(support);
        updateSupportGrid();

    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    private void setupEmergencyContact(){
        FormLayout          emergencyContactForm = new FormLayout();
        add(new Label("Emergency Contact"));
        addTextField(emergencyContactForm,"Name", "emergencyContactName" );
        addTextField(emergencyContactForm,"Email", "emergencyContactEmail" );
        addTextField(emergencyContactForm,"Mobile", "emergencyContactMobile");
        addTextField(emergencyContactForm,"LandLine","emergencyContactLandline" );

        add(emergencyContactForm);
    }
    private void addTextField(FormLayout form, String label, String bindValue){

        TextField field = new TextField();
        form.addFormItem(field,label);
        if (bindValue != null)
            binder.bind(field,bindValue);


    }

    private void saveForm(){
        try {
            binder.writeBean(model);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        System.out.println("Save Form");
        if (model.isPersisted()==true)
            service.update(model);
        else
            service.add(model);
        onSave.run();
        close();

    }
    private void cancelForm(){
       close();


    }

}
