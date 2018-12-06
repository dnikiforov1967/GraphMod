/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.api;

import java.lang.reflect.Field;

/**
 *
 * @author dnikiforov
 */
public interface ReflectionObservee {
	default void modify(String fieldName, Object value) {
		try {
			final Field field = this.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(this, value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
