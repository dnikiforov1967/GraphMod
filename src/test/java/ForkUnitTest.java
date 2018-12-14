/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
public class ForkUnitTest {

	private static final Map<Node, Boolean> listOfVisits = new ConcurrentHashMap<>();
	private static final Set<Thread> setOfThreads = new HashSet<>();	
	
	private static class EasyAction extends RecursiveAction {

		private final Node node;

		public EasyAction(Node node) {
			this.node = node;
		}
		
		@Override
		protected void compute() {
			setOfThreads.add(Thread.currentThread());
			synchronized (node) {
				System.out.println("Thread "+Thread.currentThread().getName()+" visit node "+node);
				final Boolean visited = listOfVisits.putIfAbsent(node, Boolean.TRUE);
				if (visited != null) {
					return;
				}
				final Collection<Node> children = node.getChildren();
				if(children!=null) {
					ForkJoinTask.invokeAll(createSubtasks(children));
				}
				node.proceed();
				System.out.println("Thread "+Thread.currentThread().getName()+" proceeded node "+node);
			}	
		}
		
		private List<EasyAction> createSubtasks(Collection<Node> children) {
			return children.stream().map(EasyAction::new).collect(Collectors.toList());
		}
		
	}
	
	
	public ForkUnitTest() {
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

	private static void execute(Collection<Node> list) {
		final List<EasyAction> collect = list.stream().map(EasyAction::new).collect(Collectors.toList());
		ForkJoinTask.invokeAll(collect);
	}	
	
	@Test
	public void forkingTest() throws InterruptedException {
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

		
		ForkJoinPool.managedBlock(new ForkJoinPool.ManagedBlocker() {
			@Override
			public boolean block() throws InterruptedException {
				return true;
			}

			@Override
			public boolean isReleasable() {
				return true;
			}
		});
		
		execute(Arrays.asList(nodes[1], nodes[2], nodes[3]));

		TimeUnit.SECONDS.sleep(1);
		System.out.println("Number of threads "+setOfThreads.size());
	}
}
