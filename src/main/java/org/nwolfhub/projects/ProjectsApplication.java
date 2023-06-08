package org.nwolfhub.projects;

import com.vaadin.flow.server.VaadinService;
import org.nwolfhub.easycli.Defaults;
import org.nwolfhub.easycli.EasyCLI;
import org.nwolfhub.easycli.InputTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@SpringBootApplication
// TODO: 05.06.2023 comment ALL code and make a readme. Yup. Get your lazy ass working!
public class ProjectsApplication {
	public static EasyCLI cli;
	public static AnnotationConfigApplicationContext context;

	public static void main(String[] args) throws InterruptedException {
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
		context = new AnnotationConfigApplicationContext(Configurator.class);
	}

}
