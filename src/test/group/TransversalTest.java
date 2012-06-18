package test.group;

import group.Permutation;
import group.SSPermutationGroup;
import model.Graph;
import model.GraphDiscretePartitionRefiner;

import org.junit.Test;

public class TransversalTest {
	
	@Test
	public void testAutPentagonInSym5() {
		SSPermutationGroup sym5 = SSPermutationGroup.makeSymN(5);
		Graph pentagon = new Graph("0:1,0:4,1:2,2:3,3:4");
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		SSPermutationGroup autP = refiner.getAutomorphismGroup(pentagon);
		String minimal = pentagon.toString();
		for (Permutation pi : sym5.transversal(autP)) {
			String permutedEdgeString = pentagon.getPermutedEdgeString(pi.getValues());
			int cmp = pentagon.toString().compareTo(permutedEdgeString);
			System.out.println(pi + "\t" + permutedEdgeString + "\t" + cmp);
			if (minimal.compareTo(permutedEdgeString) > 0) {
				minimal = permutedEdgeString;
			}
		}
		System.out.println("minimal " + minimal);
	}
	
	@Test
	public void testPawTransversal() {
		SSPermutationGroup sym4 = SSPermutationGroup.makeSymN(4);
//		Graph paw = new Graph("0:1,0:2,0:3,1:2");
		Graph paw = new Graph("0:1,0:2,0:3,1:3");
		GraphDiscretePartitionRefiner refiner = new GraphDiscretePartitionRefiner();
		SSPermutationGroup autP = refiner.getAutomorphismGroup(paw);
		String minimal = paw.toString();
		for (Permutation pi : sym4.transversal(autP)) {
			String permutedEdgeString = paw.getSortedPermutedEdgeString(pi.getValues());
			int cmp = minimal.compareTo(permutedEdgeString);
			System.out.println(pi + "\t" + permutedEdgeString + "\t" + cmp);
			if (minimal.compareTo(permutedEdgeString) > 0) {
				minimal = permutedEdgeString;
			}
		}
		System.out.println("minimal " + minimal);
	}

}
