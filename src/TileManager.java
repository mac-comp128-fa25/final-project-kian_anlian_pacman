/*
 * Uses 2D array to determine what tiles are 
 * walkable and what tiles are MazeWalls.
 * This Maze will be used to 
 * implement the Ghost's DFS algorithm. 
 * Using the Graph-Maze activity as our template
 * will make this easier.
 */

import java.io.InputStream;
import java.util.Scanner;

import edu.macalester.graphics.CanvasWindow;

public class TileManager implements Manager{
    private CanvasWindow canvas;
    private int pacManSize;
    private Tile[][] tileMatrix;
    private static final int NUM_COLS = 19;
    private static final int NUM_ROWS = 10;

    public TileManager(CanvasWindow canvas, PacMan pacMan){
        this.canvas = canvas;
        pacManSize = pacMan.getScale(); 
        spawnCollection();
    }

    public Tile[][] getTileMatrix(){
        return tileMatrix;
    }

    public Tile getTile(int column, int row){
        return tileMatrix[column][row];
    }

    @Override
    public void spawnCollection() { //Where the algorithm for spawning the layout of walls will be
        createTiles();
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
        
        tileMatrix = new Tile [NUM_COLS][NUM_ROWS]; //[#columns][#rows]  
        
        Vector2D tilePosVector = new Vector2D(0,0);
        
        Tile tile = new Tile (false, false, tilePosVector, canvas, pacManSize);

        for (int i = 0; i < tileMatrix.length; i++){
            
            for (int j = 0; j < tileMatrix[0].length; j++){

                Vector2D newTilePosVector = new Vector2D(tile.size() * i, tile.size() * j); //We were overcomplicating it way too much

                Tile newTile = new Tile(false, false, newTilePosVector, canvas, pacManSize);

                if (i == tileMatrix.length - 1){ //scaling to cover last bit of y axis
                    newTile.scaleTile(2, 1);
                }
                
                tileMatrix[i][j] = newTile;
                
                newTile.addToCanvas();

            }
        }

        setTiles();
    }

    public void setTiles(){
        InputStream resourceStream = TileManager.class.getResourceAsStream("/Maze1");

        Scanner input = new Scanner(resourceStream);
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                int n = input.nextInt();
                Tile tile = tileMatrix[j][i];
                switch (n) {
                    case 0:
                        tile.setWall();
                        break;
                    case 1:
                        tile.setPellet();
                        break;
                    case 2:
                        tile.setDefault();
                        break;
                    default:
                        throw new RuntimeException("square type value must be between [0,2]");
                }
            }
        }
        input.close();
    }
    
}
