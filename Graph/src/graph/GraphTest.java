package graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

public class GraphTest {

	@Test
	public void testSimpleGraph(){
		Graph<String> graph = new Graph<String>();
		graph.add("A");
		graph.add("B");
		graph.add("C");
		graph.add("D");
		graph.addEdge("A", "B", 5);
		graph.addEdge("A", "C", 20);
		graph.addEdge("A", "D", 29);
		graph.addEdge("B", "A", 6);
		graph.addEdge("B", "C", 25);
		graph.addEdge("B", "D", 10);
		graph.addEdge("C", "A", 19);
		graph.addEdge("C", "B", 23);
		graph.addEdge("C", "D", 5);
		graph.addEdge("D", "A", 10);
		graph.addEdge("D", "B", 21);
		graph.addEdge("D", "C", 6);
		
		LinkedList<String> result = graph.shortestPath("A", "D");
		assertEquals("[A, B, D]",result.toString());
		assertEquals(15,graph.pathWeight(result));
		assertEquals(15,graph.shortestDistance("A", "D"));
	}
	
	@Test
	public void testAdd(){
		Graph<String> graph = new Graph<String>();
		assertTrue(addVertices(graph));
		String r = graph.toString();
		assertEquals("A - \r\nB - \r\nC - \r\nD - \r\nE - \r\nF - \r\nG - \r\n"
				+ "H - \r\nI - \r\nJ - \r\nK - \r\nL - \r\nM - \r\nN - \r\n"
				+ "O - \r\nP - \r\nQ - \r\nR - \r\nS - \r\nT - \r\nU - \r\n"
				+ "V - \r\nW - \r\nX - \r\nY - \r\nZ - \r\nnull -",r);
		assertFalse(graph.add("A"));
	}
	
	@Test
	public void testAddEdge(){
		Graph<String> graph = new Graph<String>();
		assertTrue(addVertices(graph));
		assertTrue(addEdges(graph));
	}
	
	@Test
	public void testMutableKeyScrewUp(){
		Graph<ArrayList<String>> graph = new Graph<ArrayList<String>>();
		ArrayList<String> list = new ArrayList<String>();
		list.add("Hello");
		graph.add(list);
		assertTrue(graph.contains(list));
		list.add("World");
		assertFalse(graph.contains(list));
	}
	
	private static boolean addVertices(Graph<String> g){
		for(int i=0;i<26;i++){
			String key = String.valueOf((char)('A'+i));
			g.add(key);
		}
		g.add(null);
		return true;
	}
	
	private static boolean addEdges(Graph<String> g){
		g.addEdge("A", "B");
		g.addEdge("B", "C");
		g.addEdge("C", "D");
		g.addEdge("D", "E");
		g.addEdge("E", "F");
		g.addEdge("F", "G");
		g.addEdge("A", "D");
		g.addEdge("C", "F");
		g.addEdge("A", "C");
		return true;
	}
}
