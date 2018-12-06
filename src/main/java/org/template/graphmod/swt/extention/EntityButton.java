/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.extention;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.template.graphmod.swt.annotations.Listened;
import org.template.graphmod.swt.api.ReflectionObservee;

/**
 *
 * @author dnikiforov
 */
public abstract class EntityButton<T extends ReflectionObservee> extends Button {

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

	protected final void fillListeners() {
		final Field[] declaredFields = observee.getClass().getDeclaredFields();
		final Stream<Field> fieldStream = Stream.of(declaredFields);
		fieldStream
				.filter((t) -> {
					return t.isAnnotationPresent(Listened.class);
				})
				.forEach((t) -> {
					final String name = t.getName();
					listeners.put(name, new ModifyListener() {
						@Override
						public void modifyText(ModifyEvent e) {
							final Text wiget = (Text) e.widget;
							observee.modify(name, wiget.getText());
						}
					});
				});
	}

	public final ModifyListener getListener(String fieldName) {
		return listeners.get(fieldName);
	}

}
