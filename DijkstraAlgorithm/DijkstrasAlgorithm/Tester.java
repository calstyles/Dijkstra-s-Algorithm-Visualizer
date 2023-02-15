import java.util.Scanner;

public class Tester {
	public static void main(String[] args) {
		Graph graph = JSONHelper.createGraphFromJSON();
		Visualizer vis = new Visualizer(graph);
		Node n1 = null;
		Node n2 = null;
		while(true) {
			Scanner scan = new Scanner(System.in);
			System.out.println("Testing Code Here...");
			while(true) {
				System.out.println("Insert a source node name: ");
				String input1 = scan.next();
				System.out.println("Insert a destination node name: ");
				String input2 = scan.next();
				boolean found1 = false;
				boolean found2 = false;
				for(Node node : graph.nodes) {
					if(input1.toUpperCase().equals(node.name)) {
						found1 = true;
						n1 = node;
					}
					
					if(input2.toUpperCase().equals(node.name)) {
						found2 = true;
						n2 = node;
					}
				}
				if(found1 && found2) {
					break;
				}else {
					System.out.println("Invalid source and destination nodes, please re-enter nodes in the list!");
					System.out.println();
				}
			}
			System.out.println("Is it rush hour? Y/N");
			boolean b3 = true;
			while(true) {
				String str1 = scan.next();
				str1 = str1.toUpperCase();
				if(str1.contains("Y")) {
					break;
				}else if(str1.contains("N")) {
					b3 = false;
					break;
				}else {
					System.out.println("Invalid input, please try again. Type Y/N");
				}
			}			
			graph.printDirections(n1, n2, b3);
			// Display Graph to the screen 
			System.out.println("Nodes:");
			graph.printNodes();
			if(b3) {
				System.out.println("Edges (Rush Hour):");
				graph.printEdges(true);
			}else{
				System.out.println("Edges (NOT Rush Hour):");
				graph.printEdges(false);
			}
			
			System.out.println("Outputting Graph to JavaScript file (open dijkstra.html in your web browser");
			vis.save("output.js");
			System.out.println("Continue? Y/N");
			while(true) {
				String answer = scan.next();
				if(answer.toUpperCase().contains("Y")) {
					break;
				}else if(answer.toUpperCase().contains("N")) {
					System.out.println("Program Finished");
					System.exit(0);
					break;
				}else {
					System.out.println("Invalid input, please try again. Type Y/N");
					continue;
				}
			}
		}
	}

}
