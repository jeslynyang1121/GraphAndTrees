package Project_4.src;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;

// Collaborators: none
// Resources: Piazza, Project 4 Handout

public class MatrixGraph {

    public int[][] edgeMatrix;        // matrix of edges to act as adjacency matrix
    public int totalNumEdges;         // total number of edges in graph (undirected counts as 2)
    public int numVertices;        // total number of vertices in graph

    // constructor for non-weighted graph
    public MatrixGraph(int size) {
        edgeMatrix = new int[size][size];
        totalNumEdges = 0;
        numVertices = size;
    }
    
    /*Read the graph from the file*/
    public static MatrixGraph read(String filepath) throws IOException {
        // open file and create MatrixGraph object
        FileInputStream fIS = new FileInputStream(filepath);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(fIS));

        int numVert = Integer.parseInt(bfr.readLine());
        MatrixGraph graph = new MatrixGraph(numVert);

        // go through every node in list to add their edges to list
        for (int i = 0; i < numVert; i++) {
            String[] edges = bfr.readLine().split(" ");

            // add edges as 1's in matrix[u][v] 
            if (!edges[0].equals("")) {
                for (int j = 0; j < edges.length; j++) {
                    graph.edgeMatrix[i][Integer.parseInt(edges[j])] = 1;
                }
                graph.totalNumEdges += edges.length;
            } 
        }
        return graph;
    }

}
