package graph;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Graph implementation using adjacency lists (actually hashmaps for efficient retrieval).  
 * The graph is weighted (with non-negative integer weights only) and directional, and does not 
 * allow repeated edges or vertices.  The graph does allow a single null key.  
 * 
 * <p>Vertex is an internal class that is not visible outside the graph class.  The only 
 * method of interacting with the vertices is through various functions that make use of
 * keys.  As such, the terms "Vertex" and "Key" are sometimes used interchangeably within
 * the documentation.  
 * 
 * <p>The parameter type K is used as the key for each vertex.  The behavior of the class is
 * undefined if K is mutable and is modified.  
 *
 * @author Reed Simpson.  Based on work by Reed Simpson and Tim Snyder.  
 *
 * @param <K> - Vertex key type.  The key type should be immutable.  The behavior of 
 * the graph is undefined if K is mutable and is modified after being added to the graph.  
 */
public class Graph<K> implements Set<K> {
	protected HashMap<K,Vertex<K>> vertices = new LinkedHashMap<K,Vertex<K>>();
	protected int numberEdges=0;

	/**
	 * Adds a new vertex to the graph with the given key if it does not already contain a 
	 * vertex with that key.  
	 * @param key - The key to be added to the graph.
	 * @return true if the key was successfully added to the graph, false otherwise. 
	 */
	@Override
	public boolean add(K key){
		if(vertices.containsKey(key)) {
			return false;
		}else{
			vertices.put(key, new Vertex<K>(key));
			return true;
		}
	}
	/**
	 * Adds a new vertex to the graph.  Deprecated, use add(key) instead.  
	 * @param key - The key to be added to the graph.
	 * @return true if the key was successfully added to the graph, false otherwise. 
	 */
	@Deprecated
	public boolean addVertex(K key){
		return this.add(key);
	}

