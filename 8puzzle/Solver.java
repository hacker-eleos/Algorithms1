/* *****************************************************************************
 *  Name: Vikram Bhatt
 *  Date: 24 March, 2021
 *  Description: 8Puzzle Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class Solver {
    private final Queue<Board> q = new Queue<>();
    private boolean isSolvable = false;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("null argument");
        MinPQ<Node> pq = new MinPQ<Node>();
        pq.insert(new Node(0, initial, null));
        while (!pq.isEmpty()) {
            Node node = pq.delMin();
            q.enqueue(node.current);
            if (node.isGoal()) {
                isSolvable = true;
                moves = node.numberOfMoves;
                break;
            }
            for (Board neighbor : node.current.neighbors()) {
                if (node.previous == null) {
                    pq.insert(new Node(node.numberOfMoves + 1, neighbor, node.current));
                }
                else if (!node.previous.equals(neighbor))
                    pq.insert(new Node(node.numberOfMoves + 1, neighbor, node.current));
            }
        }
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
        return q;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return moves;
    }

    private class Node implements Comparable<Node> {
        private final Board current;
        private final Board previous;
        private int numberOfMoves;

        public Node(int numberOfMoves, Board current, Board previous) {
            this.numberOfMoves = numberOfMoves;
            this.current = current;
            this.previous = previous;

        }

        public boolean isGoal() {
            return this.current.isGoal();
        }

        public int compareTo(Node that) {
            return this.current.manhattan() + this.numberOfMoves - that.current.manhattan()
                    - that.numberOfMoves;
        }
    }

}
