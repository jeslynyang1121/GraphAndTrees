package Project_4.src;

// Collaborators: none
// Resources: Piazza

public class MaxWeight {

    private boolean[] marked;

    public MaxWeight(int size) {
        marked = new boolean[size];
    }
    
    private int[] computeWeightsChain(ListGraph a, int u, int end, int[] prevWeights, int actualMax) {
        marked[u] = true;
        Node uNode = a.edgeList[u];

        // calculate 3 possible weights for Node u 
        int[] currWeights = {( a.weights[u] + Math.max(prevWeights[1], prevWeights[2]) ),
                prevWeights[0], prevWeights[1]};
        // keeping track of actual max weight, usually if last node is not added
        actualMax = Math.max(currWeights[0], actualMax);

        if (u == end) {
            // base case, when Node u is the other leaf node
            currWeights[0] = actualMax;
            return currWeights;
        } else if (!marked[uNode.val]) {
            // first node connected to Node u has not been visited yet
            return computeWeightsChain(a, uNode.val, end, currWeights, actualMax);
        } else {
            // first node connected to Node u has been visited but second node has not
            return computeWeightsChain(a, uNode.next.val, end, currWeights, actualMax);
        }
    }
    
    /* Compute max weighted path in a chain */
    public static int maxWeightChain(ListGraph a) {
        MaxWeight mw = new MaxWeight(a.numVertices);
        // find 2 leafNodes that computeWeightsChain function starts and ends with
        int indexOfStart = -1;
        int indexOfEnd = -1;
        for (int i = 0; i < a.numVertices; i++) {
            int numOfEdges = 0;
            Node n = a.edgeList[i];
            while ((n != null) && (numOfEdges < 1)) {
                numOfEdges++;
                n = n.next;
            }
            if (n == null) {
                if (indexOfStart == -1) {
                    // first index with only 1 edge will be starting node
                    indexOfStart = i;
                } else {
                    // second index with only 1 edge will be ending node
                    indexOfEnd = i;
                    break;
                }
            }
        }
        int[] possibleWeights = mw.computeWeightsChain(a, indexOfStart, indexOfEnd, new int[] {0, 0, 0}, 0);
        return possibleWeights[0];
    }

    private int[] computeWeightsTree(ListGraph a, int u) {
        marked[u] = true;
        Node uNode = a.edgeList[u];

        // calculate 4 possible weights for Node u 
        int[] childWeights;
        int[] currWeights = new int[4];

        // base case, when Node u is the other leaf node
        if ((uNode.next == null) && (marked[uNode.val])) {
            return new int[] {0, a.weights[u], 0, 0};
        } 
        
        while (uNode != null) {
            if (!marked[uNode.val]) {
                // first node connected to Node u has not been visited yet
                childWeights = computeWeightsTree(a, uNode.val);
                
                // calculate node's path weight based on each child's weight;
                currWeights[0] = Math.max(currWeights[0], childWeights[0]);
                currWeights[0] = Math.max(currWeights[0], (currWeights[1] + Math.max(childWeights[2], childWeights[3])) );
                currWeights[0] = Math.max(currWeights[0], (currWeights[2] + Math.max(childWeights[1], childWeights[2])) );
                currWeights[0] = Math.max(currWeights[0], (currWeights[3] + childWeights[1]) );
                currWeights[1] = Math.max(currWeights[1], ( a.weights[u] + Math.max(childWeights[2], childWeights[3]) ));
                currWeights[2] = Math.max(currWeights[2], childWeights[1]);
                currWeights[3] = Math.max(currWeights[3], childWeights[2]);
            }
            uNode = uNode.next;
        }
        return currWeights;
    }

    /* Compute max weighted path in a tree */
    public static int maxWeightTree(ListGraph a) {
        // run helper function to find possibleWeights
        MaxWeight mw = new MaxWeight(a.numVertices);
        int[] possibleWeights = mw.computeWeightsTree(a, 0);
        
        // get max of all possible weights
        int actualMax = Math.max(possibleWeights[0], possibleWeights[1]);
        actualMax = Math.max(possibleWeights[2], actualMax);
        actualMax = Math.max(possibleWeights[3], actualMax);
        
        return actualMax;
    }

}
