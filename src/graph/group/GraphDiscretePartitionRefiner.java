package graph.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import graph.model.Graph;
import group.AbstractDiscretePartitionRefiner;
import group.IEquitablePartitionRefiner;
import group.Partition;
import group.Permutation;
import group.PermutationGroup;


/**
 * Test implementation of a discrete partition refiner for simple graphs.
 * 
 * @author maclean
 *
 */
public class GraphDiscretePartitionRefiner extends AbstractDiscretePartitionRefiner {
    
    private Graph graph;
    
    private boolean checkForDisconnected;
    
    private Map<Integer, List<Integer>> connectionTable;
    
    public GraphDiscretePartitionRefiner() {
        this(false);
    }
    
    public GraphDiscretePartitionRefiner(boolean checkForDisconnected) {
        this.checkForDisconnected = checkForDisconnected;
    }
    
    public void setup(Graph graph) {
        if (checkForDisconnected) {
            connectionTable = makeCompactConnectionTable(graph);
        } else {
            connectionTable = graph.getConnectionTable();
        }
        int n = graph.getVertexCount();
        this.graph = graph;
        PermutationGroup group = new PermutationGroup(new Permutation(n));
        IEquitablePartitionRefiner refiner = new GraphEquitablePartitionRefiner(graph, connectionTable);
        setup(group, refiner);
    }
    
    private void setup(Graph graph, PermutationGroup group) {
        IEquitablePartitionRefiner refiner = new GraphEquitablePartitionRefiner(graph, connectionTable);
        setup(group, refiner);
    }

    public boolean isCanonical(Graph graph) {
        refine(graph);
        return firstIsIdentity();
    }
    
    public void refine(Graph graph) {
        setup(graph);
        refine(Partition.unit(graph.getVertexCount()));
    }
    
    public boolean isCanonical(Graph graph, Partition initialPartition) {
        setup(graph);
        refine(initialPartition);
        return firstIsIdentity();
    }
    
    public PermutationGroup getAutomorphismGroup(Graph graph, Partition partition) {
        setup(graph);
        refine(partition);
        return getAutomorphismGroup();
    }
    
    public PermutationGroup getAutomorphismGroup(Graph graph) {
        setup(graph);
        int n = graph.getVertexCount();
        Partition unit = Partition.unit(n);
        refine(unit);
        return getAutomorphismGroup();
    }
    
    public PermutationGroup getAutomorphismGroup(Graph graph, PermutationGroup group) {
        setup(graph, group);
        refine(Partition.unit(graph.getVertexCount()));
        return getAutomorphismGroup();
    }
    
    @Override
    public int getVertexCount() {
        return graph.getVertexCount();
    }
    
    public Map<Integer, List<Integer>> makeCompactConnectionTable(Graph graph) {
        List<List<Integer>> table = new ArrayList<List<Integer>>();
        int tableIndex = 0;
        int count = graph.getVertexCount();
        int[] indexMap = new int[count];
        for (int i = 0; i < count; i++) {
            List<Integer> connected = graph.getConnected(i);
            if (connected != null && connected.size() > 0) {
                table.add(connected);
                indexMap[i] = tableIndex;
                tableIndex++;
            }
        }
        Map<Integer, List<Integer>> shortTable =  new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < table.size(); i++) {
            List<Integer> originalConnections = table.get(i);
            List<Integer> mappedConnections = new ArrayList<Integer>();
            for (int j : originalConnections) {
                mappedConnections.add(indexMap[j]);
            }
            shortTable.put(i, mappedConnections);
        }
        return shortTable;
    }

    @Override
    public int getConnectivity(int vertexI, int vertexJ) {
        if (connectionTable.containsKey(vertexI) && connectionTable.get(vertexI).contains(vertexJ)) {
            return 1;
        } else {
            return 0;
        }
    }
}
