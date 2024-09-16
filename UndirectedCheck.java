package Project_4.src;

// Collaborators: none
// Resources: Piazza, Project 4 Handout, Week 9 Slides

public class UndirectedCheck {
    
    private boolean[] marked;
    
    public UndirectedCheck(int size, boolean isTreeCheck) {
        marked = new boolean[size];
    }
    
    // recursive DFS from Week 9 Slides
    private void dfs (ListGraph G, int v) {
        marked[v] = true;
        Node vNode = G.edgeList[v];
        while (vNode != null) {
            if (!marked[vNode.val]) {
                dfs(G, vNode.val);
            } else {
                vNode = vNode.next;
            }
        }
    }
    
    /* Check whether the given undirected graph is a tree 
    *  Tree: graph with n-1 edges and is connected but has no cycles */
    public static boolean treeCheck(ListGraph a) {
        // check if there are n-1 edges and can assume graph is undirected else it is not a tree
        if ((a.totalNumEdges / 2) != (a.numVertices - 1)) {
            return false;
        }

        // implements DFS if there are n-1 edges, stop when hit a already marked node to get O(n) time
        UndirectedCheck uc = new UndirectedCheck(a.numVertices, true);
        uc.dfs(a, 0);
        
        // if all nodes are not marked, it is not a tree
        for (int i = 0; i < a.numVertices; i++) {
            if (!uc.marked[i]) {
                return false;
            }
        }
        
        // graph is a tree if it passes all these conditions
        return true;
    }
    
    /* Count the distinct number of triangles for an undirected graph using adjacency matrix representation */
    public static int countTriangles(MatrixGraph a) {
        int numTriangles = 0;
        for (int x = 0; x < a.numVertices; x++) {
            for (int y = x + 1; y < a.numVertices; y++) {
                for (int z = y + 1; z < a.numVertices; z++) {
                    if ((a.edgeMatrix[x][y] == 1) && (a.edgeMatrix[y][z] == 1) && (a.edgeMatrix[z][x] == 1)) {
                        // edges (x,y), (y, z), and (z,x) are in G so they form a triangle
                        numTriangles++;
                    }
                }
            }
        }
        return numTriangles;
    }

    /* Clustering coefficient of the given vertex u */
    public static double vertexClusterCoeff(ListGraph a, int u) {
        double coeff;
        int degreeU = 0;
        int[] adj = new int[a.numVertices];
        
        // go through edgeList[u] to calculate degree(u) and mark all adjacent nodes into aux array with 1's
        Node n = a.edgeList[u];
        while (n != null) {
            adj[n.val] = 1;
            n = n.next;
            degreeU++;
        }
        if (degreeU < 2) {
            return 0.0;
        }

        // calculate number of mutual node friends among the ones in aux array (which are all connected to Node u)
        int numFriends = 0;
        // go through linked list of connected nodes for each node in aux array
        for (int i = 0; i < a.numVertices; i++) {
            if (adj[i] == 1) {
                Node v = a.edgeList[i];
                // go through all nodes connected to Node v to check if any are also in aux array
                while (v != null) {
                    // found mutual node friend
                    if (adj[v.val] == 1) {
                        numFriends++;
                    }
                    v = v.next;
                }
            }
        }

        // calculate and return vertex cluster coefficient
        coeff = (2 * (numFriends / 2)) / (double)(degreeU * (degreeU - 1));
        return coeff;
    }


    /*Clustering coefficient of the entire graph*/
    public static double graphClusterCoeff(ListGraph a) {
        double totalCoeff = 0.0;
        for (int i = 0; i < a.numVertices; i++) {
            totalCoeff += vertexClusterCoeff(a, i);
        }
        return totalCoeff / (double)(a.numVertices);
    }
}
