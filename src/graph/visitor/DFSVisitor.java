package graph.visitor;

import graph.model.GraphObject;
import graph.model.Vertex;

public interface DFSVisitor {
	
	public void visit(GraphObject g, Vertex v);

	public boolean seen(Vertex v);
	
	public void reset();

}
