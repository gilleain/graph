package test.graph.model;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.Permutation;
import group.PermutationGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class GroupTests {
    
    public int makeFromGenerators(int n, String... cycleStrings) {
        List<Permutation> generators = new ArrayList<Permutation>();
        for (String cycleString : cycleStrings) {
            generators.add(Permutation.fromCycleString(cycleString, n));
        }
        PermutationGroup group = new PermutationGroup(n, generators);
        int index = 0;
        for (Permutation p : group.all()) {
            System.out.println(index + "\t" + p + "\t" + p.toCycleString());
            index++;
        }
        return index;
    }
    
    @Test
    public void makeCubeFromGenerators() {
        int n = makeFromGenerators(8, "(0,1,3,7,6,4)(2,5)", "(0,1,3,2)(4,5,7,6)");
        Assert.assertEquals(48, n);
    }
    
    @Test
    public void makeFrom6CycleAlternateColorGenerators() {
        // actually need all these!
        int n = makeFromGenerators(6, "(1,5)(2,4)", "(0,2)(3,5)", "(0,4)(1,3)");
        
        // should only need these two
//        int n = makeFromGenerators(6, "(1,5)(2,4)", "(0,2)(3,5)");
        
        Assert.assertEquals(6, n);
    }
    
    @Test
    public void makeFrom4CycleColoredGenerators() {
        int n = makeFromGenerators(4, "(1,3)", "(0,2)");
        Assert.assertEquals(4, n);
    }
    
    @Test
    public void makeFromPlainGenerators() {
        // actually need all these!
//        int n = makeFromGenerators(4, "(1,3)", "(0,1)(2,3)", "(0,3)(1,2)");
        
     // should only need these two
//        int n = makeFromGenerators(4, "(1,3)", "(0,1)(2,3)");
    	
    	// but works with these two
    	int n = makeFromGenerators(4, "(0,1,2,3)", "(0,1)(2,3)");
        Assert.assertEquals(8, n);
    }
    
    @Test
    public void testMckaysMethod() {
//    	Graph parent = new Graph("0:1,1:2");
//    	Graph child = new Graph("0:1,0:2,1:3");
    	IntGraph child = new IntGraph("0:1,1:2");
    	GraphDiscretePartitionRefiner childRefiner = new GraphDiscretePartitionRefiner();
    	PermutationGroup group = childRefiner.getAutomorphismGroup(child);
    	for (Permutation p : group.all()) {
    		IntGraph pGraph = child.getPermutedGraph(p.getValues());
    		System.out.println(p + "\t" + pGraph.getSortedEdgeString());
    	}
    }
    
    @Test
    public void mckayTestTwo() {
    	IntGraph X = new IntGraph("0:1,0:2,1:3,2:4");
    	IntGraph lX0 = new IntGraph("0:1,0:2,1:3,2:4,3:5");
    	IntGraph lX1 = new IntGraph("0:1,0:2,1:3,2:4,4:5");
    	GraphDiscretePartitionRefiner childRefiner = new GraphDiscretePartitionRefiner();
    	PermutationGroup group = childRefiner.getAutomorphismGroup(X);
    	for (Permutation p : group.all()) {
    		int[] pExp = new int[p.size() + 1];
    		System.arraycopy(p.getValues(), 0, pExp, 0, p.size());
    		pExp[p.size()] = p.size();
    		System.out.print(Arrays.toString(pExp) + "\t");
    		System.out.print(lX0.getSortedPermutedEdgeString(pExp) + "\t");
    		System.out.println(lX1.getSortedPermutedEdgeString(pExp) + "\t");
    	}
    }

}
