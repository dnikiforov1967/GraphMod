/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.api;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * @author dima
 */
public interface ShellFactory {
    
    Shell createShell(Display disp);
    
}
