import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;

public class PacManGame {
    private CanvasWindow canvas;
    private static final int CANVAS_WIDTH = 1920; 
    private static final int CANVAS_HEIGHT = 1080; // 1080p
    private PacMan pacMan;
    private UI ui;
    private GhostManager ghostManager;
    private TileManager tileManager;
    private Vector2D pacManPositionVector;
    private Movement pacManMovement;
    private KeyHandler keyHandler;
    public enum GameState {MENU, PLAYING, PAUSED, GAME_OVER} //enum = way to enclose a bunch of constants guarenteed to be different integers
    private static GameState gameState = GameState.PLAYING; //start off in playing state
    
    public PacManGame(){
        createGameObjects();
        update();
    }

    private void createGameObjects(){
        canvas = new CanvasWindow("Pac-Man: Java", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.BLACK); 
        
        pacManPositionVector = new Vector2D(canvas.getWidth()/2 - 10, canvas.getHeight()/2.3);
        pacManMovement = new RotationMovement(pacManPositionVector, canvas);

        pacMan = new PacMan(pacManPositionVector, canvas);
        pacManMovement.setShape(pacMan.getObjectShape());

        tileManager = new TileManager(canvas, pacMan);

        keyHandler = new KeyHandler(pacManMovement, pacMan, tileManager);
        
        canvas.onKeyDown(keyDown -> keyHandler.keyPressed(keyDown)); 
        canvas.onKeyUp(keyUp -> keyHandler.keyReleased(keyUp));     
        
        pacMan.addToCanvas(); //we want pac-man and ui to be top elements
        
        ui = new UI(canvas, 3, tileManager);
        ui.initialize();
        ghostManager = new GhostManager(canvas, pacManMovement, ui);
    }

    private void update(){ //Where we'll call all the move functions. Animates objects.
        canvas.animate(animationEvent -> {
        
        if (gameState == GameState.PLAYING){
            ui.update(); 
            updateLives();
            tileManager.handlePellets(ghostManager);
            keyHandler.checkKeyPresses();
        }
    });
    }

   public void updateLives(){
        if (ghostManager.ghostCollision()) {
            pacMan.respawn();
        }
    }

    public static void gameOver(){
        gameState = GameState.GAME_OVER;
    }

    public static void restartGame(){
        gameState = GameState.PLAYING;
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
