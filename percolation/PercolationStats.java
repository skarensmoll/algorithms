import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] openSitesRatio;
    private final int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.openSitesRatio = new double[trials];
        this.trials = trials;

        int counter = 0;

        while (counter < trials) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                percolation.open(x, y);
            }

            openSitesRatio[counter] = percolation.numberOfOpenSites() / Math.pow(n, 2);

            counter += 1;
        }
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.openSitesRatio);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.openSitesRatio);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev() / Math.sqrt(this.trials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev() / Math.sqrt(this.trials)));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(n, T);
        StdOut.println("mean                    = " + percolationStats.mean());
        StdOut.println("stddev                  = " + percolationStats.stddev());
        StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");

    }
}
