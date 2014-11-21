package graph.tree;

import graph.model.IntGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class TreeCertificateMaker {
	
	public static String treeToCertificate(IntGraph tree) {
		int n = tree.getVertexCount();
		int numberOfRemainingVertices = n;
		
		String[] labels = new String[n];
		Arrays.fill(labels, "01");
		
		int lastParent = -1;
		
		Map<Integer, List<Integer>> adjacencyMatrix = tree.getConnectionTable();
		List<List<Integer>> children = new ArrayList<List<Integer>>();
		for (int i = 0; i < n; i++) { children.add(new ArrayList<Integer>()); }
		int[] leaves = new int[n];
		while (numberOfRemainingVertices > 2) {
			numberOfRemainingVertices = 
				numberOfRemainingVertices - findLeavesAndChildren(n, adjacencyMatrix, leaves, children);
			lastParent = reduce(tree, children, adjacencyMatrix, labels);
		}
		findLeavesAndChildren(n, adjacencyMatrix, leaves, children);
		
		String cert;
		if (numberOfRemainingVertices == 2) {
			String label0 = labels[leaves[0]];
			String label1 = labels[leaves[1]];
			if (label0.compareTo(label1) < 0) {
				cert = label0 + label1;
			} else {
				cert = label1 + label0;
			}
		} else {
			cert = labels[lastParent];
		}
		return cert;
	}
	
	private static int reduce(IntGraph tree, List<List<Integer>> children, Map<Integer, List<Integer>> adjacencyMatrix, String[] labels) {
		int n = tree.getVertexCount();
		int lastParent = -1;
		for (int i = 0; i < n; i++) {
			List<Integer> childrenI = children.get(i); 
			if (!childrenI.isEmpty()) {
				lastParent = i;
				adjacencyMatrix.get(i).removeAll(childrenI);
				List<String> childLabels = new ArrayList<String>();
				for (int childIndex : children.get(i)) {
					adjacencyMatrix.get(childIndex).clear();
					childLabels.add(labels[childIndex]);
				}
				if (labels[i].length() > 2) {
					childLabels.add(labels[i].substring(1, labels[i].length() - 1));
				}
				Collections.sort(childLabels);
//				System.out.println("sorted labels " + childLabels);
				labels[i] = "0";
				for (String label : childLabels) {
					labels[i] += label;
				}
				labels[i] += "1";
//				System.out.println("setting label " + labels[i] +" in labels " + Arrays.toString(labels));
			}
		}
		return lastParent;
	}

	private static int findLeavesAndChildren(int n, Map<Integer, List<Integer>> adjacencyMatrix, int[] leaves, List<List<Integer>> children) {
//		System.out.println("before(A)\t" + A);
//		System.out.println("before(L)\t" + Arrays.toString(leaves));
		for (int i = 0; i < n; i++) { children.set(i, new ArrayList<Integer>()); }
		int num = 0;
		for (int j = 0; j < n; j++) {
			List<Integer> connected = adjacencyMatrix.get(j); 
			if (connected.size() == 1) {
				leaves[num] = j;
				int k = connected.get(0);
				children.get(k).add(j);
				num++;
			}
		}
//		System.out.println("after(A)\t" + A);
//		System.out.println("after(C)\t" + children);
		return num;
	}
}
