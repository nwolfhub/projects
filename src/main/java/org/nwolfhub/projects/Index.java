package org.nwolfhub.projects;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;

@Route("/")
public class Index extends AppLayout {
    public Index() {
        H1 title = new H1(Configurator.getEntry("website_name"));
        title.getStyle().set("font-size", "14px").set("margin", "5px");
        addToNavbar(title);
        ProgressBar loadingBar = new ProgressBar();
        loadingBar.setIndeterminate(true);
        Div loadingDiv = new Div();
        loadingDiv.getElement().setProperty("align", "center");
        Div progressBarLabel = new Div();
        progressBarLabel.setText("Fetching database...");
        progressBarLabel.getStyle().set("margin-left", "auto").set("margin-right", "auto").set("vertical-align", "middle");
        loadingDiv.add(progressBarLabel, loadingBar);
        setContent(loadingDiv);
    }
}
