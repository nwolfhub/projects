package org.nwolfhub.projects;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;
import org.nwolfhub.projects.database.HibernateController;
import org.nwolfhub.projects.database.ObjectDao;

@Route("/project")
public class Project extends AppLayout implements HasDynamicTitle, HasUrlParameter<String> {

    public String projectId = "";

    public Project() {
    }

    @Override
    public String getPageTitle() {
        return Configurator.getEntry("website_name");
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String s) {
        ProjectsApplication.cli.print("Requested project: " + s);
        projectId = s;
        H1 title = new H1(Configurator.getEntry("website_name"));
        title.getStyle().set("font-size", "14px").set("margin", "5px");
        title.addClickListener((ComponentEventListener<ClickEvent<H1>>) h1ClickEvent -> {
            UI.getCurrent().access((Command) () -> UI.getCurrent().getPage().setLocation("/"));
        });
        addToNavbar(title);
        try {
            Integer id = Integer.valueOf(projectId);
            ProgressBar loadingBar = new ProgressBar();
            loadingBar.setIndeterminate(true);
            Div loadingDiv = new Div();
            loadingDiv.getElement().setProperty("align", "center");
            Div progressBarLabel = new Div();
            progressBarLabel.setText("Fetching database...");
            progressBarLabel.getStyle().set("margin-left", "auto").set("margin-right", "auto").set("vertical-align", "middle");
            loadingDiv.add(progressBarLabel, loadingBar);
            setContent(loadingDiv);
            ObjectDao dao = new ObjectDao(ProjectsApplication.context.getBean("hibernateController", HibernateController.class));
            org.nwolfhub.projects.database.model.Project project = dao.getProject(id);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            UI.getCurrent().access(() -> UI.getCurrent().getPage().setLocation("/"));
        }
    }
}
