import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class StandardMovement implements Movement{
    private Vector2D positionVector;
    private TileManager tileManager;
    private Vector2D velocityVector = new Vector2D(0,0);
    private double velVectorComponent = 2;
    private GraphicsObject objectShape; 
    private GraphicsObject hitCircleShape;
    private HitCircle hitCircle;
    private Vector2D hitCirclePosVector;
    private double offsetX = 18;
    private double offsetY = 20;
    public enum Move{UP, DOWN, LEFT, RIGHT} 
    private Move nextMove = null;
    private Move currentMove = null;

    public StandardMovement(Vector2D positionVector, CanvasWindow canvas) {
        this.positionVector = positionVector;
        hitCirclePosVector = new Vector2D(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        hitCircle = new HitCircle(hitCirclePosVector, canvas, this, tileManager);
        hitCircleShape = hitCircle.getObjectShape();
    }

    public void setSpeed(double velVectorComponent){
        this.velVectorComponent = velVectorComponent;
    }

    public void setTileManager(TileManager tileManager){
        this.tileManager = tileManager;
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

    public void center(GraphicsObject objectShape, GraphicsObject hitCircleShape, Vector2D tileCenterVector){
        positionVector = tileCenterVector; //update reference... thinks we're still @ 0,0 in main class if not
        objectShape.setCenter(tileCenterVector.getVX(), tileCenterVector.getVY());
        hitCircleShape.setCenter(tileCenterVector.getVX(), tileCenterVector.getVY());
    }

    public boolean centered(){
        return tileManager.getCurrentTile(objectShape).getCenterVector().distance(positionVector) < 10;
    }

    public void moveUp() {
        velocityVector.set(0,velVectorComponent);
        positionVector.add(velocityVector.getVX(),velocityVector.getVY()); //move 0 units right and 5 units up every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }
    
    public void moveDown() {
        velocityVector.set(0,velVectorComponent);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); //move 0 units right and 5 units down every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    public void moveLeft() {
        velocityVector.set(velVectorComponent,0);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); 
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    public void moveRight() {
        velocityVector.set(velVectorComponent,0);
        positionVector.add(velocityVector.getVX(), velocityVector.getVY());
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    public void queueUp(){
        nextMove = Move.UP;
    }

    public void queueDown(){
        nextMove = Move.DOWN;
    }

    public void queueLeft(){
        nextMove = Move.LEFT;
    }

    public void queueRight(){ 
        nextMove = Move.RIGHT;
    }

    public void handleQueue(){ //Instead: Are we at the center of currentTile, and is aboveTile (belowTile, etc) legal? If so go up. Else do nothing.
        if (centered() && nextMove == Move.UP && tileManager.aboveLegal(objectShape)){
            currentMove = Move.UP;
        }

        if (centered() && nextMove == Move.DOWN && tileManager.belowLegal(objectShape)){
            currentMove = Move.DOWN;
        }

        if (centered() && nextMove == Move.LEFT && tileManager.leftLegal(objectShape)){
            currentMove = Move.LEFT;
        }

        if (centered() && nextMove == Move.RIGHT && tileManager.rightLegal(objectShape)){
            currentMove = Move.RIGHT;
        }

        if (centered() && currentMove == Move.UP && !tileManager.aboveLegal(objectShape)){
            currentMove = null;
            nextMove = null;
        }

        if (centered()  && currentMove == Move.DOWN && !tileManager.belowLegal(objectShape)){
            currentMove = null;
            nextMove = null;
        }

        if (centered()  && currentMove == Move.LEFT && !tileManager.leftLegal(objectShape)){
            currentMove = null;
            nextMove = null;
        }

        if (centered()  && currentMove == Move.RIGHT && !tileManager.rightLegal(objectShape)){
            currentMove = null;
            nextMove = null;
        }
    }

    public void handleMovement(){
        if (currentMove == Move.UP){
            moveUp();
        }

        if (currentMove == Move.DOWN){
            moveDown();
        }

        if (currentMove == Move.LEFT){
            moveLeft();
        }

        if (currentMove == Move.RIGHT){
            moveRight();
        }
    }

    public void move(){
        handleQueue();
        handleMovement();
    }
}
