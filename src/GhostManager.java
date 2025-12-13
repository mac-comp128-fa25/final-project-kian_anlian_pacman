import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsObject;

/**
 * @author Kian Naeimi
 * @author AnLian Krishnamurthy
 * December 2025
 * 
 * With help from Professor Suhas Arehalli and preceptor Lucy Manalang
 * on the overall pathfinding algorithm / structure.
 * 
 * This class is the heart and soul of the program. Here, the Movement abstraction
 * and TileManager class work together to create four dynamic AI agents. They
 * choose their paths in real time based on their location in the game-map 
 * compared to Pac-Man's. 
 * 
 * This was a very interesting problem to chew on: 
 * How do you create a graph pathfinding algorithm that can hold up to both it's target (controlled by the user!) 
 * and it's sources constantly traveling between vertices in real-time? As it turns out, besides some interesting 
 * variations, Pac-Man really is just a variation of a maze problem*. So let's treat it as such!
 * 
 *  *Actually, the original Pac-Man didn't use any complex pathfinding algorithms for ghost behavior! 
 * Instead, the developers opted for a simpler heuristic: Euclidian Distance comparisons between tiles,
 * and gave the ghosts hard-coded 'personalities' which gave the illusion of algorithmic complexity.
 */

public class GhostManager{
    private CanvasWindow canvas;
    private UI ui;
    private TileManager tileManager;
    private GraphicsObject pacMan;
    private Deque<Tile> finalPathStack;

    //Names from original game!
    private Ghost pinky; //pink ghost
    private Ghost blinky; //red ghost
    private Ghost inky; //blue ghost
    private Ghost clyde; //orange ghost

    private Vector2D pinkyTileCenterVector;
    private Vector2D blinkyTileCenterVector;
    private Vector2D inkyTileCenterVector;
    private Vector2D clydeTileCenterVector;
    
    private Movement pinkyMovement;
    private Movement blinkyMovement;
    private Movement inkyMovement;
    private Movement clydeMovement;
    private Movement pacManMovement;

    private Movement[] ghostMovements = new Movement[4];
    private Ghost[] ghosts = new Ghost[4];

    public GhostManager(CanvasWindow canvas, Movement pacManMovement, GraphicsObject pacMan, UI ui, TileManager tileManager){
        this.tileManager = tileManager;
        this.canvas = canvas;
        this.ui = ui;
        this.pacManMovement = pacManMovement;
        this.pacMan = pacMan;
        spawnGhosts();
    }  
    
    /**
     * The high-level idea of this algorithm operates as follows: 
     * 
     * -For any ghost, add their initial tile (so their current tile) to the queue. Next, grab one of the 
     * following adjacent tiles: 
     *  
     * If this tile is Pac-Man's, we can end the search and backtrack from Pac-Man's tile 
     * (via a Stack for properly reversed order) all the way to the Ghost's using each tile's previous pointer. Once we
     * get to the Ghost's original startingTile, we pop off the adjacentTile we added most recently to the stack. 
     * This is because a Ghost only needs to know one thing every frame: What direction it's nextTile is in.
     * Following this approach, we can simply return the next adjacentTile that takes each Ghost the fastest to Pac-Man!
     * 
     *  Otherwise, we'll keep visiting the neighbors of every following adjacent tile, level by level, until we
     * find Pac-Man. We have a Breadth-First-Search on our hands! 
     * 
     * We could have done a Depth-First-Search instead, but then we wouldn't have the guarentee of the shortest path
     * that we get via a BFS.
     * 
     * @param pacMan We pass in Pac-Man so we can get the reference of his currentTile.
     * @param adjacencyList Our HashMap where each Key is some Tile in the tileMatrix, and each value is the associated LinkedList of adjacent tiles.
     * @param ghost Each ghost is passed into this function to compute their path via recursion in findPreviousTilesRecursive()
     * @return The next (adjacent) tile such that we are at an optimal distance from Pac-Man.
     */
    public Tile findShortestPath(GraphicsObject pacMan, HashMap<Tile,List<Tile>> adjacencyList, Ghost ghost){
        Queue<Tile> tileQueue = new ArrayDeque<Tile>();
        GraphicsObject ghostShape = ghost.getObjectShape();
        Tile startingTile = tileManager.getCurrentTile(ghostShape);
        finalPathStack = new ArrayDeque<Tile>();
        tileQueue.add(startingTile);
        
        while(!tileQueue.isEmpty()){
            Tile currentTile = tileQueue.poll(); 

            if (currentTile == tileManager.getCurrentTile(pacMan)) {
                findPreviousTileRecursive(currentTile, ghost); 
                break;
            }
            
            List<Tile> adjacentTiles = adjacencyList.get(currentTile);
            
            for (Tile tile : adjacentTiles) {
                boolean adjExplored = tile.isExplored();
                boolean alreadyQueued = tileQueue.contains(tile);
                
                if (!(adjExplored || alreadyQueued)){
                    tile.setPrevious(currentTile); //For following pointers back once we begin recursing.
                    tileQueue.add(tile);
                }
                currentTile.setExplored(true);
            }
        }
        for (Tile tile : adjacencyList.keySet()) tile.setExplored(false); //Reset explored state for the next search. 
            
        if (finalPathStack.peek() == null) return tileManager.getCurrentTile(ghostShape); // Null when pac-man and ghost are on the same tile, so just give the ghost his own tile for now.
        
        return finalPathStack.pop(); //First tile off the stack is all we need (the next tile for next direction)!
    }

