package data;
import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
 * @author Reed Simpson.  
 *
 * @param <Point> - Vertex key type.  The key type should be immutable.  The behavior of 
 * the graph is undefined if K is mutable and is modified after being added to the graph.  
 */
public class AStarGraph implements Set<Point> {
	public static final int EUCLIDEAN_DISTANCE = 0;
	public static final int HEX_DISTANCE = 1;
	public static final int TAXICAB_DISTANCE = 2;
	public static final int CHESSBOARD_DISTANCE = 3;

	protected HashMap<Point,Vertex> vertices = new LinkedHashMap<Point,Vertex>();
	protected int numberEdges=0;
	protected double distanceFactor;
	private EdgeWeightComparator comparator;
	private int distanceMetric;

	public AStarGraph(double distance) {
		this.distanceFactor=distance;
		this.distanceMetric = HEX_DISTANCE;
	}

	public AStarGraph() {
		new AStarGraph(1.0);
	}

	/**
	 * Adds a new vertex to the graph with the given key if it does not already contain a 
	 * vertex with that key.  
	 * @param key - The key to be added to the graph.
	 * @return true if the key was successfully added to the graph, false otherwise. 
	 */
	@Override
	public boolean add(Point key){
		if(vertices.containsKey(key)) {
			return false;
		}else{
			vertices.put(key, new Vertex(key));
			return true;
		}
	}

