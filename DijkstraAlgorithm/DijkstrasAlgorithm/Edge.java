import org.json.simple.JSONObject;

public class Edge {
	public Node target;
	public Node source;
	private Integer id;
	private String name;
	private Integer numLanes = 0;
	private Integer speedLimit;
	private Float congestionFactor;
	private Integer length;
	private boolean isRushHour = false;
	
	// Done for you
	public Edge(
			int id, String name, int numLanes, int speedLimit, 
			float congestionFactor, int length, Node source, Node target) {
		
		this.id = id;
		this.name = name;
		this.numLanes = numLanes;
		this.speedLimit = speedLimit;
		this.congestionFactor = congestionFactor;
		this.length = length;
		this.source = source;
		this.target = target;
	}
	
	// Implemented for you:
	public void setIsRushHour(boolean isRushHour) {
		this.isRushHour = isRushHour;
	}
	
	
	public Float getWeight() {
		if(isRushHour) {
            return (float) ((length * congestionFactor) / (speedLimit * numLanes * 10.0));
        }else {
            return (float) (length / (speedLimit * numLanes * 10.0)); 
        }
	}
	
	// Implemented for you:
	public String toString() {
		String edgeString = this.id + "\t";
		edgeString += this.name + "\t";
		edgeString += (this.source != null) ? this.source.name + "\t" : "--\t";
		edgeString += (this.target != null) ? this.target.name + "\t" : "--\t";
		edgeString += this.getWeight() + "\t";
		edgeString += this.numLanes + "\t";
		edgeString += this.speedLimit + "\t";
		edgeString += this.congestionFactor + "\t\t";
		edgeString += this.length + "\t";
		
		return edgeString;
	}
	
	// converts a Java Course object to a list of JSON edge objects (to write to a file)
	@SuppressWarnings("unchecked")
	public JSONObject toJSON(boolean inTree) {
		
		JSONObject entry = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("target", this.target.id);
		data.put("source", this.source.id);
		data.put("weight", this.getWeight());
		data.put("inTree", inTree);
		data.put("name", this.name);
		data.put("id", this.id);
		entry.put("data", data);
		return entry;
	}

}
