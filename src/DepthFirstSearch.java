import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by NSPACE on 10/2/2016.
 */
public class DepthFirstSearch extends SearchAlgorithm {

    public DepthFirstSearch(Node startNode) {
        super(startNode);
    }

    @Override
    public void searchTree(Node startNode) {
        Stack<Node> openList = new Stack<Node>();
        ArrayList<Node> closedList = new ArrayList<>();

        openList.add(startNode);
        startNode.visited = true;

        //check if element is goal state
        if(startNode.isGoalState()) {
            System.out.println("================");
            System.out.println("GOAL STATE FOUND");
            System.out.println("================");
            printPath(startNode);
            return;
        }

        while (!openList.isEmpty()) {
            Node element = openList.pop();

            System.out.println("");
            System.out.println("CURRENT NODE: ");
            System.out.println("============");
            System.out.println(element.toString(false));
            System.out.println("");



            closedList.add(element);
            ArrayList<Node> childNodes = element.getChildNodes();

            for (Node node : childNodes) {
                if (node != null && !node.visited && !closedList.contains(node)) {
                    openList.add(node);
                    node.visited = true;
                    this.parentMap.put(node,element);

                    //check if element is goal state
                    if(node.isGoalState()) {
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

            for (Node node : openList) {
                System.out.println(node.toString(true));
            }
        }

    }
}
