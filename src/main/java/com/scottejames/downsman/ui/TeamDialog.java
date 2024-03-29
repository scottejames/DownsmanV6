package com.scottejames.downsman.ui;

import com.scottejames.downsman.model.*;
import com.scottejames.downsman.services.LogService;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.ui.validators.EmailValidator;
import com.scottejames.downsman.ui.validators.PhoneValidator;
import com.scottejames.downsman.utils.Config;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.component.checkbox.Checkbox;

import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

public class TeamDialog extends Dialog {


    private static final String ERROR = "error";
    private static final String OK = "ok";

    private final TeamService         service             = ServiceManager.getInstance().getTeamService();
    private TeamModel                 model               = null;
    private final Binder<TeamModel>   binder              = new Binder<>(TeamModel.class);
    private final FormLayout          teamDetailsForm     = new FormLayout();
    private final FormLayout          teamPhoneForm = new FormLayout();
    private final FormLayout          emergencyContactForm = new FormLayout();

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

    private  Button              saveFormButton     = null;
    private  Button              cancelFormButton   = null;
    private  Button              submitTeamButton   = null;
    private  Button              payButton          = null;
    private  Button              validateTeamButton = null;


    private  Runnable            onSave = null;

    public TeamDialog(TeamModel team, Runnable onSave) {

        boolean locked = Config.getInstance().isLocked();
        UserModel user = SessionState.getInstance().getCurrentUser();

        if (user.getBreakLock()){
            locked = false;
        }

        if (team == null) {
            model = new TeamModel();
            model.setTeamName("Default");
            model.setLeaderName(SessionState.getInstance().getCurrentUser().getUsername());
            model.setOwnerID(SessionState.getInstance().getCurrentUser().getId());
            service.add(model);

        }
        else
            model = team;
        this.onSave = onSave;

        setSizeUndefined();

        setupTeamDetails();
        setupScoutGrid();
        setupTeamPhone();
        setupSupportGrid();
        setupEmergencyContact();

        setupStatusDetails();

        setupButtons();
        binder.readBean(model);

        if ((model.isTeamSubmitted() == true)||(locked)){
            teamDetailsForm.setEnabled(false);
            teamPhoneForm.setEnabled(false);
            emergencyContactForm.setEnabled(false);
            supportMembersGrid.setEnabled(false);
            teamMembersGrid.setEnabled(false);
            addNewScoutButton.setEnabled(false);
            addNewSupportButton.setEnabled(false);
            saveFormButton.setEnabled(false);
            submitTeamButton.setEnabled(false);
            validateTeamButton.setEnabled(false);
        } else {

        }


    }



    private void setupStatusDetails() {
        HorizontalLayout statusLayout = new HorizontalLayout();

        statusLayout.add(new Label("Team Status  ["));
        statusLayout.add(new Label("Payment Status : "));
        statusLayout.add(new Label(model.getPaymentStatus()));
        statusLayout.add(new Label("Submitted Status : "));
        statusLayout.add(new Label(model.getSubmittedStatus() + " ]"));
        add(statusLayout);
    }


