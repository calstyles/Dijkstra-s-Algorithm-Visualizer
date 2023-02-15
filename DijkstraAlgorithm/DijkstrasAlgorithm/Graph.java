import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Graph {
	public List<Node> nodes;
	public List<Edge> edges;
	
	public Graph(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}
	
	private void initializeSingleSource(Graph graph, Node s) {
		for(Node v : graph.nodes) {
			v.d = Integer.MAX_VALUE;
			v.p = null;
		}
		s.d = 0;
	}
	
	private void relax(Node u, Node v) {
		if(u.d == Integer.MAX_VALUE) {
			return;
		}
		Edge edge = u.getBackEdge(v);
		if(v.d > (float) (u.d + edge.getWeight())) {
			v.d = (float) (u.d + edge.getWeight());
			v.p = u;
		}
	}
	
	// TODO: Implement
	public void doDijkstra(Node source, boolean isRushHour) {
		for(Edge edge : edges) {
			edge.setIsRushHour(isRushHour);
		}
		List<Node> temp = new LinkedList<>(this.nodes);
		initializeSingleSource(this, source);
		Queue<Node> pq = new PriorityQueue<>();
        pq.addAll(nodes);
        while(pq.size() != 0) {
        	Node u = pq.poll();
        	for(Edge v : u.adjList) {
        		relax(u, v.target);
        	}
        	temp.remove(u);
        	pq = new PriorityQueue<>(temp);
        }
	}
	

	
	// TODO: Implement
	public void printDirections(Node source, Node destination, boolean isRushHour) {
		doDijkstra(source, isRushHour);
		Node tempNode = destination;
		Stack<Node> st = new Stack<Node>();
		while(tempNode != source.p) {
			st.push(tempNode);
			tempNode = tempNode.p;
		}
		while(!st.isEmpty()) {
			Node n = st.pop();
			if(st.size() == 1) {
				System.out.println(n.name + " -> " + destination.name);
				break;
			}
			System.out.print(n.name + " -> ");
		}
		System.out.println("Shortest Path Distance: " + destination.d);
		System.out.println();
	}
	
	private void printDashes(int numDashes) {
		for(int i = 0; i < numDashes; i++) {
			System.out.print("-");
		}
		System.out.println();
		
	}
	// Implemented for you:
	public void printNodes() {
		System.out.println();
		printDashes(25);
		System.out.println("id\tname\tparent");
		printDashes(25);
		for (Node node: this.nodes) {
			System.out.println(node);
		}
		printDashes(25);
		System.out.println();
	}
	
	// Implemented for you:
	public void printEdges(boolean isRushHour) {
		System.out.println();
		printDashes(80);
		System.out.println("id\tname\tsource\ttarget\tweight\tlanes\tspeed\tcongestion\tlength");
		printDashes(80);
		for (Edge edge: this.edges) {
			edge.setIsRushHour(isRushHour);
			System.out.println(edge);
		}
		printDashes(80);
		System.out.println();
	}
}