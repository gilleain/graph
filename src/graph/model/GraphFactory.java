package graph.model;

public class GraphFactory {
    
    public static IntGraph star(int n) {
        IntGraph g = new IntGraph();
        for (int i = 1; i < n; i++) {
            g.makeEdge(0, i);
        }
        return g;
    }
    
    public static IntGraph cycle(int n) {
        return GraphFactory.cycle(0, n);
    }
    
    public static IntGraph cycle(int s, int n) {
        assert n > 2;   // ?
        IntGraph g = new IntGraph();
        int m = s + n;
        for (int i = s; i < m - 1; i++) {
            g.makeEdge(i, i + 1);
        }
        g.makeEdge(s, m - 1);
        return g;
    }
    
    public static IntGraph cube() {
        IntGraph graph = new IntGraph();
        graph.makeMultipleEdges(0, 1, 2, 4);
        graph.makeMultipleEdges(1, 3, 5);
        graph.makeMultipleEdges(2, 3, 7);
        graph.makeMultipleEdges(3, 6);
        graph.makeMultipleEdges(4, 5, 7);
        graph.makeMultipleEdges(5, 6);
        graph.makeMultipleEdges(6, 7);
        
        return graph;
    }
    
    public static IntGraph nPrism(int n) {
        IntGraph cycleA = GraphFactory.cycle(0, n);
        IntGraph cycleB = GraphFactory.cycle(n, n);
        cycleA.edges.addAll(cycleB.edges);
        for (int i = 0; i < n; i++) {
            cycleA.makeEdge(i, i + n);
        }
        return cycleA;
    }
    
}
