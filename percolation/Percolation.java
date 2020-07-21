import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int gridLength;
    private final boolean[] grid;
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final WeightedQuickUnionUF weightedQuickUnionTop;
    private int openedSites = 0;
    private final int top;
    private final int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.gridLength = n;
        this.grid = new boolean[n * n];
        this.bottom = (n * n) + 1;
        this.top = 0;
        this.weightedQuickUnionUF = new WeightedQuickUnionUF((n * n) + 2);
        this.weightedQuickUnionTop = new WeightedQuickUnionUF((n * n) + 1);

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isRangeInValid(row, col)) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {

            int siteId = getId(row, col);

            this.grid[siteId - 1] = true;

            uniteAdjacentSites(row, col);

            this.openedSites += 1;
        }
    }

    // it must not receive the row and col formated
    private int getId(int row, int col) {
        return (row * this.gridLength) - (this.gridLength - col);
    }

    // unites the adjacent sites based upon a site
    private void uniteAdjacentSites(int row, int col) {
        int siteId = getId(row, col);

        int left = col - 1;
        int right = col + 1;
        int top = row - 1;
        int bottom = row + 1;


        // FirstRow - virtualTop
        if (row == 1) {
            this.weightedQuickUnionUF.union(siteId, this.top);
            this.weightedQuickUnionTop.union(siteId, this.top);
        }

        // LastRow - virtualBottom
        if (row == this.gridLength) {
            this.weightedQuickUnionUF.union(siteId, this.bottom);
        }

        // leftSide
        if (!isRangeInValid(row, left) && isOpen(row, left)) {
            setRoot(row, left, siteId);
        }

        // rightSide
        if (!isRangeInValid(row, right) && isOpen(row, right)) {
            setRoot(row, right, siteId);

        }

        // upperSide
        if (!isRangeInValid(top, col) && isOpen(top, col)) {
            setRoot(top, col, siteId);
        }

        // bottomSide
        if (!isRangeInValid(bottom, col) && isOpen(bottom, col)) {
            setRoot(bottom, col, siteId);
        }
    }

    private void setRoot(int row, int col, int siteId) {
        int adjacentId = getId(row, col);
        this.weightedQuickUnionUF.union(siteId, adjacentId);
        this.weightedQuickUnionTop.union(siteId, adjacentId);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (isRangeInValid(row, col)) {
            throw new IllegalArgumentException();
        }

        int siteId = getId(row, col);

        return this.grid[siteId - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        if (isRangeInValid(row, col)) {
            throw new IllegalArgumentException();
        }

        int siteId = getId(row, col);

        return this.weightedQuickUnionTop.find(siteId) == this.weightedQuickUnionTop.find(this.top);
    }

    // validates whether the provided range is within provided boundaries of the grid
    private boolean isRangeInValid(int row, int col) {
        return row <= 0
                || col <= 0
                || row > this.gridLength
                || col > this.gridLength;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.weightedQuickUnionUF.find(top) == this.weightedQuickUnionUF.find(bottom);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openedSites;
    }
}
