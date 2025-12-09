# Pac-Man: Java

Developed by Kian Naeimi and AnLian Krishnamurthy 

## Project Description and Expected Functionality
Our goal is to recreate the original Pac-Man and its core features using only the libraries/frameworks we’re already familiar with from this course. 			       

We had a lot to juggle: 

-Pac-Man and the Ghosts moving as expected <br>
-Wall tile collisions working as expected (stopping the player) <br>
-Pac-Man - Ghost collisions resulting in a lost life <br>
-Ghosts chasing Pac-Man <br>
-Visual life indicators <br>
-Real-time score <br>
-And a game-map that could be dynamically created through arbitrary arrangements of a matrix text file. <br>

But in the end, our implementation of the game works like we hoped. The player is able to play as many rounds of the game as they want without closing the program (we have a restart button!) and running the code again. 

## 1.1   Expected Functionality
The user runs the code, and is prompted to start the game
The map loads with the walls, pellets, ghosts, and Pac-Man
The user can only use the arrow keys to move, and once he moves, the ghosts begin their movement patterns
As the user moves Pac-Man across tiles that aren’t walls, every pellet he collects by going over them increases his score. Once the pellets are connected, they disappear, and if Pac-Man eats all of the pellets, he wins the game. While moving, if Pac-Man collides with a wall, he stops moving until the user switches their direction
If Pac-Man at any point collides with the ghosts, a life is lost and Pac-Man is reset to the center, while the ghosts are reset to their respective corners. If Pac-Man loses all his lives, he loses the game
In the case of Pac-Man winning or losing, the user is prompted to restart the game, and everything is reset to normal

## 1.2   Implementation Details

To create a program that could support multiple moving (literally) parts which could each interlock with another, we needed to be creative with:

Interfaces: Pac-Man and the Ghosts all had to be able to move, manage collision state, and provide access and helper methods for manipulating their underlying GraphicsObjects. We handled this by separating concerns based on whether the class was a GameObject the user can see, or some form of Movement. By having classes such as PacMan, Ghost, and FoodPellet implement our GameObject interface, we had the freedom to make any GameObject implementation movable via KeyEvents taken in by our KeyHandler. With our Movement interface, we could abstract away what type of concrete movement a GameObject would require (StandardMovement or RotationMovement), and simply call move methods wherever we want in the program that delegated to their Movement implementing object. Our Collidable interface (which we’ll extract during final refactoring) allows any object to detect collisions based on its bounding box.

Managers: Our GhostManager and TileManager classes abstract away the complex algorithms from the Tile and Ghost classes, so they just have to worry about their own creation.. The managers also allow clean method calls in the main class that have a clear purpose. 

## Data Structure Choice


## 2.1	Storing Adjacent Tiles


## Our Approach: Adjacency List HashMap

We created an Adjacency List as a Hash Map, where the keys are tiles in the map, and the values are linked lists of adjacent tiles. We essentially assigned a coordinate to each tile in the map by using integer division to estimate which tile the player is in, and ran an algorithm to create the Adjacency List using this coordinate system. This Adjacency List is useful in creating our Ghost movement algorithms in order for them to move towards Pac-Man in the quickest possible way.


## 2.2  Storing the Map


## Our Approach: 2D Array

We decided a 2D Array was the best choice because we believed it would be the most efficient structure for the Ghost’s search computations. Because we have a relatively dense graph      (consider only walkable tiles as vertices) where every position in the matrix was being used, we accepted the tradeoff of the fixed-size requirement. It did lead to us using constants for the number of rows and columns, but as mentioned earlier in the paper, a user can fill the 19 x 10 matrix in our text file (read in during game-map generation) with whatever arrangement of wall/default/food-pellet tiles they please. The best thing about this data structure was that we had O(1) index access via its contiguous memory, which was important for one reason: The ghost algorithm is constantly performing lookups by index in the matrix to find Pac-Man’s and the Ghost’s current positions. 

