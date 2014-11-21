package graph.group;

import graph.model.IntGraph;
import group.Permutor;

import java.util.Iterator;


public class GraphPermutor extends Permutor implements Iterator<IntGraph> {
    
    private IntGraph graph;

    public GraphPermutor(IntGraph graph) {
        super(graph.getVertexCount());
        this.graph = graph;
    }

    public IntGraph next() {
        return graph.getPermutedGraph(getNextPermutation());
    }
    
    public void remove() {
        //
    }

}
