/**
 * Created by NSPACE on 10/2/2016.
 */
public class DriverTreeNode {

    public static void main(String[] args) {

        /**
         * Tree Node Search Test
         */

        TreeNode startNode = new TreeNode(0,false,0,0);

        TreeNode node1 = new TreeNode(1,false,4,5);
        TreeNode node2 = new TreeNode(2,false,5,6);
        TreeNode node3 = new TreeNode(3,false,2,3);
        TreeNode node4 = new TreeNode(4,false,3,1);
        TreeNode node5 = new TreeNode(5,true,1,0);

        startNode.addChildNode(node1);
        startNode.addChildNode(node2);

        node1.addChildNode(node3);
        node1.addChildNode(node4);

        node2.addChildNode(node5);


        System.out.println("BREADTH FIRST SEARCH");
        System.out.println("====================");
        SearchAlgorithm sa = new BreadthFirstSearch(startNode); //WORKING
        sa.searchTree(startNode);

        startNode.visited = false;
        node1.visited = false;
        node2.visited = false;
        node3.visited = false;
        node4.visited = false;
        node5.visited = false;
        sa.nodeGenCount = 0;

        System.out.println("DEPTH FIRST SEARCH");
        System.out.println("====================");
        sa = new DepthFirstSearch(startNode);
        sa.searchTree(startNode);

        startNode.visited = false;
        node1.visited = false;
        node2.visited = false;
        node3.visited = false;
        node4.visited = false;
        node5.visited = false;
        sa.nodeGenCount = 0;

        System.out.println("BEST FIRST SEARCH");
        System.out.println("====================");
        sa = new BestFirstSearch(startNode);
        sa.searchTree(startNode);

        startNode.visited = false;
        node1.visited = false;
        node2.visited = false;
        node3.visited = false;
        node4.visited = false;
        node5.visited = false;
        sa.nodeGenCount = 0;

        System.out.println("ASTAR FIRST SEARCH");
        System.out.println("====================");
        sa = new AStarSearch(startNode);
        sa.searchTree(startNode);
    }
}
