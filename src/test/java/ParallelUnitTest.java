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
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author dnikiforov
 */
public class ParallelUnitTest {

	private static final Map<Node, Boolean> listOfVisits = new ConcurrentHashMap<>();
	private static final Set<Thread> setOfThreads = new HashSet<>();

	public ParallelUnitTest() {
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

	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	private static class EasyTask implements Callable<Void> {

		private final ExecutorService es;
		private final Node node;

		public EasyTask(ExecutorService es, Node node) {
			this.es = es;
			this.node = node;
		}

		@Override
		public Void call() throws Exception {
			recursion(node, es);
			return null;
		}

	}

	private static void recursion(Node node, ExecutorService es) {
		setOfThreads.add(Thread.currentThread());
		synchronized (node) {
			System.out.println("Thread " + Thread.currentThread().getName() + " visit node " + node);
			final Boolean visited = listOfVisits.putIfAbsent(node, Boolean.TRUE);
			if (visited != null) {
				return;
			}
			final Collection<Node> children = node.getChildren();
			if (children != null) {
				final List<EasyTask> collect = children.stream().map(r -> {
					return new EasyTask(es, r);
				}).collect(Collectors.toList());
				try {
					final List<Future<Void>> invokeAll = es.invokeAll(collect);
					for (Future<Void> f : invokeAll) {
						f.get();
					}
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
			node.proceed();
			System.out.println("Thread " + Thread.currentThread().getName() + " proceeded node " + node);
		}
	}

	private static void execute(Collection<Node> list, ExecutorService es) {
		final List<EasyTask> collect = list.stream().map(r -> {
			return new EasyTask(es, r);
		}).collect(Collectors.toList());
		try {
			final List<Future<Void>> invokeAll = es.invokeAll(collect);
			for (Future<Void> f : invokeAll) {
				f.get();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Test
	public void testOfParallel() throws InterruptedException {
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
		ExecutorService es = Executors.newCachedThreadPool();

		execute(Arrays.asList(nodes[1], nodes[2], nodes[3]), es);

		TimeUnit.SECONDS.sleep(1);
		es.shutdown();
		System.out.println("Number of threads " + setOfThreads.size());
	}
}
