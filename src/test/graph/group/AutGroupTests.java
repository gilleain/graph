package test.graph.group;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.Partition;
import group.Permutation;
import group.PermutationGroup;

import org.junit.Test;


public class AutGroupTests {
    
    public void makeAutG(IntGraph g) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup autG = refiner.getAutomorphismGroup(g);
        for (Permutation p : autG.all()) {
            System.out.println(p + "\t" + p.toCycleString());
        }
    }
    
    public void makeAutGWithInitialPartiton(IntGraph g, Partition initial) {
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup autG = refiner.getAutomorphismGroup(g, initial);
        System.out.println("size with partition " + initial + " = " + autG.order());
        for (Permutation p : autG.all()) {
            System.out.println(p + "\t" + p.toCycleString());
        }
    }
    
    @Test
    public void test4CycleWithAlternatingInitialPartition() {
        makeAutGWithInitialPartiton(new IntGraph("0:1,0:3,1:2,2:3"),
                                    Partition.fromString("[0,2|1,3]"));
    }
    
    @Test
    public void test6CycleWithAlternatingInitialPartition() {
        makeAutGWithInitialPartiton(new IntGraph("0:1,0:5,1:2,2:3,3:4,4:5"),
                                    Partition.fromString("[0,2,4|1,3,5]"));
    }
    
    @Test
    public void test4CycleWith2AlternatingColors() {
        IntGraph g = new IntGraph("0:1,0:3,1:2,2:3");
        g.setColors(0, 1, 0, 1);
        // TODO : test with color partition!
        makeAutG(g);
    }
    
    @Test
    public void test6CycleWith2AlternatingColors() {
        IntGraph g = new IntGraph("0:1,0:5,1:2,2:3,3:4,4:5");
        g.setColors(0, 1, 0, 1, 0, 1);
        // TODO : test with color partition!
        makeAutG(g);
    }

}
