package org.nwolfhub.projects;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import org.nwolfhub.projects.database.model.Project;

public class ProjectRenderer extends FormLayout {
    private final Label projectId = new Label("Project id");
    private final TextField shortDescription = new TextField("Short description");
    private final Button viewProject = new Button("View project");
    public ProjectRenderer() {
        shortDescription.setReadOnly(true);
        add(projectId);
        add(shortDescription);
        add(viewProject);
    }

    public void setProject(Project p) {
        projectId.setText(p.getId().toString());
        shortDescription.setValue(p.getShortDescription());
        viewProject.addClickListener((ComponentEventListener<ClickEvent<Button>>) buttonClickEvent -> {
            UI.getCurrent().access(() -> {
                UI.getCurrent().getPage().setLocation("projects/" + p.getId());
            });
        });
    }
    public static ComponentRenderer<ProjectRenderer, Project> createRenderer() {
        return new ComponentRenderer<>(ProjectRenderer::new, ProjectRenderer::setProject);
    }
}
