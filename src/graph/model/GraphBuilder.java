package graph.model;

import signature.AbstractGraphBuilder;

public class GraphBuilder extends AbstractGraphBuilder {
    
    private IntGraph product;
    
    public GraphBuilder() {
        this.product = null;
    }

    @Override
    public void makeGraph() {
        this.product = new IntGraph();
    }

    @Override
    public void makeVertex(String label) {
        product.makeVertex();
    }

    @Override
    public void makeEdge(int vertexIndex1, int vertexIndex2,
            String vertexSymbol1, String vertexSymbol2, String edgeLabel) {
        product.makeEdge(vertexIndex1, vertexIndex2);
    }
    
    public IntGraph getProduct() {
        return product;
    }

}
