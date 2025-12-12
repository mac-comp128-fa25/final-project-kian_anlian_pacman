import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

public class StandardMovement implements Movement{
    private Vector2D positionVector;
    private TileManager tileManager;
    private Vector2D velocityVector = new Vector2D(0,0);
    private double velVectorComponent = 2;
    private GraphicsObject objectShape; 
    private GraphicsObject hitCircleShape;
    private GraphicsObject secondHitCircleShape;
    private HitCircle hitCircle;
    private HitCircle secondHitCircle;
    private Vector2D hitCirclePosVector;
    private double offsetX = 18;
    private double offsetY = 20;
    public enum Move{UP, DOWN, LEFT, RIGHT, STOPPED} //TODO: Figure out how to center characters here... and queue movements...
    private Move nextMove = null;
    private Move currentMove = null;

    public StandardMovement(Vector2D positionVector, CanvasWindow canvas) {
        this.positionVector = positionVector;
        hitCirclePosVector = new Vector2D(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        hitCircle = new HitCircle(hitCirclePosVector, canvas, this, tileManager, 40);
        hitCircleShape = hitCircle.getObjectShape();
        Vector2D spawnPos = new Vector2D (positionVector.getVX() - 100, positionVector.getVY() - 30);
        secondHitCircle = new HitCircle(spawnPos, canvas, this, tileManager, 100);
        secondHitCircleShape = secondHitCircle.getObjectShape();
        
        // secondHitCircle.addToCanvas();
    }

    public HitCircle getQueueHitCircle(){
        return secondHitCircle;
    }
    public void setSpeed(double velVectorComponent){
        this.velVectorComponent = velVectorComponent;
    }

    public void setTileManager(TileManager tileManager){
        this.tileManager = tileManager;
    }

    public Tile getCurrentTile(){
        return tileManager.getCurrentTile(hitCircle);
    }

    public Vector2D getTileCenter(){
        return getCurrentTile().getCenterVector();
    }

    public double getTileX(){
        return getCurrentTile().getXPosition();
    }

    public double getTileY(){
        return getCurrentTile().getYPosition();
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

    public void currentUp(){
        currentMove = Move.UP;
    }

    public void currentDown(){
        currentMove = Move.DOWN;
    }

    public void currentLeft(){
        currentMove = Move.LEFT;
    }

    public void currentRight(){
        currentMove = Move.RIGHT;
    }

    public void moveUp() {
        velocityVector.set(0,velVectorComponent);
        positionVector.add(velocityVector.getVX(),velocityVector.getVY()); //move 0 units right and 5 units up every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        secondHitCircleShape.setPosition(positionVector.getVX() - 50, positionVector.getVY() - 20);
    }
    
    public void moveDown() {
        velocityVector.set(0,velVectorComponent);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); //move 0 units right and 5 units down every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        secondHitCircleShape.setPosition(positionVector.getVX() - 50, positionVector.getVY() - 80);
    }

    public void moveLeft() {
        velocityVector.set(velVectorComponent,0);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); 
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        secondHitCircleShape.setPosition(positionVector.getVX() - 10, positionVector.getVY() - 30);
    }

    public void moveRight() {
        velocityVector.set(velVectorComponent,0);
        positionVector.add(velocityVector.getVX(), velocityVector.getVY());
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        secondHitCircleShape.setPosition(positionVector.getVX() - 80, positionVector.getVY() - 30);
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

    public void handleQueue(){
        if (!secondHitCircle.topTileCollision() && nextMove == Move.UP){
            currentMove = Move.UP;
        }

        if (!secondHitCircle.bottomTileCollision() && nextMove == Move.DOWN){
            currentMove = Move.DOWN;
        }

        if (!secondHitCircle.leftTileCollision() && nextMove == Move.LEFT){
            currentMove = Move.LEFT;
        }

        if (!secondHitCircle.rightTileCollision() && nextMove == Move.RIGHT){
            currentMove = Move.RIGHT;
        }

        if (hitCircle.topTileCollision() || hitCircle.bottomTileCollision() || hitCircle.leftTileCollision() || hitCircle.rightTileCollision()){
            currentMove = Move.STOPPED;
            
        }

        if (hitCircle.topTileCollision() && nextMove == Move.DOWN){
            currentMove = Move.DOWN;
        }

        if (hitCircle.bottomTileCollision() && nextMove == Move.UP){
            currentMove = Move.UP;
        }

        if (hitCircle.leftTileCollision() && nextMove == Move.RIGHT){
            currentMove = Move.RIGHT;
        }

        if (hitCircle.rightTileCollision() && nextMove == Move.LEFT){
            currentMove = Move.LEFT;
        }

        handleMovement();
    }
}
