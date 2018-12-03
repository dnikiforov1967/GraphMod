/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.template.graphmod.swt.annotations.MongoApi;
import org.template.graphmod.swt.api.CrudApi;
import org.template.graphmod.swt.model.mongo.Customer;
import org.template.graphmod.swt.repository.CustomerRepository;

/**
 *
 * @author dnikiforov
 */
@Service
@MongoApi
public class MongoApiImpl implements CrudApi {

	@Autowired 
	private CustomerRepository repository;
	
	@Override
	public void save() {
		repository.save(new Customer("Alice", "Smith"));
	}

	@Override
	public void search() {
		for (Customer customer : repository.findAll()) {
			System.out.println(customer);
		}		
	}
	
}
