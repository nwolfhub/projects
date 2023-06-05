package org.nwolfhub.projects;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;

import java.util.HashMap;
import java.util.Random;

@Route("/error")
public class Error extends AppLayout implements HasUrlParameter<Integer> {
    public static HashMap<Integer, String> errors = new HashMap<>();
    public Error() {

    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer integer) {
        H1 title = new H1(Configurator.getEntry("website_name"));
        title.getStyle().set("font-size", "14px").set("margin", "5px");
        title.addClickListener((ComponentEventListener<ClickEvent<H1>>) h1ClickEvent -> {
            UI.getCurrent().access((Command) () -> UI.getCurrent().getPage().setLocation("/"));
        });
        addToNavbar(title);
        if(errors.containsKey(integer)) {
            Label l = new Label("Error id: " + integer + "\n" + errors.get(integer));
            setContent(l);
        } else {
            Notification.show("Error not found!", 10000, Notification.Position.MIDDLE);
        }
    }
    public static Integer addError(String error) {
        Random r = new Random();
        Integer chosen = r.nextInt();
        while (errors.containsKey(chosen)) {
            chosen = r.nextInt();
        }
        errors.put(chosen, error);
        return chosen;
    }

    public static void addError(Integer id, String error) {
        errors.put(id, error);
    }

    public static void removeError(Integer id) {
        errors.remove(id);
    }
}
