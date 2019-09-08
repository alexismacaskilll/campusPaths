# campusPaths

In this project, I wrote a Generic route-finding tool to find the shortest walking route between any two buildings on the UW campus. I implemented Dijkstra's algorithm and built a GUI for the route-finding tool using React and the Spark Java Framework. 

## Important Files to look at

- [SparkServer](https://github.com/alexismacaskilll/campusPaths/blob/master/src/main/java/campuspaths/SparkServer.java) 
  - The Spark Java server that allows other applications to make requests to query data and computation from my pathfinder    application.
  - This server is designed to respond to exactly the requests that your React application will make. 
- [Generic Graph](https://github.com/alexismacaskilll/campusPaths/blob/master/src/main/java/graph/DirectedGraph.java) is the graph.
  - Implemented a directed labeled graph. 
- [Edges](https://github.com/alexismacaskilll/campusPaths/blob/master/src/main/java/graph/DirectedLabeledEdge.java) 
- [Dijkstras](https://github.com/alexismacaskilll/campusPaths/blob/master/src/main/java/pathfinder/Dijkstra.java) 
  - I implemented Dijkstra's algorithm, which finds a minimum-cost path between two given nodes in a graph with all nonnegative edge weights. 
  - In campusPaths, since all edge weights represent physical distances, all my edge weights are nonnegative and Dijkstra's algorithm will work well.

