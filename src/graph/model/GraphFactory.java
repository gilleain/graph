package graph.model;

public class GraphFactory {
    
    public static Graph cycle(int n) {
        return GraphFactory.cycle(0, n);
    }
    
    public static Graph cycle(int s, int n) {
        assert n > 2;   // ?
        Graph g = new Graph();
        int m = s + n;
        for (int i = s; i < m - 1; i++) {
            g.makeEdge(i, i + 1);
        }
        g.makeEdge(s, m - 1);
        return g;
    }
    
    public static Graph cube() {
        Graph graph = new Graph();
        graph.makeMultipleEdges(0, 1, 2, 4);
        graph.makeMultipleEdges(1, 3, 5);
        graph.makeMultipleEdges(2, 3, 7);
        graph.makeMultipleEdges(3, 6);
        graph.makeMultipleEdges(4, 5, 7);
        graph.makeMultipleEdges(5, 6);
        graph.makeMultipleEdges(6, 7);
        
        return graph;
    }
    
    public static Graph nPrism(int n) {
        Graph cycleA = GraphFactory.cycle(0, n);
        Graph cycleB = GraphFactory.cycle(n, n);
        cycleA.edges.addAll(cycleB.edges);
        for (int i = 0; i < n; i++) {
            cycleA.makeEdge(i, i + n);
        }
        return cycleA;
    }
    
}
