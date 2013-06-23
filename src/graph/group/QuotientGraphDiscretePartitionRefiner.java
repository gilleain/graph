package graph.group;

import group.AbstractDiscretePartitionRefiner;

public class QuotientGraphDiscretePartitionRefiner extends AbstractDiscretePartitionRefiner {
    
    private GraphQuotientGraph gq;
    
    private int[][] connectionTable;
    
    private int[][] edgeColors;

    public QuotientGraphDiscretePartitionRefiner(GraphQuotientGraph gq) {
        this.gq = gq;
    }
    
    @Override
    public int getVertexCount() {
        return gq.getVertexCount();
    }

    @Override
    public int getConnectivity(int i, int j) {
        return gq.isConnected(i, j)? 1 : 0;
    }
    
    private void setupConnectionTable(GraphQuotientGraph gq) {
        int vertexCount = gq.getVertexCount();
        connectionTable = new int[vertexCount][];
        
        for (int i = 0; i < vertexCount; i++) {
            int conn = 0;
            for (int j = 0; j < vertexCount; j++) {
                if (gq.isConnected(i, j)) {
                    conn++;
                }
            }
            connectionTable[i] = new int[conn];
            edgeColors[i] = new int[conn];
            
            int arrayIndex = 0;
            for (int j = 0; j < vertexCount; j++) {
                if (gq.isConnected(i, j)) {
                    connectionTable[i][arrayIndex] = j;
                    edgeColors[i][arrayIndex] = gq.getEdgeMultiplicity(i, j);
                    arrayIndex++;
                }
            }
        }
    }
    
}
