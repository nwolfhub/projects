package org.nwolfhub.projects;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.Command;
import org.nwolfhub.projects.database.HibernateController;
import org.nwolfhub.projects.database.ObjectDao;
import org.nwolfhub.projects.database.model.Tag;

import java.util.List;

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
            List<Tag> tags = TagRenderer.getTags(project, dao);
            Div mainDiv = new Div();
            VerticalLayout centeredBox = new VerticalLayout();
            Label projectName = new Label(project.getName());
            centeredBox.add(projectName);
            centeredBox.setAlignItems(FlexComponent.Alignment.CENTER);
            centeredBox.add(new HorizontalDivider("black"));
            mainDiv.add(centeredBox);
            HorizontalLayout layout = new HorizontalLayout();
            VerticalDivider divider = new VerticalDivider();
            Label descriptionLabel = new Label(project.getDescription());
            descriptionLabel.getStyle().set("max-width", "70%").set("word-wrap", "break-word");
            layout.add(descriptionLabel);
            divider.getStyle().set("margin-left", "auto").set("margin-right", "0").set("background-color", "black").set("max-width", "1px");
            layout.add(divider);
            VerticalLayout tagsLayout = TagRenderer.renderTags(tags);
            tagsLayout.getStyle().set("max-width", "30%");
            tagsLayout.setAlignItems(FlexComponent.Alignment.START);
            layout.add(tagsLayout);
            mainDiv.add(layout);
            setContent(mainDiv);
        } catch (NumberFormatException e) {
            UI.getCurrent().access(() -> UI.getCurrent().getPage().setLocation("/"));
        } catch (Exception e) {
            Integer errorId = Error.addError(e.toString());
            getUI().get().access((Command) () -> UI.getCurrent().getPage().setLocation("/error/" + errorId));
        }
    }
    public static class HorizontalDivider extends Span {
        public HorizontalDivider() {
            getStyle().set("background-color", "white");
            getStyle().set("flex", "0 0 1px");
            getStyle().set("margin-top", "0px");
            getStyle().set("align-self", "stretch");
        }
        public HorizontalDivider(String color) {
            getStyle().set("background-color", color);
            getStyle().set("flex", "0 0 1px");
            getStyle().set("margin-top", "0px");
            getStyle().set("align-self", "stretch");
        }
    }
    public static class VerticalDivider extends Div {
        public VerticalDivider() {
            getStyle().set("border-left", "1px solid #000");
            getStyle().set("height", "500px");
        }
    }
}
