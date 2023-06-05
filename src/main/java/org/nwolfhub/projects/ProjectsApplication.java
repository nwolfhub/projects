package org.nwolfhub.projects;

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
		cli.addTask("help", new InputTask() {
			@Override
			public void act(EasyCLI cli, String... params) {
				cli.print("help - display this page\naddError *id* *text* - add a error to error list\nremoveError *id* - removes error from list\ngetError *id* - gets error info\ngetErrors - list all error ids", Defaults.boxedText);
			}
		});
		cli.addTask("addError", new InputTask() {
			@Override
			public void act(EasyCLI cli, String... params) {
				if (params.length<2) {
					cli.print("Usage: addError *id* *text*");
				} else {
					Error.addError(Integer.valueOf(params[0]), String.join(" ", Arrays.stream(params).toList().subList(1, params.length)));
					cli.print("Error added");
				}
			}
		});
		cli.addTask("removeError", new InputTask() {
			@Override
			public void act(EasyCLI cli, String... params) {
				if (params.length!=1) {
					cli.print("Usage: removeError *id*");
				} else {
					Error.removeError(Integer.valueOf(params[0]));
					cli.print("Error removed");
				}
			}
		});
		cli.addTask("getError", new InputTask() {
			@Override
			public void act(EasyCLI cli, String... params) {
				if (params.length!=1) {
					cli.print("Usage: getError *id*");
				} else {
					cli.print(Error.errors.get(Integer.valueOf(params[0])));
				}
			}
		});
		cli.addTask("getErrors", new InputTask() {
			@Override
			public void act(EasyCLI cli, String... params) {
				cli.print(Error.errors.keySet().stream().toList());
			}
		});
		cli.startListening();
		SpringApplication.run(ProjectsApplication.class, args);
		context = new AnnotationConfigApplicationContext(Configurator.class);
		cli.print("CLI is ready! Type help");
	}

}
