package graph.visitor;

import graph.model.Block;
import graph.model.Edge;
import graph.model.VertexGraph;
import graph.model.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class BlockFinder implements DFSVisitor {
    
    private Stack<Edge> stack;
    
    private int[] DFI;
    
    private int[] lowpoint;
    
    private int[] parent;
    
    private int counter;
    
    private List<Block> blocks;
    
    private boolean restart;
    
    public BlockFinder() {
        stack = new Stack<Edge>();
        restart = true;
    }
    
    public void outputInternals() {
        System.out.println("i\tDFI\tlowpoint\tparent");
        for (int i = 0; i < DFI.length; i++) {
            System.out.println(i + "\t" + DFI[i] + "\t" + lowpoint[i] + "\t" + parent[i]);
        }
    }
    
    public void reset() {
        restart = true;
//        DFI = null;
    }
    
    public void visit(VertexGraph g, Vertex v) {
        if (restart) {
            stack.clear();
            int vSize = g.getVertexCount();
            DFI = new int[vSize];
            lowpoint = new int[vSize];
            parent = new int[vSize];
            Arrays.fill(parent, -1);
            counter = 1;
            blocks = new ArrayList<Block>();
            restart = false;
        }
        dfsb(g, v);
    }
    
    public List<Block> getBlocks() {
        return blocks;
    }
    
    private void dfsb(VertexGraph g, Vertex v) {
        int vI = v.getIndex();
        
        DFI[vI] = counter;
        lowpoint[vI]   = counter;
        counter++;
        
        for (Vertex w : g.getConnected(v)) {
            int wI = w.getIndex();
            Edge e = new Edge(v, w);
            if (DFI[wI] == 0) {
                stack.push(e);
                parent[wI] = vI;
                dfsb(g, w);
                if (lowpoint[wI] >= DFI[vI]) {
                    makeBlockFromStack(e);
                }
                lowpoint[vI] = Math.min(lowpoint[vI], lowpoint[wI]);
            } else if (wI != parent[vI] && DFI[wI] < DFI[vI]) {
                stack.push(e);
                lowpoint[vI] =  Math.min(lowpoint[vI], DFI[wI]);
            }
        }
    }
    
    private void makeBlockFromStack(Edge end) {
        Block block = new Block();
        Edge current = null;
        while (current == null || !current.equals(end)) {
            current = stack.pop();
            block.add(current.getA());
            block.add(current.getB());
            block.add(current.getA(), current.getB());
        }
        blocks.add(block);
    }
    
    public boolean seen(Vertex v) {
        return DFI != null && DFI[v.getIndex()] > 0;
    }
    
}
