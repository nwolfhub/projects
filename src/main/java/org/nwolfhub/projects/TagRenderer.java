package org.nwolfhub.projects;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.nwolfhub.projects.database.ObjectDao;
import org.nwolfhub.projects.database.model.Project;
import org.nwolfhub.projects.database.model.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagRenderer {
    public static VerticalLayout renderTags(List<Tag> tags) {
        VerticalLayout layout = new VerticalLayout();
        for(Tag tag:tags) {
            Span span = new Span(tag.getText());
            span.getStyle().set("background-color", tag.getColor()).set("color", "white");
            layout.add(span);
        }
        return layout;
    }

    public static List<Tag> getTags(Project project, ObjectDao dao) {
        List<Tag> tags = new ArrayList<>();
        for(Integer tagId:Arrays.stream(project.getTags().split(",")).map(Integer::valueOf).toList()) {
            Tag tag = dao.getTag(tagId);
            if(tag==null) {
                ProjectsApplication.cli.print("Could not find tag with id " + tagId + ", project " + project.getId(), "default");
            } else {
                tags.add(tag);
            }
        }
        return tags;
    }
}