	/**
	 * Adds an edge to the graph from key1 to key2 with the given weight.
	 * @param key1 - The key of the vertex to gain the edge.
	 * @param key2 - The key of the vertex that the edge connects to.
	 * @param weight - The weight of the edge (must be non-negative).
	 * @return true if the edge was successfully added to the graph, false otherwise. 
	 */
	public boolean addEdge(Point key1,Point key2, int weight){
		if(weight<0){
			throw new IllegalArgumentException("Edge weight must be non-negative");
		}
		Vertex startpoint = vertices.get(key1);
		Vertex endpoint = vertices.get(key2);
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
	public boolean addEdge(Point key1,Point key2){
		return this.addEdge(key1, key2, 1);
	}

	/**
	 * Gets the weight of an existing edge from key1 to key2.  
	 * @param key1 - The key of the vertex that has the edge.  
	 * @param key2 - The key of the vertex that the edge connects to.  
	 * @return The weight of the edge, or -1 if the edge does not exist.  
	 */
	public int getEdgeWeight(Point key1,Point key2) {
		Vertex v1 = vertices.get(key1);
		Vertex v2 = vertices.get(key2);
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
	private int getEdgeWeight(Vertex v1,Vertex v2){
		if(v1==null) return -1;
		else {
			int result = v1.getEdgeWeight(v2);
			if(result==-1&&this.comparator!=null) {
				result = this.comparator.compare(v1.key, v2.key);
			}
			return result;
		}
	}

	/**
	 * Performs Dijkstra's Shortest Path algorithm to retrieve the shortest 
	 * distance between any two vertices of the graph.
	 * @param start - The key of the starting vertex of the path.
	 * @param finish - The key of the ending vertex of the path.  
	 * @return The distance from start to finish along existing edges, or -1 if no path 
	 * exists. 
	 */
	public int shortestDistance(Point start, Point finish){
		HashMap<Point,Point> previous = new HashMap<Point,Point>();
		Integer distance = this.dijkstras(previous,start).get(finish);
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
	public LinkedList<Point> shortestPath(Point start, Point finish){
		HashMap<Point,Point> previous = new HashMap<Point,Point>();
		this.dijkstras(previous,start);
		LinkedList<Point> result = new LinkedList<Point>();
		if(start.equals(finish)){
			result.add(start);
			return result;
		}else if(previous.get(finish)==null){
			return null;
		}
		Point current = finish;
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
	public HashMap<Point,Integer> dijkstras(HashMap<Point,Point> previous,Point start){
		//this hashmap is used to reconstruct a correct path post algorithm
		if(previous==null) previous = new HashMap<Point,Point>();
		//this hashmap is used to compare different paths to find a shortest one
		HashMap<Point,Integer> distance = new HashMap<Point,Integer>();

		//the starting vertex is added to the hashmap.  
		//null in this context indicates that this vertex is the beginning of the route
		previous.put(start, null);
		distance.put(start, 0);

		LinkedList<Vertex> list = new LinkedList<Vertex>();
		list.add(vertices.get(start));
		while(!list.isEmpty()){
			Iterator<Vertex> iterator = list.iterator();
			Vertex min = iterator.next();
			while(iterator.hasNext()){
				Vertex v = iterator.next();
				Integer dist1 = distance.get(min.key);
				Integer dist2 = distance.get(v.key);
				if(dist2<dist1) min=v;
			}
			list.remove(min);
			Set<Entry<Vertex, Integer>> adj = min.getAdjacentVertices().entrySet();
			for(Entry<Vertex, Integer> entry:adj){
				int newDist = distance.get(min.key)+entry.getValue();
				Point key = entry.getKey().key;
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
		return distance;
	}


	public HashMap<Point, Integer> dijkstras(Point start){
		return dijkstras(null, start);
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
	public int pathWeight(List<Point> path){
		if(path==null) return -1;
		int result = 0;
		Iterator<Point> iterator = path.iterator();
		Vertex prev = vertices.get(iterator.next());
		while(iterator.hasNext()){
			Vertex next = vertices.get(iterator.next());
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
		for(Entry<Point, Vertex> e:vertices.entrySet()){
			if(e.getKey()==null) result.append("null");
			else result.append(e.getKey().toString());
			result.append(" - ");
			Set<Entry<Vertex, Integer>> adjacentvertices = e.getValue().getAdjacentVertices().entrySet();
			for(Entry<Vertex, Integer> adj:adjacentvertices){
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
	public boolean addAll(Collection<? extends Point> c) {
		boolean result = false;
		for(Point key:c){
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
	public Iterator<Point> iterator() {
		return this.vertices.keySet().iterator();
	}

	@Override
	public boolean remove(Object key) {
		for(Vertex v:this.vertices.values()){
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
	
	public void setDistanceMetric(int metric) {
		this.distanceMetric = metric;
	}

	public double dist(Point p1,Point p2) {
		if(distanceMetric== TAXICAB_DISTANCE) {
			int dx = p2.x-p1.x;
			int dy = p2.y-p1.y;
			return Math.abs(dx+dy)*this.distanceFactor;
		}else if(distanceMetric== HEX_DISTANCE) {
			int dx = p2.x-p1.x;
			int dy = p2.y-p1.y;
			double distance;
			if(dx*dy>=0) {
				distance = Math.abs(dx+dy);
			}else {
				dx=Math.abs(dx);
				dy=Math.abs(dy);
				distance = Math.max(dx, dy);
			}
			return distance*this.distanceFactor;
		}else if(distanceMetric== CHESSBOARD_DISTANCE) {
			int dx = Math.abs(p2.x-p1.x);
			int dy = Math.abs(p2.y-p1.y);
			return Math.max(dx, dy)*this.distanceFactor;
		} else {
			int dx = p2.x-p1.x;
			int dy = p2.y-p1.y;
			return Math.sqrt(dx*dx+dy*dy)*this.distanceFactor;
		}
	}
	public int shortestPath(Point start, Point finish,LinkedList<Point> list){
		HashMap<Point,Point> previous = new HashMap<Point,Point>();
		int result = aStar(previous, start, finish);
		if(start.equals(finish)){
			list.add(start);
			//return list;
		}else if(previous.get(finish)==null){
			//return null;
		}else {
			Point current = finish;
			while(previous.get(current)!=null){
				list.push(current);//adds the previous key to the beginning of the list
				current = previous.get(current);
			}
			list.push(current);
			//return list;
		}
		return result;
	}


	public Integer aStar(HashMap<Point,Point> previous,Point start, Point finish){
		//this hashmap is used to reconstruct a correct path post algorithm
		if(previous==null) previous = new HashMap<Point,Point>();
		//this hashmap is used to compare different paths to find a shortest one
		HashMap<Point,Integer> distance = new HashMap<Point,Integer>();

		//the starting vertex is added to the hashmap.  
		//null in this context indicates that this vertex is the beginning of the route
		previous.put(start, null);
		distance.put(start, 0);

		LinkedList<Vertex> list = new LinkedList<Vertex>();
		list.add(vertices.get(start));
		while(!list.isEmpty()){
			Iterator<Vertex> iterator = list.iterator();
			Vertex min = iterator.next();
			while(iterator.hasNext()){
				Vertex v = iterator.next();
				double dist1 = distance.get(min.key)+dist(min.key,finish);
				double dist2 = distance.get(v.key)+dist(v.key,finish);
				if(dist2<dist1) min=v;
			}
			//break out of the loop early if the target vertex is the lowest distance
			if((min.key==null&&finish==null)||min.key.equals(finish)) break;
			list.remove(min);
			Set<Entry<Vertex, Integer>> adj = min.getAdjacentVertices().entrySet();
			for(Entry<Vertex, Integer> entry:adj){
				int newDist = distance.get(min.key)+entry.getValue();
				Point key = entry.getKey().key;
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
		return distance.get(finish);
	}

	public Set<Point> getAdjacencyList(Point key){
		HashSet<Point> result = new HashSet<Point>();
		Vertex vertex = vertices.get(key);
		if(vertex!=null&&vertex.getAdjacentVertices()!=null&&vertex.getAdjacentVertices().keySet()!=null) {
			for(Vertex v:vertex.getAdjacentVertices().keySet()) {
				result.add(v.key);
			}
		}
		return result;
	}

	/**
	 * an interface for setting a way to get the edge weight between verticies without manually adding each edge
	 * If an edgeWeightComparator is set, manually added edges will be checked first, then the EdgeWeightComparator will be checked
	 * only if an edge is not found
	 */
	public static interface EdgeWeightComparator{
		public int compare(Point p1,Point p2);
		public Iterable<Point> getAdjacentVertices(Point p);
	}

	public void addEdgeWeightcomparator(EdgeWeightComparator comparator) {
		this.comparator = comparator;
	}

	private class Vertex{
		public final Point key;
		private Integer hashCode;
		private HashMap<Vertex,Integer> adjacent;

		public Vertex(Point key) {
			this.key = key;
			this.hashCode = null;
			this.adjacent=new LinkedHashMap<Vertex,Integer>();
		}

		public HashMap<Vertex, Integer> getAdjacentVertices(){
			if(comparator!=null) {
				for(Point e:comparator.getAdjacentVertices(key)) {
					Vertex v = vertices.get(e);
					int weight = comparator.compare(key, e);
					if(!adjacent.containsKey(v) && weight>=0) {
						adjacent.put(v, weight);
					}
				}
			}
			return adjacent;
		}

		public boolean addEdge(Vertex endpoint, int weight){
			return adjacent.put(endpoint, weight)==null;
		}

		public int getEdgeWeight(Vertex endpoint) {
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
			if(o instanceof AStarGraph.Vertex){
				Vertex v = (Vertex) o;
				return this.key.equals(v.key);
			}else{
				return false;
			}
		}

	}


}
