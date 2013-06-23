package test.graph.tree;

import graph.model.Graph;
import graph.tree.TreeCanonicalVerifier;

import java.util.Arrays;


import org.junit.Test;


public class TreeCanonicalVerifierTest {
	
	@Test
	public void initialSequenceTest() {
		Graph tree = new Graph("0:1,0:4,1:2,1:3,4:5,4:6");
		int[] seq = TreeCanonicalVerifier.initialSequence(tree, 0);
		System.out.println(Arrays.toString(seq));
	}
	
	@Test
	public void initialSequenceTest2() {
		Graph tree = new Graph("0:1,0:2,0:3,3:4,3:5");
		int[] seq = TreeCanonicalVerifier.initialSequence(tree, 0);
		System.out.println(Arrays.toString(seq));
	}

}
