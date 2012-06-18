package model;

public class Edge {
    
    private int a;
    
    private int b;
    
    public Edge(int a, int b) {
        this.a = a;
        this.b = b;
    }
    
    public boolean contains(int v) {
        return a == v || b == v;
    }
    
    public int getOther(int v) {
        if (a == v) return b;
        if (b == v) return a;
        return -1;
    }

    public String toString() {
        return this.a + "-" + this.b;
    }
}
