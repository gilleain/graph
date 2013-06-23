package test.graph.tree;

import graph.model.Graph;
import graph.tree.TreeCertificateMaker;
import junit.framework.Assert;

import org.junit.Test;


public class TreeCertificateMakerTest {
	
	@Test
	public void oneCenterTreeTestFromCAGES_pp246() {
		String expected = "00001011100011100111";
		Graph tree = new Graph("0:2,1:2,1:4,2:7,3:4,3:8,4:5,5:6,6:9");
		String cert = TreeCertificateMaker.treeToCertificate(tree);
		Assert.assertEquals(expected, cert);
	}
	
	@Test
	public void twoCenterTreeTestFromCAGES_pp247() {
		String expected = "00011001101100011011";
		Graph tree = new Graph("0:1,0:7,1:2,1:4,1:9,2:8,3:4,4:5,5:6");
		String cert = TreeCertificateMaker.treeToCertificate(tree);
		Assert.assertEquals(expected, cert);
	}
	
	@Test
	public void broadTreeOn5() {
		String expected = "001010101011";
		Graph tree = new Graph("0:1,0:2,0:3,0:4,0:5");
		String cert = TreeCertificateMaker.treeToCertificate(tree);
		Assert.assertEquals(expected, cert);
	}

}
