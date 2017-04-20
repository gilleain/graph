package graph.group;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import graph.model.IntGraph;
import group.AbstractDiscretePartitionRefiner;
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
    
    private boolean ignoreEdgeColors;
    
    private boolean ignoreVertexColors;
    
    private GraphRefinable refinable;
    
    public GraphDiscretePartitionRefiner() {
    	this(false, false);
    }
    
    public GraphDiscretePartitionRefiner(boolean ignoreVertexColors, boolean ignoreEdgeColors) {
    	this.ignoreVertexColors = ignoreVertexColors;
    	this.ignoreEdgeColors = ignoreEdgeColors;
    }
    
    private GraphRefinable getRefinable(IntGraph graph) {
        if (refinable == null) {
            refinable = new GraphRefinable(graph, ignoreEdgeColors);
        }
        return refinable;
    }
    
    public int[] getConnectedIndices(int vertexIndex) {
        return refinable.getConnectedIndices(vertexIndex);
    }
    
    public Partition getInitialPartition(IntGraph graph) {
        if (ignoreVertexColors) {
            int n = graph.getVertexCount();
            return Partition.unit(n);
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
    	GraphRefinable refinable = getRefinable(graph);
        int n = graph.getVertexCount();
        PermutationGroup group = new PermutationGroup(new Permutation(n));
        super.setup(group, new GraphEquitablePartitionRefiner(refinable));
    }
    
    private void setup(IntGraph graph, PermutationGroup group) {
    	super.setup(group, new GraphEquitablePartitionRefiner(getRefinable(graph)));
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
        return refinable.getVertexCount();
    }
    
    @Override
    public int getConnectivity(int vertexI, int vertexJ) {
    	return refinable.getConnectivity(vertexI, vertexJ);
    }
}
