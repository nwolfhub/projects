package org.nwolfhub.projects;

import org.nwolfhub.easycli.Defaults;
import org.nwolfhub.easycli.EasyCLI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectsApplication {
	public static EasyCLI cli;

	public static void main(String[] args) {
		cli = new EasyCLI();
		cli.addTemplate(Defaults.defaultTemplate, "default");
		cli.addTemplate(Defaults.boxedText, "boxed");
		cli.addTemplate(Defaults.carriageReturn, "return");
		cli.setActiveTemplate("default");
		SpringApplication.run(ProjectsApplication.class, args);
	}

}
