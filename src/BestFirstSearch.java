import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by NSPACE on 10/2/2016.
 */
public class BestFirstSearch extends SearchAlgorithm {

    public BestFirstSearch(Node startNode) {
        super(startNode);
    }

    @Override
    public void searchTree(Node startNode) {
        Comparator<Node> bestFirstComparator = new BestFirstComparator();
        Queue<Node> openList = new PriorityQueue<Node>(bestFirstComparator);
        ArrayList<Node> closedList = new ArrayList<>();

        openList.add(startNode);
        startNode.visited = true;

        while (!openList.isEmpty()) {
            Node element = openList.poll();

            System.out.println("");
            System.out.println("CURRENT NODE: ");
            System.out.println("============");
            System.out.println(element.toString(false));
            System.out.println("");

            //check if element is goal state
            if (element.isGoalState()) {
                System.out.println("================");
                System.out.println("GOAL STATE FOUND");
                System.out.println("================");
                printPath(element);
                return;
            }
            closedList.add(element);
            ArrayList<Node> childNodes = element.getChildNodes();

            for (Node node : childNodes) {
                if (node != null && !node.visited && !closedList.contains(node)) {
                    openList.add(node);
                    node.visited = true;
                    this.parentMap.put(node, element);
                }
            }

            System.out.println("     OPEN LIST NODES:");
            System.out.println("     ===============");

            for (Node node : openList) {
                System.out.println(node.toString(true));
            }
        }
    }
}
