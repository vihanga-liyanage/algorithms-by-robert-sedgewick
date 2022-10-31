/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int n;
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufWithoutBottom;
    private int top = 0;
    private int bottom;
    private int numberOfOpenSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be less than or equal to 0!");
        }
        this.n = n;
        grid = new boolean[n + 1][n + 1];
        bottom = n * n + 1;
        uf = new WeightedQuickUnionUF(n * n + 2);
        ufWithoutBottom = new WeightedQuickUnionUF(n * n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (0 >= row || row > n || 0 >= col || col > n) {
            throw new IllegalArgumentException("row and col should be withing the permitted limits!");
        }

        if (!grid[row][col]) {
            grid[row][col] = true;
            numberOfOpenSites++;
        }

        int p = getQUIndex(row, col);
        // Connect to top
        if (row == 1) {
            uf.union(p, top);
            ufWithoutBottom.union(p, top);
        }
        // Connect to bottom
        if (row == n)
            uf.union(p, bottom);

        // Checking neighbours
        if (row != 1 && grid[row - 1][col]) {
            uf.union(p, getQUIndex(row - 1, col));
            ufWithoutBottom.union(p, getQUIndex(row - 1, col));
        }
        if (row != n && grid[row + 1][col]) {
            uf.union(p, getQUIndex(row + 1, col));
            ufWithoutBottom.union(p, getQUIndex(row + 1, col));
        }
        if (col != 1 && grid[row][col - 1]) {
            uf.union(p, getQUIndex(row, col - 1));
            ufWithoutBottom.union(p, getQUIndex(row, col - 1));
        }
        if (col != n && grid[row][col + 1]) {
            uf.union(p, getQUIndex(row, col + 1));
            ufWithoutBottom.union(p, getQUIndex(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        if (0 >= row || row > n || 0 >= col || col > n) {
            throw new IllegalArgumentException("row and col should be withing the permitted limits!");
        }
        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        if (0 >= row || row > n || 0 >= col || col > n) {
            throw new IllegalArgumentException("row and col should be withing the permitted limits!");
        }
        if (percolates()) {
            return isOpen(row, col) &&
                    ufWithoutBottom.find(getQUIndex(row, col)) == ufWithoutBottom.find(top);
        }
        return isOpen(row, col) && uf.find(getQUIndex(row, col)) == uf.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {

        return uf.find(top) == uf.find(bottom);
    }

    private int getQUIndex(int p, int q) {

        return n * (p - 1) + q - 1;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
