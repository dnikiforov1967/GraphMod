/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.template.graphmod.swt.model.mongo.Customer;

/**
 *
 * @author dnikiforov
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {

    public Customer findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);

}
