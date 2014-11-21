package graph.model;

import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.List;

public class Block extends VertexGraph {

	public Block() {
		super(new ArrayList<Vertex>());
	}
	
	public Block(List<Vertex> vertices) {
		super(vertices);
	}
	
	public Block(List<Vertex> vertices, List<Edge> edges) {
		super(vertices, edges);
	}

	public Block(int i) {
		super(i);
	}
	
	public Block(IntGraph g) {
	    super(g);
	}
	
	public Block(Block other) {
	    super(new ArrayList<Vertex>(other.vertices), new ArrayList<Edge>(other.edges));
	}

}
