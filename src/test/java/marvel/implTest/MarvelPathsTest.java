package marvel.implTest;

import graph.DirectedGraph;
import graph.DirectedLabeledEdge;
import marvel.MarvelPaths;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import org.junit.rules.Timeout;


/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * DirectedGraph class.
 * <p>
 */

public class MarvelPathsTest {

    private DirectedGraph marvel;
    private DirectedGraph smallerMarvel;
    private DirectedGraph<String, String> alphabetical;
    private DirectedGraph<String, String> staff;
    private static final int timeout1 = 10000;
    private Set<String> nodes;

    private List<DirectedLabeledEdge> path;

    @Before
    public void setUp() {
        staff = MarvelPaths.loadGraph(
                "src/test/resources/marvel/data/staffSuperheroes.tsv");
        path = new ArrayList<>();
        nodes = new HashSet<>();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  load Graph null
    ///////////////////////////////////////////////////////////////////////////////////////


    //trying to build a graph from a null file name throws an IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testBuildGraphWithNullInput() { MarvelPaths.loadGraph(null); }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  find Path null
    ///////////////////////////////////////////////////////////////////////////////////////

    //trying to find shortest path of null graph throws an IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullGraph() { MarvelPaths.findPath(null,
            "Ernst-the-Bicycling-Wizard", "Notkin-of-the-Superhuman-Beard"); }

    //trying to find shortest path in graph from null node to valid node throws an
    // IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullStart() { MarvelPaths.findPath(staff, null, "Notkin-of-the-Superhuman-Beard"); }

    //trying to find shortest path in graph from valid node to null node throws an
    // IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNullEnd() { MarvelPaths.findPath(staff,
            "Ernst-the-Bicycling-Wizard", null); }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  find Path missing nodes
    ///////////////////////////////////////////////////////////////////////////////////////

    //trying to find shortest path in graph from start to end when start is not in the graph
    // throws an IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNonExistingStart() { MarvelPaths.findPath(staff,
            "Alexis", "Notkin-of-the-Superhuman-Beard"); }


    //trying to find shortest path in graph from start to end when end is not in the graph
    // throws an IllegalArgumentException.
    @Test(expected = IllegalArgumentException.class)
    public void testBFSWithNonExistingEnd() { MarvelPaths.findPath(staff,
            "Ernst-the-Bicycling-Wizard", "Alexis"); }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  load Graph
    ///////////////////////////////////////////////////////////////////////////////////////

    //tests loadGraph timing out on smaller marvel file.
    @Test (timeout = timeout1)
    public void testLoadGraphSmallerMarvel() {
        MarvelPaths.loadGraph("src/test/resources/marvel/data/smallerMarvel.tsv");
    }


    //tests loadGraph timing out on the marvel file.
    @Test (timeout = timeout1)
    public void testLoadGraphMarvel() {
        MarvelPaths.loadGraph("src/test/resources/marvel/data/marvel.tsv");
    }




    //tests nodes in loadGraph from staff file.
    @Test (timeout = timeout1)
    public void testLoadStaffGraphNodes() {
        DirectedGraph<String, String> staffGraph = MarvelPaths.loadGraph(
                "src/test/resources/marvel/data/staffSuperheroes.tsv");
        nodes.add("Ernst-the-Bicycling-Wizard");
        nodes.add("Notkin-of-the-Superhuman-Beard");
        nodes.add("Perkins-the-Magical-Singing-Instructor");
        nodes.add("Grossman-the-Youngest-of-them-all");
        assertEquals(staffGraph.listNodes(), nodes);
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  find Path
    ///////////////////////////////////////////////////////////////////////////////////////
    //tests nodes in loadGraph from alphabetical file.
    @Test (timeout = timeout1)
    public void testFindPath() {
        DirectedGraph<String, String> graph = MarvelPaths.loadGraph(
                "src/test/resources/marvel/data/alphabetical.tsv");
        path.add(new DirectedLabeledEdge<>("hero1", "hero2", "book1"));
        path.add(new DirectedLabeledEdge<>("hero2", "hero4", "book3"));

        List<DirectedLabeledEdge<String, String>> BFSPath = MarvelPaths.findPath(graph, "hero1", "hero4");
        assertEquals(path, BFSPath);

    }

    //tests nodes in loadGraph from alphabetical file.
    @Test (timeout = timeout1)
    public void testFindPathNew() {
        DirectedGraph<String, String> graph = MarvelPaths.loadGraph(
                "src/test/resources/marvel/data/alphabeticalNew.tsv");
        path.add(new DirectedLabeledEdge<>("hero 1", "hero 2", "book 1"));
        path.add(new DirectedLabeledEdge<>("hero 2", "hero 4", "book 3"));

        List<DirectedLabeledEdge<String, String>> BFSPath = MarvelPaths.findPath(graph, "hero 1", "hero 4");
        assertEquals(path, BFSPath);

    }

    //tests unknown character
    @Test(expected = IllegalArgumentException.class)
    public void testUnknownBoth() {
        DirectedGraph<String, String> graph = MarvelPaths.loadGraph("src/test/resources/marvel/data/marvel.tsv");
        List<DirectedLabeledEdge<String, String>> BFSPath = MarvelPaths.findPath(graph, "alexis 1", "hi");


    }



}

