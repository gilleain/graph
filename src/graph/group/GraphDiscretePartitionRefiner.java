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
import group.SSPermutationGroup;


/**
 * Test implementation of a discrete partition refiner for simple graphs.
 * 
 * @author maclean
 *
 */
public class GraphDiscretePartitionRefiner extends AbstractDiscretePartitionRefiner {
    
    private Graph graph;
    
    /**
     * Use colors in the refinement to equitable partition 
     */
    private boolean useColorsInEquitable;
    
    /**
     * Use vertex colors in generating automorphisms
     */
//    private boolean useVertexColorsInAut;	//TODO : remove?
    
    private boolean checkForDisconnected;
    
    private Map<Integer, List<Integer>> connectionTable;
    
    public GraphDiscretePartitionRefiner() {
        this(false, false, false);
    }
    
    public GraphDiscretePartitionRefiner(boolean useColorsInEquitable) {
        this(useColorsInEquitable, false, false);
    }
    
    public GraphDiscretePartitionRefiner(boolean useColorsInEquitable, boolean checkForDisconnected) {
        this(useColorsInEquitable, checkForDisconnected, false);
    }
    
    public GraphDiscretePartitionRefiner(boolean useColorsInEquitable, 
                                         boolean checkForDisconnected, 
                                         boolean useVertexColorsInAut) {
        super(useVertexColorsInAut);
        this.useColorsInEquitable = useColorsInEquitable;
        this.checkForDisconnected = checkForDisconnected;
//        this.useVertexColorsInAut = useVertexColorsInAut;
    }
    
    public void setup(Graph graph) {
        if (checkForDisconnected) {
            connectionTable = makeCompactConnectionTable(graph);
        } else {
            connectionTable = graph.getConnectionTable();
        }
        int n = graph.getVertexCount();
        this.graph = graph;
        SSPermutationGroup group = new SSPermutationGroup(new Permutation(n));
        IEquitablePartitionRefiner refiner = 
            new GraphEquitablePartitionRefiner(graph, useColorsInEquitable, connectionTable);
        setup(group, refiner);
    }
    
    private void setup(Graph graph, SSPermutationGroup group) {
        IEquitablePartitionRefiner refiner = 
            new GraphEquitablePartitionRefiner(graph, useColorsInEquitable, connectionTable);
        setup(group, refiner);
    }

    public boolean isCanonical(Graph graph) {
        setup(graph);
        refine(Partition.unit(graph.getVertexCount()));
        return firstIsIdentity();
    }
    
    public boolean isCanonical(Graph graph, Partition initialPartition) {
        setup(graph);
        refine(initialPartition);
        return firstIsIdentity();
    }
    
    public SSPermutationGroup getAutomorphismGroup(
            Graph graph, Partition partition) {
        setup(graph);
        refine(partition);
        return getGroup();
    }
    
    public SSPermutationGroup getAutomorphismGroup(Graph graph) {
        setup(graph);
        int n = graph.getVertexCount();
        Partition unit = Partition.unit(n);
        refine(unit);
        return getGroup();
    }
    
    public SSPermutationGroup getAutomorphismGroup(
            Graph graph, SSPermutationGroup group) {
        setup(graph, group);
        refine(Partition.unit(graph.getVertexCount()));
        return getGroup();
    }
    
    @Override
    public int getVertexCount() {
        return graph.getVertexCount();
    }

    @Override
    public boolean isConnected(int i, int j) {
//        return graph.isConnected(i, j);
        return connectionTable.containsKey(i) &&
                connectionTable.get(i).contains(j);
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
    public boolean sameColor(int i, int j) {
        return graph.getColor(i) == graph.getColor(j);
    }
}
