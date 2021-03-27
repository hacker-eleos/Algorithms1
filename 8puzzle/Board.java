/* *****************************************************************************
 *  Name: Vikram Bhatt
 *  Date: 24 March, 2021
 *  Description: 8Puzzle Assignment
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class Board {

    private final int[][] tiles;
    private final int n;
    private final int randomX1;
    private final int randomY1;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
        int X1 = StdRandom.uniform(n);
        int Y1 = StdRandom.uniform(n);
        while (this.tiles[X1][Y1] == 0) {
            X1 = StdRandom.uniform(n);
            Y1 = StdRandom.uniform(n);
        }
        randomX1 = X1;
        randomY1 = Y1;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board board = new Board(tiles);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board);
        // for (Board neighbor : board.neighbors()) {
        //     System.out.println(neighbor);
        // }
        // System.out.println(board.twin());
        // System.out.println(board.twin());
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != ((j + 1) + (i * n))) hamming++;
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                manhattan += Math.abs(i - row(tiles[i][j])) + Math.abs(j - col(tiles[i][j]));
            }
        }
        return manhattan;
    }

    private int row(int value) {
        if (value == 0) return n - 1;
        if (value <= n) return 0;
        if (value % n == 0) return (value / n) - 1;
        else return value / n;

    }

    private int col(int value) {
        if (value == 0) return n - 1;
        if (value <= n) return value - 1;
        if (value % n == 0) return n - 1;
        else return (value % n) - 1;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (tiles[i][j] != ((j + 1) + (i * n))) return false;
            }
        }
        if (tiles[n - 1][n - 1] != 0) return false;
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringBuilder.append(this.tiles[i][j] + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> q = new Queue<>();
        int blankLocationX = 0;
        int blankLocationY = 0;
        int[][] copyOfTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copyOfTiles[i][j] = tiles[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    blankLocationX = i;
                    blankLocationY = j;
                    break;
                }
            }
        }
        if (isValidLocation(blankLocationX - 1, blankLocationY)) {
            int value = copyOfTiles[blankLocationX - 1][blankLocationY];
            copyOfTiles[blankLocationX][blankLocationY] = value;
            copyOfTiles[blankLocationX - 1][blankLocationY] = 0;
            q.enqueue(new Board(copyOfTiles));
            copyOfTiles[blankLocationX][blankLocationY] = 0;
            copyOfTiles[blankLocationX - 1][blankLocationY] = value;
        }
        if (isValidLocation(blankLocationX + 1, blankLocationY)) {
            int value = copyOfTiles[blankLocationX + 1][blankLocationY];
            copyOfTiles[blankLocationX][blankLocationY] = value;
            copyOfTiles[blankLocationX + 1][blankLocationY] = 0;
            q.enqueue(new Board(copyOfTiles));
            copyOfTiles[blankLocationX][blankLocationY] = 0;
            copyOfTiles[blankLocationX + 1][blankLocationY] = value;
        }
        if (isValidLocation(blankLocationX, blankLocationY - 1)) {
            int value = copyOfTiles[blankLocationX][blankLocationY - 1];
            copyOfTiles[blankLocationX][blankLocationY] = value;
            copyOfTiles[blankLocationX][blankLocationY - 1] = 0;
            q.enqueue(new Board(copyOfTiles));
            copyOfTiles[blankLocationX][blankLocationY] = 0;
            copyOfTiles[blankLocationX][blankLocationY - 1] = value;
        }
        if (isValidLocation(blankLocationX, blankLocationY + 1)) {
            int value = copyOfTiles[blankLocationX][blankLocationY + 1];
            copyOfTiles[blankLocationX][blankLocationY] = value;
            copyOfTiles[blankLocationX][blankLocationY + 1] = 0;
            q.enqueue(new Board(copyOfTiles));
            copyOfTiles[blankLocationX][blankLocationY] = 0;
            copyOfTiles[blankLocationX][blankLocationY + 1] = value;
        }
        return q;
    }

    private boolean isValidLocation(int x, int y) {
        if (x < 0 || x > n - 1) return false;
        if (y < 0 || y > n - 1) return false;
        return true;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copyOfTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copyOfTiles[i][j] = tiles[i][j];
            }
        }

        if (isValidLocation(randomX1 + 1, randomY1)) {
            int swap = copyOfTiles[randomX1 + 1][randomY1];
            copyOfTiles[randomX1 + 1][randomY1] = copyOfTiles[randomX1][randomY1];
            copyOfTiles[randomX1][randomY1] = swap;
        }
        else if (isValidLocation(randomX1 - 1, randomY1)) {
            int swap = copyOfTiles[randomX1 - 1][randomY1];
            copyOfTiles[randomX1 - 1][randomY1] = copyOfTiles[randomX1][randomY1];
            copyOfTiles[randomX1][randomY1] = swap;
        }
        else if (isValidLocation(randomX1, randomY1 + 1)) {
            int swap = copyOfTiles[randomX1][randomY1 + 1];
            copyOfTiles[randomX1][randomY1 + 1] = copyOfTiles[randomX1][randomY1];
            copyOfTiles[randomX1][randomY1] = swap;
        }
        else if (isValidLocation(randomX1, randomY1 - 1)) {
            int swap = copyOfTiles[randomX1][randomY1 - 1];
            copyOfTiles[randomX1][randomY1 - 1] = copyOfTiles[randomX1][randomY1];
            copyOfTiles[randomX1][randomY1] = swap;
        }
        return new Board(copyOfTiles);
        // int randomX2 = StdRandom.uniform(n);
        // int randomY2 = StdRandom.uniform(n);
        // while (copyOfTiles[randomX1][randomY1] == 0 || copyOfTiles[randomX2][randomY2] == 0) {
        //     randomX1 = StdRandom.uniform(n);
        //     randomY1 = StdRandom.uniform(n);
        //     randomX2 = StdRandom.uniform(n);
        //     randomY2 = StdRandom.uniform(n);
        // }
        // int swap = copyOfTiles[randomX1][randomY1];
        // copyOfTiles[randomX1][randomY1] = copyOfTiles[randomX2][randomY2];
        // copyOfTiles[randomX2][randomY2] = swap;
        // return new Board(copyOfTiles);

    }
}