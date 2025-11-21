/*
 * Uses 2D array to determine what tiles are 
 * walkable and what tiles are MazeWalls.
 * This Maze will be used to 
 * implement the Ghost's DFS algorithm. 
 * Using the Graph-Maze activity as our template
 * will make this easier.
 */

import edu.macalester.graphics.CanvasWindow;

public class TileManager implements Manager{
    private CanvasWindow canvas;
    private PacMan pacMan;
    private int pacManSize;
    private Tile[][] tileMatrix;

    public TileManager(CanvasWindow canvas, PacMan pacMan){
        this.canvas = canvas;
        this.pacMan = pacMan;
        pacManSize = pacMan.getScale(); 
        createTiles();

    }

    public Tile[][] getTileMatrix(){
        return tileMatrix;
    }

    @Override
    public void spawnCollection() { //Where the algorithm for spawning the layout of walls will be
    }

    @Override
    public void manageCollection() {//Updating tile states to visited
    }

    @Override
    public void handleCollisions() { //Pac-Man should stop moving on collision with a MazeWall.
    } 
    //TODO: Use integer division to create a 2D array. Each tile = tiles[x][y].
    /*
    * Use int(x/tileWidth) and int(y/tileHeight) to round (x,y) pos 
    * into a tile in 2D mazeArray, where tileWidth and tileHeight are just how 
    * many pixels long and wide each tile are. For now we'll just try to get
    * grid drawn on the canvas so we can see the outline of each tile on the
    * game map. We'll hardcode the tiles with walls inside them to be 
    * unwalkable. All other tiles are walkable for PacMan and the Ghosts.
    */

    public void createTiles(){
        tileMatrix = new Tile [13][9];

        
        Vector2D tilePosVector = new Vector2D(0,0);
        Tile tile = new Tile (false, false, tilePosVector, canvas, pacManSize);
        double previousRightX = tile.getXPosition() + tile.size();
        double previousRightY = tile.getYPosition() + tile.size();

        tileMatrix[0][0] = tile;
        double tileX = tile.getXPosition();
        double tileY = tile.getYPosition();
        for (int i = 0; i < canvas.getWidth(); i++){
            for (int j = 0; j < canvas.getHeight(); j++){
                Vector2D tilePosVectorNew = new Vector2D(tileX, tileY);
                Tile tileNew = new Tile(false, false, tilePosVectorNew, canvas, pacManSize);
                tileMatrix[i][j] = tileNew;
                tileNew.addToCanvas();
                tileY = previousRightY;
                previousRightY = tileY + tile.size();
                // if (j-1 == canvas.getHeight()) {
                //     tileY = tile.getYPosition();
                //     previousRightY = tile.getYPosition() + tile.size();
                // }
            }
            tileX = previousRightX;
            previousRightX = tileX + tile.size();
        }
    }
    
}
