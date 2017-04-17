package test.graph.group;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.Partition;
import group.Permutation;
import group.PermutationGroup;

import org.junit.Test;


public class AutTest {
    
    @Test
    public void threeThreeSquare() {
        IntGraph graph = new IntGraph("0:1,0:3,1:2,1:4,2:5,3:4,3:6,4:5,4:7,5:8,6:7,7:8");
        Partition initial = Partition.fromString("[0|2,6,8|5,7|1,3|4]");
//        Partition initial = Partition.fromString("0,2,6,8|1,3,5,7|4");
//        Partition initial = Partition.fromString("4|1,3,5,7|0,2,6,8");
//        Partition initial = Partition.fromString("4|1|3,5,7|0,2,6,8");
//        Partition initial = Partition.unit(9);
        GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
        PermutationGroup group = refiner.getAutomorphismGroup(graph, initial);
        int i = 0;
        for (Permutation p : group.all()) {
            System.out.println(i + "\t" + p.toCycleString());
            i++;
        }
    }
	
	@Test
	public void nautyBridgedSquare() {
//		Graph graph = new Graph("[0:3, 0:4, 1:3, 1:4, 2:3, 2:4]");
		IntGraph graph = new IntGraph("[0:2, 0:3, 1:2, 1:3]");
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		refiner.getAutomorphismGroup(graph);
		Permutation best = refiner.getBest();
		System.out.println("best =" + best
						+ "\tpermuted graph=" + graph.getPermutedGraph(best.invert()).getSortedEdgeString() 
						+ "\tcert=" + refiner.getCertificate()
						+ "\tid_cert" + refiner.calculateCertificate(new Permutation(graph.getVertexCount())));
	}
	
	@Test
	public void missing_Eights_Zero() {
		IntGraph graph = new IntGraph("0:1, 0:2, 0:3, 1:2, 1:4, 2:5, 3:4, 3:6, 4:7, 5:6, 5:7, 6:7");
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		refiner.getAutomorphismGroup(graph);
		Permutation best = refiner.getBest();
		System.out.println("best =" + best
						+ "\tpermuted graph=" + graph.getPermutedGraph(best.invert()).getSortedEdgeString() 
						+ "\tcert=" + refiner.getCertificate()
						+ "\tid_cert" + refiner.calculateCertificate(new Permutation(graph.getVertexCount())));
	}
	
	@Test
	public void nautyClawGraph() {
		IntGraph graph = new IntGraph("0:3,1:3,2:3");
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		PermutationGroup autG = refiner.getAutomorphismGroup(graph);
		for (Permutation p : autG.all()) {
			String cert = refiner.getHalfMatrixString(p);
			System.out.println(p + "\t" + cert + "\t" + graph.getPermutedGraph(p).getSortedEdgeString());
		}
		Permutation best = refiner.getBest();
		System.out.println("best =" + best
						+ "\tpermuted graph=" + graph.getPermutedGraph(best.invert()).getSortedEdgeString() 
						+ "\tcert=" + refiner.getCertificate()
						+ "\tid_cert" + refiner.calculateCertificate(new Permutation(graph.getVertexCount())));
	}
	
	@Test
	public void myClawGraph() {
		IntGraph graph = new IntGraph("0:1,0:2,0:3");
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		PermutationGroup autG = refiner.getAutomorphismGroup(graph);
		for (Permutation p : autG.all()) {
			String cert = refiner.getHalfMatrixString(p);
			System.out.println(p + "\t" + cert + "\t" + graph.getPermutedGraph(p).getSortedEdgeString());
		}
		Permutation best = refiner.getBest();
		System.out.println("best =" + best
						+ "\tpermuted graph=" + graph.getPermutedGraph(best).getSortedEdgeString()
						+ "\tcert=" + refiner.getCertificate()
						+ "\tid_cert" + refiner.calculateCertificate(new Permutation(graph.getVertexCount())));
	}

}
