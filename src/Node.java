import java.util.ArrayList;
import java.util.List;

/**
 * Created by NSPACE on 9/27/2016.
 */
public abstract class Node<Type> {
    public boolean visited = false;
    public int distance;
    public int parentDistance;
    public int nodeID;
    public abstract ArrayList<Type> getChildNodes();
    public abstract boolean isGoalState();
    public abstract int getHeuristicValue();
    public abstract String toString(boolean indent);
}
