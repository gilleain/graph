package graph.visitor;

import graph.model.VertexGraph;
import graph.model.Vertex;

public interface DFSVisitor {
	
	public void visit(VertexGraph g, Vertex v);

	public boolean seen(Vertex v);
	
	public void reset();

}
