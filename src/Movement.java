import edu.macalester.graphics.GraphicsObject;

public class Movement {
    private boolean facingLeft, facingRight, facingUp, facingDown;
    private Vector2D positionVector;
    private Vector2D velocityVector = new Vector2D(0,0);
    private GraphicsObject objectShape;
    
    public Movement(Vector2D positionVector) {
        this.positionVector = positionVector;
    }

    public void setShape(GraphicsObject objectShape){
        this.objectShape = objectShape;
    }

    public void moveUp() {
        velocityVector.set(0,2.5);
        positionVector.add(velocityVector.getVX(),velocityVector.getVY()); //move 0 units right and 5 units up every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        
        if (!facingUp){
            objectShape.setRotation(-90);
            facingUp = true;
            facingDown = false;
            facingLeft = false;
            facingRight = false;
        }
    }

    public void moveDown() {
        velocityVector.set(0,2.5);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); //move 0 units right and 5 units down every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        
        if (!facingDown){
            objectShape.setRotation(90);
            facingDown = true;
            facingUp = false;
            facingLeft = false;
            facingRight = false;
        }
    }

    public void moveLeft() {
        velocityVector.set(2.5,0);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); 
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        
        if (!facingLeft){
            objectShape.setRotation(180);
            facingLeft = true;
            facingUp = false;
            facingRight = false;
            facingDown = false;
        }
    }

    public void moveRight() {
        velocityVector.set(2.5,0);
        positionVector.add(velocityVector.getVX(), velocityVector.getVY());
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        
        if (!facingRight){
            objectShape.setRotation(0);
            facingRight = true;
            facingLeft = false;
            facingDown = false;
            facingUp = false;
        }
    }

}