    /**
     * This is where the fun of having a fully abstracted Movement system comes in! So, now that we have a BFS algorithm
     * to work with, all we have to do is call it for each ghost. We call this method in the main class's update loop
     * so the ghost is making a decision in live time based on every frame.  We give each Ghost their nextTile
     * and their Movement objects for movement delegation. Finally, we queue moves. But not using the actual 
     * Data-Structure. Instead, we use a system of nextMove and currentMove references to center each Ghost and create 
     * cornering behavior! This system is abstracted from the ghost (all they can do is request moves!), and explained
     * in the StandardMovement class. The final move() call is just a convienience method that calls multiple
     * Movement methods at once to handle the sequence of queued moves, along with applying velocity vectors
     * in the requested direction (if legal)!
     */
    public void traverseShortestPath(){
        Movement ghostMovement = null; //Need a reference to assign each ghost's Movement to.
        
        for (Ghost ghost : ghosts){
            Tile nextTile = findShortestPath(pacMan, tileManager.getAdjacencyMap(), ghost);
            if (ghost.equals(pinky)) ghostMovement = pinkyMovement;
            if (ghost.equals(blinky)) ghostMovement = blinkyMovement;
            if (ghost.equals(clyde)) ghostMovement = clydeMovement;
            if (ghost.equals(inky)) ghostMovement = inkyMovement;
    
            int nextTileColumn = tileManager.getColumn(nextTile.getObjectShape());
            int nextTileRow = tileManager.getRow(nextTile.getObjectShape());

            int ghostTileColumn = tileManager.getColumn(ghost.getObjectShape());
            int ghostTileRow = tileManager.getRow(ghost.getObjectShape());

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
            ghostMovement.move();
        }
    }
    
    /**
     * After performing the BFS, we have a reference to Pac-Man's currentTile. Then, we
     * follow each Tile's previous pointer (set in the BFS algorithm) back to the Ghost
     * via pushing them onto the finalPathStack until we hit the Ghost's tile (our base case).
     * @param tile Our target tile / graph vertex, Pac-Man's tile! 
     * @param ghost Our source tiles, each of the Ghosts.
     */
    private void findPreviousTileRecursive(Tile tile, Ghost ghost) {
        if (tile == tileManager.getCurrentTile(ghost.getObjectShape())) {//Are we at the source yet?
            return; //Okay, we've done our in-place modification of finalPathStack, let's back out.
        }
        finalPathStack.push(tile); 
        findPreviousTileRecursive(tile.getPrevious(), ghost); 
    }

    public void spawnGhosts() { 
        chooseSpawnPoints();
        createGhosts();
        linkMovement();
        centerGhosts();
    }

    public void respawnGhosts(){
        for (Ghost ghost : ghosts){ //Remove old ghosts from canvas to get rid of old state/paths.
            ghost.removeFromCanvas();
        }
        spawnGhosts(); //Fresh ghost state.
    }

