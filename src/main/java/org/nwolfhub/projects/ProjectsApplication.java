package org.nwolfhub.projects;

import org.nwolfhub.easycli.Defaults;
import org.nwolfhub.easycli.EasyCLI;
import org.nwolfhub.projects.database.ObjectDao;
import org.nwolfhub.projects.database.model.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class ProjectsApplication {
	public static EasyCLI cli;

	public static void main(String[] args) {
		cli = new EasyCLI();
		cli.addTemplate(Defaults.defaultTemplate, "default");
		cli.addTemplate(Defaults.boxedText, "boxed");
		cli.addTemplate(Defaults.carriageReturn, "return");
		cli.setActiveTemplate("default");
		cli.print("Checking config", "return");
		if(!Configurator.checkConfigExistence()) {
			try {
				File cfgFile = Configurator.createDemoConfig();
				cli.print("New config was created at " + cfgFile.getAbsolutePath() + "\nFill it and then restart program", "return");
				System.exit(2);
			} catch (IOException e) {
				cli.print("\nFailed to create config file: " + e);
				System.exit(1);
			}
		}
		Configurator.init();
		cli.print("Config check passed. Entries parsed: " + Configurator.getEntriesAmount());
		SpringApplication.run(ProjectsApplication.class, args);
	}

}
