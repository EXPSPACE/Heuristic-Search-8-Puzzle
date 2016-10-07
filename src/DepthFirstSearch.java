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
        Stack<Node> stack = new Stack<Node>();
        stack.add(startNode);
        startNode.visited = true;

        while (!stack.isEmpty()) {
            Node element = stack.pop();

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

            ArrayList<Node> childNodes = element.getChildNodes();
            for (Node node : childNodes) {
                if (node != null && !node.visited) {
                    stack.add(node);
                    node.visited = true;
                    this.parentMap.put(node,element);
                }
            }

            System.out.println("     OPEN LIST NODES:");
            System.out.println("     ===============");

            for (Node node : stack) {
                System.out.println(node.toString(true));
            }
        }

    }
}
