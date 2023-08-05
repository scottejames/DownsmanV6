package com.scottejames.downsman.ui;

import com.scottejames.downsman.model.TeamModel;
import com.scottejames.downsman.services.TeamService;
import com.scottejames.downsman.utils.Config;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class PayDialog extends Dialog {

    TeamModel model = null;
    TeamService service = null;
    TextField amount = null;

    public PayDialog(TeamModel model,TeamService service){

        this.model = model;
        this.service = service;


        String [] validateResponse = service.validate(model);

        add(new H2("Enter Payment"));
        add(new Paragraph("To pay please send a BACS payment to " + Config.getInstance().getConfigString("bank_details")));

        if (validateResponse.length > 0) {
            add(new Paragraph("Team is not yet valid, that's ok please ensure you come back and complete it later.  " +
                    "To see whats wrong with the team click 'Validate Team' as the team is incomplete we cant predict your entrance fee"));
        } else {
            add(new Paragraph("It looks like this team should cost "+ model.getEntranceFee()));

        }




        Button delete = new Button("OK");
        HorizontalLayout buttons = new HorizontalLayout( delete);
        delete.addClickListener(e -> cancel());
        add(buttons);

    }
    private void cancel() {
        this.close();
    }
    private void save() {
        int value = 0;
        boolean valid = true;
        try {
           value = Integer.parseInt(amount.getValue());

        } catch (NumberFormatException e){

            valid = false;
        }
        if (value <= 0) {
            valid = false;
        }

        if (valid == true) {
            int currentPayment = model.getPaymentAmount();
            model.setPaymentAmount(currentPayment + value);
            model.setPaymentRecieved(false);
            service.update(model);
            close();
        } else {
            new MessageDialog("ERROR","Amount must be a number greater than zero!",true).open();

        }



    }
}
