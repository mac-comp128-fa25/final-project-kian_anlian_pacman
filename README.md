# Pac-Man: Java
## Real-Time Pathfinding in a Dynamic Graph

Developed by Kian Naeimi and AnLian Krishnamurthy <br>
COMP 128 <br>
December 2025<br>

## 1.   Project Description and Expected Functionality

In most modern video-games, there is some form of Artificial Intelligence (Not to be confused with an LLM!) at play that enforces the gameplay-loop. Consider popular franchises like Grand Theft Auto, or the Souls-Borne series. They are known for ultra-polished, fully autonomous systems. These include procedural content generation, graphics/physics simulation, and pathfinding behavior. Their implementations of pathfinding in particular inform how efficient enemies are at tracking and engaging with the player. Thus, our project seeks to dive deeper into how these systems work, and what trade-offs game-studios consider when implementing pathfinding behavior. <br>

However, they also have their own in-house game-engines, physics engines, and APIs to leverage. So we need to scale back, and identify a simpler title to limit the scope of the project. We found the original Pac-Man to fit the scope and timeline of our project. It also provided a very interesting programming challenge: How do you implement a graph pathfinding algorithm that can hold up to both its target (controlled by the user!) and its sources constantly traveling between vertices in real-time? Our project’s goal is to answer this question, using only the Kilt-Graphics library and the Java packages we’re familiar with.<br>

Our system allows a user to simply press a start button on the title screen, and be immediately thrust into gameplay. They are presented with a retro game-map full of food pellets and walls, and four ghosts that are already on their way to the user (Pac-Man!) in the center of the screen. The goal of the game is simple: Eat all of the food pellets on the game-map without losing all three of your lives. If the user accomplishes this feat, they’re presented with a win screen and prompted to restart. Similarly if they lose, they also get a prompt to restart. Both restart buttons refresh all game/character state.<br>


## 1.1   Expected Functionality
 
1. The user runs the application through the VSCode interface. <br>
2. The user clicks the start button when prompted. <br>
3. At any time, the user can pause the game with P or Escape and unpause the game with any other key. <br>
4. The user is spawned in the center of the game map, and is able to use either WASD controls or the arrow keys to control Pac-Man. <br>
5. The user is able to instantly change direction when not blocked by a wall. If they are, that direction is saved and executed at the next available intersection. This way, we have a built-in cornering mechanic similar to the original game, which allows the user a flexible response time. The user and ghosts are always centered in their tiles, so tile-relative positioning is also taken off the user’s hands.<br>
6. The Ghosts find the quickest way to Pac-Man every frame, able to switch directions depending on player positioning and distance. <br>
7. A collision with a Ghost means a lost life and being respawned back in the center of the game map. The Ghosts are also respawned at their four respective original positions, then begin searching for Pac-Man again. Current life count is represented by static Pac-Man objects on the top right of the UI, which remove themselves from the canvas on each death. <br>
8. The user’s goal is to eat every single pellet on the map, which they can estimate progress from based on the real-time score in the top portion of the UI. <br>
9. If the user manages to clear all of the pellets, they are presented with a win-screen and restart button which resets all game-character/behavior state. Similarly, if they lose all three of their lives they are prompted with the restart button. <br>
10. In the top left portion of the UI, there is a real-time frames-per-second (FPS) count, so the user can observe performance and stability of the game. <br>


## 1.2   Implementation Details

Our code to create and manage the game-loop operates as follows:<br>
	
