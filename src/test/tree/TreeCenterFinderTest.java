package test.tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import model.Graph;
import model.GraphFileReader;

import org.junit.Test;

import tree.TreeCenterFinder;

public class TreeCenterFinderTest {
	
	public static final String IN_DIR = "output/trees/prufer";
	
	public void testFile(String filename) throws FileNotFoundException {
		File inFile = new File(IN_DIR, filename);
		GraphFileReader file = new GraphFileReader(new FileReader(inFile));
		for (Graph tree : file) {
			List<Integer> center = TreeCenterFinder.findCenter(tree);
			System.out.println(center + "\t" + tree.getSortedEdgeString());
		}
	}
	
	@Test
	public void testCycle() {
	    System.out.println(TreeCenterFinder.findCenter(new Graph("0:1,0:2,1:2")));
	}
	
	@Test
	public void testTwo() {
	    Graph tree = new Graph("0:1");
	    List<Integer> center = TreeCenterFinder.findCenter(tree);
	    System.out.println(center);
	}
	
	@Test
	public void bugOn6() {
	    Graph tree = new Graph("0:2,1:2,1:4,3:4,4:5");
	    List<Integer> center = TreeCenterFinder.findCenter(tree);
        System.out.println(center + "\t" + tree.getSortedEdgeString());
	}
	
	@Test
	public void testFours() throws FileNotFoundException {
		testFile("fours.txt");
	}
	
	@Test
	public void testFives() throws FileNotFoundException {
		testFile("fives.txt");
	}
	
	@Test
	public void testSixes() throws FileNotFoundException {
		testFile("sixes.txt");
	}

}
