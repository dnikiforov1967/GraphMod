/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.template.graphmod.swt.extention;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dnikiforov
 */
public class ExecPlanUnitTest {

	private static final Map<Integer, VulnerabilityScript> scripts = new TreeMap<>();
	
	private static void prepareData() throws IOException {
		scripts.put(1, new VulnerabilityScript(1, null));
		scripts.put(3, new VulnerabilityScript(3, null));
                scripts.put(0, new VulnerabilityScript(0, Arrays.asList(6)));
		scripts.put(2, new VulnerabilityScript(2, Arrays.asList(3)));
		scripts.put(4, new VulnerabilityScript(4, Arrays.asList(1,2,5)));
		scripts.put(5, new VulnerabilityScript(5, Arrays.asList(3,2)));
                scripts.put(6, new VulnerabilityScript(6, Arrays.asList(4)));
	}
	
	public ExecPlanUnitTest() {
	}
	
	@BeforeClass
	public static void setUpClass() throws IOException {
		prepareData();
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	@Test
	public void execPlanTest() {
		ExecutionPlan executionPlan = new ExecutionPlan(scripts);
		final Collection<VulnerabilityScript> values = scripts.values();
		executionPlan.execute(scripts.keySet());
                executionPlan.print();
	}
}
