/* *****************************************************************************
 *  Name: Vikram Bhatt
 *  Date: 24 March, 2021
 *  Description: 8Puzzle Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {
    private final Stack<Board> stack = new Stack<>();
    private boolean isSolvable = false;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("null argument");
        MinPQ<Node> pq = new MinPQ<Node>();
        pq.insert(new Node(initial, null));
        pq.insert(new Node(initial.twin(), null));
        // add twin in case the initial is not solvable we get the twin solved.
        while (!pq.min().isGoal()) {
            Node node = pq.delMin();
            for (Board neighbor : node.board.neighbors()) {
                if (node.previous == null) {
                    pq.insert(new Node(neighbor, node));
                }
                else if (!node.previous.board.equals(neighbor)) {
                    pq.insert(new Node(neighbor, node));
                }
            }
        }

        Node n = pq.min();
        while (n.previous != null) {
            stack.push(n.board);
            n = n.previous;
        }
        stack.push(n.board);
        if (n.board.equals(initial)) isSolvable = true;
    }

    // test client (see below)
    public static void main(String[] args) {
        Board board = new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, { 7, 8, 6 } });
        Solver solver = new Solver(board);
        for (Board board1 : solver.solution()) {
            System.out.println(board1);
        }

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return stack;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return stack.size() - 1;
    }

    private class Node implements Comparable<Node> {
        private final Board board;
        private final Node previous;
        private final int numberOfMoves;

        public Node(Board board, Node previous) {
            this.board = board;
            this.previous = previous;
            numberOfMoves = previous == null ? 0 : previous.numberOfMoves + 1;
        }

        public boolean isGoal() {
            return this.board.isGoal();
        }

        public int compareTo(Node that) {
            int priority = this.board.manhattan() + this.numberOfMoves - that.board.manhattan()
                    - that.numberOfMoves;
            return priority == 0 ? this.board.manhattan() - that.board.manhattan() : priority;
        }
    }

}
