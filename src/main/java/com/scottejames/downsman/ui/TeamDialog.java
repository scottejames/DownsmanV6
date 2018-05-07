package com.scottejames.downsman.ui;

import com.scottejames.downsman.model.ReferenceData;
import com.scottejames.downsman.model.ScoutModel;
import com.scottejames.downsman.model.SupportModel;
import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.ui.validators.EmailValidator;
import com.scottejames.downsman.ui.validators.PhoneValidator;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;

class TeamDialog extends Dialog {


    private static final String ERROR = "error";
    private static final String OK = "ok";

    private final TeamService         service             = ServiceManager.getInstance().getTeamService();
    private TeamModel           model               = null;
    private final Binder<TeamModel>   binder              = new Binder<>(TeamModel.class);
    private final FormLayout          teamDetailsForm     = new FormLayout();
    private final Grid<SupportModel>  supportMembersGrid  = new Grid<>();
    private Button              addNewSupportButton = null;
    private Button              editSupportButton   = null;
    private Button              deleteSupportButton = null;
    private SupportModel        selectedSupport     = null;
    private SupportDialog       supportDialog       = null;

    private final Grid<ScoutModel>    teamMembersGrid     = new Grid<>();
    private Button              addNewScoutButton   = null;
    private Button              editScoutButton     = null;
    private Button              deleteScoutButton   = null;
    private ScoutModel          selectedScout       = null;
    private ScoutDialog         scoutDialog         = null;

    private final Button              saveFormButton;
    private final Button              cancelFormButton;

    private final Runnable            onSave;

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


        TextField activePhone = new TextField();
        Label activePhoneStatus = new Label();

        activePhone.setValueChangeMode(ValueChangeMode.ON_BLUR);
        teamPhoneForm.addFormItem(activePhone,"Active Phone");
        teamPhoneForm.addFormItem(activePhoneStatus, "");
        binder.forField(activePhone)
                .withValidator(new PhoneValidator("That does not look like a phone number"))
                .withStatusLabel(activePhoneStatus)
                .bind("activeMobile");

        TextField backupPhone = new TextField();
        Label backupPhoneStatus = new Label();

        backupPhone.setValueChangeMode(ValueChangeMode.ON_BLUR);
        teamPhoneForm.addFormItem(backupPhone,"Backup Phone");
        teamPhoneForm.addFormItem(backupPhoneStatus, "");
        binder.forField(backupPhone)
                .withValidator(new PhoneValidator("That does not look like a phone number"))
                .withStatusLabel(backupPhoneStatus)
                .bind("backupMobile");



        add(teamPhoneForm);
    }
    private void setupTeamDetails(){
        add(new Label("Team Details"));

        addTextField(teamDetailsForm,"Team Name", "teamName");
        addTextField(teamDetailsForm,"Section", "section");
        addTextField(teamDetailsForm,"District", "district");
        addTextField(teamDetailsForm,"County", "county");

        ComboBox<String> startTimeCombo= new ComboBox<>();
        startTimeCombo.setItems(ReferenceData.START_TIMES);
        teamDetailsForm.addFormItem(startTimeCombo,"Start Time");
        binder.bind(startTimeCombo,"prefStart");

        ComboBox<String> hikeClass= new ComboBox<>();
        hikeClass.setItems(ReferenceData.HIKE_CLASSES);
        teamDetailsForm.addFormItem(hikeClass,"Hike Class Time");
        binder.bind(hikeClass,"hikeClass");

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

    private void updateScoutGrid(){
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


    private void updateSupportGrid(){

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

        TextField name = new TextField();
        Label nameStatus = new Label();

        name.setValueChangeMode(ValueChangeMode.EAGER);
        emergencyContactForm.addFormItem(name,"Name");
        emergencyContactForm.addFormItem(nameStatus, "");
        binder.forField(name)
                .withStatusLabel(nameStatus)
                .bind("emergencyContactName");

        TextField email = new TextField();
        Label emailStatus = new Label();

        email.setValueChangeMode(ValueChangeMode.ON_BLUR);
        emergencyContactForm.addFormItem(email,"Email");
        emergencyContactForm.addFormItem(emailStatus, "");
        binder.forField(email)
                .withValidator(new EmailValidator("That does not look like a valid email"))
                .withStatusLabel(emailStatus)
                .bind("emergencyContactEmail");

        TextField mobilePhone = new TextField();
        Label mobilePhoneStatus = new Label();

        mobilePhone.setValueChangeMode(ValueChangeMode.ON_BLUR);
        emergencyContactForm.addFormItem(mobilePhone,"Mobile");
        emergencyContactForm.addFormItem(mobilePhoneStatus, "");
        binder.forField(mobilePhone)
                .withValidator(new PhoneValidator("That does not look like a phone number"))
                .withStatusLabel(mobilePhoneStatus)
                .bind("emergencyContactMobile");

        TextField landLinePhone = new TextField();
        Label landlinePhoneStatus = new Label();

        landLinePhone.setValueChangeMode(ValueChangeMode.ON_BLUR);
        emergencyContactForm.addFormItem(landLinePhone,"Landline");
        emergencyContactForm.addFormItem(landlinePhoneStatus, "");
        binder.forField(landLinePhone)
                .withValidator(new PhoneValidator("That does not look like a phone number"))
                .withStatusLabel(landlinePhoneStatus)
                .bind("emergencyContactLandline");

        add(emergencyContactForm);
    }
    private void addTextField(FormLayout form, String label, String bindValue){

        TextField field = new TextField();
        form.addFormItem(field,label);
        if (bindValue != null)
            binder.bind(field,bindValue);


    }

    private void saveForm(){
            if (binder.writeBeanIfValid(model)){
                if (model.isPersisted())
                    service.update(model);
                else
                    service.add(model);
                showNotification("Team Saved Successfully", model.toString(),false);
                onSave.run();

                close();
            } else {
                showNotification("Error", "Team not saved check errors", true);
            }
    }
    private void cancelForm(){
       close();


    }

    private void showNotification(String title, String message, boolean error) {
        Dialog dialog = createDialog(title, message, error);
        if (getUI().isPresent())
            getUI().get().add(dialog);
        dialog.open();
    }
    private Dialog createDialog(String title, String text,
                                boolean error) {
        Dialog dialog = new Dialog();
        dialog.setId("notification");
        dialog.add(new H2(title));
        HtmlComponent paragraph = new HtmlComponent(Tag.P);
        paragraph.getElement().setText(text);
        if (error) {
            paragraph.setClassName(ERROR);
        }
        dialog.add(paragraph);
        return dialog;
    }

}
