/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.factory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.template.graphmod.swt.annotations.MongoApi;
import org.template.graphmod.swt.api.ButtonFactory;
import org.template.graphmod.swt.api.CrudApi;
import org.template.graphmod.swt.extention.EntityButton;
import org.template.graphmod.swt.model.mongo.Customer;
import org.template.graphmod.swt.wigets.mongo.CustomerButton;


/**
 *
 * @author dima
 */
@Service
@MongoApi
public class CustomerButtonFactory implements ButtonFactory<Customer> {

	@Autowired
	@MongoApi
	private CrudApi crudApi;
	
	@Override
	public CustomerButton createButton(int index, Composite shell) {
		final CustomerButton button = new CustomerButton(shell, SWT.PUSH, new Customer());
		assingListenerAndText(button, index);
		return button;
	}

	private void assingListenerAndText(CustomerButton button, int index) {
		switch (index) {
			case SWT.SAVE:
				button.setText("&Save");
				final Customer customer = button.getObservee();
				button.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						crudApi.save(customer);
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