    private void setupTeamPhone(){
        add(new Label("Team Phones"));


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
        addTextField(teamDetailsForm, "Group Name","groupName");
        addTextField(teamDetailsForm,"District", "district");
        addTextField(teamDetailsForm,"County", "county");

        ComboBox<String> hikeClass= new ComboBox<>();
        hikeClass.setItems(ReferenceData.HIKE_CLASSES);
        teamDetailsForm.addFormItem(hikeClass,"Hike Class");
        binder.bind(hikeClass,"hikeClass");

//        Checkbox teamRunningCheckBox = new Checkbox();
//        teamDetailsForm.addFormItem(teamRunningCheckBox,new Label("Team committed to running"));

//        Checkbox campingAtStart = new Checkbox();
//        teamDetailsForm.addFormItem(campingAtStart,"Camping at Plumpton College (available to teams travelling from afar)");
//        hikeClass.addValueChangeListener(e-> {
//            if (e.getValue()!=null) {
//                if ((e.getValue().equals("A-Class")) || (e.getValue().equals("B-Class"))) {
//                    campingAtStart.setEnabled(true);
//
//                } else {
//                    campingAtStart.setEnabled(false);
//                    campingAtStart.setValue(false);
//
//                }
//            } else {
//                campingAtStart.setEnabled(false);
//                campingAtStart.setValue(false);
//            }
//        });
//        binder.bind(campingAtStart,"campingAtStart");
//        campingAtStart.setEnabled(true);



//        teamRunningCheckBox.addValueChangeListener(e-> {
//                    if (e.getValue() == true) {
//                        MessageDialog message = new MessageDialog("Info!", "Please review rules regarding running, additional checks required", false);
//                        message.open();
//                    }
//                });
//        binder.bind(teamRunningCheckBox,"committedToRun");
        add(teamDetailsForm);
    }



    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    private void setupScoutGrid(){
        add(new Label("Hiking Team"));

        teamMembersGrid.addColumn(ScoutModel::getFullName).setHeader("Full Name");
        teamMembersGrid.addColumn(ScoutModel::getDobString).setHeader("DOB");
        teamMembersGrid.addColumn(ScoutModel::isLeader).setHeader("is Leader");


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

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    private void setupButtons(){
        HorizontalLayout buttonLayout = new HorizontalLayout();

        if (model.isTeamSubmitted() == false){
            // button is withdraw
            // Should check here for LOCKED
            submitTeamButton = new Button ("Submit Team");
            submitTeamButton.addClickListener( e-> this.submitTeam());


        } else {
            submitTeamButton = new Button("Withdraw Team");
            submitTeamButton.addClickListener(e -> this.withdrawTeam());
        }
        validateTeamButton = new Button ("Validate Team");
        validateTeamButton.addClickListener( e-> this.validateTeam());

        payButton = new Button ("How do I pay");
        payButton.addClickListener( e-> this.markPayment());

        saveFormButton = new Button("Save");
        saveFormButton.addClickListener(e -> this.saveForm(true));
        saveFormButton.getElement().setAttribute("theme", "contrast primary");

        cancelFormButton = new Button("Cancel");
        cancelFormButton.addClickListener(e -> this.cancelForm());


        HorizontalLayout saveButtonLayout = new HorizontalLayout();
        //saveButtonLayout.add(validateTeamButton,submitTeamButton,saveFormButton, cancelFormButton);
        saveButtonLayout.add(payButton,validateTeamButton,submitTeamButton,saveFormButton, cancelFormButton);

        add(saveButtonLayout);
    }


    private void markPayment() {

        PayDialog dialog = new PayDialog(model,service);
        dialog.open();
    }

    private void submitTeam() {
        String [] submissionString = service.validate(model);
        if (submissionString.length == 0){
            LogService.logEvent("Submitted team " + model.getTeamName());

            model.setTeamSubmitted(true);
            saveForm(true);

        }
        else {
            LogService.logEvent("Failed to submit team (validation failed) " + model.getTeamName());

            MessageDialog dialog = new MessageDialog("Validation Failed", submissionString,true);
            dialog.open();
        }

    }
    private void validateTeam(){
        saveForm(false);

        String [] submissionString = service.validate(model);

        if (submissionString.length==0){
            MessageDialog dialog = new MessageDialog("Team all looks great", submissionString,false);
            dialog.open();
        } else {
            MessageDialog dialog = new MessageDialog("Validation Failed", submissionString,true);
            dialog.open();
        }


    }
    private void withdrawTeam() {
        LogService.logEvent("Withdrew team " + model.getTeamName());

        model.setTeamSubmitted(false);
        saveForm(true);
    }

    private void saveForm(boolean close){
        LogService.logEvent("Saved team " + model.getTeamName());

            if (binder.writeBeanIfValid(model)){
                if (model.isPersisted())
                    service.update(model);
                else
                    service.add(model);
                onSave.run();
                if (close) {
                    showNotification("Team Saved Successfully ", model.toString(), false);
                    close();
                }
            } else {
                showNotification("Error", "Team not saved check errors", true);
            }
    }

    private void cancelForm(){
       close();
    }
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    private void addTextField(FormLayout form, String label, String bindValue){

        TextField field = new TextField();
        form.addFormItem(field,label);
        if (bindValue != null)
            binder.bind(field,bindValue);
//        Registration registration = field.addInputListener(new ComponentEventListener<InputEvent>() {
//            @Override
////            public void onComponentEvent(InputEvent inputEvent) {
////                System.out.println("EVent fired : " + inputEvent);
////            }
//        });
//         registration = field.addKeyPressListener(new ComponentEventListener<KeyPressEvent>() {
//             @Override
//             public void onComponentEvent(KeyPressEvent keyPressEvent) {
//                 //System.out.println("EVent fired : " + keyPressEvent);
//             }
//         });

    }
    private void showNotification(String title, String message, boolean error) {
        MessageDialog dialog = new MessageDialog(title,message,error);
        dialog.open();

    }


}