However, we also needed to perform more costly searches over our M rows and N columns when an object’s position in the matrix was unknown. Examples of this are: 


Our handlePellets() method, which is running an O(M * N) search every frame to determine if Pac-Man has intersected a Food-Pellet
Our setTiles() method, which runs an O(M*N) file reading algorithm to determine the structure of the game-map. This method is also called when restarting the game so tiles return to their original state (i.e. a tile that doesn’t have a pellet anymore gets it’s pellet back)
Our createAdjacencyList() method itself, which filled the adjacencyList HashMap with Tiles as keys and LinkedLists of their respective adjacent tiles. The main loop is again O(M * N), but fortunately because the amount of adjacent tiles is at max 4, our for-each loop which mapped every Tile to its corresponding LinkedList was O(1). 


## 2.3  Ghost Search Algorithm


## Our Naive Approach: 

Originally, we adapted our main search algorithm from the code of one of the in-class maze activities, but we quickly ran into problems. First of all, we didn’t want to give a Ghost a LinkedList of ALL tiles leading from them to Pac-Man, which our original BFS did. 

Because we had already abstracted a Vector2D class for all position/velocity related computations, the most natural way  (maybe not…) to create some sort of heuristic was comparing the distance vector from each adjacent tile to Pac-Man and choosing the tile based of which vector was the smallest in magnitude. This got us closer to returning only the shortest path, but we ran into the issue of one other path still being returned as a result of the vector distance heuristic we were using. A lot of the time, tiles actually had close to if not the exact same distance to Pac-Man, and these computations also felt very slow and unnecessary. 


## Our Final Approach:

Another option that a preceptor (Lucy!) had suggested was getting rid of the distance calculations entirely, and instead returning the shortest path by recursively backtracking from Pac-Man once the first path to reach Pac-Man had been computed. It took a while to figure out how to fit recursion into our current Queue based BFS, but once we decided to add a previous field to each tile and set it during maze creation, we were able to easily follow pointers all the way back to the Ghost. Every frame, each ghost is moving towards the next tile in its path while concurrently running another search. These searches are just O(V + E), where we have |V| vertices (walkable tiles) and |E| edges (between adjacent tiles) in the game-map. Since we’ve already found the shortest path by the time we start backtracking, reconstructing the path takes time proportional to the path's length, which worst case includes every vertex such that backtracking is an O(V) operation. Similarly, the space complexity of our BFS algorithm is O(V).


## 3.  Bugs and Incomplete Features
	

## 3.1 Automatic Player Centering & Queueing Intended Directions
	
Currently, we have no algorithm in place to keep the player in the center of a given path/tile. This means that depending on the response time of the player, gameplay could be unintendedly challenging. One feature that could help is the player being able to queue intended directions before they reach an intersection, but this was another idea scrapped due to time constraints. 


## 3.2 Unstable Framerate On Restart
		
Whenever the player either wins the game by eating every pellet, or loses by being caught by a ghost three times, we allow the player to restart via a Button that pops up. Because all tiles need to be reset to their initial states like mentioned earlier, we read the matrix text file again and call the setTiles() method. Every time a player restarts, we lose ≈ 10 frames (i.e suppose we start at a stable 60 FPS… after 3 resets we’re at a variable 30 FPS). We plan on addressing this bug sometime after we have a functional build but before the 15th. 



## 4. Sources Used

RyiSnow on YouTube for KeyHandler Class Inspiration
Vector2D Class on Unity Forums for Movement Abstraction
Pac-Man Dossier for Game Rules and General Structure
COMP 127 HW4 (Breakout) for Bounding Box Collision 
MazeActivity for Matrix File Reading and Ghost Search Algorithm Logic
Preceptor Lucy for Recursive Backtracking, Movement Abstraction, and Integer Rounding for Player/Ghost Tile Position Estimation.
Professor Arehalli for TileMatrix (the actual game-map) Creation.

