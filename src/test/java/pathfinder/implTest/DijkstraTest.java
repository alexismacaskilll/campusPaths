package pathfinder.implTest;

import graph.DirectedGraph;
import graph.DirectedLabeledEdge;
import pathfinder.Dijkstra;
import org.junit.Before;
import org.junit.Test;



/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * DirectedGraph class.
 * <p>
 */

public class DijkstraTest {


    private DirectedGraph<String, Double> graph;

    @Before
    public void setUp() {
        graph = new DirectedGraph<>();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addEdge(new DirectedLabeledEdge<>("A", "C", 10.0));
        graph.addEdge(new DirectedLabeledEdge<>("A", "B", 1.0));
        graph.addEdge(new DirectedLabeledEdge<>("B", "C", 2.0));
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  find Path null
    ///////////////////////////////////////////////////////////////////////////////////////

    //trying to find shortest path of null graph throws an IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstraWithNullGraph() { Dijkstra.findPath(null,
            "A", "B"); }

    //trying to find min cost path in graph from null node to valid node throws an
    // IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstraWithNullStart() { Dijkstra.findPath(graph, null, "B"); }

    //trying to find min cost path in graph from valid node to null node throws an
    // IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstraWithNullEnd() { Dijkstra.findPath(graph,
            "B", null); }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  find Path missing nodes
    ///////////////////////////////////////////////////////////////////////////////////////

    //trying to find min cost path in graph from start to end when start is not in the graph
    // throws an IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstraWithNonExistingStart() { Dijkstra.findPath(graph,
            "E", "B"); }


    //trying to find min cost path in graph from start to end when end is not in the graph
    // throws an IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testDijkstraWithNonExistingEnd() { Dijkstra.findPath(graph,
            "B", "E"); }


}

