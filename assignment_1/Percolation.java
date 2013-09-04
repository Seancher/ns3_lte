public class Percolation {
    private boolean[] openSites;
    private int size;    
    private WeightedQuickUnionUF sites;
    public Percolation(int N) {              // create N-by-N grid, with all sites blocked
        sites = new WeightedQuickUnionUF(N * N + 1);
        size = N;
        openSites = new boolean[N * N + 1];
        for (int i = 1; i <= N * N; i++) {
            openSites[i] = false; }
        openSites[0] = true; }
   
    public void open(int i, int j) {        // open site (row i, column j) if it is not already
        if (i < 1 || j < 1 || i > size || j > size) {
            throw new java.lang.IndexOutOfBoundsException(); }
        int p = size * (i - 1) + j; // get site id
        int q;
        openSites[p] = true;
        // check up, down, left, right neighbors. if they are open then union sites
        q = size * (i - 2) + j; // check up
        if (i > 1) { // if it has up heighbor
            if (openSites[q]) {
                sites.union(p, q); } }
        else sites.union(p, 0);
        
        q = size * i + j; // check down heighbor
        if (i < size) { // if it has down heighbor
            if (openSites[q]) {
                sites.union(p, q); } }
        
        q = size * (i - 1) + j - 1; // check left heighbor
        if (j > 1) { // if it has left heighbor
            if (openSites[q]) {
                sites.union(p, q); } }
        
        q = size * (i - 1) + j + 1; // check right heighbor
        if (j < size) { // if it has right heighbor
            if (openSites[q]) {
                sites.union(p, q); } } }    
   
    public boolean isOpen(int i, int j) {      // is site (row i, column j) open?
        if (i < 1 || j < 1 || i > size || j > size) {
            throw new java.lang.IndexOutOfBoundsException(); }        
        return openSites[size * (i - 1) + j]; }  
   
    public boolean isFull(int i, int j) {      // is site (row i, column j) full?
        if (i < 1 || j < 1 || i > size || j > size) {
            throw new java.lang.IndexOutOfBoundsException(); }        
        return sites.connected(0, size * (i - 1) + j); }
   
    public boolean percolates() {             // does the system percolate?
        for (int j = 1; j <= size; j++) {
            if (sites.connected(0, size * (size - 1) + j)) {
                return true; } } 
        return false; }
   
    public static void main(String[] args) {
        Percolation pSites = new Percolation(5);
        for (int i = 1; i < 6; i++) {
            StdOut.println(pSites.isOpen(i, 1)); } }
}
