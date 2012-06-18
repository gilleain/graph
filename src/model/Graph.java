package model;

import group.Partition;
import group.Permutation;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple graph for testing.
 * 
 * @author maclean
 *
 */
public class Graph {
    
    public class Edge implements Comparable<Edge> {
        
        public int a;
        
        public int b;
        
        public int o = 1;
        
        public Edge(int a, int b) {
            this.a = a;
            this.b = b;
        }
        
        public Edge(int a, int b, int o) {
            this(a, b);
            this.o = o;
        }
        
        public Edge(Edge e) {
            this.a = e.a;
            this.b = e.b;
        }
        
        public Edge(String s) {
        	String[] bits = s.split(":");
        	this.a = Integer.parseInt(bits[0].trim());
        	this.b = Integer.parseInt(bits[1].trim());
        }
        
        public String getSortedPermutedColorOnlyString(int[] p, List<Integer> colors) {
            int pa = p[this.a];
            int pb = p[this.b];
            int ca = colors.get(pa);
            int cb = colors.get(pb);
            if (ca < cb) {
                return ca + ":" + cb;
            } else {
                return cb + ":" + ca;
            }
        }
        
        public String getSortedPermutedColoredString(int[] p, List<Integer> colors) {
            int pa = p[this.a];
            int pb = p[this.b];
            if (pa < pb) {
//                return pa + "(" + colors.get(pa) + "):" + pb + "(" + colors.get(pb) + ")";
                return pa + "(" + colors.get(a) + "):" + pb + "(" + colors.get(b) + ")";
            } else {
//                return pb + "(" + colors.get(pb) + "):" + pa + "(" + colors.get(pa) + ")";
                return pb + "(" + colors.get(b) + "):" + pa + "(" + colors.get(a) + ")";
            }
        }
        
        public String getSortedPermutedString(int[] p) {
            int pa = p[this.a];
            int pb = p[this.b];
            if (pa < pb) {
                return pa + ":" + pb;
            } else {
                return pb + ":" + pa;
            }
        }
        
        public String getPermutedString(int[] p) {
            return p[this.a] + ":" + p[this.b];
        }
        
        public String toSortedColorOnlyString(List<Integer> colors) {
            int ca = colors.get(a);
            int cb = colors.get(b);
            if (ca < cb) {
                return ca + ":" + cb;
            } else {
                return cb + ":" + ca;
            }
            
        }
        
        public String toSortedColoredString(List<Integer> colors) {
            if (a < b) {
                return a + "(" + colors.get(a) + "):" + b + "(" + colors.get(b) + ")";
            } else {
                return b + "(" + colors.get(b) + "):" + a + "(" + colors.get(a) + ")";
            }
        }
        
        public String toSortedString() {
            if (a < b) {
                return a + ":" + b;
            } else {
                return b + ":" + a;
            }
        }
        
        public String toSortedStringWithEdgeOrder() {
            if (a < b) {
                return a + ":" + b + "(" + o + ")";
            } else {
                return b + ":" + a + "(" + o + ")";
            }
        }

        public String toString() {
            return this.a + ":" + this.b;
        }

        public int compareTo(Edge other) {
            int tMin = (a < b)? a : b;
            int tMax = (a < b)? b : a;
            int oMin = (other.a < other.b)? other.a : other.b;
            int oMax = (other.a < other.b)? other.b : other.a;
            if (tMin < oMin || (tMin == oMin && tMax < oMax)) {
                return -1;
            } else {
                if (tMin == oMin && tMax == oMax) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }

        public boolean adjacent(Edge other) {
            return a == other.a || a == other.b || b == other.a || b == other.b;
        }
    }
    
    public List<Edge> edges;
    
    public int maxVertexIndex;
    
    public List<Integer> colors;
    
    private Map<Integer, List<Integer>> lazyConnectionTable;
    
    public Graph() {
        this.edges = new ArrayList<Edge>();
        this.colors = new ArrayList<Integer>();
        this.maxVertexIndex = -1;
        lazyConnectionTable = null;
    }
    
    public Graph(String graphString) {
        this();
        if (graphString.startsWith("[")) {
            graphString = graphString.substring(1, graphString.length() - 1);
        }
        for (String edgeString : graphString.split(",")) {
            String[] vertexStrings = edgeString.split(":");
            int a = Integer.parseInt(vertexStrings[0].trim());
            int b = Integer.parseInt(vertexStrings[1].trim());
            this.makeEdge(a, b);
        }
    }
    
