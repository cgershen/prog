import edu.princeton.cs.algs4.Vector;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;

public class TestBoid {
    
    private double X_LIMIT = 5.0;
    private double Y_LIMIT = 5.0;
    private double VX_LIMIT = 0.1;
    private double VY_LIMIT = 0.1;
    
    public Boid randomBoid() {
        double x = StdRandom.uniform(-1*X_LIMIT, X_LIMIT);
        double y = StdRandom.uniform(-1*Y_LIMIT, Y_LIMIT);
        double vx = StdRandom.uniform(-1*VX_LIMIT, VX_LIMIT);
        double vy = StdRandom.uniform(-1*VY_LIMIT, VY_LIMIT);
        return new Boid(new Vector(x, y), new Vector(vx, vy));
    }
    
    public Boid[] randomBoids(int n) {
        Boid[] boids = new Boid[n];
        for (int i = 0; i < n; i++) {
            boids[i] = randomBoid();
        }
        return boids;
    }
    
    public void showPlane() {
        StdDraw.setPenRadius(0.005);
        StdDraw.setXscale(-2*X_LIMIT, X_LIMIT*2);
        StdDraw.setYscale(-2*Y_LIMIT, Y_LIMIT*2);
    }
    
    public void showBoid(Boid[] boids) {
        for (Boid boid : boids) {
            boid.draw();
        }
        boids[0].updatePosition();
        //boids[0].updateVelocity(boids);
    }
    
    public void showBoids(Boid[] boids) {
        for (Boid boid : boids) {
            boid.draw();
            boid.updatePosition();
            //boid.updateVelocity(boids);
        }
    }
    
    public void testCohesion() {
        
    }
    
    public static void main(String[] args) {
        TestBoid test = new TestBoid();
        test.showPlane();
        Boid[] boids = test.randomBoids(1000);
        StdDraw.enableDoubleBuffering();
        while(true) {
            StdDraw.clear();
            test.showBoids(boids);
            StdDraw.show();
            StdDraw.pause(10);
        }
    }
}