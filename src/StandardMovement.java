import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class StandardMovement implements Movement{
    private Vector2D positionVector;
    private Vector2D velocityVector = new Vector2D(0,0);
    private double velVectorComponent = 3;
    private GraphicsObject objectShape; 
    private GraphicsObject hitCircleShape;
    private HitCircle hitCircle;
    private Vector2D hitCirclePosVector;
    private double offsetX = 18;
    private double offsetY = 20;
    private boolean facingUp, facingDown, facingLeft, facingRight;
    
    public StandardMovement(Vector2D positionVector, CanvasWindow canvas) {
        this.positionVector = positionVector;
        hitCirclePosVector = new Vector2D(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        hitCircle = new HitCircle(hitCirclePosVector, canvas, this);
        hitCircleShape = hitCircle.getObjectShape();
    }

    public boolean getFacingUp(){
        return facingUp;
    }

    public boolean getFacingDown(){
        return facingDown;
    }

    public boolean getFacingLeft(){
        return facingLeft;
    }

    public boolean getFacingRight(){
        return facingRight;
    }

    public HitCircle getHitCircle() {
        return hitCircle;
    }

    public GraphicsObject getShape(){
        return objectShape;
    }
    
    public void setShape(GraphicsObject objectShape){
        this.objectShape = objectShape;
    }

    public void moveUp() {
        facingUp = true;
        facingLeft = false;
        facingRight = false;
        facingDown = false;
        velocityVector.set(0,velVectorComponent);
        positionVector.add(velocityVector.getVX(),velocityVector.getVY()); //move 0 units right and 5 units up every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    public void moveDown() {
        facingDown = true;
        facingLeft = false;
        facingUp = false;
        facingRight = false;
        velocityVector.set(0,velVectorComponent);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); //move 0 units right and 5 units down every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    public void moveLeft() {
        facingUp = false;
        facingDown = false;
        facingRight = false;
        facingLeft = true;
        velocityVector.set(velVectorComponent,0);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); 
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    public void moveRight() {
        facingRight = true;
        facingLeft = false;
        facingUp = false;
        facingDown = false;
        velocityVector.set(velVectorComponent,0);
        positionVector.add(velocityVector.getVX(), velocityVector.getVY());
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    public void stop(){
        velocityVector.set(0,0);
    }

    public void collisionBuffer(double xBuffer, double yBuffer){
        positionVector.subtract(xBuffer, yBuffer);
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
    }

}
