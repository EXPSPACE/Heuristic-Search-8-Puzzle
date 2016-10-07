import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by NSPACE on 10/2/2016.
 */
public class BreadthFirstSearch extends SearchAlgorithm {

    public BreadthFirstSearch(Node startNode) {
        super(startNode);
    }

    @Override
    public void searchTree(Node startNode) {
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(startNode);
        startNode.visited = true;


        while (!queue.isEmpty()) {
            Node element = queue.poll();




            System.out.println("");
            System.out.println("CURRENT NODE: ");
            System.out.println("============");
            System.out.println(element.toString(false));
            System.out.println("");

            //check if element is goal state
            if(element.isGoalState()) {
                System.out.println("================");
                System.out.println("GOAL STATE FOUND");
                System.out.println("================");
                printPath(element);
                return;
            }

            //TODO: add to closed list avoid repeats
            ArrayList<Node> childNodes = element.getChildNodes();
            queue.c
            for (Node node : childNodes) {
                //TODO: check not in closed list
                if (node != null && !node.visited) {
                    queue.add(node);
                    node.visited = true;
                    this.parentMap.put(node,element);
                }
            }

            System.out.println("     OPEN LIST NODES:");
            System.out.println("     ===============");

            for (Node node : queue) {
                System.out.println(node.toString(true));
            }

        }
    }
}
