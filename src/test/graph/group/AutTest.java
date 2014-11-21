package test.graph.group;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.Permutation;
import group.PermutationGroup;

import org.junit.Test;


public class AutTest {
	
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
