import java.util.Comparator;

/**
 * Created by NSPACE on 10/2/2016.
 */

public class AStarComparator implements Comparator<Node> {
    @Override
    public int compare(Node x, Node y) {
        if (x.getHeuristicValue() + x.distance < y.getHeuristicValue() + y.distance) {
            return -1;
        }
        if (x.getHeuristicValue() + x.distance > y.getHeuristicValue() + y.distance) {
            return 1;
        }
        return 0;
    }
}
