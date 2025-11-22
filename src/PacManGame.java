import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;

public class PacManGame {
    private CanvasWindow canvas;
    private static final int CANVAS_WIDTH = 1920; 
    private static final int CANVAS_HEIGHT = 1080; // 1920 x 1080p
    private PacMan pacMan;
    private UI ui;
    private GhostManager ghostManager;
    private Vector2D pacManPositionVector;
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

        pacMan = new PacMan(pacManPositionVector, canvas, pacManMovement);
        pacManMovement.setShape(pacMan.getObjectShape());

        new TileManager(canvas, pacMan);
        
        ui = new UI(canvas, 3);
        ui.initialize();
        ghostManager = new GhostManager(canvas);
        keyHandler = new KeyHandler(pacManMovement);
        canvas.onKeyDown(keyDown -> keyHandler.keyPressed(keyDown)); 
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
