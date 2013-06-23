package graph.group;

import graph.model.Graph;
import group.Permutor;

import java.util.Iterator;


public class GraphPermutor extends Permutor implements Iterator<Graph> {
    
    private Graph graph;

    public GraphPermutor(Graph graph) {
        super(graph.getVertexCount());
        this.graph = graph;
    }

    public Graph next() {
        return graph.getPermutedGraph(getNextPermutation());
    }
    
    public void remove() {
        //
    }

}
