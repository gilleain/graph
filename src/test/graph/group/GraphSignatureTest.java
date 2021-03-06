package test.graph.group;

import graph.model.IntGraph;
import graph.model.GraphSignature;

import org.junit.Test;

public class GraphSignatureTest {
	
	@Test
	public void testDeAugment() {
		IntGraph gPrime = new IntGraph("0:1,1:2");
		IntGraph gPrimeMinusE = gPrime.removeLastEdge();
		GraphSignature gPrimeMinusESig = new GraphSignature(gPrimeMinusE);
		System.out.println(gPrimeMinusE + " " + gPrimeMinusE.maxVertexIndex);
		System.out.println(gPrimeMinusESig.toFullString());
		System.out.println(gPrimeMinusESig.toCanonicalString());
	}
	
	public IntGraph getCanonicalForm(IntGraph g) {
		GraphSignature sig = new GraphSignature(g);
		return g.getPermutedGraph(sig.getCanonicalLabels());
	}
	
	@Test
	public void missingSixes() {
		String[] missingList = new String[] {
				"0:3, 0:4, 0:5, 1:4, 1:5, 2:5, 4:5",
				"0:3, 0:5, 1:4, 1:5, 2:4, 2:5",
				"0:3, 0:4, 0:5, 1:3, 1:4, 1:5, 2:5, 3:5",
				"0:3, 0:4, 0:5, 1:3, 1:5, 2:4, 2:5",
				"0:3, 0:4, 0:5, 1:3, 2:4, 2:5, 3:5"
		};
		for (String missing : missingList) {
			System.out.println(getCanonicalForm(new IntGraph(missing)).getSortedEdgeString());
		}
	}

}
