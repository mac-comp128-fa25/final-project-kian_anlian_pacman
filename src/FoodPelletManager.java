    /*
     * TODO: Create a HashMap where each key is some tile at (x,y) and the value 
     * is the FoodPellet. If PacMan traverses one of the keys, we'll call get(tile)
     * to get a foodPellet GraphicsObject and remove it from the screen. 
     */
public class FoodPelletManager implements Manager{

    /*
     * Spawn pellets at hardcoded locations in the game map
     */
    @Override
    public void spawnCollection() { 
    }

    /*
     * If Pac-Man is on a Tile key with a FoodPellet 
     * value in the map HashMap<Tile, FoodPellet>,
     * remove from the screen and the collection/list.
     * We can call handleCollisions inside this method.
     */
    @Override
    public void manageCollection() { 
    }

    /*
     * Where the algorithm that detects Pac-Man & FoodPellet collisions will
     * be. Could mark that Tile key somehow so manageCollections will know
     * where to go in the HashMap.
     */
    @Override
    public void handleCollisions() {
    }
}
