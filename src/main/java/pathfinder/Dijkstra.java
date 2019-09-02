package pathfinder;
import graph.DirectedGraph;
import graph.DirectedLabeledEdge;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusPath;

import java.util.*;

public class Dijkstra {


    /**
     * <b>AllCampusPaths</b> is a representation of a function to find the shortest path between
     * two nodes in a graph.
     *
     */

    // No Abstraction Function or RepInvariant listed because Dijkstra is not
    // an Abstract Data Type.

    // Abstract description: Dijkstra is a function to find the minimum cost path between
    // two nodes in a graph.



    /** method to load the campus building graph. It loads all the points in the campus paths tsv file and creates
     //new campus buildings for them if they weren't in the campus building tsv file. It then adds all these campus
     //buildings as the nodes, and then it adds an edge for each path with labels that are equal to the path cost.*/
    public static DirectedGraph<Point, Double> loadGraph(List<CampusPath> paths) {

        DirectedGraph<Point, Double> graph = new DirectedGraph<>();
        for (CampusPath path: paths) {
            Point startPoint = new Point(path.getX1(), path.getY1());
            Point destPoint = new Point(path.getX2(), path.getY2());



            graph.addNode(startPoint);


            graph.addNode(destPoint);

            if ((!graph.containsNode(startPoint)) || (!graph.containsNode(destPoint))) {
                throw new IllegalArgumentException("Both buildings need to exist in the graph.");
            } else {

                graph.addEdge(new DirectedLabeledEdge<>(startPoint, destPoint, path.getDistance()));
                graph.addEdge(new DirectedLabeledEdge<>(destPoint, startPoint, path.getDistance()));

            }
        }

            return graph;
        }


    /**
     * Finds the minimum cost path between two nodes in a graph.
     *
     * @spec.requires graph contains start and dest nodes.
     * @throws IllegalArgumentException if graph = null, start = null, dest = null.
     * @param graph the graph that is used to find the shortest distance between two nodes.
     * @param start a node in the graph
     * @param dest a node in the graph
     * @return a List of DirectedLabeledEdges that represents the minimum cost path between start and
     * dest nodes in the graph.
     */
    public static <E> Path<E> findPath(DirectedGraph<E, Double> graph, E start, E dest) {
        if (graph == null || start == null || dest == null) {
            throw new IllegalArgumentException("Graph, start, and dest cannot be null.");
        }
        //if either src or dest node are not in the graph, there can be no path
        if (!(graph.containsNode(start) && graph.containsNode(dest))) {
            throw new IllegalArgumentException("Graph must contain start and dest nodes of path");
        }
        Queue<Path<E>> active = new PriorityQueue<Path<E>>( (first, second)-> (int) (first.getCost() - second.getCost()));
        //finished = set of nodes for which we know the minimum-cost path from start.
        Set<E> finished = new HashSet<>();
        // Initially we only know of the path from start to itself, so we add that path to queue.
        active.add(new Path<E>(start));
        while (!active.isEmpty()) {
            // minPath is the lowest cost path in active
            Path<E> minPath = active.remove();
            // minDest is the destination node in minPath
            E minDest = minPath.getEnd();
            if (minDest.equals(dest)) {
                return minPath;
            }


                for (DirectedLabeledEdge<E, Double> edge : graph.listChildren(minDest)) {
                    E current = edge.getDest();

                    // If we don't have min cost path from edge's dest node
                    if (!finished.contains(current)) {
                        Path<E> thePath = minPath.extend(current, edge.getLabel());

                        active.add(thePath);
                    }
                }
                // add minDest to known set
                finished.add(minDest);
            }
       // }
        // no path exists from start to dest
        return null;
    }
}
