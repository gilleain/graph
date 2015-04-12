package graph.model;

import graph.visitor.DFSVisitor;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VertexGraph implements Graph, Iterable<Vertex>, Layoutable {
	
	protected List<Vertex> vertices;
	
	protected List<Edge> edges;
	
	public VertexGraph() {
		this(new ArrayList<Vertex>(), new ArrayList<Edge>());
	}
	
	public VertexGraph(int vCount) {
		this();
		for (int index = 0; index < vCount; index++) {
			vertices.add(new Vertex(index));
		}
	}
	
	public VertexGraph(List<Vertex> vertices) {
		this(vertices, new ArrayList<Edge>());
	}
	
	public VertexGraph(List<Vertex> vertices, List<Edge> edges) {
	    if (edges == null) {
	        this.edges = new ArrayList<Edge>();
	    } else {
	        this.edges = edges;
	    }
		if (vertices == null) {
			this.vertices = new ArrayList<Vertex>();
			for (Edge edge : edges) {
				if (!this.vertices.contains(edge.getA())) {
					this.vertices.add(edge.getA());
				}
				if (!this.vertices.contains(edge.getB())) {
					this.vertices.add(edge.getB());
				}
			}
		} else {
			this.vertices = vertices;
		}
	}
	
	// XXX - nasty - why are these two classes not unified?
	public VertexGraph(IntGraph graph) {
	    
        BitSet usedVertices = new BitSet(graph.getVertexCount());
        for (IntEdge e : graph.edges) {
            usedVertices.set(e.a);
            usedVertices.set(e.b);
        }
        
        this.vertices = new ArrayList<Vertex>(); 
        for (int i = 0; i < graph.getVertexCount(); i++) {
            if (usedVertices.get(i)) {
                vertices.add(new Vertex(i));
            }
        }
        
        this.edges = new ArrayList<Edge>();
        for (IntEdge e : graph.edges) {
            add(getVertexWithIndex(e.a), getVertexWithIndex(e.b));
        }
	}
	
	public IntGraph asGraph() {
	    IntGraph graph = new IntGraph();
	    for (Edge e : edges) {
	        graph.makeEdge(e.getA().getIndex(), e.getB().getIndex());
	    }
        return graph;
    }
	
	public int getEdgeCount() {
	 return edges.size();   
	}

	@Deprecated
	public int esize() {
	    return getEdgeCount();
	}

	@Deprecated
	public int vsize() {
		return getVertexCount();
	}
	
	public int getVertexCount() {
	    return vertices.size();
	}
	
	public int indexOf(Vertex v) {
	    int index = 0;
	    for (Vertex o : vertices) {
	        if (v.equals(o)) {
	            return index;
	        }
	        index++;
	    }
	    return -1;
	}
	
	public void add(Vertex v) {
		if (vertices.contains(v)) {
			return;
		} else {
			vertices.add(v);	/// yeah, yeah, we could use sets.
		}
	}

	public void add(int ai, int bi) {
		add(vertices.get(ai), vertices.get(bi));
	}
	
	public void add(int ai, int...bs) {
		for (int bi : bs) {
			add(ai, bi);
		}
	}
	
	public void add(Vertex a, Vertex b) {
		edges.add(new Edge(a, b));
	}
	
	public Vertex getVertex(int i) {
		return vertices.get(i);
	}
	
	public Vertex getVertexWithIndex(int i) {
		for (Vertex v : vertices) {
			if (v.getIndex() == i) {
				return v;
			}
		}
		return null;
	}
	
	public void accept(DFSVisitor visitor) {
		for (Vertex v : vertices) {
			if (visitor.seen(v)) {
				continue;
			} else {
				visitor.visit(this, v);
			}
		}
		visitor.reset();
	}
	
	public List<Vertex> getConnected(Vertex v) {
		List<Vertex> connected = new ArrayList<Vertex>();
		for (Edge e : edges) {
			Vertex o = e.other(v);
			if (o != null) {
				connected.add(o);
			}
		}
		return connected;
	}
	
	public List<Integer> getConnectedIndices(Vertex v) {
	    List<Integer> connected = new ArrayList<Integer>();
	    for (Edge e : edges) {
            Vertex o = e.other(v);
            if (o != null) {
                connected.add(vertices.indexOf(o));
            }
        }
        return connected;
    }

    /**
	 * Find this graph minus another, i.e. the vertices and edges that are in this but not in the other.
	 * 
	 * @param other
	 * @return
	 */
	public VertexGraph difference(VertexGraph other) {
		VertexGraph diff = new VertexGraph();
		for (Edge e : edges) {
			if (other.edges.contains(e)) {
				continue;
			} else {
				diff.add(e.getA());
				diff.add(e.getB());
				diff.edges.add(e);
			}
		}
		return diff;
	}
	
	public boolean connectedTo(VertexGraph other) {
	    for (Vertex v : vertices) {
	        if (other.hasVertex(v)) {
	            return true;
	        }
	    }
	    return false;
	}
	
	public Iterator<Vertex> iterator() {
		return vertices.iterator();
	}
	
	public String toString() {
		return vertices + "|" + edges;
	}

	public boolean hasVertex(Vertex other) {
		return vertices.contains(other);
	}
	
	public boolean isEmpty() {
		// WARNING : will return true even if there are edge but no vertices...
		return vertices.isEmpty();
	}
	
	public boolean hasEdge(int i, int j) {
		Vertex vi = getVertex(i);
		Vertex vj = getVertex(j);
		return hasEdge(vi, vj);
	}
	
	public boolean hasEdge(Vertex a, Vertex b) {
		for (Edge e : edges) {
		    if (e.contains(a) && e.contains(b)) {
		        return true;
		    }
		}
		return false;
	}

	public Edge getEdge(Vertex a, Vertex b) {
		for (Edge e : edges) {
			if (e.other(a) == b) return e;
		}
		return null;
	}
	
	public boolean hasEdge(Edge e) {
		return edges.contains(e);
	}

	/**
	 * Necessary because when removing edge-bridges from the graph diff, 
	 * some vertices may become disconnected. There may be a better way to handle this.
	 * 
	 * @param vI
	 */
	public void removeIfDisconnected(Vertex v) {
		for (Edge e : edges) {
			if (e.contains(v)) {
				return;
			}
		}
		vertices.remove(v);
	}
	
	public List<Edge> getEdges() {
        return edges;
    }
	
	public List<Vertex> getVertices() {
	    return vertices;
	}

	@Override
	public List<Integer> getConnected(int vertex) {
	    return getConnectedIndices(getVertex(vertex));
	}
	
	@Override
	public Map<Integer, List<Integer>> getConnectionTable() {
	    Map<Integer, List<Integer>> table = new HashMap<Integer, List<Integer>>();
	    for (Vertex v : vertices) {
	        List<Integer> connected = getConnectedIndices(v);
	        table.put(v.getIndex(), connected);
	    }
	    return table;
	}
	
	public void makeEdge(int vertexI, int vertexJ) {
	    edges.add(new Edge(getVertexWithIndex(vertexI), getVertexWithIndex(vertexJ)));
	}

    @Override
    public void addLabel(int vertex, String label) {
        vertices.get(vertex).setLabel(label);
    }

    @Override
    public String getLabel(int i) {
        return vertices.get(i).getLabel();
    }

    @Override
    public int getEdgeColor(int vertexIndex, int otherVertexIndex) {
        // TODO
        return -1;
    }

    @Override
    public int degree(int index) {
        int degree = 0;
        Vertex vertexToSearchFor = new Vertex(index);
        for (Edge e : edges) {
            if (e.contains(vertexToSearchFor)) degree++;
        }
        return degree;
    }

}
