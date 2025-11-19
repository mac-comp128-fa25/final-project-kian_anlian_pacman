import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;

public class Ghost {
    private Ellipse ghostShape; //Ellipse until we figure out how to draw ghosts

    public Ghost (Vector2D positionVector, CanvasWindow canvas){
        ghostShape = new Ellipse(0,0,10,10); //temp
        /*
         * TODO: Implement DFS w/ integer division. 
        
         * At each tile, the Ghost will look at adjacent tiles, and count
         * how many tiles it would take to get to Pac-Man from that 
         * adjacent tile. The adjacent tile that has the lowest
         * tileCount (returned from the DFS) will be the one that
         * the Ghost will move to, using the same Movement object
         * that PacMan does. So if the adjacent tile is to the
         * left, moveLeft() will be called, if it's to the 
         * right moveRight() will be called and so on.
         */
    }
    

}
