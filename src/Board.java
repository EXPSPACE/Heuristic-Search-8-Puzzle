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
    public static final int HEURISTIC_LEARNING_FEATURES = 4;
    public static final int HEURISTIC_MANHATTAN_PLUS_LINEAR_CONFLICT = 5;

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

    public void setBlankXY() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                if (state[i][j] == 0) {
                    blank_x = i;
                    blank_y = j;
                }
            }
        }
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
        sb.append("\n" + indentSpace + "Heuristic value h(n): " + heuristicValue);
        sb.append("\n" + indentSpace + "Distance g(n): " + distance);
        if (heuristicFunction != null) {
            sb.append("\n" + indentSpace + "Estimated distance to goal f(n): " + (getHeuristicValue() + distance));
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
            case HEURISTIC_LEARNING_FEATURES:
                heuristicFunction = new LearningHeuristic();
                HEURISTIC_STRING = "LEARNING_NOT_ADMISSABLE";
                break;
            case HEURISTIC_MANHATTAN_PLUS_LINEAR_CONFLICT:
                heuristicFunction = new ManhattanPlusLinearConflict();
                HEURISTIC_STRING = "MANHATTAN_PLUS_LINEAR_CONFLICT";
                break;
        }
    }

    /**
     * Heuristic functions h(n)
     * used strategy pattern for different heuristic functions
     **/

    //h0(N) hamming distance - number of misplaced tiles
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

    //h1(N) manhattan distance - sum of moves for each tile to move to final position
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

    //h2(N) min_hamming_manhattan - minimum between hamming parentDistance and manhattan parentDistance
    //TODO max?
    private class MinHammingManhattan implements HeuristicFunction {
        @Override
        public int getHeuristicValue(Board board) {
            return Math.min(new Hamming().getHeuristicValue(board), new Manhattan().getHeuristicValue(board));
        }
    }



    //h3(N) - a heuristic that is not admissible, uses a linear combination of hamming and manhattan "features",
    //c1, c2 should be adjusted to fit actual data on solution costs
    private class LearningHeuristic implements HeuristicFunction {
        @Override
        public int getHeuristicValue(Board board) {
            //TODO: curve fit c1 and c2 to fit actual solution costs
            int c1 = 1;
            int c2 = 2;
            int hammingValue = new Hamming().getHeuristicValue(board);
            int manhattanValue = new Manhattan().getHeuristicValue(board);
            return c1 * hammingValue + c2 * manhattanValue;
        }
    }

    //h4(n) - Manhattan + Linear combination

    //SOURCE : https://heuristicswiki.wikispaces.com/Manhattan+Distance
    //SOURCE : https://heuristicswiki.wikispaces.com/Linear+Conflict

    //The limitation of the Manhattan Distance heuristic is that it considers each tile independently, while in fact tiles
    //interfere with each other. Higher accuracy can be achieved by combining other heuristics, such as the Linear Conflict Heuristic.

    //Linear Conflict Tiles Definition: Two tiles tj and tk are in a linear conflict if tj and tk are in the same line,
    // the goal positions of tj and tk are both in that line, tj is to the right of tk and goal position of tj is to the
    // left of the goal position of tk. The linear conflict adds at least two moves to the Manhattan Distance of the two
    // conflicting tiles, by forcing them to surround one another. Therefore the heuristic function will add a cost of 2
    // moves for each pair of conflicting tiles.

    private class ManhattanPlusLinearConflict implements HeuristicFunction {
        @Override
        public int getHeuristicValue(Board board) {
            int linearConflictDistance = 0;
            int manhattanDistance = new Manhattan().getHeuristicValue(board);

            for (int i = 0; i < DIMENSION; i++) {
                for (int j = 0; j < DIMENSION; j++) {
                    if (board.state[i][j] != 0) {
                        Integer[] valueGoalStateCoords = GOAL_COORDINATE_MAP.get(board.state[i][j]);

                        //checks tj and tk elements in same row
                        for (int k = i, l = j + 1; l < DIMENSION; l++) {
                            if (board.state[k][l] != 0) {
                                Integer[] comparedGoalStateCoords = GOAL_COORDINATE_MAP.get(board.state[k][l]);

                                //checks tj and tk elements goal state position are both in this row
                                if (valueGoalStateCoords[0] == k && comparedGoalStateCoords[0] == k) {

                                    //tj is to the right of tk and goal position of tj is to the left of the goal position of tk
                                    if (valueGoalStateCoords[1] > comparedGoalStateCoords[1]) {
                                        linearConflictDistance++;
                                    }
                                }

                            }
                        }

                        //checks tj and tk elements in same column
                        for (int k = i + 1, l = j; k < DIMENSION; k++) {
                            if (board.state[k][l] != 0) {
                                Integer[] comparedGoalStateCoords = GOAL_COORDINATE_MAP.get(board.state[k][l]);

                                //checks tj and tk elements goal state position are both in this column
                                if (valueGoalStateCoords[1] == l && comparedGoalStateCoords[1] == l) {

                                    //tj is to the right of tk and goal position of tj is to the left of the goal position of tk
                                    if (valueGoalStateCoords[0] > comparedGoalStateCoords[0]) {
                                        linearConflictDistance++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return manhattanDistance + 2 * linearConflictDistance;
        }
    }

    /** TEST FOR SOLVABILITY **/

    public static int getSumOfPermutationInversions(Board board) {
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

    public static boolean isSolvable(Board startBoard) {
        int sumOfPermutationInversion = getSumOfPermutationInversions(startBoard);
        if (sumOfPermutationInversion % 2 == 1) {
            return false;
        }
        return true;
    }
}