    /*
     * You can pass each ghost whatever spawnTile you want. The important part is remembering to compute the tile's 
     * corresponding centerVector, because part of what allows the smooth movement queueing behavior is locking movement 
     * changes only at the very center of any tile. 
     */
    public void chooseSpawnPoints(){ //Have to center after this method because of reference order
        Tile pinkySpawnTile = tileManager.getTile(1,1);
        pinkyTileCenterVector = pinkySpawnTile.getCenterVector();

        Tile inkySpawnTile = tileManager.getTile(1, 8);
        inkyTileCenterVector =inkySpawnTile.getCenterVector();

        Tile blinkySpawnTile = tileManager.getTile(17, 8);
        blinkyTileCenterVector = blinkySpawnTile.getCenterVector();

        Tile clydeSpawnTile = tileManager.getTile(17,1);
        clydeTileCenterVector = clydeSpawnTile.getCenterVector();
    }

    /**
     * We use each character's tileCenterVector to make sure they start out in the center, because characters 
     * can only execute queued moves when they reach the center of a tile.
     */
    public void centerGhosts(){
        pinkyMovement.center(pinky.getObjectShape(), pinkyMovement.getHitCircle().getObjectShape(), pinkyTileCenterVector);
        inkyMovement.center(inky.getObjectShape(), inkyMovement.getHitCircle().getObjectShape(), inkyTileCenterVector);
        blinkyMovement.center(blinky.getObjectShape(), blinkyMovement.getHitCircle().getObjectShape(), blinkyTileCenterVector);
        clydeMovement.center(clyde.getObjectShape(), clydeMovement.getHitCircle().getObjectShape(), clydeTileCenterVector);
    }   

    /**
     * Assigning each Ghost a position in the ghosts array so we can iterate over all of them during path traversal.
     */
    public void createGhosts(){
        pinky = new Ghost(pinkyTileCenterVector, canvas, Color.PINK);
        blinky = new Ghost(blinkyTileCenterVector, canvas, Color.RED);
        inky = new Ghost(inkyTileCenterVector, canvas, Color.CYAN);
        clyde = new Ghost(clydeTileCenterVector, canvas, Color.ORANGE);

        ghosts[0] = pinky;
        ghosts[1] = blinky;
        ghosts[2] = inky;
        ghosts[3] = clyde;
    }
    
    /*
     * So Ghosts don't clip under Tiles or Pac-Man.
     */
    public void topLayer(){
        pinky.addToCanvas();
        blinky.addToCanvas();
        inky.addToCanvas();
        clyde.addToCanvas();
    }

    /**
     * Due to how everything is tied together, unfortunately we need to link together Movements and the GraphicsObjects 
     * (the ghosts) they move, even though we're outside of their class.
     */
    public void linkMovement(){
        chooseMovement();
        
        pinkyMovement.setShape(pinky.getObjectShape());
        blinkyMovement.setShape(blinky.getObjectShape());
        inkyMovement.setShape(inky.getObjectShape());
        clydeMovement.setShape(clyde.getObjectShape());
    }

    /**
     * Assigning each Movement reference and giving each Ghost a different speed to give gameplay a more 
     * dynamic feel.
     */
    public void chooseMovement(){
        pinkyMovement = new StandardMovement(pinkyTileCenterVector, canvas);
        blinkyMovement = new StandardMovement(blinkyTileCenterVector, canvas);
        inkyMovement = new StandardMovement(inkyTileCenterVector, canvas);
        clydeMovement = new StandardMovement(clydeTileCenterVector, canvas);

        pinkyMovement.setSpeed(3.05);
        blinkyMovement.setSpeed(1.5);
        inkyMovement.setSpeed(2);
        clydeMovement.setSpeed(2.5);
        
        ghostMovements[0] = pinkyMovement;
        ghostMovements[1] = blinkyMovement;
        ghostMovements[2] = inkyMovement;
        ghostMovements[3] = clydeMovement;

        for (Movement movement : ghostMovements){
            movement.setTileManager(tileManager); //Again, references are tied together tightly so we need to setTileManager outside of the Movement class
        }
    }

    /**
     * This method handles collision state between the Ghosts and Pac-Man. 
     * You can see here why Movement and HitCircle are linked... Movement 
     * needs to *move* each character's HitCircle! 
     * @return True if and only if a ghost has collided with Pac-Man and he has lives to lose.
     */
    public boolean ghostCollision(){
        for (Movement movement : ghostMovements){
            HitCircle hitCircle = movement.getHitCircle();
            HitCircle pacManHitCircle = pacManMovement.getHitCircle();
            
            if (hitCircle.intersects(pacManHitCircle) && ui.removeLife()){ //UI's removeLife() method returns false when Pac-Man still has more lives to lose.
                return true;
            }
        }
        return false;
    }
}