    public Graph(Graph other) {
        this();
        for (Edge edge : other.edges) {
            this.makeEdge(edge.a, edge.b, edge.o);
        }
        this.maxVertexIndex = other.maxVertexIndex;
    }
    
    public Graph(int vertices) {
        this();
        maxVertexIndex = vertices - 1;
    }
    
    public boolean equals(Object o) {
    	if (o instanceof Graph) {
    		Graph other = (Graph) o;
    		return this.getSortedEdgeString().equals(other.getSortedEdgeString());
    	}
    	return false;
    }
    
    public int hashCode() {
    	return getSortedEdgeString().hashCode();
    }
    
    public void makeVertex() {
        this.maxVertexIndex++;
    }
    
    public Graph getPermutedGraph(int[] permutation) {
        Graph graph = new Graph();
        for (Edge e : this.edges) {
            graph.makeEdge(permutation[e.a], permutation[e.b], e.o);
        }
        return graph;
    }
    
    public int getColor(int index) {
        if (index >= colors.size()) {
            return 0;
        } else { 
            return colors.get(index);
        }
    }
    
    public void setColors(int... colors) {
        for (int color : colors) {
            this.colors.add(color);
        }
    }
    
    public List<Integer> getPermutedColorString(Permutation p) {
        List<Integer> permutedColors = new ArrayList<Integer>();
        for (int i = 0; i < this.colors.size(); i++) {
            permutedColors.add(this.colors.get(p.get(i)));
        }
        return permutedColors;
    }
    
    public List<String> getLabels() {
        return null;
    }
    
    private void updateMaxVertexIndex(int a, int b) {
        if (a > maxVertexIndex) maxVertexIndex = a;
        if (b > maxVertexIndex) maxVertexIndex = b;
    }
    
    public Edge getEdge(int i, int j) {
        for (Edge e : this.edges) {
            if ((e.a == i && e.b ==j) || (e.a == j && e.b == i)) {
                return e;
            }
        }
        return null;
    }
    
    public void makeIsolatedVertex() {
    	this.maxVertexIndex++;
    }
    
    public void makeMultipleEdges(int a, int... bs) {
    	for (int b : bs) {
    		makeEdge(a, b);
    	}
    }
    
    public void makeEdge(int a, int b) {
        Edge e = getEdge(a, b);
        if (e == null) {
            this.edges.add(new Edge(a, b));
        } else {
            e.o++;
        }
        this.updateMaxVertexIndex(a, b);
    }
    
    public void makeEdge(int a, int b, int c) {
        this.edges.add(new Edge(a, b, c));
        this.updateMaxVertexIndex(a, b);
    }
    
    public int getVertexCount() {
        return this.maxVertexIndex + 1;
    }
    
    public List<Integer> getConnected(int vertexIndex) {
        Map<Integer, List<Integer>> connectionTable = calculateConnectionTable();
        return connectionTable.get(vertexIndex);
    }
    
    public List<Integer> getSameColorConnected(int vertexIndex) {
        int color = getColor(vertexIndex);
        List<Integer> connected = new ArrayList<Integer>();
        for (Edge edge : this.edges) {
            if (edge.a == vertexIndex && getColor(edge.b) == color) {
                connected.add(edge.b);
            } else if (edge.b == vertexIndex && getColor(edge.a) == color) {
                connected.add(edge.a);
            } else {
                continue;
            }
        }
        
        return connected;
    }
    