1. We create all of the character (Pac-Man and the Ghosts) objects and assign them Movement objects that they delegate movement requests to. <br>

    (a) Pac-Man and all of the Ghost’s are just Arc objects from the Kilt-Graphics library. To create Pac-Man’s signature appearance, we simply start at a 60 degree angle and sweep all the way to our stopping point, 240 degrees. The space left between the start and stopping points of the Arc creates Pac-Man’s mouth, like a pizza slice (probably multiple!) taken out of a pizza. <br>
    Similarly, Ghost objects are also each an Arc but are simply drawn from 0 to 180 degrees. We decrease their width to create the iconic Ghost silhouette. Each Ghost is given a different color which allows the user to differentiate between the levels of aggression (each Ghost just travels at a different speed), and make decisions on what Pellets are safe to head towards. <br>

    (b) Each character also has an invisible HitCircle that uses simple radius comparisons to determine if a given Ghost has collided with Pac-Man. These HitCircles are baked into the base Movement class for easier access and better abstraction of movement from characters. <br>

    (c) In addition to a Movement object, each character also has a position vector of type Vector2D. Our Vector2D class contains methods that compute simple vector operations for even further abstraction of movement. The Movement interface requires a Movement implementation to allow movement requests (queues – but not the actual data structure!), to be able to call the move() convenience method, and center themselves. This is how we enforce wall-collisions and smooth cornering. The StandardMovement implementation takes in movement requests the same way from both Ghosts and Pac-Man, and enforces legal moves by queueing illegal moves for execution when legal but allowing concurrently legal moves instantly. It creates a velocity vector that is added to each character’s (and their HitCircle) position vector every frame while traveling. The RotationMovement object is a subclass of StandardMovement because it shares all of the previous behavior with the addition of rotating Pac-Man’s underlying GraphicsObject orientation. Each Ghost uses StandardMovement while Pac-Man uses his special RotationMovement implementation. <br>

2. We create objects that handle game state independently. <br>
	
    (a) The UI class handles the majority of text and any other independent graphical objects (i.e Pac-Man’s life system and their physical representation on the canvas). Because the main class PacManGame has public static methods that set the current GameState enum value (Running, Paused, etc), we’re able to call them directly when necessary. <br>

    (b) The KeyHandler class operates as you’d expect. We update the values of several keyPressed booleans (i.e upPressed, downPressed, etc) to enable smooth direction changes. Pac-Man requests Movement from RotationMovement the same way Ghosts make requests, the only difference being requests are determined by key presses and not maze logic. We allow the user to pause and unpause the game by once again making use of PacManGame’s GameState enum. <br>

3. Next, we create the objects that are core to the game-loop. <br>

    (a) The FoodPellet object is just a simple Ellipse. It takes in a Tile reference and position vector so that it knows where in the game-map to go and center itself. <br>

    (b) The Tile object is where the fun begins. Each Tile is passed boolean values that determine what type will be spawned (isDefault, hasPellet, isWall). It also has a previous field which will be gone over in a later section. We also scale each Tile to Pac-Man using a scalar constant, so he takes up the appropriate amount of space in every Tile. <br>

    (c) We have two Manager objects that concern themselves with distinct behavior. The TileManager handles both the visual and data-structure representation of the game-map. We have methods to get specific Tiles, and methods that create and set all Tile types by reading the DefaultMap text file. The text file can be changed if the user would like a different game-map representation. Centrally, the TileManager creates the adjacency list we use for ghost pathfinding behavior. 

    (d) The GhostManager utilizes this adjacency list to enable Ghosts to choose their next tile based on the current shortest path to Pac-Man. If this nextTile is above the Ghost, they request their Movement object to move them up. Other directions operate similarly. Once a Ghost has found the shortest path to Pac-Man, they follow each Tile’s previous pointer from the end recursively all the way back to the Ghost. Once they have a reference on the first tile of the shortest path, they are able to make their request, because the first tile on the path is adjacent to the Ghost! The GhostManager class also handles assigning Movement, collision state with Pac-Man, and everything else to get them on the screen and operating as expected. <br>

4. Finally, we link all behavior together into a cohesive gameplay-loop with the main class, PacManGame. This class handles the update loop and GameState, along with instantiating all of the other classes. <br>


## 2.   Data Structure Choice

There were two major representation questions in this project: First, what data structure will allow us to efficiently represent the visual representation of the game-map, meaning quick access to GraphicsObjects for rendering? <br>

Take into account these three concepts: First, Pac-Man’s game-map is inherently a 2D grid, as are most platformers/arcade games. Second, adjacent tile relationships are actually derived implicitly from their positions in the game-map, so this data structure will not need to concern itself with adjacency. And finally, it’s imperative we have the most efficient lookups for rendering and collision logic. Naturally, we chose the 2D Array to handle the visual representation of our game-map because it hits all of those points. <br>

