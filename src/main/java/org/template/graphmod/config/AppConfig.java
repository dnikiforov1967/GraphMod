/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.config;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.template.graphmod.swt.annotations.MongoApi;
import org.template.graphmod.swt.api.ButtonFactory;
import org.template.graphmod.swt.repository.CustomerRepository;




/**
 *
 * @author dima
 */
@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = {CustomerRepository.class})
@ComponentScan(basePackages = {"org.template.graphmod.swt"})
public class AppConfig {

    @Bean
    public Display createDisplay() {
        return new Display();
    }

    /*
        eclipse Shell can not be subclassed out of org.eclipse.swt.widgets package.
        For this reason we should use @Bean technique to create injectable shell
     */
    @Bean(name = "mainShell")
    public Shell mainShell(Display display, @MongoApi ButtonFactory buttonFactory) {
        Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
        shell.setText("DBT control over business objects");
        RowLayout layout = new RowLayout();
        shell.setLayout(layout);
		final Button saveButton = buttonFactory.createButton(SWT.SAVE, shell);
		saveButton.setData(new RowData(80,30));
		final Button searchButton = buttonFactory.createButton(SWT.SEARCH, shell);
		saveButton.setData(new RowData(80,30));		
        return shell;
    }

}
