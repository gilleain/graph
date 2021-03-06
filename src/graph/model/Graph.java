package graph.model;

import java.util.List;

public interface Graph {

    int getVertexCount();
    
    int getEdgeCount();
    
    void makeEdge(int vertexI, int vertexJ);
    
    void addLabel(int vertex, String label);

    String getLabel(int i);

    List<Integer> getConnected(int vertexIndex);

    int getEdgeColor(int vertexIndex, int otherVertexIndex);

    int degree(int index);

}
