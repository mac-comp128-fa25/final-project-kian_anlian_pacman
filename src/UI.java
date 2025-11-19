
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
    
    public UI(CanvasWindow canvas){
        this.canvas = canvas;
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
