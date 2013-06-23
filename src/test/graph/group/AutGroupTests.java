package test.graph.group;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.Graph;
import group.Partition;
import group.Permutation;
import group.SSPermutationGroup;

import org.junit.Test;


public class AutGroupTests {
    
    public void makeAutG(Graph g, boolean useVertexColorsInAut) {
        GraphDiscretePartitionRefiner refiner = 
            new GraphDiscretePartitionRefiner(false, false, useVertexColorsInAut);
        SSPermutationGroup autG = refiner.getAutomorphismGroup(g);
        if (useVertexColorsInAut) {
            System.out.println("with colors " + autG.order());
        } else {
            System.out.println("without colors " + autG.order());
        }
        for (Permutation p : autG.all()) {
            System.out.println(p + "\t" + p.toCycleString());
        }
    }
    
    public void makeAutGWithInitialPartiton(Graph g, Partition initial) {
        GraphDiscretePartitionRefiner refiner = 
            new GraphDiscretePartitionRefiner(false, false, false);
        SSPermutationGroup autG = refiner.getAutomorphismGroup(g, initial);
        System.out.println("size with partition " + initial + " = " + autG.order());
        for (Permutation p : autG.all()) {
            System.out.println(p + "\t" + p.toCycleString());
        }
    }
    
    @Test
    public void test4CycleWithAlternatingInitialPartition() {
        makeAutGWithInitialPartiton(new Graph("0:1,0:3,1:2,2:3"),
                                    Partition.fromString("[0,2|1,3]"));
    }
    
    @Test
    public void test6CycleWithAlternatingInitialPartition() {
        makeAutGWithInitialPartiton(new Graph("0:1,0:5,1:2,2:3,3:4,4:5"),
                                    Partition.fromString("[0,2,4|1,3,5]"));
    }
    
    @Test
    public void test4CycleWith2AlternatingColors() {
        Graph g = new Graph("0:1,0:3,1:2,2:3");
        g.setColors(0, 1, 0, 1);
        makeAutG(g, true);
        makeAutG(g, false);
    }
    
    @Test
    public void test6CycleWith2AlternatingColors() {
        Graph g = new Graph("0:1,0:5,1:2,2:3,3:4,4:5");
        g.setColors(0, 1, 0, 1, 0, 1);
        makeAutG(g, true);
        makeAutG(g, false);
    }

}
