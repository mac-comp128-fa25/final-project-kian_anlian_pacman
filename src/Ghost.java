import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Arc;

/*
* TODO: Implement DFS w/ integer division. 
* At each tile, the Ghost will look at adjacent tiles, and count
* how many tiles it would take to get to Pac-Man from that 
* adjacent tile. The adjacent tile that has the lowest
* tileCount (returned from the DFS) will be the one that
* the Ghost will move to, using the same Movement object
* that PacMan does. So if the adjacent tile is to the
* left, moveLeft() will be called, if it's to the 
* right moveRight() will be called and so on.
*/

public class Ghost implements GameCharacter, GameObject{
    private GraphicsObject ghostShape; //Ellipse until we figure out how to draw ghosts
    private CanvasWindow canvas;
    private Vector2D positionVector;
    private Movement movement;

    public Ghost (Vector2D positionVector, CanvasWindow canvas, Movement movement, Color color){
        this.canvas = canvas;
        this.positionVector = positionVector;
        this.movement = movement;
        createGhost(color);
        addToCanvas();
    }

    @Override
    public GraphicsObject getObjectShape() {
        return ghostShape;
    }

    @Override
    public void addToCanvas() {
       canvas.add(ghostShape);
    }

    @Override
    public void removeFromCanvas() {
        canvas.remove(ghostShape);
    }

    @Override
    public double getXPosition() {
        return positionVector.getVX();
    }

    @Override
    public double getYPosition() {
        return positionVector.getVY();
    }

    @Override
    public void moveUp() {
        movement.moveUp();
    }

    @Override
    public void moveDown() {
        movement.moveDown();
    }

    @Override
    public void moveLeft() {
        movement.moveLeft();
    }

    @Override
    public void moveRight() {
        movement.moveRight();
    }

    @Override
    public void handleCollisions() {
    }

    public void createGhost(Color color){
        ghostShape = new Arc(positionVector.getVX(),positionVector.getVY(),5,10, 0, 180); //temp
        ((Arc) ghostShape).setStrokeColor(color); //4 ghosts: Blue, Pink, Red, Orange
        ((Arc) ghostShape).setStrokeWidth(10);
        ghostShape.setScale(3.5);
    }
    
}
