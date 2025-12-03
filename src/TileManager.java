/*
 * Uses 2D array to determine what tiles are 
 * walkable and what tiles are MazeWalls.
 * This Maze will be used to 
 * implement the Ghost's DFS algorithm. 
 * Using the Graph-Maze activity as our template
 * will make this easier.
 */

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;
import edu.macalester.graphics.CanvasWindow;

public class TileManager{
    private CanvasWindow canvas;
    private PacMan pacMan;
    private int pacManSize;
    private int pelletsEaten = 0;
    private int totalPellets = 0;
    private static final int TILE_SIZE = 80;
    private Tile[][] tileMatrix;
    private static final int NUM_COLS = 19;
    private static final int NUM_ROWS = 10;

    public TileManager(CanvasWindow canvas, PacMan pacMan){
        this.canvas = canvas;
        this.pacMan = pacMan;
        pacManSize = pacMan.getScale(); 
        createTiles();
    }

    public Tile getCurrentTile(GameObject gameObject){
        int column = getColumn(gameObject);
        int row = getRow(gameObject);
        
        return getTile(column, row);
    }

    public Tile getLeftTile(GameObject gameObject){
        int column = getColumn(gameObject);
        int row = getRow(gameObject);
        Tile leftTile = getTile(column - 1, row);
        
        if (leftTile != null) return leftTile;
        else return getCurrentTile(gameObject);
    }

    public Tile getRightTile(GameObject gameObject){
        int column = getColumn(gameObject);
        int row = getRow(gameObject);
        Tile rightTile = getTile(column + 1, row);
        
        if (rightTile != null) return rightTile;
        else return getCurrentTile(gameObject);
    }

    public Tile getAboveTile(GameObject gameObject){

        int column = getColumn(gameObject);
        int row = getRow(gameObject);
        Tile aboveTile = getTile(column , row - 1); //ABOVE IS ROW - 1 prev reversed
        if (aboveTile != null) return aboveTile;
        else return getCurrentTile(gameObject);
    }

    public Tile getBelowTile(GameObject gameObject){
        int column = getColumn(gameObject);
        int row = getRow(gameObject);
        Tile belowTile = getTile(column, row + 1); //BELOW IS ROW + 1 prev reversed
        
        if (belowTile != null) return belowTile;
        else return getCurrentTile(gameObject);
    }

    public Tile getGrandBelowTile(GameObject gameObject){
        int column = getColumn(gameObject);
        int row = getRow(gameObject);
        Tile belowTile = getTile(column, row + 2); //BELOW IS ROW + 1 prev reversed
        
        if (belowTile != null) return belowTile;
        else return getCurrentTile(gameObject);
    }

    public int getColumn(GameObject gameObject){
        // System.out.println(" COLUMN: " + (gameObject.getXPosition() / TILE_SIZE) + " ROUNDED: " + (int)(gameObject.getXPosition() / TILE_SIZE));
        return (int) (gameObject.getXPosition() / TILE_SIZE);
    }

    public int getRow (GameObject gameObject){
        // System.out.println(" ROW: " +gameObject.getYPosition() / TILE_SIZE + " ROUNDED: " + (int)(gameObject.getYPosition() / TILE_SIZE));
        return (int) (gameObject.getYPosition() / TILE_SIZE);
    }

    public LinkedList<Tile>[] getAdjacentTiles(Tile tile){ //returns adjacency list bc we have a sparse graph
        int adjacenyListLength = tileMatrix[getColumn(tile)].length;
        @SuppressWarnings("unchecked") //complains about cast (have to cast to do array of LinkedLists)
        LinkedList<Tile> [] adjacentTiles = (LinkedList<Tile>[]) new LinkedList<?>[adjacenyListLength];
        return adjacentTiles; //temp
    }

    public Tile[][] getTileMatrix(){
        return tileMatrix;
    }

    public Tile getTile(int column, int row){
        
        try {
            return tileMatrix[column][row];
        } catch (IndexOutOfBoundsException e) {
            return null; //so we dont crash when we return a tile out of the matrix's indices. 
        }
    }

    public void handlePellets(GhostManager ghostManager) { //Pac-Man should stop moving on collision with a MazeWall.

        for (Tile[] columnOfTiles : tileMatrix){ //columns
            for (Tile tile : columnOfTiles){ //rows
                if (tile.hasFoodPellet() && pacMan.intersects(tile)){
                    tile.setDefault();
                    ghostManager.topLayer();
                    pacMan.addToCanvas();
                    pelletsEaten++;
                }
            }
        }
    } 
    
    public void resetPelletsEaten(){
        pelletsEaten = 0;
    }

    public void resetTotalPellets(){
        totalPellets = 0;
    }

    public int getPelletsEaten(){
        return pelletsEaten;
    }

    public int getTotalPellets(){
        return totalPellets;
    }
    
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

        setTiles(); //TODO: Fix: This is causing lag on restart
    }

    public void setTiles(){
        InputStream resourceStream = TileManager.class.getResourceAsStream("/Map1");
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
                        totalPellets++;
                        tile.setPellet();
                        break;
                    case 2:
                        tile.setDefault();
                        break;
                    default:
                        throw new RuntimeException("Tile type value must be between [0,2]");
                }
            }
        }
        input.close();
    }
}
