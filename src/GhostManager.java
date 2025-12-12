import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import edu.macalester.graphics.CanvasWindow;

public class GhostManager{
    private CanvasWindow canvas;
    private UI ui;
    private TileManager tileManager;
    private PacMan pacMan;

    //Names from original game
    private Ghost pinky; //pink ghost
    private Ghost blinky; //red ghost
    private Ghost inky; //blue ghost
    private Ghost clyde; //orange ghost
    
    private Vector2D pinkyPositionVector;  
    private Vector2D blinkyPositionVector; 
    private Vector2D inkyPositionVector;
    private Vector2D clydePositionVector;

    private Movement pinkyMovement;
    private Movement blinkyMovement;
    private Movement inkyMovement;
    private Movement clydeMovement;
    private Movement pacManMovement;

    private Movement[] ghostMovements = new Movement[4];
    private Ghost[] ghosts = new Ghost[4];

    public GhostManager(CanvasWindow canvas, Movement pacManMovement, PacMan pacMan, UI ui, TileManager tileManager){
        this.tileManager = tileManager;
        this.canvas = canvas;
        this.ui = ui;
        this.pacManMovement = pacManMovement;
        this.pacMan = pacMan;
        spawnGhosts();
    }  

    public Tile findShortestPath(PacMan pacMan, HashMap<Tile,List<Tile>> adjacencyList, Ghost ghost){
        Queue<Tile> tileQueue = new ArrayDeque<Tile>();
        Deque<Tile> finalPathStack = new ArrayDeque<Tile>();
        Tile startingTile = tileManager.getCurrentTile(ghost);
        
        tileQueue.add(startingTile);
        
        while(!tileQueue.isEmpty()){
            Tile currentTile = tileQueue.poll(); //dequeue operation
            
            if (currentTile == tileManager.getCurrentTile(pacMan)) {
                finalPathStack = findPreviousTileRecursive(currentTile, finalPathStack, ghost);
                break;
            }
            
            List<Tile> adjacentTiles = adjacencyList.get(currentTile);
            
            for (Tile tile : adjacentTiles) {
                boolean adjExplored = tile.isExplored();
                boolean alreadyQueued = tileQueue.contains(tile);
                
                if (!(adjExplored || alreadyQueued)){
                    tile.setPrevious(currentTile); 
                    tileQueue.add(tile);
                }
                currentTile.setExplored(true);
            }
        }
        for (Tile tile : adjacencyList.keySet()) {
            tile.setExplored(false);
        }
        
        if (finalPathStack == null)  {
            return tileManager.getCurrentTile(ghost);
        }
        return finalPathStack.pop(); //first tile off the stack is all we need (the next tile for next direction)
    }

    public void traverseShortestPath(){
        Movement ghostMovement = null;
        
        for (Ghost ghost : ghosts){
            Tile nextTile = findShortestPath(pacMan, tileManager.getAdjacencyMap(), ghost);
            if (ghost == pinky) ghostMovement = pinkyMovement;
            if (ghost == blinky) ghostMovement = blinkyMovement;
            if (ghost == clyde) ghostMovement = clydeMovement;
            if (ghost == inky) ghostMovement = inkyMovement;
    
            int nextTileColumn = tileManager.getColumn(nextTile);
            int nextTileRow = tileManager.getRow(nextTile);

            int ghostTileColumn = tileManager.getColumn(ghost);
            int ghostTileRow = tileManager.getRow(ghost);

            if (nextTileColumn == ghostTileColumn + 1){
                ghostMovement.queueRight();
            }

            if (nextTileRow == ghostTileRow + 1){
                ghostMovement.queueDown();
                
            }

            if (nextTileRow == ghostTileRow - 1){
                ghostMovement.queueUp();
            }

            if (nextTileColumn == ghostTileColumn - 1){
                ghostMovement.queueLeft();
            }

            ghostMovement.handleQueue();
        }
    }
    
    private Deque<Tile> findPreviousTileRecursive(Tile tile, Deque<Tile> finalPathStack, Ghost ghost) {
        if (tile == tileManager.getCurrentTile(ghost)) {
            return null; //we dont want the ghost's current tile on the path
        }
        finalPathStack.push(tile);
        findPreviousTileRecursive(tile.getPrevious(), finalPathStack, ghost);

        return finalPathStack;
    }

    public void spawnGhosts() { //Spawn @ 4 cornerns, 50 x and y units away from each corner
        chooseSpawnPoints();
        createGhosts();
        linkMovement();
    }

    public void respawnGhosts(){
        for (Ghost ghost : ghosts){
            ghost.removeFromCanvas();
        }
        spawnGhosts();
    }

    public void chooseSpawnPoints(){//canvasWidth: 845  canvasHeight: 1540
        int leftX = canvas.getWidth() / 13;
        double topY = canvas.getHeight() / 7;

        int rightX = canvas.getWidth() - (canvas.getWidth() / 11);
        int bottomY = canvas.getHeight() - (canvas.getWidth() / 9);
        
        pinkyPositionVector = new Vector2D(leftX, topY); //top left
        blinkyPositionVector = new Vector2D(rightX, bottomY); //bottom right
        inkyPositionVector = new Vector2D(leftX, bottomY); //bottom left
        clydePositionVector = new Vector2D(rightX, topY); //bottom right
    }

    public void createGhosts(){
        pinky = new Ghost(pinkyPositionVector, canvas, pinkyMovement, Color.PINK);
        blinky = new Ghost(blinkyPositionVector, canvas, blinkyMovement, Color.RED);
        inky = new Ghost(inkyPositionVector, canvas, inkyMovement, Color.CYAN);
        clyde = new Ghost(clydePositionVector, canvas, clydeMovement, Color.ORANGE);

        ghosts[0] = pinky;
        ghosts[1] = blinky;
        ghosts[2] = inky;
        ghosts[3] = clyde;
    }

    public void topLayer(){
        pinky.addToCanvas();
        blinky.addToCanvas();
        inky.addToCanvas();
        clyde.addToCanvas();
    }

    public void linkMovement(){
        chooseMovement();
        
        pinkyMovement.setShape(pinky.getObjectShape());
        blinkyMovement.setShape(blinky.getObjectShape());
        inkyMovement.setShape(inky.getObjectShape());
        clydeMovement.setShape(clyde.getObjectShape());
    }

    public void chooseMovement(){
        pinkyMovement = new StandardMovement(pinkyPositionVector, canvas);
        blinkyMovement = new StandardMovement(blinkyPositionVector, canvas);
        inkyMovement = new StandardMovement(inkyPositionVector, canvas);
        clydeMovement = new StandardMovement(clydePositionVector, canvas);

        pinkyMovement.setSpeed(3.05);
        blinkyMovement.setSpeed(1.5);
        inkyMovement.setSpeed(2);
        clydeMovement.setSpeed(2.5);
        
        ghostMovements[0] = pinkyMovement;
        ghostMovements[1] = blinkyMovement;
        ghostMovements[2] = inkyMovement;
        ghostMovements[3] = clydeMovement;

        for (Movement movement : ghostMovements){
            movement.setTileManager(tileManager);
        }
    }

    public boolean ghostCollision(){
        for (Movement movement : ghostMovements){
            HitCircle hitCircle = movement.getHitCircle();
            HitCircle pacManHitCircle = pacManMovement.getHitCircle();
            
            if (hitCircle.intersects(pacManHitCircle) && ui.removeLife()){
                return true;
            }
        }
        return false;
    }
}
