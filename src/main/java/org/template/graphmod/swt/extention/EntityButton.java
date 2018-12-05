/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.extention;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.template.graphmod.swt.api.GenericObservee;

/**
 *
 * @author dnikiforov
 */
public abstract class EntityButton<T extends GenericObservee<T>> extends Button {
	
	private final T observee;
	protected final Map<String, ModifyListener> listeners = new HashMap<>();
	
	public EntityButton(Composite parent, int style, T observee) {
		super(parent, style);
		this.observee = observee;
	}

	public final T getObservee() {
		return observee;
	}
	
	@Override
	protected final void checkSubclass() {
		
	}
	
	public final ModifyListener getListener(String fieldName) {
		return listeners.get(fieldName);
	}
	
}
