/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.wigets.mongo;

import org.eclipse.swt.widgets.Composite;
import org.template.graphmod.swt.extention.EntityButton;
import org.template.graphmod.swt.model.mongo.Customer;

/**
 *
 * @author dnikiforov
 */
public final class CustomerButton extends EntityButton<Customer> {

	public CustomerButton(Composite parent, int i, Customer customer) {
		super(parent, i, customer);
		fillListeners();
	}

}
