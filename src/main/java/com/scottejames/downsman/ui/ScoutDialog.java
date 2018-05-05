package com.scottejames.downsman.ui;


import com.scottejames.downsman.model.ScoutModel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

public class ScoutDialog extends Dialog {
    ScoutModel model = null;
    Runnable onChange = null;

    private Binder<ScoutModel> binder = new Binder<>(ScoutModel.class);
    private Button save = new Button("Save");
    private Button delete = new Button("Cancel");
    private FormLayout form = new FormLayout();

    public ScoutDialog(ScoutModel model, Runnable onChange){
        if (model == null)
            this.model = new ScoutModel();
        else
            this.model = model;

        this.onChange = onChange;

        addTextField("Full Name","fullName");
        addTextField("DOB", "dob");
        addTextField("Gender", "gender");
        binder.readBean(model);
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        add(buttons);
        save.addClickListener(e -> save());
        delete.addClickListener(e -> cancel());
        add(form);

    }

    private  void addTextField( String label, String bindValue){

        TextField textField = new TextField();
        form.addFormItem(textField,label);
        if (bindValue != null)
            binder.bind(textField,bindValue);

    }
    public ScoutModel getScout(){
        return model;
    }

    public void save(){
        try {
            binder.writeBean(model);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        onChange.run();
        close();

    }
    public void cancel(){
        close();
    }
}
