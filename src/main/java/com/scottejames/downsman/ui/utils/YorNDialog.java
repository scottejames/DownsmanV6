package com.scottejames.downsman.ui.utils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class YorNDialog extends Dialog {

    private boolean results = false;

    public boolean getResult() {
        return results;
    }

    public YorNDialog(Runnable yes){
        add(new H2("Are You Sure?"));
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(new Button("Yes", e-> yes()));
        buttons.add(new Button("No", e-> no()));
        add(buttons);
    }

    private void yes(){
        results = true;
        close();
    }
    private void no(){
        results = false;
        close();
    }

}
