import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NSPACE on 9/27/2016.
 */
public class Board extends Node<Board> {

    public static final int DIMENSION = 3;
    public static final int HEURISTIC_HAMMING = 1;
    public static final int HEURISTIC_MANHATTAN = 2;
    public static final int HEURISTIC_MIN_HAM_MAN = 3;
    public static final int HEURISTIC_NOT_ADMISSIBLE = 4;
    public static final int HEURISTIC_PERMUTATION = 5;

    //final goal state for each board - 0 represents the blank state
    public static final int[][] GOAL_STATE = new int[][]{
            {1, 2, 3},
            {8, 0, 4},
            {7, 6, 5}};
    //maps the value of a tile to the x,y coordinates it should be at in the goal state
    public static final HashMap<Integer, Integer[]> GOAL_COORDINATE_MAP = new HashMap<>();
    public static final HashMap<Integer, Integer> INDEX_MAP = new HashMap<>();
    public static String HEURISTIC_STRING = null;
    public static HeuristicFunction heuristicFunction = null;
    public static int NODE_ID_COUNTER = 0;

    static {
        GOAL_COORDINATE_MAP.put(1, new Integer[]{0, 0});
        GOAL_COORDINATE_MAP.put(2, new Integer[]{0, 1});
        GOAL_COORDINATE_MAP.put(3, new Integer[]{0, 2});
        GOAL_COORDINATE_MAP.put(8, new Integer[]{1, 0});
        GOAL_COORDINATE_MAP.put(4, new Integer[]{1, 2});
        GOAL_COORDINATE_MAP.put(7, new Integer[]{2, 0});
        GOAL_COORDINATE_MAP.put(6, new Integer[]{2, 1});
        GOAL_COORDINATE_MAP.put(5, new Integer[]{2, 2});
    }

    //local variables
    public int[][] state;
    public int blank_x;
    public int blank_y;
    public String moveString;
    public int parentNodeID;
    public int heuristicValue;

    public Board() {
    }

    public Board(int blank_x, int blank_y, int distance, String moveString, int nodeID, int parentNodeID) {
        this.distance = distance + 1;
        this.parentDistance = 1;
        this.state = new int[DIMENSION][DIMENSION];
        this.blank_x = blank_x;
        this.blank_y = blank_y;
        this.moveString = moveString;
        this.nodeID = nodeID;
        this.parentNodeID = parentNodeID;
    }

    //finds all legal moves depending on x,y coordinates of the blank tile
    @Override
    public ArrayList<Board> getChildNodes() {
        ArrayList<Board> childBoards = new ArrayList<>();

        //blank up move
        if (blank_x - 1 >= 0 && moveString != "DOWN") {
            Board childBoard = new Board(blank_x - 1, blank_y, distance, "UP", ++NODE_ID_COUNTER, nodeID);
            copyState(childBoard);

            //swap blank and top tile in child board
            childBoard.state[blank_x][blank_y] = state[blank_x - 1][blank_y];
            childBoard.state[blank_x - 1][blank_y] = 0;

            if (heuristicFunction != null) {
                childBoard.heuristicValue = heuristicFunction.getHeuristicValue(childBoard);
            }

            childBoards.add(childBoard);
        }

        //blank down move
        if (blank_x + 1 < DIMENSION && moveString != "UP") {
            Board childBoard = new Board(blank_x + 1, blank_y, distance, "DOWN", ++NODE_ID_COUNTER, nodeID);
            copyState(childBoard);

            //swap blank and top tile in child board
            childBoard.state[blank_x][blank_y] = state[blank_x + 1][blank_y];
            childBoard.state[blank_x + 1][blank_y] = 0;

            if (heuristicFunction != null) {
                childBoard.heuristicValue = heuristicFunction.getHeuristicValue(childBoard);
            }

            childBoards.add(childBoard);

        }

        //blank right move
        if (blank_y + 1 < DIMENSION && moveString != "LEFT") {
            Board childBoard = new Board(blank_x, blank_y + 1, distance, "RIGHT", ++NODE_ID_COUNTER, nodeID);
            copyState(childBoard);

            //swap blank and top tile in child board
            childBoard.state[blank_x][blank_y] = state[blank_x][blank_y + 1];
            childBoard.state[blank_x][blank_y + 1] = 0;

            if (heuristicFunction != null) {
                childBoard.heuristicValue = heuristicFunction.getHeuristicValue(childBoard);
            }

            childBoards.add(childBoard);
        }

        //blank left move
        if (blank_y - 1 >= 0 && moveString != "RIGHT") {
            Board childBoard = new Board(blank_x, blank_y - 1, distance, "LEFT", ++NODE_ID_COUNTER, nodeID);
            copyState(childBoard);

            //swap blank and top tile in child board
            childBoard.state[blank_x][blank_y] = state[blank_x][blank_y - 1];
            childBoard.state[blank_x][blank_y - 1] = 0;

            if (heuristicFunction != null) {
                childBoard.heuristicValue = heuristicFunction.getHeuristicValue(childBoard);
            }

            childBoards.add(childBoard);
        }

        return childBoards;
    }

