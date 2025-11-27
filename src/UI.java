
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;

/*
 *Handles score text, game over text, etc
 */
public class UI { 
    private CanvasWindow canvas;
    private GraphicsText fpsText = new GraphicsText("null", 50,50); //Need reference
    private static long nextSecond = System.currentTimeMillis() + 1000; //1000 ms = 1 sec
    private static int framesInLastSecond = 0;
    private static int framesInCurrentSecond = 0;
    private int lifeCount = 3;
    private PacManShape [] lives;
    
    public UI(CanvasWindow canvas, int lifeCount){
        this.canvas = canvas;
        this.lifeCount = lifeCount;
        lives =  new PacManShape[lifeCount];
        createLifeIndicators();
    }

    public void removeLife(){
        if (lifeCount > 0){
            lives[--lifeCount].removeFromCanvas(); 
            lives[lifeCount] = null;
        }
    }

    public void createLifeIndicators(){
        lifeCount = 3; //in case we need to call again for restarting the game
        int startX = 0;
        int y = canvas.getHeight() - (canvas.getWidth() / 20);
        int scale = 3;
        Vector2D currentPositionVector;

        for (int i = 0; i < lifeCount; i++){
            currentPositionVector = new Vector2D (startX += 50, y);
            PacManShape pacManLife  = new PacManShape(currentPositionVector, canvas, scale);
            lives[i] = pacManLife;
            pacManLife.addToCanvas();
        }
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

    public void initialize(){ //Adds FPS counter, will add play button later
        canvas.add(fpsText);
        fpsText.setFillColor(Color.GREEN);
        fpsText.setScale(2);
    }

    public void update(){
        fpsText.setText(getFPS() + " FPS");
    }
}