    public boolean isConnected(int i, int j) {
        for (Edge e : this.edges) {
            if ((e.a == i && e.b ==j) || (e.a == j && e.b == i)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isConnected() {
        Map<Integer, List<Integer>> connectionTable = this.getConnectionTable();
        List<Integer> visited = new ArrayList<Integer>();
        dfs(0, visited, connectionTable);
        return visited.size() == this.getVertexCount();
    }
    
    private Map<Integer, List<Integer>> calculateConnectionTable() {
        Map<Integer, List<Integer>> connectionTable = 
            new HashMap<Integer, List<Integer>>();
        for (Edge e : this.edges) {
            if (connectionTable.containsKey(e.a)) {
                connectionTable.get(e.a).add(e.b);
            } else {
                List<Integer> connected = new ArrayList<Integer>();
                connected.add(e.b);
                connectionTable.put(e.a, connected);
            }
            if (connectionTable.containsKey(e.b)) {
                connectionTable.get(e.b).add(e.a);
            } else {
                List<Integer> connected = new ArrayList<Integer>();
                connected.add(e.a);
                connectionTable.put(e.b, connected);
            }
        }
        for (int i = 0; i <= maxVertexIndex; i++) {
            if (connectionTable.containsKey(i)) {
                continue;
            } else {
                connectionTable.put(i, new ArrayList<Integer>());
            }
        }
        return connectionTable;
    }
    
    public Map<Integer, List<Integer>> getConnectionTable() {
        if (lazyConnectionTable == null) {
            lazyConnectionTable = calculateConnectionTable();
        }
        return lazyConnectionTable;
    }
    
    public void dfs(
        int vertex, List<Integer> visited, Map<Integer, List<Integer>> table) {
        visited.add(vertex);
        try {
            for (int connected : table.get(vertex)) {
                if (visited.contains(connected)) {
                    continue;
                } else {
                    dfs(connected, visited, table);
                }
            }
        } catch (NullPointerException npe) {
            System.out.println("ERR " + vertex + " " + visited + " " + table + " " + this);
        }
    }
    
    public boolean saturated(int i, int maxDegree) {
        int degree = 0;
        for (Edge e : this.edges) {
            if (e.a == i || e.b == i) {
                degree += e.o;
            }
            
            if (degree == maxDegree) {
                return true;
            }
        }
        return false;
    }
    
    public boolean saturated(int i, Map<Integer, Integer> colorDegrees) {
        return saturated(i, colorDegrees.get(getColor(i)));
    }
    
    public Graph makeNew(int i, int j) {
        Graph copy = new Graph();
        for (Edge e : this.edges) {
            copy.makeEdge(e.a, e.b, e.o);
        }
        for (int color : this.colors) { copy.colors.add(color); }
        copy.makeEdge(i, j);
        return copy;
    }
    
    public Graph makeNew(int i, int j, int colorJ) {
        Graph copy = new Graph();
        for (Edge e : this.edges) {
            copy.makeEdge(e.a, e.b, e.o);
        }
        copy.makeEdge(i, j);
        for (int color : this.colors) { copy.colors.add(color); }
        copy.colors.add(colorJ);
        return copy;
    }
    
    public Graph makeNew(int i, int j, int colorI, int colorJ) {
        Graph copy = new Graph();
        for (Edge e : this.edges) {
            copy.makeEdge(e.a, e.b, e.o);
        }
        copy.makeEdge(i, j);
        for (int color : this.colors) { copy.colors.add(color); }
        copy.colors.add(colorI);
        copy.colors.add(colorJ);
        return copy;
    }
    
    public String getSortedPermutedColoredOnlyEdgeString(int[] p) {
        List<String> edgeStrings = new ArrayList<String>();
        for (Edge e : this.edges) {
            edgeStrings.add(e.getSortedPermutedColorOnlyString(p, colors));
        }
        Collections.sort(edgeStrings);
        return edgeStrings.toString();
    }
    
    public String getSortedPermutedColoredEdgeString(int[] p) {
        List<String> edgeStrings = new ArrayList<String>();
        for (Edge e : this.edges) {
            edgeStrings.add(e.getSortedPermutedColoredString(p, colors));
        }
        Collections.sort(edgeStrings);
        return edgeStrings.toString();
    }
    
    public String getSortedColorOnlyEdgeString() {
        List<String> edgeStrings = new ArrayList<String>();
        for (Edge e : this.edges) {
            edgeStrings.add(e.toSortedColorOnlyString(colors));
        }
        Collections.sort(edgeStrings);
        return edgeStrings.toString();
    }
    
    public String getColorOnlyEdgeString() {
        List<String> edgeStrings = new ArrayList<String>();
        for (Edge e : this.edges) {
            edgeStrings.add(e.toSortedColorOnlyString(colors));
        }
        return edgeStrings.toString();
    }
    
    public String getSortedColoredEdgeString() {
        List<String> edgeStrings = new ArrayList<String>();
        for (Edge e : this.edges) {
            edgeStrings.add(e.toSortedColoredString(colors));
        }
        Collections.sort(edgeStrings);
        return edgeStrings.toString();
    }
    
    public String getSortedPermutedEdgeString(int[] p) {
        List<String> edgeStrings = new ArrayList<String>();
        for (Edge e : this.edges) {
//            edgeStrings.add(e.getPermutedString(p));
            edgeStrings.add(e.getSortedPermutedString(p));
        }
        Collections.sort(edgeStrings);
        return edgeStrings.toString();
    }
    
    public String getSortedEdgeStringWithEdgeOrder() {
        List<String> edgeStrings = new ArrayList<String>();
        for (Edge e : this.edges) {
            edgeStrings.add(e.toSortedStringWithEdgeOrder());
        }
        Collections.sort(edgeStrings);
        return edgeStrings.toString();
    }
    
    public String getSortedEdgeString() {
        List<Edge> sortedEdges = new ArrayList<Edge>(edges);
        Collections.sort(sortedEdges);
        List<String> edgeStrings = new ArrayList<String>();
        for (Edge e : sortedEdges) {
            edgeStrings.add(e.toSortedString());
        }
        return edgeStrings.toString();
    }
    
    public List<Edge> getSortedEdges() {
        List<Edge> sortedEdges = new ArrayList<Edge>(edges);
        Collections.sort(sortedEdges);
        return sortedEdges;
    }
    
    public String getPermutedEdgeString(int[] p) {
        List<String> edgeStrings = new ArrayList<String>();
        for (Edge e : this.edges) {
            edgeStrings.add(e.getSortedPermutedString(p));
        }
        return edgeStrings.toString();
    }
    
    public boolean edgesInOrder() {
        if (this.edges.size() == 0) return true;
        String edgeString = this.edges.get(0).toString();
        for (int i = 1; i < this.edges.size(); i++) {
            String current = this.edges.get(i).toString(); 
            if (current.compareTo(edgeString) < 0) {
                return false;
            }
            edgeString = current; 
        }
        return true;
    }
    
    public boolean colorsInOrder() {
        int prev = -1;
        for (int color : colors) {
            if (color >= prev) {
                prev = color;
            } else {
                return false;
            }
        }
        return true;
    }
    
    public String toStringIncludingSize() {
    	return edges.toString() + " " + maxVertexIndex;
    }

    public String toString() {
        return edges.toString();
    }

    public int degree(int i) {
        int degree = 0;
        for (Edge edge : edges) {
            if (edge.a == i || edge.b == i) {
                degree++;
            }
        }
        return degree;
    }
    
    public Graph removeLastEdge() {
    	
    	// TODO : more efficiently
    	List<String> edgeStrings = new ArrayList<String>();
    	for (Edge edge : edges) { 
    		edgeStrings.add(edge.toSortedString());
    	}
    	Collections.sort(edgeStrings);
    	int l = edgeStrings.size();
    	int n = getVertexCount();
    	Graph g = new Graph();
    	BitSet connected = new BitSet(n);
    	for (int i = 0; i < l - 1; i++) {
    		String[] bits = edgeStrings.get(i).split(":");
    		int a = Integer.parseInt(bits[0].trim());
    		int b = Integer.parseInt(bits[1].trim());
    		if (a < b) {
    			g.makeEdge(a, b);
    		} else {
    			g.makeEdge(b, a);
    		}
    		connected.set(a);
    		connected.set(b);
    	}
    	if (connected.cardinality() == n) {
    		return g;
    	} else {
    		int[] map = new int[n];
    		int offset = 0;
    		for (int i = 0; i < n; i++) {
    			if (connected.get(i)) {
    				map[i] = i - offset;
    			} else {
    				map[i] = -1;
    				offset++;
    			}
    		}
    		for (Edge e : g.edges) {
    			e.a = map[e.a];
    			e.b = map[e.b];
    		}
    		g.maxVertexIndex = connected.cardinality() - 1;
    		return g;
    	}
    }

    public Graph remove(Edge edgeToRemove) {
        Graph copy = new Graph();
        for (Edge edge : this.edges) {
            if (edge == edgeToRemove) {
                continue;
            } else {
                copy.makeEdge(edge.a, edge.b);
            }
        }
        return copy;
    }

    public Partition getColorsAsPartition() {
        Partition p = new Partition();
        for (int index = 0; index <= maxVertexIndex; index++) {
            int color = colors.get(index);
            if (color > p.size() - 1) {
                p.addCell(index);
            } else {
                p.addToCell(color, index);
            }
        }
        return p;
    }

	public Graph getPermutedGraph(Permutation permutation) {
		return getPermutedGraph(permutation.getValues());
	}

	public boolean hasEdge(int i, int j) {
		return getEdge(i, j) != null;
	}

	public Map<Integer, List<Integer>> getConnectionTableCopy() {
		return calculateConnectionTable();
	}

    public int getEdgeIndex(int a, int b) {
        for (int index = 0; index < edges.size(); index++) {
            Edge e = edges.get(index); 
            if ((e.a == a && e.b == b) || (e.b == a && e.a == b)) {
                return index;
            }
        }
        return -1;
    }

    public int esize() {
        return edges.size();
    }

    public int vsize() {
        return getVertexCount();
    }
    
}