    public void copyState(Board childBoard) {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                childBoard.state[i][j] = state[i][j];
            }
        }
    }

    @Override
    public boolean isGoalState() {
        boolean isGoalState = true;
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (state[i][j] != GOAL_STATE[i][j]) {
                    isGoalState = false;
                }
            }
        }
        return isGoalState;
    }

    @Override
    public int getHeuristicValue() {
        return heuristicValue;
    }

    @Override
    public String toString(boolean indent) {
        String indentSpace = "";
        if (indent) {
            indentSpace = "     ";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n" + indentSpace + "-----" + "\n" + indentSpace);
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                sb.append(state[i][j] + " ");
            }
            sb.append("\n" + indentSpace);
        }
        sb.append("-----");
        sb.append("\n" + indentSpace + "Node ID: " + nodeID);
        sb.append("\n" + indentSpace + "Move: " + moveString);
        sb.append("\n" + indentSpace + "Parent node ID: " + parentNodeID);
        sb.append("\n" + indentSpace + "Heuristic: " + HEURISTIC_STRING);
        sb.append("\n" + indentSpace + "Heuristic value: " + heuristicValue);
        sb.append("\n" + indentSpace + "Distance: " + distance);
        if (heuristicFunction != null) {
            sb.append("\n" + indentSpace + "Estimated distance to goal: " + (getHeuristicValue() + distance));
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Board board = (Board) obj;
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (state[i][j] != board.state[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setHeuristic(int heuristic) {
        switch (heuristic) {
            case HEURISTIC_HAMMING:
                heuristicFunction = new Hamming();
                HEURISTIC_STRING = "HAMMING";
                break;
            case HEURISTIC_MANHATTAN:
                heuristicFunction = new Manhattan();
                HEURISTIC_STRING = "MANHATTAN";
                break;
            case HEURISTIC_MIN_HAM_MAN:
                heuristicFunction = new MinHammingManhattan();
                HEURISTIC_STRING = "MIN_HAM_MAN";
                break;
            case HEURISTIC_NOT_ADMISSIBLE:
                heuristicFunction = new NotAdmissable();
                HEURISTIC_STRING = "NOT_ADMISSABLE";
                break;
            case HEURISTIC_PERMUTATION:
                heuristicFunction = new Permutation();
                HEURISTIC_STRING = "PERMUTATION";
                break;
        }
    }

    /**
     * Heuristic functions h(n)
     * used strategy pattern for different heuristic functions
     **/

    //h0(N) - hamming parentDistance
    private class Hamming implements HeuristicFunction {
        @Override
        public int getHeuristicValue(Board board) {
            int distance = 0;
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    if (board.state[i][j] != GOAL_STATE[i][j] && board.state[i][j] != 0) {
                        distance++;
                    }
                }
            }
            return distance;
        }
    }

    //h1(N) - manhattan distance
    private class Manhattan implements HeuristicFunction {
        @Override
        public int getHeuristicValue(Board board) {
            int distance = 0;
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    if (board.state[i][j] != 0) {
                        int value = board.state[i][j];
                        int x_value = i;
                        int y_value = j;
                        int x_goal = GOAL_COORDINATE_MAP.get(value)[0];
                        int y_goal = GOAL_COORDINATE_MAP.get(value)[1];
                        distance += Math.abs(x_value - x_goal) + Math.abs(y_value - y_goal);
                    }
                }
            }
            return distance;
        }
    }


    //h2(N) - minimum between hamming parentDistance and manhattan parentDistance
    private class MinHammingManhattan implements HeuristicFunction {
        @Override
        public int getHeuristicValue(Board board) {
            return Math.min(new Hamming().getHeuristicValue(board), new Manhattan().getHeuristicValue(board));
        }
    }

    //TODO:
    //h3(N) - a heuristic that is not admissible (and does not implement A*)
    private class NotAdmissable implements HeuristicFunction {
        @Override
        public int getHeuristicValue(Board board) {
            return new Hamming().getHeuristicValue(board) + new Manhattan().getHeuristicValue(board);
        }
    }


    //h4(N) - sum of permutation inversions
    //for each numbered tile, count how many tiles on its right should be on its left in the goal state.
    //(admissible and better than either Manhattan parentDistance or hamming tiles)
    //TODO: make sure its working properly
    private class Permutation implements HeuristicFunction {
        @Override
        public int getHeuristicValue(Board board) {
            int distance = 0;
            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    if (board.state[i][j] != 0) {
                        Integer[] valueGoalStateCoords = GOAL_COORDINATE_MAP.get(board.state[i][j]);
                        //determine if following tiles should be before or after by looking at coordinates of following tiles
                        int columnStart = j;
                        for (int k = i; k < DIMENSION; k++) {
                            for (int l = columnStart; l < DIMENSION; l++) {
                                if (board.state[k][l] != 0) {
                                    Integer[] comparedGoalStateCoords = GOAL_COORDINATE_MAP.get(board.state[k][l]);
                                    //check if the row or column of the goal state coords are before or after current tile
                                    if (valueGoalStateCoords[0] > comparedGoalStateCoords[0]) {
                                        distance++;
                                    } else if (valueGoalStateCoords[0] == comparedGoalStateCoords[0] &&
                                            valueGoalStateCoords[1] > comparedGoalStateCoords[1]) {
                                        distance++;
                                    }
                                }

                            }
                            columnStart = 0;
                        }
                    }
                }
            }
            return distance;
        }
    }
}
