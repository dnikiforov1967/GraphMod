/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.factory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * @author dima
 */
public class ButtonFactory {
    
    public Button createButton(Shell shell, int i) {
        final Button button = new Button(shell, SWT.PUSH);
        FormData buttonData = new FormData(80, 30);
        buttonData.right = new FormAttachment();
        buttonData.bottom = new FormAttachment(10);
        button.setLayoutData(buttonData);
        button.setText("Button");
        return button;
    }
    
}
