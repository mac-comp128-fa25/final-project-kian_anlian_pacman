
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import edu.macalester.graphics.CanvasWindow;

public class TileManager{
    private HashMap<Tile,List<Tile>> adjacencyList;
    private CanvasWindow canvas;
    private PacMan pacMan;
    private int pacManSize;
    private int pelletsEaten = 0;
    private int totalPellets = 0;
    private static final int TILE_SIZE = 80;
    private Tile[][] tileMatrix;
    public static final int NUM_COLS = 19; //public for access in GhostManager
    public static final int NUM_ROWS = 10;

    public TileManager(CanvasWindow canvas, PacMan pacMan){
        this.canvas = canvas;
        this.pacMan = pacMan;
        adjacencyList = new HashMap<>();
        pacManSize = pacMan.getScale(); 
        createTiles();
    }

    public Tile[][] getTileMatrix(){
        return tileMatrix;
    }

    public HashMap<Tile,List<Tile>> getAdjacencyMap(){
        return adjacencyList;
    }

    public Tile getCurrentTile(GameObject gameObject){
        int column = getColumn(gameObject);
        int row = getRow(gameObject);
        
        return getTile(column, row);
    }

    public Tile getTile(int column, int row){
        
        try {
            return tileMatrix[column][row];
        } catch (IndexOutOfBoundsException e) {
            return null; //so we dont crash when we return a tile out of the matrix's indices. 
        }
    }

    public int getColumn(GameObject gameObject){
        return (int) (gameObject.getXPosition() / TILE_SIZE);
    }

    public int getRow (GameObject gameObject){
        return (int) (gameObject.getYPosition() / TILE_SIZE);
    }

    public List<Tile> getAdjacentTiles(Tile tile){ //returns adjacency list bc we have a sparse graph
        return adjacencyList.get(tile);
    }
    
    public void createTiles(){
        
        tileMatrix = new Tile [NUM_COLS][NUM_ROWS]; //[#columns][#rows]  
        
        Vector2D tilePosVector = new Vector2D(0,0);
        
        Tile tile = new Tile (false, false, tilePosVector, canvas, pacManSize);

        for (int i = 0; i < tileMatrix.length; i++){
            
            for (int j = 0; j < tileMatrix[0].length; j++){

                Vector2D newTilePosVector = new Vector2D(tile.size() * i, tile.size() * j); 

                Tile newTile = new Tile(false, false, newTilePosVector, canvas, pacManSize);

                if (i == tileMatrix.length - 1){ //scaling to cover last bit of y axis
                    newTile.scaleTile(2, 1);
                }
                
                tileMatrix[i][j] = newTile;
                newTile.addToCanvas();
            }
        }

        setTiles(); //TODO: Fix: This is causing lag on restart
        createAdjacencyList();
    }

    public void createAdjacencyList() { //creates HashMap of Tile keys and TileAdjacencyList values
        
        
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                
                Tile currentTile = getTile(j,i);
                List<Tile> listOfAdjacentTiles = new LinkedList<>();
                List<Tile> nearTiles = new LinkedList<>();
                
                nearTiles.add(getTile(j-1, i));
                nearTiles.add(getTile(j+1, i));
                nearTiles.add(getTile(j, i+1));
                nearTiles.add(getTile(j, i-1));
            
                for (Tile tile : nearTiles){
                    if (tile == null || tile.isWall()){
                        continue;
                    }
                    listOfAdjacentTiles.add(tile);
                }
            
                adjacencyList.put(currentTile, listOfAdjacentTiles);
            }
        }
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

    public void handlePellets(GhostManager ghostManager) { 

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
}
