package org.nwolfhub.projects;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;
import org.nwolfhub.projects.database.HibernateController;
import org.nwolfhub.projects.database.ObjectDao;
import org.nwolfhub.projects.database.model.Project;

import java.util.List;

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
        ObjectDao dao = new ObjectDao(ProjectsApplication.context.getBean("hibernateController", HibernateController.class));
        List<Project> projects = dao.getProjects();
        if (projects.size()==0) {
            Div nothingDiv = new Div();
            nothingDiv.getStyle().set("margin-left", "auto").set("margin-right", "auto").set("vertical-align", "middle");
            Label nothingLabel = new Label("No projects yet :(");
            nothingLabel.getStyle().set("text-align", "center").set("font-size", "300%");
            nothingDiv.getElement().setProperty("align", "center");
            nothingDiv.add(nothingLabel);
            setContent(nothingDiv);
        } else {
            Grid<Project> grid = new Grid<>();
            grid.addColumn(Project::getName).setHeader("Title");
            grid.addColumn(Project::getShortDescription).setHeader("Description");
            grid.setItems(projects);
            setContent(grid);
        }
    }
}
