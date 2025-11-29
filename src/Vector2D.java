
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

    public void subtract(double vx, double vy){ //Getting vector between Ghost and PacMan
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

    public void stop(){ //For when PacMan collides w/ wall
        vx = 0;
        vy = 0;
    }

    public double distance(Vector2D v) { 
        double newVX = v.getVX() - vx;
        double newVY = v.getVY() - vy;
        
        return Math.sqrt(newVX * newVX + newVY * newVY);
    }
}
