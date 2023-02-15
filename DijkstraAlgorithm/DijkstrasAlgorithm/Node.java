import java.util.LinkedList;

import org.json.simple.JSONObject;

public class Node implements Comparable<Node>{
	public Integer id;
	public String name;
	public Node p;
	public float d;
	public LinkedList<Edge> adjList = new LinkedList<Edge>();

	
	public Node(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	// TODO: Implement
	public void addEdge(Edge edge) {
		this.adjList.add(edge);
	}
	
	// TODO: Implement
	public Edge getBackEdge(Node node) {
		for(Edge e : adjList) {
			if(e.target.equals(node)) {
				return e;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject entry = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("id", this.id);
		data.put("distance", this.d);
		data.put("label", this.name);
		entry.put("data", data);
		return entry;
	}
		
	
	// Implemented for you:
	public String toString() {
		String edgeString = this.id + "\t";
		edgeString += this.name + "\t";
		edgeString += (this.p != null) ? this.p.name : '*';
		return edgeString;
	}

	@Override
	public int compareTo(Node o) {
		if (this.d == o.d) {
			return 0;
		} else if (this.d > o.d) {
			return 1;
		}
		return -1;
	}

}
