package Project_4.src;

// Collaborators: none
// Resources: Piazza, Project 4 Handout, Week 10 Slides

public class DirectedCheck {

    private int [] inDeg;

    public DirectedCheck(int size, boolean isDagCheck) {
        inDeg = new int[size];
    }
    
    // recursive DFS from Week 9 Slides
    private void topSort (ListGraph G, int v) {
        if (inDeg[v] == 0) {
            // signify that Node i has been visited by giving it a degree of -1
            inDeg[v] = -1;

            Node vNode = G.edgeList[v];
            while (vNode != null) {
                inDeg[vNode.val]--;
                if (inDeg[vNode.val] == 0) {
                    topSort(G, vNode.val);
                }

                vNode = vNode.next;
            }
        }
    }
    
    /* Compute Indegree of every vertex in the graph */
    public static int[] computeInDegrees(ListGraph a) {
        int[] inDeg = new int[a.numVertices];
        for (int i = 0; i < a.numVertices; i++) {
            Node n = a.edgeList[i];
            while (n != null) {
                inDeg[n.val]++;
                n = n.next;
            }
        }
        return inDeg;
    }

    /* DAG Check - Determine if a given graph is a DAG */
    public static int[] dagCheck(ListGraph a) {
        int[] numSourceAndSink = {0, 0};
        
        // test if graph is a DAG through topological sorting
        DirectedCheck dc = new DirectedCheck(a.numVertices, true);
        dc.inDeg = computeInDegrees(a);
        
        // first count number of sources and sinks in O(n) in case graph G is a DAG
        for (int i = 0; i < a.numVertices; i++) {
            if (dc.inDeg[i] == 0) {
                // found source, do top sort at every source node
                numSourceAndSink[0]++;
                dc.topSort(a, i);
            } 
            if (a.edgeList[i] == null) {
                // found sink
                numSourceAndSink[1]++;
            } 
        }
        if (numSourceAndSink[0] == 0) {
            return new int[] {-1, -1};
        }

        // check if graph passed topological sorting
        for (int i = 0; i < a.numVertices; i++) {
            if (dc.inDeg[i] != -1) {
                // graph did not pass topological sorting and therefore is not a DAG
                return new int[] {-1, -1};
            }
        }

        return numSourceAndSink;
    }

}
