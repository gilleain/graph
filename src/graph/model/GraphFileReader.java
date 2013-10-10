package graph.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphFileReader implements Iterable<Graph> {
    
    private BufferedReader reader;
    
    private int fieldIndex;
    
    public GraphFileReader(FileReader reader) {
        this(reader, 0);
    }
    
    public GraphFileReader(FileReader reader, int fieldIndex) {
        this.reader = new BufferedReader(reader);
        this.fieldIndex = fieldIndex;
    }

    public GraphFileReader(String string) throws FileNotFoundException {
    	this(new FileReader(string));
	}

	public Iterator<Graph> iterator() {
        List<Graph> graphs = new ArrayList<Graph>();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 0) continue;
                if (fieldIndex == 0) {
                    graphs.add(new Graph(line));
                } else {
                    graphs.add(new Graph(line.split("\t")[fieldIndex]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graphs.iterator();
    }
	
	public static List<Graph> readAll(String filename) throws FileNotFoundException {
	    return GraphFileReader.readAll(new File(filename));
	}
    
    public static List<Graph> readAll(File file) throws FileNotFoundException {
    	GraphFileReader reader = new GraphFileReader(new FileReader(file));
    	List<Graph> graphs = new ArrayList<Graph>();
    	for (Graph graph : reader) {
    		graphs.add(graph);
    	}
    	return graphs;
    }

}
