import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF quickUnionStructure;
    private final WeightedQuickUnionUF quickUnionStructureForIsFull;
    private final boolean[][] grid;
    private final int gridSize;
    private final int virtualTopSite;
    private final int virtualBottomSite;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("N must be at least 1");
        this.gridSize = n;
        grid = new boolean[gridSize][gridSize];
        this.openSites = 0;
        this.virtualTopSite = 0;
        this.virtualBottomSite = (gridSize * gridSize) + 1;
        quickUnionStructure = new WeightedQuickUnionUF(
                (gridSize * gridSize) + 2); // includes virtual top and virtual bottom sites
        quickUnionStructureForIsFull = new WeightedQuickUnionUF((gridSize * gridSize) + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // Write test code for percolation
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateIndex(row, col);
        if (isOpen(row, col)) return;
        if (row == 1) {
            quickUnionStructure.union(virtualTopSite, toFlatIndex(row, col));
            quickUnionStructureForIsFull.union(virtualTopSite, toFlatIndex(row, col));
        }
        if (row == gridSize) quickUnionStructure.union(virtualBottomSite, toFlatIndex(row, col));
        if (isValidIndex(row - 1, col) && isOpen(row - 1, col)) {
            quickUnionStructure.union(toFlatIndex(row, col), toFlatIndex(row - 1, col));
            quickUnionStructureForIsFull.union(toFlatIndex(row, col), toFlatIndex(row - 1, col));
        }
        if (isValidIndex(row + 1, col) && isOpen(row + 1, col)) {
            quickUnionStructure.union(toFlatIndex(row, col), toFlatIndex(row + 1, col));
            quickUnionStructureForIsFull.union(toFlatIndex(row, col), toFlatIndex(row + 1, col));
        }

        if (isValidIndex(row, col - 1) && isOpen(row, col - 1)) {
            quickUnionStructure.union(toFlatIndex(row, col), toFlatIndex(row, col - 1));
            quickUnionStructureForIsFull.union(toFlatIndex(row, col), toFlatIndex(row, col - 1));
        }

        if (isValidIndex(row, col + 1) && isOpen(row, col + 1)) {
            quickUnionStructure.union(toFlatIndex(row, col), toFlatIndex(row, col + 1));
            quickUnionStructureForIsFull.union(toFlatIndex(row, col), toFlatIndex(row, col + 1));
        }
        grid[row - 1][col - 1] = true;
        openSites++;
    }

    private void validateIndex(int row, int col) {
        if (!isValidIndex(row, col))
            throw new IllegalArgumentException("Invalid indices. Out of range.");
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateIndex(row, col);
        return grid[row - 1][col - 1];
    }

    private int toFlatIndex(int row, int col) {
        int index = col + (row - 1) * gridSize;
        return index;
    }

    private boolean isValidIndex(int row, int col) {
        if ((row < 1) || (row > gridSize) || (col < 1) || (col > gridSize)) return false;
        return true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateIndex(row, col);
        return quickUnionStructureForIsFull.find(virtualTopSite) == quickUnionStructureForIsFull
                .find(toFlatIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionStructure.find(virtualTopSite) == quickUnionStructure
                .find(virtualBottomSite);
    }
}
