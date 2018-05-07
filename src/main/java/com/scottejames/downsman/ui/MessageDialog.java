package com.scottejames.downsman.ui;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;

import static com.sun.tools.internal.xjc.reader.Ring.add;

public class MessageDialog  extends Dialog {
    private static final String ERROR = "error";
    private static final String OK = "ok";

    public MessageDialog(String title, String text,
                                boolean error) {
        add(new H2(title));
        HtmlComponent paragraph = new HtmlComponent(Tag.P);
        paragraph.getElement().setText(text);
        if (error) {
            paragraph.setClassName(ERROR);
        }
        add(paragraph);
        add(new Button(OK, e->close()));
    }


}
