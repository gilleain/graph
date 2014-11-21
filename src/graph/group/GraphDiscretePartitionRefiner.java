package graph.group;

import graph.model.IntGraph;
import group.AbstractDiscretePartitionRefiner;
import group.Partition;
import group.Permutation;
import group.PermutationGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * Test implementation of a discrete partition refiner for simple graphs.
 * 
 * @author maclean
 *
 */
public class GraphDiscretePartitionRefiner extends AbstractDiscretePartitionRefiner {
    
    private boolean ignoreEdgeColors;
    
    private boolean ignoreVertexColors;
    
    /**
     * A convenience lookup table for connections.
     */
    private int[][] connectionTable;
    
    /**
     * A convenience lookup table for edge colors.
     */
    private int[][] edgeColors;
    
    
    public GraphDiscretePartitionRefiner() {
    	this(false, false);
    }
    
    public GraphDiscretePartitionRefiner(boolean ignoreVertexColors, boolean ignoreEdgeColors) {
    	this.ignoreVertexColors = ignoreVertexColors;
    	this.ignoreEdgeColors = ignoreEdgeColors;
    }
    
    public int[] getConnectedIndices(int vertexIndex) {
        return connectionTable[vertexIndex];
    }
    
    public Partition getInitialPartition(IntGraph graph) {
        if (ignoreVertexColors) {
            int n = graph.getVertexCount();
            return Partition.unit(n);
        }
        
        if (connectionTable == null) {
            setupConnectionTable(graph);
        }
        
        Map<Integer, SortedSet<Integer>> cellMap = 
                new HashMap<Integer, SortedSet<Integer>>();
        int numberOfVertices = graph.getVertexCount();
        for (int vertexIndex = 0; vertexIndex < numberOfVertices; vertexIndex++) {
            int color = graph.getColor(vertexIndex);
            SortedSet<Integer> cell;
            if (cellMap.containsKey(color)) {
                cell = cellMap.get(color);
            } else {
                cell = new TreeSet<Integer>();
                cellMap.put(color, cell);
            }
            cell.add(vertexIndex);
        }
        
        List<Integer> colors = new ArrayList<Integer>(cellMap.keySet());
        Collections.sort(colors);
        
        Partition colorPartition = new Partition();
        for (Integer key : colors) {
            SortedSet<Integer> cell = cellMap.get(key);
            colorPartition.addCell(cell);
        }
        
        return colorPartition;
    }
    
    public void setup(IntGraph graph) {
    	if (connectionTable == null) {
    		setupConnectionTable(graph);
    	}
        int n = graph.getVertexCount();
        PermutationGroup group = new PermutationGroup(new Permutation(n));
        super.setup(group, new GraphEquitablePartitionRefiner(graph, this));
    }
    
    private void setup(IntGraph graph, PermutationGroup group) {
    	setupConnectionTable(graph);
    	super.setup(group, new GraphEquitablePartitionRefiner(graph, this));
    }

    public boolean isCanonical(IntGraph graph) {
        return isCanonical(graph, getInitialPartition(graph));
    }
    
    public void refine(IntGraph graph) {
        refine(graph, getInitialPartition(graph));
    }
    
    public void refine(IntGraph graph, Partition partition) {
        setup(graph);
        super.refine(partition);
    }
    
    public boolean isCanonical(IntGraph graph, Partition partition) {
        setup(graph);
        refine(partition);
        return isCanonical();
    }
    
    public PermutationGroup getAutomorphismGroup(IntGraph graph, Partition partition) {
        refine(graph, partition);
        return getAutomorphismGroup();
    }
    
    public PermutationGroup getAutomorphismGroup(IntGraph graph) {
        refine(graph, getInitialPartition(graph));
        return getAutomorphismGroup();
    }
    
    public PermutationGroup getAutomorphismGroup(IntGraph graph, PermutationGroup group) {
        setup(graph, group);
        refine(getInitialPartition(graph));
        return getAutomorphismGroup();
    }
    
    @Override
    public int getVertexCount() {
        return connectionTable.length;
    }
    
    private void setupConnectionTable(IntGraph graph) {
    	int vertexCount = graph.getVertexCount();
        connectionTable = new int[vertexCount][];
        if (!ignoreEdgeColors) {
            edgeColors = new int[vertexCount][];
        }
        for (int vertexIndex = 0; vertexIndex < vertexCount; vertexIndex++) {
        	List<Integer> connected = graph.getConnected(vertexIndex);
            int numConnVertices = connected.size();
            connectionTable[vertexIndex] = new int[numConnVertices];
            if (!ignoreEdgeColors) {
                edgeColors[vertexIndex] = new int[numConnVertices];
            }
            int i = 0;
            for (int connectedVertex : connected) {
                connectionTable[vertexIndex][i] = connectedVertex;
                if (!ignoreEdgeColors) {
                    int color = graph.getEdge(vertexIndex, connectedVertex).o;
                    edgeColors[vertexIndex][i] = color;
                }
                i++;
            }
        }
    }
    
    public Map<Integer, List<Integer>> makeCompactConnectionTable(IntGraph graph) {
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
    	 int indexInRow;
         int maxRowIndex = connectionTable[vertexI].length;
         for (indexInRow = 0; indexInRow < maxRowIndex; indexInRow++) {
             if (connectionTable[vertexI][indexInRow] == vertexJ) {
                 break;
             }
         }
         if (ignoreEdgeColors) {
             if (indexInRow < maxRowIndex) {
                 return 1;
             } else {
                 return 0;
             }
         } else {
             if (indexInRow < maxRowIndex) {
                 return edgeColors[vertexI][indexInRow];
             } else {
                 return 0;
             }
         }
     }
}
