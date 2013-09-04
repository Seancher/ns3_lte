public class PercolationStats {
    private int numIter;
    private double[] frOpenSites;
    
    public PercolationStats(int N, int T) {   // perform T independent computational experiments on an N-by-N grid
        if (N < 1 || T < 1) {
            throw new java.lang.IllegalArgumentException(); }
        frOpenSites = new double[T];
        numIter = T;
        double count;
        for (int k = 0; k < T; k++) {
            Percolation percSys = new Percolation(N);
            count = 0;
            while (!percSys.percolates()) {
                int p = StdRandom.uniform(N) + 1;
                int q = StdRandom.uniform(N) + 1;
                percSys.open(p,q); }
            for (int i = 1; i <=  N; i++) {
                for (int j = 1; j <=  N; j++) {
                    if (percSys.isOpen(i, j)) {
                        count++; } } }
            frOpenSites[k] = count / (N * N); } }
    
    public double mean() {                   // sample mean of percolation threshold
        double sum = 0;
        for (int i = 0; i < numIter; i++) {
            sum += frOpenSites[i]; }
        return sum / numIter; }
    
    public double stddev() {                 // sample standard deviation of percolation threshold
        double sum = 0;
        double curMean = mean();
        for (int i = 0; i < numIter; i++) {
            sum += Math.pow(frOpenSites[i] - curMean,2); }
        return Math.sqrt(sum / (numIter - 1)); }
    
    public double confidenceLo() {             // returns lower bound of the 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(numIter); }
    
    public double confidenceHi() {           // returns upper bound of the 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(numIter); }
    
    public static void main(String[] args) {   // test client, described below
        int N, T;
    if (args.length > 0) {
        N = Integer.valueOf(args[0]);
        T = Integer.valueOf(args[1]); }
    else {N = 2; T = 1000; }
        PercolationStats myPercStats = new PercolationStats(N, T);
        StdOut.println("mean                    = " + myPercStats.mean());
        StdOut.println("stddev                  = " + myPercStats.stddev());
        StdOut.println("95% confidence interval = " + myPercStats.confidenceLo() + ", " + myPercStats.confidenceHi()); }
}