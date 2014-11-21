package graph.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphFileReader implements Iterable<IntGraph> {
    
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

	public Iterator<IntGraph> iterator() {
        List<IntGraph> graphs = new ArrayList<IntGraph>();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() == 0) continue;
                if (fieldIndex == 0) {
                    graphs.add(new IntGraph(line));
                } else {
                    graphs.add(new IntGraph(line.split("\t")[fieldIndex]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return graphs.iterator();
    }
	
	public static List<IntGraph> readAll(String filename) throws FileNotFoundException {
	    return GraphFileReader.readAll(new File(filename));
	}
    
    public static List<IntGraph> readAll(File file) throws FileNotFoundException {
    	GraphFileReader reader = new GraphFileReader(new FileReader(file));
    	List<IntGraph> graphs = new ArrayList<IntGraph>();
    	for (IntGraph graph : reader) {
    		graphs.add(graph);
    	}
    	return graphs;
    }
    
    public void close() throws IOException {
        reader.close();
    }

}
