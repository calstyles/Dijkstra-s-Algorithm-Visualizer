import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONHelper {
	private static String getInputFilePath() {
		// Windows users: make sure you're in the src directory. If
        // not, delete the "src" below:
        
		String dir = System.getProperty("user.dir");
        String delimiter = System.getProperty("file.separator");
        String directories[] = {dir, "road-data.json"};
        return String.join(delimiter, directories);
	}
	
	private static List<Node> mapToList(HashMap<Integer, Node> map) {
		List<Node> nodes = new ArrayList<Node>(map.values());
		return nodes;
	}
	
	public static Graph createGraphFromJSON() {
		// loads the graph from the JSON file (if it exists):
		String filePath = getInputFilePath();
		File tempFile = new File(filePath);
		if (!tempFile.exists()) {
			System.out.println("Can't find road-data.json file");
			return null;
		}
		
		HashMap<Integer, Node> nodeLookup = new HashMap<Integer, Node>();
		List<Edge> edges = new ArrayList<Edge>();
		JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader(filePath)) {

        		JSONObject data = (JSONObject)parser.parse(reader);
        		JSONArray nodesJSON = (JSONArray)data.get("nodes");
      
        		for (Object nodeJSON : nodesJSON) {
        			Node node = parseNode((JSONObject)nodeJSON);
        			nodeLookup.put(node.id, node);
        		}
        		JSONArray edgesJSON = (JSONArray)data.get("edges");
        		for (Object edgeJSON : edgesJSON) {
        			Edge edge = parseEdge((JSONObject)edgeJSON, nodeLookup);
        			edges.add(edge);
        			edge.source.addEdge(edge);
        		}
        		
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
			e.printStackTrace();
            return null;
		}

		return new Graph(mapToList(nodeLookup), edges);
	}
	
	public static Node parseNode(JSONObject obj) {
		obj = (JSONObject)obj.get("data");
		Integer id = ((Long)obj.get("id")).intValue();
		String name = (String)obj.get("name");
//		System.out.println(
//			"TODO: Convert to a Node:\n" + 
//			"     id=" + id + ", name=" + name
//		);
		return new Node(id, name);
	}
	
	public static Edge parseEdge(JSONObject obj, HashMap<Integer, Node> nodeLookup) {
		obj = (JSONObject)obj.get("data");
		Integer id = ((Long)obj.get("id")).intValue();
		String name = (String)obj.get("name");
		Integer lanes = ((Long)obj.get("lanes")).intValue();
		Integer speed = ((Long)obj.get("speed")).intValue();
		float congestion = ((Double)obj.get("congestion")).floatValue();
		Integer length = ((Long)obj.get("length")).intValue();
		Integer sourceId = ((Long)obj.get("source")).intValue();
		Integer targetId = ((Long)obj.get("target")).intValue();
		Node sourceNode = nodeLookup.get(sourceId);
		Node targetNode = nodeLookup.get(targetId);
//		System.out.println(
//			"TODO: Convert to an Edge: \n" + 
//		    "     id=" + id +  
//		    ", name=(" + name + ")" + 
//		    ", lanes=" + lanes + 
//			", speed=" + speed +
//			", congestion=" + congestion +
//			", length=" + length  +
//			", source=" + sourceId  +
//			", target=" + targetId  
//		);
		return new Edge(id, name, lanes, speed, congestion, length, sourceNode, targetNode);
	}
}
