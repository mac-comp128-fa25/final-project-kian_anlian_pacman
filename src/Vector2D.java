public class Vector2D {//For moving PacMan & Ghosts
    private double vx;
    private double vy;

    public Vector2D(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }
    public void add(double vx, double vy){ //Updating GameCharacter positions
        this.vx += vx;
        this.vy += vy;
    }

    public void subtract(double vx, double vy){ //Getting vector between Ghost and PacMan
        this.vx -= vx;
        this.vy -= vy;
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

    public void rotateLeft(){ //rotate left 90 deg
        
    }

    public void rotateRight(){ //rotate right 90 deg

    }

    public void rotate180(){ //rotates by negating
        vx = -vx;
        vy = -vy;
    }

    public void stop(){ //For when PacMan collides w/ wall
        vx = 0;
        vy = 0;
    }
}
