package graph.model;

import java.util.List;

import signature.AbstractVertexSignature;
import signature.ColoredTree;

public class VertexSignature extends AbstractVertexSignature {
    
    private IntGraph graph;
    
    public VertexSignature(int vertexIndex, IntGraph graph) {
        this.graph = graph;
        super.createMaximumHeight(vertexIndex, graph.getVertexCount());
    }
    
    public VertexSignature(int vertexIndex, IntGraph graph, int height) {
        this.graph = graph;
        super.create(vertexIndex, graph.getVertexCount(), height);
    }

    @Override
    protected int getIntLabel(int vertexIndex) {
        return 0;
    }

    @Override
    protected String getVertexSymbol(int vertexIndex) {
        return ".";
    }

    @Override
    protected int[] getConnected(int vertexIndex) {
        List<Integer> connectedList = graph.getConnected(vertexIndex); 
        int[] connected = new int[connectedList.size()];
        for (int i = 0; i < connectedList.size(); i++) {
            connected[i] = connectedList.get(i);
        }
        return connected;
    }

    @Override
    protected String getEdgeLabel(int vertexIndex, int otherVertexIndex) {
        // XXX - for some odd reason, just using the string value of the order doesn't work...
        switch (graph.getEdge(vertexIndex, otherVertexIndex).o) {
            case 1  : return "-";
            case 2  : return "=";
            default : return "";
        }
    }
    
    public static VertexSignature fromString(String signatureString) {
        ColoredTree tree = AbstractVertexSignature.parse(signatureString);
        GraphBuilder builder = new GraphBuilder();
        builder.makeFromColoredTree(tree);
        IntGraph g = builder.getProduct();
        GraphSignature graphSig = new GraphSignature(g);
        return (VertexSignature) graphSig.signatureForVertex(0);
    }

    @Override
    protected int convertEdgeLabelToColor(String label) {
        if (label.equals("-")) return 1;
        if (label.equals("=")) return 2;
        else return 0;
    }

}
