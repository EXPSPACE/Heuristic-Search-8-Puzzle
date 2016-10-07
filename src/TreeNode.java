import java.util.ArrayList;

/**
 * Created by NSPACE on 9/27/2016.
 */
public class TreeNode extends Node<TreeNode> {

    public ArrayList<TreeNode> childNodes;

    public int heuristicValue;
    public boolean isGoalState;

    public TreeNode(int label, boolean isGoalState, int parentDistance, int heuristicValue) {
        this.nodeID = label;
        this.isGoalState = isGoalState;
        this.parentDistance = parentDistance;
        this.heuristicValue = heuristicValue;
        this.childNodes = new ArrayList<>();
    }

    public void addChildNode(TreeNode node) {
        childNodes.add(node);
        node.distance = distance + node.parentDistance;
    }

    @Override
    public ArrayList<TreeNode> getChildNodes() {
        return childNodes;
    }

    @Override
    public boolean isGoalState() {
        return isGoalState;
    }

    @Override
    public int getHeuristicValue() {
        return heuristicValue;
    }

    @Override
    public String toString(boolean indent) {
        return "TreeNode { " +
                "nodeID ='" + nodeID + '\'' +
                ", parent distance = " + parentDistance +
                ", distance g(N) = " + distance +
                ", heuristic value h(N) = " + heuristicValue +
                " }";
    }
}
