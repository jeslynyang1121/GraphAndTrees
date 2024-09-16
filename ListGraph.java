package Project_4.src;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;

// Collaborators: none
// Resources: Piazza, Project 4 Handout

// Node class
class Node {
    public int val;		  // value of node
    public Node next;     // pointer to next node in linked list

    // Star constructor
    public Node(int val) {
        this.val = val;
    }
}

// ListGraph class
public class ListGraph {

    public Node[] edgeList;        // array of linked nodes to act as adjacency list
    public int[] weights;          // array of weights that corresponds to nodes 
    public int totalNumEdges;      // total number of edges in graph (undirected counts as 2)
    public int numVertices;        // total number of vertices in graph
    
    // constructor for non-weighted graph
    public ListGraph(int size) {
        edgeList = new Node[size];
        totalNumEdges = 0;
        numVertices = size;
    }

    // constructor for weighted graph
    public ListGraph(int size, boolean isWeighted) {
        edgeList = new Node[size];
        weights = new int[size];
        totalNumEdges = 0;
        numVertices = size;
    }

    /* Read a non-weighted graph */
    public static ListGraph read(String filepath) throws IOException, FileNotFoundException {
        
        // open file and create ListGraph object
        FileInputStream fIS = new FileInputStream(filepath);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(fIS));
        
        int numVert = Integer.parseInt(bfr.readLine());
        ListGraph graph = new ListGraph(numVert);
        
        // go through every node in list to add their edges to list
        for (int i = 0; i < numVert; i++) {
            String[] edges = bfr.readLine().split(" ");

            // if node has edges, then add first edge to edgeList
            Node prevNode = null;
            if (!edges[0].equals("")) {
                prevNode = new Node( Integer.parseInt(edges[0]) );
            }
            graph.edgeList[i] = prevNode;
            
            // add other edges as next nodes to first node 
            for (int j = 1; j < edges.length; j++) {
                Node n = new Node( Integer.parseInt(edges[j]) );
                prevNode.next = n;
                prevNode = n;
            }
            graph.totalNumEdges += edges.length;
        }
        return graph;
    }
    
    /* Read a weighted graph */
    public static ListGraph readWeighted(String filepath) throws IOException {
        // open file and create ListGraph object
        FileInputStream fIS = new FileInputStream(filepath);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(fIS));

        int numVert = Integer.parseInt(bfr.readLine());
        ListGraph graph = new ListGraph(numVert, true);

        // add weights of every node into weights array
        String[] weightsAsStrings = bfr.readLine().split(" ");
        for (int i = 0; i < numVert; i++) {
            graph.weights[i] = Integer.parseInt(weightsAsStrings[i]);
        }

        // go through every node in list to add their edges to list
        for (int i = 0; i < numVert; i++) {
            String[] edges = bfr.readLine().split(" ");

            // if node has edges, then add first edge to edgeList
            Node prevNode = null;
            if (edges.length > 0) {
                prevNode = new Node( Integer.parseInt(edges[0]) );
            }
            graph.edgeList[i] = prevNode;

            // add other edges as next nodes to first node 
            for (int j = 1; j < edges.length; j++) {
                Node n = new Node( Integer.parseInt(edges[j]) );
                prevNode.next = n;
                prevNode = n;
            }
            graph.totalNumEdges += edges.length;
        }
        return graph;
    }
}


