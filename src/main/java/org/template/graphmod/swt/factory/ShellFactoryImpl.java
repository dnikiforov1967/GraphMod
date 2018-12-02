/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.factory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.springframework.stereotype.Component;
import org.template.graphmod.swt.api.ShellFactory;

/**
 *
 * @author dima
 */
@Component
public class ShellFactoryImpl implements ShellFactory {
    
    public Shell createShell(Display disp) {
       return new Shell(disp, SWT.SHELL_TRIM | SWT.CENTER);
    }
    
}