We’d like to focus this section on our second question: What data structure will most efficiently represent a graph such that the sources and target are able to follow edges to any vertex in the graph at any time? <br>

Define an edge as the walk between the center of one legal (non-wall) Tile and the center of another legal Tile. Consider two Tiles adjacent if this walk exists. Then, let n = the total amount of legal tiles. A Tile has at most 4 neighboring Tiles, so to determine the total amount of unique edges we simply multiply the n tiles by 4. So we have 4n unique Tiles… but wait! If we consider all 4 adjacent tiles unique edges, then it follows that the edge between two adjacent Tiles is being counted twice. So really, we have at most 2n unique edges in the graph so O(n) total edges. So the data structure we choose to represent the graph needs to leverage O(n) space complexity and support efficient iteration over adjacent tiles. We knew from the beginning of the project we would be using some variation of a Breadth-First-Search (BFS) algorithm to get Ghosts to Pac-Man, because BFS guarantees us the shortest path. Taking this into account,  it’s imperative that the chosen data structure supports efficient iteration, because each Ghost will be running the search 60 times (frames) per second. We have two choices, then. <br>


## 2.1 Approach 1: Adjacency List

One option is an adjacency list. Because the majority of Tiles will not have four neighbors, we have a sparse graph. But what about how we store this adjacency list? Well, each Tile would need one, and we’d need to be able to grab the list as quickly as possible. Additionally, because the size of each list is variable and we iterate sequentially, it makes sense to use a LinkedList as the underlying adjacency list for each Tile, allocating memory only when necessary. However, it’s important to note that an ArrayList would perform better if we had to directly access values in the List implementation at O(1) random access versus O(n) indexing with the LinkedList. Even so, we’ll never use random access anyway since we just grab the entire List from some data structure mapping Tiles to their neighbors, and iterate through the elements of the List during the BFS to determine which neighbors of the current tile have already been visited. The ones that haven’t been visited have their previous pointer set to the current tile, and are added to the tile queue. For this mapping relationship, the easiest data structure to use is the HashMap because of its average time complexity being O(1) for insertions and lookups. So, our overarching adjacency list representation would be a HashMap where each key is a Tile and its value is the Tiles LinkedList of neighbors. All we’re ever doing with the map is inserting lists and performing a lookup for a Tile’s list of neighbors, again both O(1) operations with the HashMap. 

But how would we actually implement this specific implementation? Well, first we would create the Tile grid using the aforementioned 2D array, adding all the Tiles to the canvas after creating them and assigning them to the array. Then, we would call some method that reads in the map text file (DefaultMap in res, which is a matrix of numbers 0-2, where 0 is a wall, 1 is a Tile with a FoodPellet, and 2 is a defaultTile), and simply set the states of each Tile in the 2D array to its corresponding Tile type, updating the GraphicsObjects after. Finally, we would create the actual adjacency list data structure, where we would simply loop over the array and add each legal adjacent tile to the Tile’s personal adjacency list (the LinkedList), and then add the Tile to adjacency list mapping to our overall HashMap adjacency list representation. All of those operations are O(M x N), where even though we iterate through each list of neighbors to determine legal adjacent tiles, we are bounded by a constant (2, remember?) so those iterations operations are O(1). 

We’ve now created our adjacency list! But what will our ghosts’s BFS powered pathfinding algorithm look like with this data structure? The first thing we’ll do is create a tile queue, represented as an ArrayDeque that uses the Queue ADT. Then we’ll add the given Ghost’s current tile (we get a reference using the TileManager!) to the queue, representing our starting tile. While the queue isn’t empty, we’ll keep polling adjacent tiles and their adjacent tiles, and setting them as explored if they haven’t already been added to the queue or have already been explored. Once we finally reach Pac-Man’s current tile, we follow the previous pointers all the way back to the Ghost to determine which of his adjacent tiles he should request his Movement object to take him to next. To accomplish this, we’ll need a recursive helper method that is only called once the BFS has been completed. The base case would be if the Tile is the Ghost’s, in which case we would stop because we’d know which adjacent tile to move to at that point. We use a stack to reverse the order of Tiles and pop the most recent Tile (the optimal adjacent one!), and return this Tile. This method and its recursive helper would be called for each Ghost, and the Ghost would request the corresponding direction to get to the optimal Tile. This is the entire algorithm! And it would be called every frame if we choose the Adjacency List as our representation of the graph.

