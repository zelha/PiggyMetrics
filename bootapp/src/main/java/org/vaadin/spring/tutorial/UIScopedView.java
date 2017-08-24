package org.vaadin.spring.tutorial;

import javax.annotation.PostConstruct;

import com.vaadin.annotations.Push;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.security.access.prepost.PreAuthorize;

// Pay attention to the order of annotations
@UIScope
@SpringView(name = UIScopedView.VIEW_NAME)

@Push(value = PushMode.AUTOMATIC ,transport = Transport.WEBSOCKET_XHR )
public class UIScopedView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "ui";

    @Autowired
    private Greeter greeter;

    @PostConstruct
    void init() {
        addComponent(new Label("This is a UI scoped view. Greeter says: "
                + greeter.sayHello()));
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }


    class  FeederThread extends Thread {
        int count = 0;
    }

}