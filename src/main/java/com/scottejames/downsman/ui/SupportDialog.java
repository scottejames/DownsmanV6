package com.scottejames.downsman.ui;

import com.scottejames.downsman.model.SupportModel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

class SupportDialog extends Dialog {
    private SupportModel model = null;
    private Runnable onChange = null;

    private final Binder<SupportModel> binder = new Binder<>(SupportModel.class);
    private final FormLayout form = new FormLayout();

    public SupportDialog(SupportModel model, Runnable onChange) {
        if (model == null)
            this.model = new SupportModel();
        else
            this.model = model;

        this.onChange = onChange;
        addTextField("Full Name","fullName");
        addTextField("Phone", "phoneNumber");
        addTextField("From", "from");
        addTextField("To", "to");

        binder.readBean(model);
        Button save = new Button("Save");
        save.getElement().setAttribute("theme", "contrast primary");

        Button delete = new Button("Cancel");
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
    public SupportModel getSupport(){
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
