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
import sun.plugin2.message.Message;

public class PayDialog extends Dialog {

    TeamModel model = null;
    TeamService service = null;
    TextField amount = null;

    public PayDialog(TeamModel model,TeamService service){

        this.model = model;
        this.service = service;


        String [] validateResponse = service.validate(model);


        add(new H2("Enter Payment"));

        add(new Paragraph("We would like to know how much you have paid, so that we can ensure that our records " +
                "give a clear view of which teams are all completed and ready to go! Once we validate reciept we will update your " +
                "record to show that payment has been recieved.  We do know though that plans change if you add members to the team " +
                "your entry fee may go up and additional payment may be required.  If you have to pay extra just add the extra payment here.  "));
        add(new Paragraph(
                "If your team is valid " +
                "this form will give you an idea of how much you should pay.  If you are making a single payment for several teams " +
                "allocate this payment to each team."));


        add(new Paragraph("To pay please send a BACS payment to " + Config.getInstance().getConfigString("bank_details")));

        if (model.getPaymentAmount() != 0) {
            String message = "Currently paid : " + model.getPaymentAmount();
            if (model.isPaymentRecieved() == true)
                message += " this is confirmed as received";
            else
                message += " this is not yet confirmed received";
            add(new Paragraph(message));
        }

        if (validateResponse.length > 0) {
            add(new Paragraph("Team is not yet valid, that's ok please ensure you come back and complete it later.  " +
                    "To see whats wrong with the team click 'Validate Team' as the team is incomplete we cant predict your entrance fee"));
        } else {

            if (model.getPaymentAmount() == model.getEntranceFee()) {
                add(new Paragraph("All paid up : " + model.getEntranceFee()));
            } else if (model.getPaymentAmount() < model.getEntranceFee()){
                int outstanding = model.getEntranceFee() - model.getPaymentAmount();
                add(new Paragraph("You have " + outstanding + " pounds outstanding of " + model.getEntranceFee() +
                " entrance fee"));
            } else{
                int overpayment = model.getPaymentAmount() - model.getEntranceFee();
                add(new Paragraph("It looks lie you have overpaid by " + overpayment + " if you have please contact us via the website"));
            }
        }



        amount = new TextField();

        add(amount);

        Button save = new Button("Save");
        Button delete = new Button("Cancel");
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addClickListener(e -> save());
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
