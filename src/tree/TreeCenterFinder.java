package tree;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import model.Graph;

public class TreeCenterFinder {
	
	public static List<Integer> findCenter(Graph tree) {
//	    System.out.println(tree.esize() + "\t" + tree.vsize());
	    if (tree.esize() > tree.vsize() - 1) {
	        return new ArrayList<Integer>();
	    }
		Map<Integer, List<Integer>> adjMat = tree.getConnectionTableCopy();
		removeDisconnected(adjMat);
		prune(adjMat);
		return new ArrayList<Integer>(adjMat.keySet());
	}
	
	private static void removeDisconnected(Map<Integer, List<Integer>> adjMat) {
	    List<Integer> toRemove = new ArrayList<Integer>();
        for (Integer v : adjMat.keySet()) {
            int degree = adjMat.get(v).size(); 
            if (degree == 0) {
                toRemove.add(v);
            }
        }
        for (Integer x : toRemove) {
            adjMat.remove(x);
        }
	}
	
	private static void prune(Map<Integer, List<Integer>> adjMat) {
	    if (adjMat.size() <= 2) {
	        return;
	    } else {
    		List<Integer> toRemove = new ArrayList<Integer>();
    		for (Integer v : adjMat.keySet()) {
    			int degree = adjMat.get(v).size(); 
    			if (degree == 1) {
    				toRemove.add(v);
    			}
    		}
    		for (Integer x : toRemove) {
    			adjMat.remove(x);
    			for (Integer v : adjMat.keySet()) {
    			    List<Integer> partners = adjMat.get(v);
    			    partners.remove(x); // careful! - have to use integer here, not int
    			}
    		}
    		prune(adjMat);
	    }
	}

    public static Integer findUniqueCenter(Graph tree) {
        List<Integer> center = TreeCenterFinder.findCenter(tree);
        if (center.size() == 0) {
            return -1;  // ugh - only happens if its a cycle!
        } else if (center.size() == 1) {
            return center.get(0);
        } else {
            int centerA = center.get(0);
            int centerB = center.get(1);
            int centerAHeight = TreeCenterFinder.getHeight(tree, centerA, centerB);
            int centerBHeight = TreeCenterFinder.getHeight(tree, centerB, centerA);
            if (centerAHeight >= centerBHeight) {
                return centerA;
            } else {
                return centerB;
            }
        }
    }
    
    private static int getHeight(Graph tree, int root, int partner) {
        int height = 1;
        BitSet visited = new BitSet();
        for (int child : tree.getConnected(root)) {
            if (child == partner) continue;
            height += getHeight(tree, child, visited);
        }
        return height;
    }
    
    private static int getHeight(Graph tree, int root, BitSet visited) {
        visited.set(root);
        int height = 1;
        for (int child : tree.getConnected(root)) {
            if (visited.get(child)) continue;
            height += getHeight(tree, child, visited);
        }
        return height;
    }

}
