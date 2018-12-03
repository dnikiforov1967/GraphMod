/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.factory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.template.graphmod.swt.annotations.MongoApi;
import org.template.graphmod.swt.api.ButtonFactory;
import org.template.graphmod.swt.api.CrudApi;

/**
 *
 * @author dima
 */
@Service
@MongoApi
public class MongoButtonFactory implements ButtonFactory {

	@Autowired
	@MongoApi
	private CrudApi crudApi;
	
	@Override
	public Button createButton(int index, Shell shell) {
		final Button button = new Button(shell, SWT.PUSH);
		assingListenerAndText(button, index);
		return button;
	}

	private void assingListenerAndText(Button button, int index) {
		switch (index) {
			case SWT.SAVE:
				button.setText("&Save");
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						crudApi.save();
					}
				});
				break;
			case SWT.SEARCH:
				button.setText("&Search");
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						crudApi.search();
					}
				});
				break;
		}

	}

}
