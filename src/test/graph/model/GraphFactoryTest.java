package test.graph.model;

import graph.model.Graph;
import graph.model.GraphFactory;

import org.junit.Assert;
import org.junit.Test;

public class GraphFactoryTest {
    
    @Test
    public void nCycleTest() {
        Graph triangle = GraphFactory.cycle(3);
        Assert.assertEquals(3, triangle.esize());
        Graph square = GraphFactory.cycle(4);
        Assert.assertEquals(4, square.esize());
        Graph pentagon = GraphFactory.cycle(5);
        Assert.assertEquals(5, pentagon.esize());
    }
    
    @Test
    public void nPrismTest() {
        Graph prism3 = GraphFactory.nPrism(3);
        Assert.assertEquals(9, prism3.esize());
        Graph prism4 = GraphFactory.nPrism(4);  // 'cube', or 'Frinkahedron'
        Assert.assertEquals(12, prism4.esize());
        Graph prism5 = GraphFactory.nPrism(5);
        Assert.assertEquals(15, prism5.esize());
    }
}
