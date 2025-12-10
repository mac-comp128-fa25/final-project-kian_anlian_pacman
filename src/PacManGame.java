import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.ui.Button;

public class PacManGame {
    private CanvasWindow canvas = new CanvasWindow("Pac-Man: Java", CANVAS_WIDTH, CANVAS_HEIGHT);
    private static final int CANVAS_WIDTH = 1920; 
    private static final int CANVAS_HEIGHT = 1080; // 1080p
    private PacMan pacMan;
    private UI ui;
    private Button startButton;
    private GraphicsText pacManText;
    private GraphicsText javaText;
    private GhostManager ghostManager;
    private TileManager tileManager;
    private Vector2D pacManPositionVector;
    private Movement pacManMovement;
    private KeyHandler keyHandler;
    public enum GameState {MENU, PLAYING, PAUSED, GAME_OVER} //enum = way to enclose a bunch of constants guarenteed to be different integers
    private static GameState gameState = GameState.PLAYING; //start off in playing state
    
    public PacManGame(){
        menu();
        update();
    }

    public void createGameObjects(){ //All the references are tied together here so the order matters
        pacManPositionVector = new Vector2D(canvas.getWidth()/2 - 10, canvas.getHeight()/2.3);
        pacManMovement = new RotationMovement(pacManPositionVector, canvas);

        pacMan = new PacMan(pacManPositionVector, canvas);
        pacManMovement.setShape(pacMan.getObjectShape());

        tileManager = new TileManager(canvas, pacMan);
        pacManMovement.setTileManager(tileManager);

        keyHandler = new KeyHandler(pacManMovement, pacMan, tileManager);
        
        canvas.onKeyDown(keyDown -> keyHandler.keyPressed(keyDown));    
        
        pacMan.addToCanvas(); //we want pac-man and ui to be top elements
        
        ui = new UI(canvas, 3, tileManager);
        ui.initialize();
        
        ghostManager = new GhostManager(canvas, pacManMovement, pacMan, ui, tileManager);
    }

    public void update(){ //Where we'll call all the move functions. Animates objects.
        canvas.animate(animationEvent -> {
        if (gameState == GameState.PLAYING){
            ui.update(); 
            handleCollisions();
            tileManager.handlePellets(ghostManager);
            keyHandler.checkKeyPresses();
            ghostManager.traverseShortestPath();
        }
    });
    }

   public void handleCollisions(){
        if (ghostManager.ghostCollision()) {
            pacMan.respawn();
            canvas.pause(1000);  //so the player has a second to breathe (literally)
        }
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
