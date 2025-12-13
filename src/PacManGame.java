import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.ui.Button;

/**
 * @author Kian Naeimi
 * @author AnLian Krishnamurthy
 * December 2025
 * 
 * Main-Class. We piece together every object and it's corresponding state into one cohesive game-loop here!
 * We use the GameState enum to smoothly transition between each GameState, and provide several methods 
 * available to the UI class for access to state transitions.
 */
public class PacManGame {
    private CanvasWindow canvas = new CanvasWindow("Pac-Man: Java", CANVAS_WIDTH, CANVAS_HEIGHT);
    private static final int CANVAS_WIDTH = 1920; 
    private static final int CANVAS_HEIGHT = 1080; // 1080p
    private PacMan pacMan;
    private Tile pacSpawnTile; //want col 9 row 4
    private Vector2D tileCenterVector;
    private UI ui;
    private Button startButton;
    private GraphicsText pacManText;
    private GraphicsText javaText;
    private GhostManager ghostManager;
    private TileManager tileManager;
    private Vector2D pacManPositionVector;
    private Movement pacManMovement;
    private KeyHandler keyHandler;
    private enum GameState {MENU, RUNNING, PAUSED, GAME_OVER} //makes state transitions easier
    private static GameState gameState = GameState.RUNNING; //start off in running state
    
    public PacManGame(){
        menu();
        update();
    }

    /*
     * //TODO: Finish report, add more choices/thoughts for each area. Add encountered problems etc.
     * //TODO: Create slideshow.
     */

    /**
     * Where we instantiate all of our objects before begining the main update loop. 
     */
    public void createGameObjects(){ 
        pacManPositionVector = new Vector2D(0,0); //Need reference for pacManMovement
        pacManMovement = new RotationMovement(pacManPositionVector, canvas);
        
        pacMan = new PacMan(pacManPositionVector, canvas, 5);
        pacManMovement.setShape(pacMan.getObjectShape());

        tileManager = new TileManager(canvas, pacMan);
        pacManMovement.setTileManager(tileManager); //We have setters like this one because objects are tied together a bit roughly.

        spawnPacMan();

        keyHandler = new KeyHandler(pacManMovement, pacMan.getObjectShape(), tileManager);
        
        canvas.onKeyDown(keyDown -> keyHandler.keyPressed(keyDown));    
        
        pacMan.addToCanvas(); //we want pac-man and ui to be top elements
        
        ui = new UI(canvas, tileManager, pacMan);
        ui.initialize();
        
        ghostManager = new GhostManager(canvas, pacManMovement, pacMan.getObjectShape(), ui, tileManager);
        ui.setGhostManager(ghostManager); //Another example of one of these setters: GhostManager needs a reference to ui before it can be assigned to UI itself!
    }

    public void update(){ //Where our game-loop lives. All objects are animated and handle state (collision, movement, etc) here.
        canvas.animate(animationEvent -> {
        if (gameState == GameState.RUNNING){
            ui.update(); 
            tileManager.handlePellets(ghostManager);
            keyHandler.checkKeyPresses();
            pacManMovement.move();
            ghostManager.traverseShortestPath();
            handleGhostCollisions();
        }
        if (gameState == GameState.GAME_OVER) respawnCharacters();
    });
    }

    /**
     * To maintain the smooth centering mechanic implemented in StandardMovement, we need to start PacMan (and all the 
     * other characters) in the center of whatever tile we choose to spawn them on.
     */
    public void spawnPacMan(){ 
        pacSpawnTile = tileManager.getTile(9,4); 
        tileCenterVector = pacSpawnTile.getCenterVector();
        pacMan.setPositionVector(tileCenterVector);
        pacManMovement.center(pacMan.getObjectShape(), pacManMovement.getHitCircle().getObjectShape(), tileCenterVector);
    }

   public void handleGhostCollisions(){
        if (ghostManager.ghostCollision()) { 
            respawnCharacters();
            canvas.pause(500);  //So the player has half a second to breathe... literally.
        }
    }

    public void respawnCharacters(){
        spawnPacMan();
        ghostManager.respawnGhosts();
    }

    public void menu(){
        canvas.setBackground(Color.BLACK);
        gameMenu();
        createPacManText();
        createJavaText();
        createStartButton();
    }

    public void createStartButton(){
        startButton = new Button("S T A R T ");
        startButton.setPosition(canvas.getWidth()/2.23,canvas.getHeight()/2.4);
        canvas.add(startButton);
        startButton.onClick(() -> {
           canvas.remove(startButton);
           canvas.remove(pacManText);
           canvas.remove(javaText);
           createGameObjects();
           gameRunning();
        });
    }

      public void createPacManText(){
        pacManText = new GraphicsText("PAC-MAN", canvas.getWidth()/2.1 ,canvas.getHeight()/2.5);
        pacManText.setStrokeWidth(.7);
        pacManText.setStrokeColor(Color.YELLOW);
        pacManText.setScale(25);
        pacManText.setFilled(false);
        canvas.add(pacManText);
    }

    public void createJavaText(){
        javaText = new GraphicsText("Java", canvas.getWidth()/2.1, canvas.getHeight()/1.5);
        javaText.setStrokeWidth(.2);
        javaText.setStrokeColor(new Color(200,0,255));
        javaText.setScale(15);
        javaText.setFontStyle(FontStyle.BOLD_ITALIC);
        javaText.setFilled(false);
        canvas.add(javaText);
    }

    public static void gameOver(){
        gameState = GameState.GAME_OVER;
    }

    public static void gameRunning(){
        gameState = GameState.RUNNING;
    }

    public static void pauseGame(){
        gameState = GameState.PAUSED;
    }

    public static void gameMenu(){
        gameState = GameState.MENU;
    }

    public static void main(String[] args) {
        new PacManGame();
    }
    
}
