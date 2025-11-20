import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;

public class PacManGame {
    private CanvasWindow canvas;
    private static final int CANVAS_WIDTH = 1920; 
    private static final int CANVAS_HEIGHT = 1080; // 1920 x 1080p
    private PacMan pacMan;
    private Tile pelletTile;
    private Tile defaultTile;
    private Tile wallTile;
    private UI ui;
    private GhostManager ghostManager;
    private Vector2D pacManPositionVector;
    private Vector2D defaultTilePosVector;
    private Vector2D wallTilePosVector;
    private Movement pacManMovement;
    private KeyHandler keyHandler;

    public PacManGame(){
        createGameObjects();
        update();
    }

    private void createGameObjects(){
        canvas = new CanvasWindow("Pac-Man: Java", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.BLACK); 
        
        ui = new UI(canvas, 3);
        ui.initialize();

        pacManPositionVector = new Vector2D(canvas.getWidth()/2, canvas.getHeight()/2);
        pacManMovement = new StandardMovement(pacManPositionVector);

        ghostManager = new GhostManager(canvas);
        
        pacMan = new PacMan(pacManPositionVector, canvas, pacManMovement);
        pacManMovement.setShape(pacMan.getObjectShape());
        
        keyHandler = new KeyHandler(pacManMovement);
        canvas.onKeyDown(keyDown -> keyHandler.keyPressed(keyDown)); 

        testTiles();
    }

    private void testTiles(){
        defaultTilePosVector = new Vector2D(pacMan.getXPosition() + 500, pacMan.getYPosition());
        wallTilePosVector = new Vector2D(pacMan.getXPosition() - 500, pacMan.getYPosition()); 
        pelletTile = new Tile (false, true, pacManPositionVector, canvas, pacMan.getScale());
        defaultTile = new Tile (false, false, defaultTilePosVector, canvas, pacMan.getScale());
        wallTile = new Tile(true, false, wallTilePosVector, canvas, pacMan.getScale());

        pacMan.addToCanvas(); //shouldn't be neccesary normally, just have to draw him again in this case to have him above the tiles
    }

    private void update(){ //Where we'll call all the move functions. Animates objects.
    canvas.animate(animationEvent -> {
        keyHandler.checkKeyPresses();
        ui.update();
    });
    }

    
    public static void main(String[] args) {
        new PacManGame();
    }
}
