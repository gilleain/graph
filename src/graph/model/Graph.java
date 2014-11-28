package graph.model;

public interface Graph {

    int getVertexCount();
    
    void makeEdge(int vertexI, int vertexJ);
    
    void addLabel(int vertex, String label);

    String getLabel(int i);

}
