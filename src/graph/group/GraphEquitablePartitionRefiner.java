package graph.group;

import java.util.Set;

import group.AbstractEquitablePartitionRefiner;
import group.IEquitablePartitionRefiner;
import group.Refinable;
import group.invariant.IntegerInvariant;
import group.invariant.Invariant;


/**
 * Implementation of an abstract equitable partition refiner for simple graphs.
 * 
 * @author maclean
 *
 */
public class GraphEquitablePartitionRefiner extends AbstractEquitablePartitionRefiner 
                                       implements IEquitablePartitionRefiner {
    
    private final Refinable refinable;
    
    public GraphEquitablePartitionRefiner(Refinable refinable) {
        this.refinable = refinable;
    }

    public int getNumberOfVertices() {
        return refinable.getVertexCount();
    }

    @Override
    public Invariant neighboursInBlock(Set<Integer> block, int vertexIndex) {
        int n = 0;
        
        for (int i : refinable.getConnectedIndices(vertexIndex)) {
            if (block.contains(i)) {
                n++;
            }
        }
        return new IntegerInvariant(n);
    }

}
