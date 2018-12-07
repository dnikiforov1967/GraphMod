/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.extention;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author dnikiforov
 */
public class ExecutionPlan {

    private final Map<Integer, VulnerabilityScript> scripts;
    private final Map<Integer, Boolean> executionTrack = new ConcurrentHashMap<>();
    private final Queue<Integer> executionFlow = new ConcurrentLinkedQueue<>();

    public ExecutionPlan(Map<Integer, VulnerabilityScript> scripts) {
        this.scripts = scripts;
    }

    private void recursiveCall(Integer scriptId) {
        //I was visited ?
        final Boolean result = executionTrack.putIfAbsent(scriptId, Boolean.TRUE);
        if (result == null) {
            final List<Integer> dependencies = scripts.get(scriptId).getDependencies();
            //Dependencies should be executed first
            if (dependencies != null) {
                dependencies.forEach(this::recursiveCall);
            }
            executionFlow.add(scriptId);
        }
    }

    public void execute(Collection<Integer> list) {
        executionTrack.clear();
        list.forEach(this::recursiveCall);
    }
    
    public void print() {
        executionFlow.forEach(System.out::println);
    }
}
