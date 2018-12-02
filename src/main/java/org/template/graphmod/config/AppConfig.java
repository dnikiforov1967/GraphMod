/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.config;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.template.graphmod.swt.factory.ButtonFactory;

/**
 *
 * @author dima
 */
@Configuration
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
    public Shell mainShell(Display display) {
        Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.CENTER);
        shell.setText("DBT control over business objects");
        FormLayout layout = new FormLayout();
        shell.setLayout(layout);
        final ButtonFactory buttonFactory = new ButtonFactory();
        for (int i = 0; i < 10; i++) {
            buttonFactory.createButton(shell, i);
        }

        return shell;
    }

}
