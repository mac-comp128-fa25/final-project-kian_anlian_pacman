import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;

public class PacManGame {
    private CanvasWindow canvas;
    private static final int CANVAS_WIDTH = 1920; //1080p
    private static final int CANVAS_HEIGHT = 1080;
    private GameCharacter pacMan;
    private KeyHandler keyHandler;
    private Vector2D positionVector;

    public PacManGame(){
        canvas = new CanvasWindow("Pac-Man: Java", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.setBackground(Color.BLACK); 
        positionVector = new Vector2D(canvas.getWidth()/2, canvas.getHeight()/2);
        pacMan = new PacMan(positionVector, canvas);
        keyHandler = new KeyHandler(pacMan);
        createMenu();
        animateObjects();
    }

    private void createMenu(){ //Will just add Pac-Man: Java as a text object and also a Start Button
    }

    private void animateObjects(){ //Where we'll call all the move functions
    canvas.animate(animationEvent -> {
        keyHandler.checkKeyPresses();
        canvas.onKeyDown(keyDown -> keyHandler.keyPressed(keyDown)); //constantly checking keyPresses
    });
    }
    
    public static void main(String[] args) {
        new PacManGame();

    }


}
