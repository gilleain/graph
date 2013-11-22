package test.graph.model;

import graph.model.Graph;

import org.junit.Assert;
import org.junit.Test;

public class GraphTest {
    
    @Test
    public void toDegreeSeqTest() {
        Graph g = new Graph("0:6, 5:6, 1:5, 2:5, 0:4, 1:4, 2:4, 0:3, 1:3, 2:3, 3:4, 0:1, 0:2, 1:2");
        int[] degSeq = g.degreeSequence(true);
        Assert.assertArrayEquals(new int[] { 5, 5, 5, 4, 4, 3, 2 }, degSeq);
    }
    
}
