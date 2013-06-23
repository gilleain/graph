package graph.model;

import java.util.List;

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
//            return pa + "(" + colors.get(pa) + "):" + pb + "(" + colors.get(pb) + ")";
            return pa + "(" + colors.get(a) + "):" + pb + "(" + colors.get(b) + ")";
        } else {
//            return pb + "(" + colors.get(pb) + "):" + pa + "(" + colors.get(pa) + ")";
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
    
    public int hashCode() {
    	return (a + 1) * (b + 1);
    }
    
    public boolean equals(Object obj) {
    	if (obj instanceof Edge) {
    		Edge other = (Edge) obj;
    		return (a == other.a && b == other.b) || (a == other.b && b == other.a);
    	} else {
    		return false;
    	}
    }

    public boolean adjacent(Edge other) {
        return a == other.a || a == other.b || b == other.a || b == other.b;
    }
}
