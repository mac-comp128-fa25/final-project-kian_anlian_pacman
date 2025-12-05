
import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.FontStyle;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.ui.Button;

/*
 *Handles score text, game over text, etc
 */
public class UI { 
    private CanvasWindow canvas;
    private TileManager tileManager;
    private GraphicsText fpsText = new GraphicsText("null", 50,50); //Need reference
    private static long nextSecond = System.currentTimeMillis() + 1000; //1000 ms = 1 sec
    private static int framesInLastSecond = 0;
    private static int framesInCurrentSecond = 0;
    private int lifeCount = 3;
    private int score = 0;
    private PacManShape [] lives;
    private Button restartButton;
    private GraphicsText scoreText = new GraphicsText("SCORE: " + score, 700, 40);
    
    public UI(CanvasWindow canvas, int lifeCount, TileManager tileManager){
        this.canvas = canvas;
        this.lifeCount = lifeCount;
        this.tileManager = tileManager;
        createLifeIndicators();
    }

    public boolean removeLife(){ 
        if (lifeCount > 0 ){
            lives[--lifeCount].removeFromCanvas(); 
            lives[lifeCount] = null;
            return true;
        }
        return false;
    }

    public void createLifeIndicators(){
        lifeCount = 3; //in case we need to call again for restarting the game
        lives =  new PacManShape[lifeCount];
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
    
    public void createRestartButton(){ //once we get canvas to pause on no lives left
      restartButton = new Button("R E S T A R T ?");
      restartButton.setPosition(canvas.getWidth()/2.2,canvas.getHeight()/2.5);
      canvas.add(restartButton);  
      restartButton.onClick(() -> {
           canvas.remove(restartButton);
        //    canvas.removeAll(); //remember to fix lag bc draws twice
           resetTileMatrix();
           createLifeIndicators();
           initialize();
           PacManGame.gameRunning();
        });
    }

    public void resetTileMatrix(){
        tileManager.resetPelletsEaten();
        tileManager.resetTotalPellets();
        tileManager.setTiles(); //TODO: Get help from Suhas for lag... -20 FPS every restart
    }

    public void initialize(){ //Adds FPS counter, will add play button later
        canvas.add(fpsText);
        canvas.add(scoreText);
        fpsText.setFillColor(Color.GREEN);
        scoreText.setFilled(false);
        scoreText.setStrokeWidth(.5);
        scoreText.setStrokeColor(new Color(200, 0, 255)); //purple
        fpsText.setScale(2);
        scoreText.setScale(5);
        scoreText.setFontStyle(FontStyle.BOLD);
    }

    public void update(){
        if (lifeCount == 0 || tileManager.getPelletsEaten() == tileManager.getTotalPellets()) {
            PacManGame.gameOver();
            createRestartButton();

        }
        score = tileManager.getPelletsEaten() * 20;
        scoreText.setText("Score : " + score);
        fpsText.setText(getFPS() + " FPS");
    }
}
