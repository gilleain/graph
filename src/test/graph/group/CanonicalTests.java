package test.graph.group;

import java.util.Arrays;

import graph.group.GraphDiscretePartitionRefiner;
import graph.model.IntGraph;
import group.Partition;
import group.Permutation;
import group.PermutationGroup;

import org.junit.Test;


public class CanonicalTests {
	
	public void test(IntGraph g, Partition p) {
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
//		boolean canonical = refiner.isCanonical(g, p);
//		System.out.println(canonical);
		PermutationGroup group = refiner.getAutomorphismGroup(g, p);
		for (Permutation perm : group.all()) {
			System.out.println(perm);
		}
		System.out.println("Best :\t" + refiner.getBest());
		
		String bestString = refiner.getBestHalfMatrixString();
		String firstString = refiner.getFirstHalfMatrixString();
		String identString = refiner.getHalfMatrixString();
		System.out.println(bestString + "\n" + firstString + "\n" + identString);
	}
	
	@Test
	public void test_3To4_2To1_1To2() {
//		Graph gA = new Graph("0:1,0:2,0:4,1:3,1:5,2:3,2:6,3:4");
//		Graph gB = new Graph("0:1,0:2,0:4,1:3,1:6,2:3,2:5,3:4");
		IntGraph gC = new IntGraph("0:2,0:3,0:4,1:2,1:3,1:4,2:5,3:6");
		Partition p = Partition.fromString("0,1,2,3|4|5,6");
//		test(gA, p);
//		test(gB, p);
		test(gC, p);
	}
	
	@Test
	public void findCanonical() {
		IntGraph gA = new IntGraph("0:1,0:2,0:4,1:3,1:5,2:3,2:6,3:4");
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		refiner.setup(gA);
		String initialBest = refiner.getHalfMatrixString();
		System.out.println(initialBest);
		
		PermutationGroup symN = PermutationGroup.makeSymN(gA.vsize());
		for (Permutation p : symN.all()) {
			refiner = new GraphDiscretePartitionRefiner();
			IntGraph gP = gA.getPermutedGraph(p);
			refiner.setup(gP);
			String best = refiner.getHalfMatrixString();
//			System.out.println(best);
			int[] degSeq = gP.degreeSequence(false);
			if (initialBest.compareTo(best) < 0) {
				System.out.println(
						best 
						+ "\t" + gP.getSortedEdgeString()
						+ "\t" + Arrays.toString(degSeq)
						+ "\t" + ordered(degSeq)
						);
			}
		}
	}
	
	private boolean ordered(int[] degSeq) {
		for (int i = 1; i < degSeq.length; i++) {
			if (degSeq[i - 1] >= degSeq[i]) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

}
