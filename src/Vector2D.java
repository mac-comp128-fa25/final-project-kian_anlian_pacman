/**
 * @author Kian Naeimi
 * @author AnLian Krishnamurthy
 * December 2025
 * 
 * This class lets us further abstract movement, and just pass around velocity/position vectors.
 * We have access to a few vector operations (the ones that make sense for this game anyway).
 * If we wanted to do fancier computations, we could easily add methods that compute the
 * dotProduct between characters, or if we really wanted to cover an interesting edge case*
 * we could normalize our characters velocity vectors.
 * 
 *  *In 2D games, applying a vector in a cardinal direction is always a consistent operation. However
 * if you apply that same vector in a diagonal direction, because you essentially are taking the 
 * hypotenuse, you're traveling faster and as such we have a larger magnitude vector. In this case,
 * we would normalize the velocityVector so we're direction invariant. Luckily Pac-Man only travels
 * via cardinal direction so we don't need to worry about that.
 * 
 * Inspired by Unity Vector2 API: https://docs.unity3d.com/ScriptReference/Vector2.html 
 */
public class Vector2D {//For moving PacMan & Ghosts
    private double vx;
    private double vy;

    public Vector2D(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }

    public void add(double vx, double vy){ //Updating GameCharacter positions
        this.vx += vx;
        this.vy -= vy; //Because we're using Canvas coordinates, y sign is flipped
    }

    public void subtract(double vx, double vy){ //Moving down or moving left uses subtraction
        this.vx -= vx;
        this.vy += vy;
    }

    public double getVX(){
        return vx;
    }

    public double getVY(){
        return vy;
    }

    public void set(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }

    public void set(Vector2D v){
        this.vx = v.getVX();
        this.vy = v.getVY();
    }

    /**
     * Helpful for more Movement abstraction: Can just use Vector objects to do most of our movement/distance 
     * computations.
     * @param v some positionVector
     * @return distance between our current positionVector (probably a centerVector) and the vector v.
     */
    public double distance(Vector2D v) { 
        double newVX = v.getVX() - vx;
        double newVY = v.getVY() - vy;
        
        return Math.sqrt(newVX * newVX + newVY * newVY);
    }
}
