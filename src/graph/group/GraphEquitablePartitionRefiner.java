package graph.group;

import graph.model.Graph;
import group.AbstractEquitablePartitionRefiner;
import group.IEquitablePartitionRefiner;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Implementation of an abstract equitable partition refiner for simple graphs.
 * 
 * @author maclean
 *
 */
public class GraphEquitablePartitionRefiner extends AbstractEquitablePartitionRefiner 
                                       implements IEquitablePartitionRefiner {
    
    private Graph graph;
    
    private Map<Integer, List<Integer>> connectionTable;
    
    public GraphEquitablePartitionRefiner(Graph graph, Map<Integer, List<Integer>> connectionTable) {
        this.graph = graph;
        this.connectionTable = connectionTable;
    }

    public int getNumberOfVertices() {
        return graph.getVertexCount();
    }

    @Override
    public int neighboursInBlock(Set<Integer> block, int vertexIndex) {
        int n = 0;
        List<Integer> connected;
        connected = connectionTable.get(vertexIndex);
        if (connected == null) return 0;
        
        for (int i : connected) {
            if (block.contains(i)) {
                n++;
            }
        }
        return n;
    }

}
