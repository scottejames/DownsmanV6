package com.scottejames.downsman.ui.admin;

import com.scottejames.downsman.model.UserModel;
import com.scottejames.downsman.services.Service;
import com.scottejames.downsman.services.ServiceManager;
import com.scottejames.downsman.services.UserService;
import com.scottejames.downsman.ui.utils.YorNDialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.List;

public class UserAdminDialog extends Dialog {
    private final Grid<UserModel> userGrid  = new Grid<>();
    private final Button deleteUserButton;
    private final Button resetPasswordButton;
    private final Button toggleAdminButton;
    private UserService service = ServiceManager.getInstance().getUserService();
    UserModel selectedUser = null;

    UserAdminDialog(){


        userGrid.addColumn(UserModel::getUsername).setHeader("User Name");
        userGrid.addColumn(UserModel::isAdmin).setHeader("Admin");

        add(userGrid);

        HorizontalLayout gridButtons  = new HorizontalLayout();
        deleteUserButton= new Button("Delete User", e->deleteUser());
        resetPasswordButton = new Button("Reset Password", e->resetPassword());
        toggleAdminButton = new Button("Toggle Admin",e->toggleAdmin());

        gridButtons.add(deleteUserButton,resetPasswordButton,toggleAdminButton);
        add(gridButtons);
        setupUserGrid();

        userGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null){
                deleteUserButton.setEnabled(true);
                resetPasswordButton.setEnabled(true);
                toggleAdminButton.setEnabled(true);
            } else {
                deleteUserButton.setEnabled(false);
                resetPasswordButton.setEnabled(false);
                toggleAdminButton.setEnabled(false);
            }

            selectedUser = event.getValue();
        });

    }

    private void setupUserGrid (){

        List<UserModel> users = service.getAll();
        userGrid.setItems(users);
        userGrid.setHeightByRows(true);

    }
    private void resetPassword() {
        setupUserGrid();
//        String user = service.getCurrentUser().getUsername();
//        System.err.println(user);
    }

    public void deleteUser(){

        // Check that we are not trying to delete current user!

            service.remove(selectedUser);
            selectedUser = null;
            setupUserGrid();



    }
    public void toggleAdmin(){
        if (selectedUser.isAdmin())
            selectedUser.setAdmin(false);
        else
            selectedUser.setAdmin(true);
        service.update(selectedUser);

        setupUserGrid();

    }
}