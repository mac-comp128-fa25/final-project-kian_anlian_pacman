import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;

public class GhostManager implements Manager{
    private CanvasWindow canvas;

    //Names from original game
    private Ghost pinky; //pink ghost
    private Ghost blinky; //red ghost
    private Ghost inky; //blue ghost
    private Ghost clyde; //orange ghost
    
    private Vector2D pinkyPositionVector;  
    private Vector2D blinkyPositionVector; 
    private Vector2D inkyPositionVector;
    private Vector2D clydePositionVector;

    private Movement pinkyMovement;
    private Movement blinkyMovement;
    private Movement inkyMovement;
    private Movement clydeMovement;

    public GhostManager(CanvasWindow canvas){
        this.canvas = canvas;
        spawnCollection();
    }

    @Override
    public void spawnCollection() { //Spawn @ 4 cornerns, 50 x and y units away from each corner
        chooseSpawnPoints();
        createGhosts();
        linkMovement();
        
    }

    public Ghost getPinky(){
        return pinky;
    }

    public Movement getPinkyMovement(){
        return pinkyMovement;
    }

    @Override
    public void manageCollection() { //Chase algorithm
    }

    @Override
    public void handleCollisions() { //On collision, Pac-Man should lose one of his three lives
    }

    public void chooseSpawnPoints(){//canvasWidth: 845  canvasHeight: 1540
        int leftX = canvas.getWidth() / 8;
        int topY = canvas.getHeight() / 8;

        int rightX = canvas.getWidth() - (canvas.getWidth() / 8);
        int bottomY = canvas.getHeight() - (canvas.getWidth() / 8);
        
        pinkyPositionVector = new Vector2D(leftX, topY); //top left
        blinkyPositionVector = new Vector2D(rightX, bottomY); //bottom right
        inkyPositionVector = new Vector2D(leftX, bottomY); //bottom left
        clydePositionVector = new Vector2D(rightX, topY); //bottom right
    }

    public void createGhosts(){
        pinky = new Ghost(pinkyPositionVector, canvas, pinkyMovement, Color.PINK);
        blinky = new Ghost(blinkyPositionVector, canvas, blinkyMovement, Color.RED);
        inky = new Ghost(inkyPositionVector, canvas, inkyMovement, Color.CYAN);
        clyde = new Ghost(clydePositionVector, canvas, clydeMovement, Color.ORANGE);
    }

    public void linkMovement(){
        chooseMovement();
        
        pinkyMovement.setShape(pinky.getObjectShape());
        blinkyMovement.setShape(blinky.getObjectShape());
        inkyMovement.setShape(inky.getObjectShape());
        clydeMovement.setShape(clyde.getObjectShape());

        
    }

    public void chooseMovement(){
        pinkyMovement = new StandardMovement(pinkyPositionVector);
        blinkyMovement = new StandardMovement(blinkyPositionVector);
        inkyMovement = new StandardMovement(inkyPositionVector);
        clydeMovement = new StandardMovement(clydePositionVector);
    }
}
