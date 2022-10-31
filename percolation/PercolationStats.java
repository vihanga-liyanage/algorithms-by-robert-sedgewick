/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0) {
            throw new IllegalArgumentException("n cannot be less than or equal to 0!");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials cannot be less than or equal to 0!");
        }

        double[] xtArray = new double[trials];

        for (int t = 0; t < trials; t++) {
            // System.out.print("Trial no: " + t);
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int x = StdRandom.uniformInt(1, n + 1);
                int y = StdRandom.uniformInt(1, n + 1);
                percolation.open(x, y);
            }
            // System.out.println(" - Percolated at: " + percolation.numberOfOpenSites() + " open sites.");
            xtArray[t] = (double) percolation.numberOfOpenSites() / (n * n);
            // System.out.println(xtArray[t]);
        }

        mean = StdStats.mean(xtArray);
        stddev = StdStats.stddev(xtArray);

        double temp = ((1.96 * stddev) / Math.sqrt(trials));
        confidenceLo = mean - temp;
        confidenceHi = mean + temp;
    }

    // sample mean of percolation threshold
    public double mean() {

        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return confidenceHi;
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Not enough arguments provided");
        } else {
            int n = Integer.parseInt(args[0]);
            int t = Integer.parseInt(args[1]);
            PercolationStats p = new PercolationStats(n, t);
            System.out.println("mean\t\t\t\t\t = " + p.mean());
            System.out.println("stddev\t\t\t\t\t = " + p.stddev());
            System.out.println("95% confidence interval\t = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
        }
    }
}
