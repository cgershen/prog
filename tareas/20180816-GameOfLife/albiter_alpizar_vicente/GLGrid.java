import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

public class GLGrid {
    
    private boolean[][] grid;
    private boolean[][] nextgrid;
    private boolean[][] changes;
    private int n;
    private int alive = 0;
    private int NEIGHBORS_LOWERBOUND = 2;
    private int NEIGHBORS_UPPERBOUND = 5;
    
    public GLGrid(int n, boolean[][] grid) {
        this.n = n;
        this.grid = new boolean[n][n];
        this.nextgrid = new boolean[n][n];
        this.changes = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j]) { alive++; }
                this.grid[i][j] = grid[i][j];
                this.nextgrid[i][j] = false;
                this.changes[i][j] = false;
            }
        }
    }
    
    // Is the (row, col) cell alive?
    public boolean isAlive(int row, int col) {
        return grid[row][col];
    }
    
    // Return the number of alive cells in the current grid 
    public int aliveCells() {
        return alive;
    }
    
    // Calculate how many alive neighbors does a cell have
    private int aliveNeighbors(int row, int col) {
        int neighbors = 0;
        if (isAlive(validate(row - 1), validate(col))) { neighbors++; }
        if (isAlive(validate(row - 1), validate(col + 1))) { neighbors++; }
        if (isAlive(validate(row), validate(col + 1))) { neighbors++; }
        if (isAlive(validate(row + 1), validate(col + 1))) { neighbors++; }
        if (isAlive(validate(row + 1), validate(col))) { neighbors++; }
        if (isAlive(validate(row + 1), validate(col - 1))) { neighbors++; }
        if (isAlive(validate(row), validate(col - 1))) { neighbors++; }
        if (isAlive(validate(row - 1), validate(col - 1))) { neighbors++; }
        return neighbors;
    }
    
    // Helper method to "blend" all the grid boundaries so that they are continuous
    private int validate(int value) {
        if (value == -1) { return n - 1; }
        else if (value == n) { return 0; }
        else { return value; }
    }
    
    // Update the grid according to the Game of Life rules
    // These rules can be changed with the following global static variables:
    // NEIGHBORS_LOWERBOUND: When this limint isn't reached, living cells die of loneliness
    // NEIGHBORS_UPERBOUND: When this limit is reached, dead cells come to life. When surpassed,
    // living cells die of overpopulation
    public void nextState() {
        alive = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (isAlive(i, j)) {
                    int neighbors = aliveNeighbors(i, j);
                    if (neighbors < NEIGHBORS_LOWERBOUND || neighbors > NEIGHBORS_UPPERBOUND) {
                        nextgrid[i][j] = false;
                    }
                    else {
                        nextgrid[i][j] = true;
                    }
                }
                else {
                    int neighbors = aliveNeighbors(i, j);
                    if (neighbors == NEIGHBORS_UPPERBOUND) {
                        nextgrid [i][j] = true;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                changes[i][j] = grid[i][j] != nextgrid[i][j];
                grid[i][j] = nextgrid[i][j];
                if (grid[i][j]) { alive++; }
            }
        }
    }
    
    // Helper method to determine whether if a certain cell changed in the last generation
    public boolean cellChange(int row, int col) {
        return changes[row][col];
    }
    
    // Helper method to print the current grid to standard output
    public void printGrid(int n, boolean[][] grid) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j]) { System.out.print("1 "); }
                else { System.out.print("0 "); }
            }
            System.out.println("");
        }
    }
    
    public static void main(String[] args) {
        //GLGrid grid = new GLGrid(10);
    }
}