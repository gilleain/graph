package graph.model;

import signature.AbstractGraphSignature;
import signature.AbstractVertexSignature;

public class GraphSignature extends AbstractGraphSignature {
    
    private IntGraph graph;
    
    public GraphSignature(IntGraph graph) {
        this.graph = graph;
    }

    @Override
    protected int getVertexCount() {
        return graph.getVertexCount();
    }

    @Override
    public String signatureStringForVertex(int vertexIndex) {
        return signatureForVertex(vertexIndex).toCanonicalString();
    }

    @Override
    public String signatureStringForVertex(int vertexIndex, int height) {
        return signatureForVertex(vertexIndex, height).toCanonicalString();
    }

    @Override
    public AbstractVertexSignature signatureForVertex(int vertexIndex) {
        return new VertexSignature(vertexIndex, graph);
    }
    
    public AbstractVertexSignature signatureForVertex(int vertexIndex, int height) {
        return new VertexSignature(vertexIndex, graph, height);
    }

	public IntGraph getGraph() {
		return this.graph;
	}
	
}
