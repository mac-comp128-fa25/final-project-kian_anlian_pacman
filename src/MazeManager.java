/*
 * Uses 2D array to determine what tiles are 
 * walkable and what tiles are MazeWalls.
 * This Maze will be used to 
 * implement the Ghost's DFS algorithm. 
 * Using the Graph-Maze activity as our template
 * will make this easier.
 */
public class MazeManager implements Manager{

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
}
