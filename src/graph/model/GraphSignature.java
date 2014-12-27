package graph.model;

import signature.AbstractGraphSignature;

public class GraphSignature extends AbstractGraphSignature {
    
    private Graph graph;
    
    public GraphSignature(Graph graph) {
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
    public VertexSignature signatureForVertex(int vertexIndex) {
        return new VertexSignature(vertexIndex, graph);
    }
    
    public VertexSignature signatureForVertex(int vertexIndex, int height) {
        return new VertexSignature(vertexIndex, graph, height);
    }

	public Graph getGraph() {
		return this.graph;
	}

    public String getGraphSignature() {
        return super.getGraphSignature();
    }
	
}
