import java.util.Scanner;

/**
 * Created by NSPACE on 10/3/2016.
 */
public class DriverBoard {
    public static void main(String[] args) {
        SearchAlgorithm sa = null;
        Board startBoard = new Board();

        //initializing start configuration

        //INITIALIZE 10 OFF
//        startBoard.state = new int[][]{
//                {8, 1, 3},
//                {6, 4, 5},
//                {2, 7, 0}};
//
        //INITIALIZE 21 OFF
        startBoard.state = new int[][]{
                {5, 6, 3},
                {0, 8, 4},
                {1, 7, 2}};

        startBoard.setBlankXY();

        if(!isSolvable(startBoard)) {
            System.out.println("Initial configuration is unsolvable");
            return;
        }

        System.out.println("Select search algorithm: ");
        System.out.println("1 - Breadth First Search ");
        System.out.println("2 - Depth First Search ");
        System.out.println("3 - Best First Search ");
        System.out.println("4 - AStar Search ");

        Scanner kb = new Scanner(System.in);
        int searchAlgo = kb.nextInt();

        switch (searchAlgo) {
            case SearchAlgorithm.BREADTH_SEARCH:
                sa = new BreadthFirstSearch(startBoard);
                break;
            case SearchAlgorithm.DEPTH_SEARCH:
                sa = new DepthFirstSearch(startBoard);
                break;
            case SearchAlgorithm.BEST_FIRST_SEARCH:
                sa = new BestFirstSearch(startBoard);
                break;
            case SearchAlgorithm.ASTAR_SEARCH:
                sa = new AStarSearch(startBoard);
                break;
        }

        if (searchAlgo == SearchAlgorithm.BEST_FIRST_SEARCH || searchAlgo == SearchAlgorithm.ASTAR_SEARCH) {
            System.out.println("Select heuristic algorithm: ");
            System.out.println("1 - Hamming ");
            System.out.println("2 - Manhattan ");
            System.out.println("3 - Minimum hamming manhattan ");
            System.out.println("4 - Learning heuristic using linear combination of features - (Not admissible) ");
            System.out.println("5 - Permutation ");

            int heuristicAlgo = kb.nextInt();

            switch (heuristicAlgo) {
                case Board.HEURISTIC_HAMMING:
                    startBoard.setHeuristic(Board.HEURISTIC_HAMMING);
                    break;
                case Board.HEURISTIC_MANHATTAN:
                    startBoard.setHeuristic(Board.HEURISTIC_MANHATTAN);
                    break;
                case Board.HEURISTIC_MIN_HAM_MAN:
                    startBoard.setHeuristic(Board.HEURISTIC_MIN_HAM_MAN);
                    break;
                case Board.HEURISTIC_LEARNING_FEATURES:
                    startBoard.setHeuristic(Board.HEURISTIC_LEARNING_FEATURES);
                    break;
                case Board.HEURISTIC_PERMUTATION:
                    startBoard.setHeuristic(Board.HEURISTIC_PERMUTATION);
                    break;
            }

            startBoard.heuristicValue = startBoard.heuristicFunction.getHeuristicValue(startBoard);
        }

        long startTime = System.nanoTime();
        sa.searchTree(startBoard);
        long estimatedTime = System.nanoTime() - startTime;

        System.out.println("Estimated millisecond execution time: " + estimatedTime / 1000000.0);
    }

    public static boolean isSolvable(Board startBoard) {
        startBoard.setHeuristic(Board.HEURISTIC_PERMUTATION);
        int heuristicValue = startBoard.getHeuristicValue();

        if(heuristicValue % 2 == 1) {
            return false;
        }
        return true;
    }
}
