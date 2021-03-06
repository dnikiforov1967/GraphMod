/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.model.mongo;

import org.springframework.data.annotation.Id;
import org.template.graphmod.swt.annotations.Listened;
import org.template.graphmod.swt.api.ReflectionObservee;

/**
 *
 * @author dnikiforov
 */
public class Customer implements ReflectionObservee {

    @Id
    public String id;

	@Listened
    public String firstName;
	@Listened
    public String lastName;

    public Customer() {}

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
	
}