	/**
	 * Adds an edge to the graph from key1 to key2 with the given weight.
	 * @param key1 - The key of the vertex to gain the edge.
	 * @param key2 - The key of the vertex that the edge connects to.
	 * @param weight - The weight of the edge (must be non-negative).
	 * @return true if the edge was successfully added to the graph, false otherwise. 
	 */
	public boolean addEdge(K key1, K key2, int weight){
		if(weight<0){
			throw new IllegalArgumentException("Edge weight must be non-negative");
		}
		Vertex<K> startpoint = vertices.get(key1);
		Vertex<K> endpoint = vertices.get(key2);
		if(startpoint==null||endpoint==null){
			throw new IllegalArgumentException("key not found");
		}
		if(startpoint.addEdge(endpoint, weight)){
			numberEdges++;
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Adds an edge to the graph from key1 to key2 with a weight of 1.  
	 * @param key1 - The key of the vertex to gain the edge.
	 * @param key2 - The key of the vertex that the edge connects to.
	 * @return true if the edge was successfully added to the graph, false otherwise. 
	 */
	public boolean addEdge(K key1, K key2){
		return this.addEdge(key1, key2, 1);
	}

	/**
	 * Gets the weight of an existing edge from key1 to key2.  
	 * @param key1 - The key of the vertex that has the edge.  
	 * @param key2 - The key of the vertex that the edge connects to.  
	 * @return The weight of the edge, or -1 if the edge does not exist.  
	 */
	public int getEdgeWeight(K key1, K key2) {
		Vertex<K> v1 = vertices.get(key1);
		Vertex<K> v2 = vertices.get(key2);
		return this.getEdgeWeight(v1, v2);
	}
	/*
	 * Internal method.  Finds the edge weight between two vertices without having to 
	 * retrieve them using the HashMap.get method.  Not visible outside the class 
	 * because the Vertex type objects have no meaning outside the class.  
	 * @param v1 - The vertex that has the edge.  
	 * @param v2 - The vertex that the edge connects to.  
	 * @return The weight of the edge, or -1 if the edge does not exist.  
	 */
	private int getEdgeWeight(Vertex<K> v1,Vertex<K> key2){
		if(v1==null) return -1;
		else return v1.getEdgeWeight(key2);
	}

	/**
	 * Performs Dijkstra's Shortest Path algorithm to retrieve the shortest 
	 * distance between any two vertices of the graph.
	 * @param start - The key of the starting vertex of the path.
	 * @param finish - The key of the ending vertex of the path.  
	 * @return The distance from start to finish along existing edges, or -1 if no path 
	 * exists. 
	 */
	public int shortestDistance(K start, K finish){
		Integer distance = this.dijkstras(start, finish).distance;
		if(distance==null) return -1;
		else return distance;
	}
	/**
	 * Performs Dijkstra's Shortest Path algorithm to retrieve the shortest 
	 * path between any two vertices of the graph.
	 * @param start - The key of the starting vertex of the path.
	 * @param finish - The key of the ending vertex of the path.  
	 * @return The path from start to finish along existing edges in the form of a 
	 * LinkedList, or null if no path exists. 
	 */
	public LinkedList<K> shortestPath(K start, K finish){
		HashMap<K,K> previous = this.dijkstras(start, finish).previous;
		LinkedList<K> result = new LinkedList<K>();
		if(start.equals(finish)){
			result.add(start);
			return result;
		}else if(previous.get(finish)==null){
			return null;
		}
		K current = finish;
		while(previous.get(current)!=null){
			result.push(current);//adds the previous key to the beginning of the list
			current = previous.get(current);
		}
		result.push(current);
		return result;
	}
	/**
	 * Internal method which performs Dijkstra's Shortest Path algorithm for other 
	 * algorithms in the class.
	 * 
	 * <p> The algorithm starts by creating two HashMaps, one to keep track of the 
	 * path information and one to keep track of the distance information.  Then, 
	 * the algorithm creates a priority queue data structure to keep track of the 
	 * vertices which have been reached but not explored.  The algorithm then enters
	 * a loop, removing the element from the priority queue with the smallest distance
	 * on each iteration.  
	 * @param start - The key of the starting vertex of the path.
	 * @param finish - The key of the ending vertex of the path.  
	 * @return A wrapper object for an integer representing the distance of the path, 
	 * and a hashmap containing the information needed to reconstruct the path
	 */
	private PathWrapper<K> dijkstras(K start, K finish){
		//this hashmap is used to reconstruct a correct path post algorithm
		HashMap<K,K> previous = new HashMap<K,K>();
		//this hashmap is used to compare different paths to find a shortest one
		HashMap<K,Integer> distance = new HashMap<K,Integer>();

		//the starting vertex is added to the hashmap.  
		//null in this context indicates that this vertex is the beginning of the route
		previous.put(start, null);
		distance.put(start, 0);

		LinkedList<Vertex<K>> list = new LinkedList<Vertex<K>>();
		list.add(vertices.get(start));
		while(!list.isEmpty()){
			Iterator<Vertex<K>> iterator = list.iterator();
			Vertex<K> min = iterator.next();
			while(iterator.hasNext()){
				Vertex<K> v = iterator.next();
				Integer dist1 = distance.get(min.key);
				Integer dist2 = distance.get(v.key);
				if(dist2<dist1) min=v;
			}
			//break out of the loop early if the target vertex is the lowest distance
			if((min.key==null&&finish==null)||min.key.equals(finish)) break;
			list.remove(min);
			Set<Entry<Vertex<K>, Integer>> adj = min.getAdjacentVertices().entrySet();
			for(Entry<Vertex<K>, Integer> entry:adj){
				int newDist = distance.get(min.key)+entry.getValue();
				K key = entry.getKey().key;
				if(!distance.containsKey(key)){
					previous.put(key, min.key);
					distance.put(key, newDist);
					list.add(entry.getKey());
				}else if(newDist<distance.get(key)){
					previous.put(key, min.key);
					distance.put(key, newDist);
				}
			}
		}

		PathWrapper<K> result = new PathWrapper<K>();
		result.previous = previous;
		result.distance = distance.get(finish);
		return result;
	}

	/**
	 * Retrieves the number of vertices that have been added to the graph.
	 * @return The number of vertices in the graph as an int.  
	 */
	public int numberVertices() {
		return this.vertices.size();
	}

	/**
	 * Retrieves the number of edges that have been added to the graph.
	 * @return The number of edges in the graph as an int.  
	 */
	public int numberEdges() {
		return this.numberEdges;
	}

	/**
	 * Checks to see if the graph contains a vertex with the given key.  
	 * @param o - The key to check.
	 * @return true if the object is a key which is contained in the graph, false 
	 * otherwise.
	 */
	@Override
	public boolean contains(Object o) {
		return vertices.containsKey(o);
	}

	/**
	 * Finds the total weight of a given path along existing edges.
	 * @param path - A list of vertices.  The method traverses the vertices in order. 
	 * @return The sum total of the weights of all the edges in the path, or -1 if any 
	 * two adjoining vertices do not have an edge.  
	 */
	public int pathWeight(List<K> path){
		if(path==null) return -1;
		int result = 0;
		Iterator<K> iterator = path.iterator();
		Vertex<K> prev = vertices.get(iterator.next());
		while(iterator.hasNext()){
			Vertex<K> next = vertices.get(iterator.next());
			int weight = prev.getEdgeWeight(next);
			if(weight==-1) return -1;
			else result+=weight;
			prev=next;
		}
		return result;
	}
	/**
	 * Returns a string representation of the graph. The returned string has one line for 
	 * each Key in the graph. Each line consists of the Key value's string representation, 
	 * followed by a visual separator (the string " - ") followed by the string representation
	 * of each Key that is adjacent to the given Key with the weight of the connecting edge
	 * given in parenthesis. For example, a graph with two Keys "A" and "B" with an edge of 
	 * weight 1 connecting each of them to the other would return the following string:
	 * "A - B(1)\r\nB - A(1)"
	 * @return The string representation of the graph
	 */
	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		for(Entry<K, Vertex<K>> e:vertices.entrySet()){
			if(e.getKey()==null) result.append("null");
			else result.append(e.getKey().toString());
			result.append(" - ");
			Set<Entry<Vertex<K>, Integer>> adjacentvertices = e.getValue().getAdjacentVertices().entrySet();
			for(Entry<Vertex<K>, Integer> adj:adjacentvertices){
				result.append(adj.getKey().toString());
				result.append("("+adj.getValue()+")");
				result.append(", ");
			}
			int end = result.lastIndexOf(",");
			if(end>0) result.delete(result.lastIndexOf(","), result.length());
			result.append("\r\n");
		}
		return result.toString().trim();
	}

	@Override
	public boolean addAll(Collection<? extends K> c) {
		boolean result = false;
		for(K key:c){
			if(this.add(key)) result=true;
		}
		return result;
	}

	@Override
	public void clear() {
		this.vertices.clear();
		this.numberEdges=0;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for(Object o:c){
			if(!this.contains(o)) return false;
		}
		return true;
	}

	@Override
	public boolean isEmpty() {
		return this.vertices.size()==0;
	}

	@Override
	public Iterator<K> iterator() {
		return this.vertices.keySet().iterator();
	}

	@Override
	public boolean remove(Object key) {
		for(Vertex<K> v:this.vertices.values()){
			if(v.removeEdge(key)){
				this.numberEdges--;
			};
		}
		this.vertices.remove(key);
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		for(Object o:c){
			this.remove(o);
		}
		return true;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		for(Object o:this.vertices.keySet()){
			if(!c.contains(o)) this.remove(o);
		}
		return true;
	}

	@Override
	public int size() {
		return this.vertices.size();
	}

	@Override
	public Object[] toArray() {
		return this.vertices.keySet().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.vertices.keySet().toArray(a);
	}

	private static class Vertex<K>{
		public final K key;
		private Integer hashCode;
		private HashMap<Vertex<K>,Integer> adjacent;

		public Vertex(K key) {
			this.key = key;
			this.hashCode = null;
			this.adjacent=new LinkedHashMap<Vertex<K>,Integer>();
		}

		public HashMap<Vertex<K>, Integer> getAdjacentVertices(){
			return adjacent;
		}

		public boolean addEdge(Vertex<K> endpoint, int weight){
			return adjacent.put(endpoint, weight)==null;
		}

		public int getEdgeWeight(Vertex<K> endpoint) {
			Integer result = adjacent.get(endpoint);
			if(result==null) return -1;
			else return result;
		}

		public String toString(){
			if(key==null) return "null";
			else return this.key.toString();
		}

		public boolean removeEdge(Object key){
			return this.adjacent.remove(key)!=null;
		}
		
		public int hashCode(){
			if(this.hashCode==null){
				this.hashCode=key.hashCode();
			}
			return this.hashCode;
		}
		
		public boolean equals(Object o){
			if(o instanceof Graph.Vertex<?>){
				Vertex<?> v = (Vertex<?>) o;
				return this.key.equals(v.key);
			}else{
				return false;
			}
		}

	}

	/*
	 * A private class used to wrap an Integer and a HashMap for use with the Dijkstra's
	 * Algorithm method and associated methods.  Not visible externally and never returned
	 * by a public method.  
	 */
	private static class PathWrapper<K>{
		HashMap<K,K> previous;
		Integer distance;
	}

}
