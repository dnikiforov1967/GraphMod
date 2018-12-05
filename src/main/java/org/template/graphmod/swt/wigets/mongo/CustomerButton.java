/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.wigets.mongo;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.template.graphmod.swt.extention.EntityButton;
import org.template.graphmod.swt.model.mongo.Customer;

/**
 *
 * @author dnikiforov
 */
public class CustomerButton extends EntityButton<Customer> {

	private void modify(final Text wiget, String fieldName) {
		CustomerButton.this.getObservee().modify(fieldName, wiget.getText(), Customer.class);
	}

	private final ModifyListener lastNameListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			final Text wiget = (Text) e.widget;
			modify(wiget, "lastName");
		}
	};

	private void fillListeners() {
		listeners.put("firstName", new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				final Text wiget = (Text) e.widget;
				modify(wiget, "firstName");
			}
		});
		listeners.put("lastName", new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				final Text wiget = (Text) e.widget;
				modify(wiget, "lastName");
			}
		});		
	}

	public CustomerButton(Composite parent, int i, Customer customer) {
		super(parent, i, customer);
		fillListeners();
	}

}
