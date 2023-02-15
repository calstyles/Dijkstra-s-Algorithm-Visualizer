import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Visualizer {
	
	private Graph graph;
	private HashMap<String, int[]> coordsLookup;
	
	public Visualizer(Graph graph) {
		this.graph = graph;
		// hardcoding the node's coords so 
		// it's easier to visualize between tests
		// ('cause they're always the same):
		coordsLookup = new HashMap<String, int[]>(); 
		coordsLookup.put("A", new int[]{100, 300});
		coordsLookup.put("B", new int[]{ 350, 100});
		coordsLookup.put("C", new int[]{ 350, 550});
		coordsLookup.put("D", new int[]{ 600, 300});
		coordsLookup.put("E", new int[]{ 600, 600});
		coordsLookup.put("F", new int[]{ 900, 450});
		coordsLookup.put("G", new int[]{ 600, 40});
		coordsLookup.put("H", new int[]{ 900, 30});
		coordsLookup.put("I", new int[]{ 900, 250});
		coordsLookup.put("J", new int[]{ 1000, 750});
	}
	
	private String getOutputFilePath(String fileName) {
		String dir = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        return dir + separator + "visualizer/" + fileName;
	}
	
	@SuppressWarnings("unchecked")
	public void save(String fileName) {
		JSONObject object = new JSONObject();
		JSONArray jsonNodes = new JSONArray();
		JSONArray jsonEdges = new JSONArray();
		for (Node node : this.graph.nodes) {
			JSONObject jsonNode = node.toJSON();
			JSONObject position = new JSONObject();
			int[] coords = coordsLookup.get(node.name);
			position.put("x", coords[0]);
			position.put("y", coords[1]);
			jsonNode.put("position", position);
			jsonNodes.add(jsonNode);
			if (node.p != null) {
//				System.out.println(node);
//				System.out.println(node.p);
//				System.out.println(node.p.getBackEdge(node));
				JSONObject jsonEdge = node.p.getBackEdge(node).toJSON(true);
				jsonEdges.add(jsonEdge);
			}
    	}
		this.appendBackgroundEdgesJSON(jsonEdges);
		object.put("nodes", jsonNodes);
		object.put("edges", jsonEdges);
		
        try (FileWriter file = new FileWriter(this.getOutputFilePath(fileName))) {
            file.write("const graphData = " + object.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	@SuppressWarnings("unchecked")
	private void appendBackgroundEdgesJSON(JSONArray currentEdges) {
		for (Edge edge : this.graph.edges) {
			boolean alreadyInThere = false;
			for(Object o : currentEdges) {
				JSONObject edgeJSON = (JSONObject)((JSONObject)o).get("data");
				Integer targetId = (Integer)edgeJSON.get("target");
				Integer sourceId = (Integer)edgeJSON.get("source");
				
				if (edge.target.id == targetId && edge.source.id == sourceId){
					alreadyInThere = true;
				}
				
			}

			if (!alreadyInThere) {
				currentEdges.add(edge.toJSON(false));
			}
		}
	}
}
