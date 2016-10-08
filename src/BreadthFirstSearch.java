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
        ArrayList<Node> closedList = new ArrayList<>();

        queue.add(startNode);
        startNode.visited = true;

        //check if element is goal state
        if (startNode.isGoalState()) {
            System.out.println("================");
            System.out.println("GOAL STATE FOUND");
            System.out.println("================");
            printPath(startNode);
            return;
        }

        while (!queue.isEmpty()) {
            Node element = queue.poll();

            System.out.println("");
            System.out.println("CURRENT NODE: ");
            System.out.println("============");
            System.out.println(element.toString(false));
            System.out.println("");



            closedList.add(element);
            ArrayList<Node> childNodes = element.getChildNodes();
            //TODO num generated nodes

            for (Node node : childNodes) {
                if (node != null && !node.visited && !closedList.contains(node)) {
                    queue.add(node);
                    node.visited = true;
                    this.parentMap.put(node, element);

                    //check if element is goal state
                    if (node.isGoalState()) {
                        System.out.println("================");
                        System.out.println("GOAL STATE FOUND");
                        System.out.println("================");
                        printPath(node);
                        return;
                    }
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