## 2.2	Approach 2: Adjacency Matrix 

The other option to represent our graph is an adjacency matrix. The overall pathfinding algorithm would be similar, except instead of a HashMap of Tile to LinkedList mappings, we’d just have one massive boolean matrix to represent adjacent pairs. The vertex i would be adjacent to vertex j if the tile matrix at position [i][j] returns a 1, true. The representation itself doesn’t sound bad by itself outside of the context of this project, if you ignore the fact that we have to assign arbitrary integer values to each Tile to create a matrix representation mapping Tile i to Tile j. But considering each Tile has a degree of at most 4, we can observe a glaring issue: We have a sparse graph, such that most Tiles don’t have the max amount of edges… which means that we would be using a data structure that has O(n^2) space complexity to allocate very few positions in the matrix. So that’s one reason we might not use this data structure, but what else? Well, what about iteration over the matrix itself to find what Tiles are adjacent to some Tile at position [i][j]? The issue here is that the placement of Tile [i][j] in the matrix means nothing relative to the position of its adjacent Tiles. With the adjacency list representation above, we were able to use the 2D array of Tile objects to our advantage and assign a list of neighbors to each Tile by traversing the array. But here, we’d iterate over all j columns in row i, so a lookup to determine if two tiles are adjacent is O(n)... compared to our HashMap representation of an adjacency list where we have O(1) lookups to get a list of neighbors. Not looking good. Ok, well let’s observe any differences between the BFS complexities. Remember how finding neighbors for one Tile in this data structure is O(n)? That’s fine if adjacent Tiles are in that row of the matrix, but what about if they’re in the m’th row? That means that our BFS operation can be worst-case O(n^2). We observed earlier that the integers i and j are assigned arbitrarily to Tiles with no relation to their physical position relative to the other Tiles… meaning each Ghost would be performing O(n^2) Breadth-First-Searches every frame to find adjacent tiles, in comparison to the O(n) BFS implementation with the adjacency list representation where every BFS is an O(n) operation. 

## 2.3	Final Choice

Based on these tradeoffs, we chose to use the adjacency list to implement our ghost BFS pathfinding algorithm. We also took into account how much more difficult it would be to build the adjacency matrix compared to the more intuitive mapping of Tiles to adjacency lists. Implementing the search algorithm took up the bulk of the project work. 


## 3	Bug Reports & Incomplete Features 

Currently, we have no game-breaking bugs. But, all four ghosts following the shortest path algorithm can lead to gameplay
feeling somewhat unfair. Due to the scope of the project we have no plans to fix this, as it is less of a bug and more just a product of the aforementioned project scope. <br>

In terms of incomplete features, most of them are only enhancements to the core gameplay loop, including: actual textures for the characters (i.e ghosts getting custom character sprites), animating Pac-Man’s mouth and the ghosts eyes, powerups, and ghost-specific behavior (i.e Pinky aggresses constantly while Blinky patrols specific areas, etc). 

## 4	Sources Used

RyiSnow on YouTube for KeyHandler Class Inspiration <br>
Vector2D Class on Unity Forums for Movement Abstraction <br>
Pac-Man Dossier for Game Rules and General Structure <br>
COMP 127 HW4 (Breakout) for Bounding Box Collision <br>
MazeActivity for Matrix File Reading and Ghost Search Algorithm Logic <br>
Preceptor Lucy for Recursive Backtracking, Movement Abstraction, and Integer Rounding for Player/Ghost Tile Position Estimation. <br>
Professor Arehalli for BFS Algorithm and Game-Map debugging. <br>
