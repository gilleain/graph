package model;

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
    
    private boolean useColors;
    
    private Map<Integer, List<Integer>> connectionTable;
    
    public GraphEquitablePartitionRefiner(
            Graph graph, Map<Integer, List<Integer>> connectionTable) {
        this(graph, false, connectionTable);
    }
    
    public GraphEquitablePartitionRefiner(
            Graph graph, boolean useColors, Map<Integer, List<Integer>> connectionTable) {
        this.graph = graph;
        this.useColors = useColors;
        this.connectionTable = connectionTable;
    }

    /* (non-Javadoc)
     * @see org.openscience.cdk.group.AbstractEquitablePartitionRefiner#getNumberOfVertices()
     */
    public int getNumberOfVertices() {
        return graph.getVertexCount();
    }

    @Override
    public int neighboursInBlock(Set<Integer> block, int vertexIndex) {
        int n = 0;
        List<Integer> connected;
        if (useColors) {
            // TODO : use conn table
            connected = graph.getSameColorConnected(vertexIndex);
        } else {
//            connected = graph.getConnected(vertexIndex);
            connected = connectionTable.get(vertexIndex);
            if (connected == null) return 0;
        }
        
        for (int i : connected) {
            if (block.contains(i)) {
                n++;
            }
        }
        return n;
    }

}
