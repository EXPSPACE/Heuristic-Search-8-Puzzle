import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by NSPACE on 10/2/2016.
 */

//using strategy pattern for search algorithm
public abstract class SearchAlgorithm {

    public static final int BREADTH_SEARCH = 1;
    public static final int DEPTH_SEARCH = 2;
    public static final int BEST_FIRST_SEARCH = 3;
    public static final int ASTAR_SEARCH = 4;

    public HashMap<Node, Node> parentMap;
    public static int nodeGenCount = 0;

    public SearchAlgorithm() {}

    public SearchAlgorithm(Node startNode) {
        this.parentMap = new HashMap<>();
        this.parentMap.put(startNode,null);
    }

    public abstract void searchTree(Node startNode);

    public void printPath(Node leafNode) {
        ArrayList<Node> nodeSequence = new ArrayList<>();

        System.out.println("PATH TO GOAL STATE: ");
        Node node = leafNode;
        while(node != null) {
            nodeSequence.add(node);

            node = parentMap.get(node);
        }

        Collections.reverse(nodeSequence);

        for(Node pathNode: nodeSequence) {
            System.out.println(pathNode.toString(false));
        }

        System.out.println();
        System.out.println("DISTANCE: " + leafNode.distance);
        System.out.println("NODES GENERATED: " + nodeGenCount);
    }
}
