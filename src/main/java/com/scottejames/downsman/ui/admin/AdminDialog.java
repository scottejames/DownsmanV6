package com.scottejames.downsman.ui.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;

public class AdminDialog extends Dialog {

    private final FormLayout adminDialogForm     = new FormLayout();

    public AdminDialog(){
        adminDialogForm.addFormItem(new Button("User Admin",e->userAdminDialogue()),"User Admin");
        adminDialogForm.addFormItem(new Button("Team Admin", e->teamAdminDialog()),"Team Admin");

        add(adminDialogForm);
    }

    private void teamAdminDialog() {
        TeamAdminDialog teamAdminDialog = new TeamAdminDialog();
        teamAdminDialog.open();
    }


    private void userAdminDialogue() {
        UserAdminDialog userAdminDialog = new UserAdminDialog();
        userAdminDialog.open();

    }
}
