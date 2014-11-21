package test.graph.model;

import graph.model.IntGraph;
import graph.model.GraphFactory;

import org.junit.Assert;
import org.junit.Test;

public class GraphFactoryTest {
    
    @Test
    public void nCycleTest() {
        IntGraph triangle = GraphFactory.cycle(3);
        Assert.assertEquals(3, triangle.esize());
        IntGraph square = GraphFactory.cycle(4);
        Assert.assertEquals(4, square.esize());
        IntGraph pentagon = GraphFactory.cycle(5);
        Assert.assertEquals(5, pentagon.esize());
    }
    
    @Test
    public void nPrismTest() {
        IntGraph prism3 = GraphFactory.nPrism(3);
        Assert.assertEquals(9, prism3.esize());
        IntGraph prism4 = GraphFactory.nPrism(4);  // 'cube', or 'Frinkahedron'
        Assert.assertEquals(12, prism4.esize());
        IntGraph prism5 = GraphFactory.nPrism(5);
        Assert.assertEquals(15, prism5.esize());
    }
}
