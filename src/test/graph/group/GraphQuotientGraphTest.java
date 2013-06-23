package test.graph.group;

import graph.group.GraphDiscretePartitionRefiner;
import graph.group.GraphQuotientGraph;
import graph.model.Graph;
import group.Partition;

import org.junit.Test;

public class GraphQuotientGraphTest {
    
    public void testGraph(String graphString) {
        Graph g = new Graph(graphString);
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        refiner.refine(g);
        Partition autPart = refiner.getAutomorphismPartition();
        GraphQuotientGraph gqg = new GraphQuotientGraph(g);
        gqg.construct(autPart);
        System.out.println(g + "\t" + gqg);
    }
    
    @Test
    public void test3Line() {
        testGraph("0:1,1:2");
    }
    
    @Test
    public void test4Line() {
        testGraph("0:1,1:2,2:3");
    }
    
    @Test
    public void testPaw() {
        testGraph("0:1,1:2,1:3,2:3");
    }
    
    @Test
    public void testSquare() {
        testGraph("0:1,0:3,1:2,2:3");
    }
    
    @Test
    public void testDoubleStalkedSquare() {
        testGraph("0:1,0:3,0:4,1:2,2:3,2:5");
    }
    
}
