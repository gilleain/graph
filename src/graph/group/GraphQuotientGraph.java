package graph.group;

import graph.model.IntGraph;
import group.Partition;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

/**
 * The quotient graph (Q) of a graph (G) has a vertex for each equivalence class
 * of vertices in G, and an edge in Q for every equivalence class of edges in G
 * (?I think..).
 * 
 * @author maclean
 * 
 */

public class GraphQuotientGraph  {
    
    private class Vertex {
        
        public List<Integer> members;
        
        public int equivalenceClass;
        
        public Vertex(List<Integer> members, int equivalenceClass) {
            this.members = members;
            this.equivalenceClass = equivalenceClass;
        }
        
        public String toString() {
            return "<" + equivalenceClass + "> " + members;
        }
    }
    
    private class Edge {
        
        public int count;
        
        public int vertexIndexA;
        
        public int vertexIndexB;
        
        public Edge(int vertexIndexA, int vertexIndexB, int count) {
            this.vertexIndexA = vertexIndexA;
            this.vertexIndexB = vertexIndexB;
            this.count = count;
        }
        
        public boolean isLoop() {
            return vertexIndexA == vertexIndexB;
        }
        
        public boolean isBetween(int i, int j) {
            return (this.vertexIndexA == i 
                        && this.vertexIndexB == j)
               ||  (this.vertexIndexA == j 
                       && this.vertexIndexB == i);
        }
        
        public String toString() {
            return vertexIndexA + "-" + vertexIndexB + "(" + count + ")";
        }
    }
    
    private List<Vertex> vertices;
    
    private List<Edge> edges;
    
    private IntGraph g;
    
    public GraphQuotientGraph(IntGraph g) {
        this.g = g;
        vertices = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
    }
    
    public boolean isConnected(int i, int j) {
        return g.isConnected(i, j);
    }
    
    public int getVertexCount() {
        return vertices.size();
    }
    
    public int getEdgeCount() {
        return edges.size();
    }
    
    public int getEdgeMultiplicity(int i, int j) {
        for (Edge e : edges) {
            if (e.isBetween(i, j)) {
                return e.count;
            }
        }
        return -1;
    }
    
    public int numberOfLoopEdges() {
        int loopEdgeCount = 0;
        for (Edge e : edges) {
            if (e.isLoop()) {
                loopEdgeCount++;
            }
        }
        return loopEdgeCount;
    }
    
    public void construct(Partition symmetryPartition) {
        // make the vertices from the symmetry classes
        for (int i = 0; i < symmetryPartition.size(); i++) {
            SortedSet<Integer> symmetryClass = symmetryPartition.getCell(i);
            List<Integer> members = new ArrayList<Integer>();
            for (int e : symmetryClass) {
                members.add(e);
            }
            vertices.add(new Vertex(members, i));
        }
        
        // compare all vertices (classwise) for connectivity
        List<Edge> visitedEdges = new ArrayList<Edge>();
        for (int i = 0; i < symmetryPartition.size(); i++) {
            SortedSet<Integer> symmetryClass = symmetryPartition.getCell(i);
            for (int j = i; j < symmetryPartition.size(); j++) {
                SortedSet<Integer> otherSymmetryClass = symmetryPartition.getCell(j);
                int totalCount = 0;
                for (int x : symmetryClass) {
                    int countForX = 0;
                    for (int y : otherSymmetryClass) {
                        if (x == y)
                            continue;
                        if (isConnected(x, y) && !inVisitedEdges(x, y, visitedEdges)) {
                            countForX++;
                            visitedEdges.add(new Edge(x, y, 0));
                        }
                    }
                    totalCount += countForX;
                }
                System.out.println(symmetryClass + " x " + otherSymmetryClass + " = " + totalCount);
                if (totalCount > 0) {
                    edges.add(new Edge(i, j, totalCount));
                }
            }
        }
    }
    
    private boolean inVisitedEdges(int x, int y, List<Edge> visitedEdges) {
        for (Edge edge : visitedEdges) {
            if (edge.isBetween(x, y)) {
                return true;
            }
        }
        return false;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Vertex vertex : vertices) {
            buffer.append(vertex).append(" ");
        }
        buffer.append(edges);
        return buffer.toString();
    }
}
