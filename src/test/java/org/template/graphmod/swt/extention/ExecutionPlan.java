/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.extention;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author dnikiforov
 */
public class ExecutionPlan {

	private final Map<Integer, VulnerabilityScript> scripts;
	private final Map<Integer, Boolean> executionTrack = new HashMap<>();

	public ExecutionPlan(Map<Integer, VulnerabilityScript> scripts) {
		this.scripts = scripts;
	}

	private void recursiveCall(VulnerabilityScript script) {
		final List<Integer> dependencies = script.getDependencies();
		final int scriptId = script.getScriptId();
		//Dependencies should be executed first
		if (dependencies != null) {
			dependencies.forEach((t) -> {
				recursiveCall(scripts.get(t));
			});
		}
		final Boolean result = executionTrack.putIfAbsent(scriptId, Boolean.TRUE);
		if (result == null) {
			System.out.println(script);
		}
	}

	public void execute(Collection<? extends VulnerabilityScript> list) {
		list.forEach(this::recursiveCall);
	}
}
