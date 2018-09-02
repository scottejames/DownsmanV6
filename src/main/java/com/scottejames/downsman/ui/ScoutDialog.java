package com.scottejames.downsman.ui;


import com.scottejames.downsman.model.ScoutModel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import java.util.Locale;

class ScoutDialog extends Dialog {
    private ScoutModel model = null;
    private Runnable onChange = null;

    private final Binder<ScoutModel> binder = new Binder<>(ScoutModel.class);
    private final FormLayout form = new FormLayout();
    private Checkbox isLeader;
    private DatePicker dobDate;

    public ScoutDialog(ScoutModel model, Runnable onChange){
        if (model == null)
            this.model = new ScoutModel();
        else
            this.model = model;

        this.onChange = onChange;

        TextField fullName = new TextField();
        binder.bind(fullName,"fullName");
        form.addFormItem(fullName,"Name");

        dobDate = new DatePicker();
        dobDate.setLocale(Locale.UK);

        binder.bind(dobDate,"dob");
        form.addFormItem(dobDate,"DOB");

        isLeader = new Checkbox();
        isLeader.setValue(false);

        binder.bind(isLeader,"leader");
        form.addFormItem(isLeader,"Scout Leader (Walking with open team)");
        isLeader.addValueChangeListener(e->leaderChanged());

        TextArea medicalNotes = new TextArea();
        medicalNotes.setHeight("500");
        binder.bind(medicalNotes,"medicalNotes");
        form.addFormItem(medicalNotes,"Medical Notes");

        binder.readBean(model);
        Button save = new Button("Save");
        Button delete = new Button("Cancel");
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        add(buttons);
        save.addClickListener(e -> save());
        delete.addClickListener(e -> cancel());
        add(form);

    }

    private void leaderChanged() {
        if (isLeader.getValue()){
            dobDate.setValue(null);
            dobDate.setEnabled(false);
        } else {
            dobDate.setEnabled(true);
        }
    }


    public ScoutModel getScout(){
        return model;
    }

    private void save(){
        try {
            binder.writeBean(model);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        onChange.run();
        close();

    }
    private void cancel(){
        close();
    }
}
