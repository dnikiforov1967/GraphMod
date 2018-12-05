/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.config;

import java.awt.TextField;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.template.graphmod.swt.annotations.MongoApi;
import org.template.graphmod.swt.api.ButtonFactory;
import org.template.graphmod.swt.extention.EntityButton;
import org.template.graphmod.swt.model.mongo.Customer;
import org.template.graphmod.swt.repository.CustomerRepository;
import org.template.graphmod.swt.wigets.mongo.CustomerButton;





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
    public Shell mainShell(Display display, @MongoApi ButtonFactory<Customer> buttonFactory) {
        Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
        shell.setText("DBT control over business objects");
        RowLayout layout = new RowLayout();
        shell.setLayout(layout);
		final EntityButton<Customer> saveButton = buttonFactory.createButton(SWT.SAVE, shell);
		saveButton.setData(new RowData(80,30));
		final EntityButton<Customer> searchButton = buttonFactory.createButton(SWT.SEARCH, shell);
		saveButton.setData(new RowData(80,30));
		Text textName = new Text(shell, SWT.SINGLE);
		textName.setData(new RowData(180,30));
		textName.addModifyListener(saveButton.getListener("firstName"));
		Text textName2 = new Text(shell, SWT.SINGLE);
		textName2.setData(new RowData(180,30));		
		textName2.addModifyListener(saveButton.getListener("lastName"));
        return shell;
    }

}
