package graph.model;

public class Vertex {
	
	private int index;
	
	private String label;
	
	public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Vertex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Vertex) {
			Vertex o = (Vertex) obj;
			return o.index == index;
		}
		return false;
	}
	
	public int hashCode() {
		return index;
	}
	
	public String toString() {
		return String.valueOf(index);
	}

}
