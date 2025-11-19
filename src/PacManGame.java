import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;

public class PacManGame {
    private CanvasWindow canvas;
    private static final int CANVAS_WIDTH = 1920; //1080p
    private static final int CANVAS_HEIGHT = 1080;
    private static long nextSecond = System.currentTimeMillis() + 1000; //1000 ms = 1 sec
    private static int framesInLastSecond = 0;
    private static int framesInCurrentSecond = 0;
    private PacMan pacMan;
    private KeyHandler keyHandler;
    private Vector2D positionVector;
    private Movement movement;
    
    GraphicsText fpsText = new GraphicsText("null", 50,50); //Need reference

    public PacManGame(){
        createGameObjects();
        createUI();
        update();
    }

    private void createUI(){ //Adds FPS counter, will add play button later
        canvas.add(fpsText);
        fpsText.setFillColor(Color.GREEN);
        fpsText.setScale(2);
    }

    private void createGameObjects(){
        canvas = new CanvasWindow("Pac-Man: Java", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.BLACK); 
        positionVector = new Vector2D(canvas.getWidth()/2, canvas.getHeight()/2);
        movement = new Movement(positionVector);
        pacMan = new PacMan(positionVector, canvas, movement);
        movement.setShape(pacMan.getObjectShape());
        keyHandler = new KeyHandler(pacMan);
        canvas.onKeyDown(keyDown -> keyHandler.keyPressed(keyDown)); 
    }

    /*
     * So we can see current FPS if we have lag spikes
     */
    public int getFPS(){
        long currentTime = System.currentTimeMillis(); //constantly updating bc in lambda
        
        if (currentTime > nextSecond){ //if it's been a second, update
            nextSecond += 1000;
            framesInLastSecond = framesInCurrentSecond;
            framesInCurrentSecond = 0; //reset to 0 for next update
        }
        framesInCurrentSecond++; //goes from 0 to 60 each second then resets
        
        return framesInLastSecond; //return the past second's FPS
    }
   
    private void update(){ //Where we'll call all the move functions. Animates objects
    canvas.animate(animationEvent -> {
        keyHandler.checkKeyPresses();
        fpsText.setText(getFPS() + " FPS");
    });
    }
    
    public static void main(String[] args) {
        new PacManGame();
    }
}
