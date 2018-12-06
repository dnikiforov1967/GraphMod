/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.api;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 *
 * @author dnikiforov
 */
public interface ReflectionObservee {
	default void modify(String fieldName, Object value) {
		try {
			final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, this.getClass());
			final Method writeMethod = propertyDescriptor.getWriteMethod();
			writeMethod.invoke(this, value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
