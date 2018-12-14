/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
public class ManagedBlockingUnitTest {
	
	private static final Map<Node, Boolean> listOfVisits = new ConcurrentHashMap<>();
	
	private static class Locker implements ForkJoinPool.ManagedBlocker {

		Node node;
		
		Locker(Node node) {
			this.node=node;
		}
		
		@Override
		public boolean block() throws InterruptedException {
			return true;
		}

		@Override
		public boolean isReleasable() {
			return node.getChildren()==null;
		}
		
	}
	
	private static class EasyTask implements Callable<Void> {

		private final Node node;

		public EasyTask(ForkJoinPool es, Node node) {
			this.node = node;
		}

		@Override
		public Void call() throws Exception {
			recursion(node);
			return null;
		}

	}	
	
	public ManagedBlockingUnitTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
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
	
	private static void recursion(Node node) {
		try {
			ForkJoinPool.managedBlock(new Locker(node));
		} catch (InterruptedException ex) {
			throw new RuntimeException();
		}
		synchronized (node) {
			System.out.println("Thread " + Thread.currentThread().getName() + " visit node " + node);
			final Boolean visited = listOfVisits.putIfAbsent(node, Boolean.TRUE);
			if (visited != null) {
				return;
			}
			final Collection<Node> children = node.getChildren();
			if (children != null) {
				//final List<EasyTask> collect = children.stream().map(r -> {
				//	return new EasyTask(es, r);
				//}).collect(Collectors.toList());
				children.stream().parallel().forEach((t)->recursion(t));
				//final List<Future<Void>> invokeAll = es.invokeAll(collect);
			}
			node.proceed();
			System.out.println("Thread " + Thread.currentThread().getName() + " proceeded node " + node);
		}
	}

	private static void execute(Collection<Node> list) {
		//final List<EasyTask> collect = list.stream().map(r -> {
		//	return new EasyTask(es, r);
		//}).collect(Collectors.toList());
		//List<Future<Void>> invokeAll = es.invokeAll(collect);
		list.stream().parallel().forEach((t)->{recursion(t);});
	}	

	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	@Test
	public void test() throws InterruptedException {
		Node[] nodes = {
			null,
			new Node(1),
			new Node(2),
			new Node(3),
			new Node(4),
			new Node(5),
			new Node(6),
			new Node(7),
			new Node(8),
			new Node(9),
			new Node(10),
			new Node(11),
			new Node(12),};
		nodes[1].setChildren(Arrays.asList(nodes[4]));
		nodes[2].setChildren(Arrays.asList(nodes[4], nodes[5], nodes[7]));
		nodes[3].setChildren(Arrays.asList(nodes[6]));
		nodes[4].setChildren(Arrays.asList(nodes[8], nodes[7]));
		nodes[5].setChildren(Arrays.asList(nodes[11], nodes[7]));
		nodes[6].setChildren(Arrays.asList(nodes[9], nodes[10]));
		nodes[7].setChildren(Arrays.asList(nodes[10], nodes[11], nodes[12]));
		nodes[8].setChildren(Arrays.asList(nodes[11], nodes[12]));	
		//final ForkJoinPool commonPool = ForkJoinPool.commonPool();
		execute(Arrays.asList(nodes[1], nodes[2], nodes[3]));

		TimeUnit.SECONDS.sleep(1);
		//commonPool.shutdown();
		
	}
}
