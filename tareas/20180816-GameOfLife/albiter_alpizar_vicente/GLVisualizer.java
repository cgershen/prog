import java.awt.Font;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

public class GLVisualizer {
    
    private static final int DELAY = 200;
    private static final int MAX_IT = 10000;
    
    public static void draw(int n, GLGrid grid) {
        StdDraw.setCanvasSize(1000, 1000);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.setXscale(-0.05*n, 1.05*n);
        StdDraw.setYscale(-0.05*n, 1.05*n);
        StdDraw.filledSquare(n/2.0, n/2.0, n/2.0);
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (grid.isAlive(row-1, col-1)) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                }
                else {
                    StdDraw.setPenColor(StdDraw.BLACK);
                }
                StdDraw.filledSquare(col - 0.5, n - row + 0.5, 0.45);
            }
        }
    }
    
    public static void updateDraw(int n, GLGrid grid) {
        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                if (grid.cellChange(row-1, col-1)) {
                    if (grid.isAlive(row-1, col-1)) {
                        StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                    }
                    else {
                        StdDraw.setPenColor(StdDraw.BLACK);
                    }
                    StdDraw.filledSquare(col - 0.5, n - row + 0.5, 0.45);
                }
            }
        }
    }
    
    // Run a Game of Life animation from a ceratin starting grid
    private static void animateGrid(int n, GLGrid glgrid, int maxit) {
        for (int i = 0; i < maxit; i++) {
            updateDraw(n, glgrid);
            StdDraw.show();
            System.out.println(glgrid.aliveCells());
            StdDraw.pause(DELAY);
            glgrid.nextState();
        }
    }
    
    // Show an "interesting" (program-generated) grid
    public static void showInterestingGrid(int n, int m, int generator, boolean animate) {
        GLUtilities utilities = new GLUtilities();
        GLGrid glgrid = new GLGrid(n, utilities.interestingGrid(n, m, generator));
        draw(n, glgrid);
        StdDraw.show();
        if (animate) { animateGrid(n, glgrid, MAX_IT); }
    }
    
    // Show a grid from standard input (set the "animate" flag to true in order to
    // visualize a simulation of the GoL with this starting grid).
    public static void showStdInputGrid(int n, boolean animate, String args) {
        GLUtilities utilities = new GLUtilities();
        GLGrid glgrid = new GLGrid(n, utilities.standardInputGrid(args));
        draw(n, glgrid);
        StdDraw.show();
    }
    
    // Show a randomized grid
    public static void showRandomGrid(int n, double density, boolean animate) {
        GLUtilities utilities = new GLUtilities();
        GLGrid glgrid = new GLGrid(n, utilities.randomGrid(n, density));
        draw(n, glgrid);
        StdDraw.show();
        if (animate) { animateGrid(n, glgrid, MAX_IT); }
    }
    
    // Show patterns stored in a text file
    public static void showPatterns(int n, int maxit, boolean animate, String[] args) {
        In in = new In(args[0]);
        String[] file = in.readAllLines();
        for (int i = 0; i < file.length; i++) {
            manualPatternCheck(n, file[i], maxit, true);
        }
    }
    
    // Helper method to manually check for patterns generated from a seed, coming from
    // standard input.
    public static void manualPatternCheck(int n, String args, int maxit, boolean animate) {
        GLUtilities utilities = new GLUtilities();
        GLGrid glgrid = new GLGrid(n, utilities.standardInputGrid(args));
        draw(n, glgrid);
        StdDraw.show();
        StdDraw.pause(DELAY);
        if (animate) { animateGrid(n, glgrid, maxit); }
    }
    
    public static void main (String[] args) {
        showRandomGrid(100, 0.5, true); 
        //showStdInputGrid(4, false, args);
        //showInterestingGrid(30, 3, 115, true);
        //showPatterns(30, 4, true, args);
    }
}