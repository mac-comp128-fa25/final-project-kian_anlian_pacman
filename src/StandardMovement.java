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
    private Move nextMove, currentMove = null;

    public StandardMovement(Vector2D positionVector, CanvasWindow canvas) {
        this.positionVector = positionVector;
        hitCirclePosVector = new Vector2D(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        hitCircle = new HitCircle(hitCirclePosVector, canvas, this, tileManager);
        hitCircleShape = hitCircle.getObjectShape();
    }

    public void move(){
        handleQueue();
        handleMovement();
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

    public void center(GraphicsObject objectShape, GraphicsObject hitCircleShape, Vector2D tileCenterVector){
        positionVector = tileCenterVector; //update reference
        objectShape.setCenter(tileCenterVector.getVX(), tileCenterVector.getVY());
        hitCircleShape.setCenter(tileCenterVector.getVX(), tileCenterVector.getVY());
    }

    public boolean centered(){
        return tileManager.getCurrentTile(objectShape).getCenterVector().distance(positionVector) < 10;
    }

    public void handleQueue(){
        if (centered()){
            handleLegalMoves();
            handleIllegalMoves();
        }
    }

    public void handleLegalMoves(){
        if (nextMove == Move.UP && tileManager.aboveLegal(objectShape)) currentMove = Move.UP;
        if (nextMove == Move.DOWN && tileManager.belowLegal(objectShape)) currentMove = Move.DOWN;
        if (nextMove == Move.LEFT && tileManager.leftLegal(objectShape)) currentMove = Move.LEFT;
        if (nextMove == Move.RIGHT && tileManager.rightLegal(objectShape)) currentMove = Move.RIGHT;
    }

    public void handleIllegalMoves(){
        boolean upIllegal = currentMove == Move.UP && !tileManager.aboveLegal(objectShape);
        boolean downIllegal = currentMove == Move.DOWN && !tileManager.belowLegal(objectShape);
        boolean leftIllegal = currentMove == Move.LEFT && !tileManager.leftLegal(objectShape);
        boolean rightIllegal = currentMove == Move.RIGHT && !tileManager.rightLegal(objectShape);

        if (upIllegal || downIllegal || leftIllegal || rightIllegal){
            currentMove = null; 
            nextMove = null;
        }
    }
}
