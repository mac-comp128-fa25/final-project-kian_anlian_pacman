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
    private GhostManager ghostManager;
    private PacMan pacMan;
    private GraphicsText fpsText = new GraphicsText("null", 50,50); //Need reference
    private static long nextSecond = System.currentTimeMillis() + 1000; //1000 ms = 1 sec
    private static int framesInLastSecond = 0;
    private static int framesInCurrentSecond = 0;
    private int lifeCount = 3;
    private int score = 0;
    private PacMan [] lives;
    private Button restartButton;
    private GraphicsText scoreText = new GraphicsText("SCORE: " + score, 700, 40);
    private GraphicsText wonText = new GraphicsText("Y O U    W O N    ", 700, 500);
    private boolean won;
    
    public UI(CanvasWindow canvas, TileManager tileManager, PacMan pacMan){
        this.canvas = canvas;
        this.tileManager = tileManager;
        this.pacMan = pacMan;
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
        lifeCount = 3;
        lives =  new PacMan[lifeCount];
        int startX = 0;
        int y = canvas.getHeight() - (canvas.getWidth() / 20);
        int scale = 3;
        Vector2D currentPositionVector;

        for (int i = 0; i < lifeCount; i++){
            currentPositionVector = new Vector2D (startX += 50, y);
            PacMan pacManLife  = new PacMan(currentPositionVector, canvas, scale);
            lives[i] = pacManLife;
            pacManLife.addToCanvas();
        }
    }

    public void clearLives(){
        for (PacMan pacManShape : lives){
            if (pacManShape != null) pacManShape.removeFromCanvas();
        }
    }
    
     /*
     * So we can see current FPS if we have lag spikes
     */
    public int getFPS(){
        long currentTime = System.currentTimeMillis(); 
        
        if (currentTime > nextSecond){ //if it's been a second, update
            nextSecond += 1000;
            framesInLastSecond = framesInCurrentSecond;
            framesInCurrentSecond = 0; //reset to 0 for next update
        }
        framesInCurrentSecond++; //goes from 0 to 60 each second then resets
        
        return framesInLastSecond; //return the past second's FPS
    }
    
    public void createRestartButton(){ 
      restartButton = new Button("R E S T A R T ?");
      restartButton.setPosition(canvas.getWidth()/2.2,canvas.getHeight()/2.5);
      canvas.add(restartButton);  
      if (won) canvas.add(wonText);
      
    restartButton.onClick(() -> {
        PacManGame.gameRunning(); //redrawing on top is issue
        if (won) canvas.remove(wonText);
        canvas.remove(restartButton);
        clearLives();
        createLifeIndicators();
        resetTileMatrix();
        initialize();
    });
    }

    public void setGhostManager(GhostManager ghostManager){
        this.ghostManager = ghostManager;
    }

    public void resetTileMatrix(){
        tileManager.resetPelletsEaten();
        tileManager.resetTotalPellets();
        tileManager.resetPelletTiles(ghostManager); 
        ghostManager.topLayer();
        pacMan.addToCanvas(); //so pac is on top layer of canvas
    }

    public void initialize(){
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
        won = tileManager.getPelletsEaten() == tileManager.getTotalPellets();
        wonText.setStrokeColor(Color.GREEN);
        wonText.setStrokeWidth(.7);
        wonText.setScale(10);
        
        if (lifeCount == 0 || won) {
            PacManGame.gameOver();
            createRestartButton();
        }
        score = tileManager.getPelletsEaten() * 20;
        scoreText.setText("Score : " + score);
        fpsText.setText(getFPS() + " FPS");
    }
}
