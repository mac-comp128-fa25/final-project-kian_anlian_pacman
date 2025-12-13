import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

/**
 * @author Kian Naeimi
 * December 2025
 * 
 * This class provides default movement behavior for Pac-Man (before we add rotation that is!) and the standard
 * behavior for each Ghost. Because of our vector abstraction, we're able to cleanly manipulate position state
 * with a velocity vector. We use the Move enum to easily transition between current and next moves, along with
 * constraints based on tile legality. 
 */
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
    private enum Move{UP, DOWN, LEFT, RIGHT} 
    private Move nextMove, currentMove = null;


    public StandardMovement(Vector2D positionVector, CanvasWindow canvas) {
        this.positionVector = positionVector;
        hitCirclePosVector = new Vector2D(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
        hitCircle = new HitCircle(hitCirclePosVector, canvas, this, tileManager);
        hitCircleShape = hitCircle.getObjectShape(); //We have to move the character's HitCircle too! 
    }

    /**
     * Convienience method 
     */
    public void move(){
        handleQueue();
        handleMovement();
    }
    
    /**
     * Mainly so Ghosts can easily choose their own speeds in GhostManager. 
     */
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

    /**
     * Take's the object's and it's hitCircle's current positionVector and applies an up vector to it.
     * The following 3 methods operate similarly.
     */
    public void moveUp() {
        velocityVector.set(0,velVectorComponent);
        positionVector.add(velocityVector.getVX(),velocityVector.getVY()); //move 0 units right and 5 units up every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }
    
    /**
     * Applies down vector.
     */
    public void moveDown() {
        velocityVector.set(0,velVectorComponent);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); //move 0 units right and 5 units down every frame
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    /**
     * Applies left vector.
     */
    public void moveLeft() {
        velocityVector.set(velVectorComponent,0);
        positionVector.subtract(velocityVector.getVX(), velocityVector.getVY()); 
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }

    /**
     * Applies right vector.
     */
    public void moveRight() {
        velocityVector.set(velVectorComponent,0);
        positionVector.add(velocityVector.getVX(), velocityVector.getVY());
        objectShape.setPosition(positionVector.getVX(), positionVector.getVY());
        hitCircleShape.setPosition(positionVector.getVX() - offsetX, positionVector.getVY() - offsetY);
    }
    
    /**
     * Applies directional vector based on currentMove state.
     */
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

    /**
     * The following movement queueing methods allow both Pac-Man and the Ghosts to request directions without
     * concerning themselves with tile legality. They'll maintain their currentMove until their nextMove is valid.
     * Players can only have one current/nextMove at a time so that they can't queue up entire sequences of moves
     * across the entire map. The idea here is that we enable smooth cornering, so players who don't have sharply
     * tuned reflexes have a wide margin of error before missing a turn. 
     */
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

    /**
     * Crucial to our cornering/movement algorithm. Every time we reach the center of a tile, nextMove's legality
     * is checked: If it's not a legal move, we keep it saved and keep applying the currentMove until it is.
     * Or until the player queues up a different nextMove by simply pressing another key before reaching
     * an intersection!
     */
    public void center(GraphicsObject objectShape, GraphicsObject hitCircleShape, Vector2D tileCenterVector){
        positionVector = tileCenterVector; //update reference
        objectShape.setCenter(tileCenterVector.getVX(), tileCenterVector.getVY());
        hitCircleShape.setCenter(tileCenterVector.getVX(), tileCenterVector.getVY());
    }

    /**
     * 
     * @return True if we're at a reasonable distance from the center of a tile.
     */
    public boolean centered(){
        return tileManager.getCurrentTile(objectShape).getCenterVector().distance(positionVector) < 10;
    }

    /**
     * If we're centered, handle all movement cases.
     */
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

        if (upIllegal || downIllegal || leftIllegal || rightIllegal){ //stop moving if we've hit an illegal tile
            currentMove = null; //This actually only applies to Pac-Man because the Ghosts queue up moves instantly every frame.
            nextMove = null;
        }
    }
}
