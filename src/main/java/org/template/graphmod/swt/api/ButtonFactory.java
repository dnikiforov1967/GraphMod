/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.api;

import org.eclipse.swt.widgets.Composite;
import org.template.graphmod.swt.extention.EntityButton;

/**
 *
 * @author dnikiforov
 */
public interface ButtonFactory<T extends ReflectionObservee> {
	EntityButton<T> createButton(int index, Composite shell);
}
