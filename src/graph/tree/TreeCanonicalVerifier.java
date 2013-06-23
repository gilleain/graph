package graph.tree;

import graph.model.Graph;
import group.Permutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Verify a tree as canonical by taking all traversals from the root, and comparing the
 * distance sequences to the one made by the initial labeling.
 * 
 * 
 * @author maclean
 *
 */
public class TreeCanonicalVerifier {
	
	public static boolean isCanonical(Graph tree, int root) {
		List<int[]> traversals = new ArrayList<int[]>();
		int[] prefix = new int[tree.getVertexCount()];
		traverse(tree, root, -1, 0, 0, prefix, traversals);
		
		return false;
	}
	
	public static int[] initialSequence(Graph tree, int root) {
		int[] seq = new int[tree.getVertexCount()];
		initialTraversal(tree, root, -1, seq, 0, 0);
		return seq;
	}
	
	private static int initialTraversal(
			Graph tree, int vertex, int parent, int[] seq, int i, int d) {
		seq[i] = d;
		int j = i + 1;
		for (int child : tree.getConnected(vertex)) {
			if (parent != -1 && child == parent) {
				continue;
			} else {
				j = initialTraversal(tree, child, vertex, seq, j, d + 1);
			}
		}
		return j;
	}
	
	public static void traverse(Graph tree, 
								int vertex, 
								int parent, 
								int depth, 
								int index,
								int[] prefix,
								List<int[]> traversals) {
		for (int[] t : traversals) {
			t[index] = depth;
		}
		List<Integer> children = tree.getConnected(vertex);
		if (parent != -1) {
			children.remove(new Integer(parent));
		}
		Collections.sort(children);
		int numberOfChildren = children.size();
		Permutor permutor = new Permutor(numberOfChildren);
		while (permutor.hasNext()) {
			int[] t = new int[tree.getVertexCount()];
			System.arraycopy(prefix, 0, t, 0, index);
			int i = index + 1;
			for (int childIndex : permutor.getNextPermutation()) {
				traverse(tree, childIndex, vertex, depth + 1, i, prefix, traversals);
				i++;
			}
		}
	}

}
