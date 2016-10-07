import java.util.Scanner;

/**
 * Created by NSPACE on 10/3/2016.
 */
public class DriverBoard {
    public static void main(String[] args) {
        SearchAlgorithm sa = null;
        Board startBoard = new Board();

//        //SIMPLE TEST INITIALIZE 2 OFF
//        //initializing start configuration
//        startBoard.state = new int[][]{
//                {1, 3, 0},
//                {8, 2, 4},
//                {7, 6, 5}};
//
//        startBoard.blank_x = 0;
//        startBoard.blank_y = 2;

        //TEST INITIALIZE 10 OFF
        //initializing start configuration
        startBoard.state = new int[][]{
                {8, 1, 3},
                {6, 4, 5},
                {2, 7, 0}};

        startBoard.blank_x = 2;
        startBoard.blank_y = 2;

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
            System.out.println("4 - Not admissable ");
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
                case Board.HEURISTIC_NOT_ADMISSIBLE:
                    startBoard.setHeuristic(Board.HEURISTIC_NOT_ADMISSIBLE);
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

        System.out.println("\nEstimated millisecond execution time: " + estimatedTime / 1000000.0);
    }
}
