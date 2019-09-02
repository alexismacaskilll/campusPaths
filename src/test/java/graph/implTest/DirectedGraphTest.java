package graph.implTest;
import static org.junit.Assert.*;

import graph.DirectedGraph;
import org.junit.Before;
import org.junit.Test;
import graph.*;

import java.util.Set;
import java.util.HashSet;


/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * DirectedGraph class.
 * <p>
 */

public class DirectedGraphTest {

    private DirectedGraph<String, String> graph;
    private Set<String> nodes;
    private Set<DirectedLabeledEdge<String, String>> edges;

    @Before
    public void setUp() {
        graph = new DirectedGraph<>();
        nodes = new HashSet<>();
        edges = new HashSet<>();

    }


    private final String n1 = "n1";
    private final String n2 = "n2";
    private final DirectedLabeledEdge<String, String> e1 = new DirectedLabeledEdge<>(n1, n2, "label1");


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  constructor
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test //no argument constructor constructs empty node set
    public void testNoArgConstructor() { assertTrue(graph.listNodes().isEmpty());
    }

    @Test //getNodes of constructor gets an empty node set.
    public void testGetNodesConstructor() { assertEquals(graph.listNodes(), nodes);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  zero nodes, zero edges
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test //size of the node set when there is 0 nodes in it.
    public void testZeroNodesSize() {
        assertEquals(graph.listNodes().size(), 0);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  null nodes and edges
    ///////////////////////////////////////////////////////////////////////////////////////

    //adding a null node to graph throws an IllegalArgumentException.
    @Test(expected=IllegalArgumentException.class)
    public void testAddNullNode() {
        graph.addNode(null);
    }

    //adding a null edge to graph throws an IllegalArgumentException.
    @Test(expected=IllegalArgumentException.class)
    public void testAddNullEdge() {
        graph.addEdge(null);
    }

    //removing a null node from graph throws an IllegalArgumentException.
    @Test(expected=IllegalArgumentException.class)
    public void testRemoveNullNode() {
        graph.removeNode(null);
    }

    //removing a null edge from graph throws an IllegalArgumentException.
    @Test(expected=IllegalArgumentException.class)
    public void testRemoveNullEdge() {
        graph.removeEdge(null);
    }

    //listing children of a null node throws an IllegalArgumentException.
    @Test(expected=IllegalArgumentException.class)
    public void testListChildrenNullParent() {
        graph.listChildren(null);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  one node, no edges
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test //containsNode method returns true when looking for valid node in graph
    public void testContainsOneNode() {
        graph.addNode(n1);
        assertTrue(graph.containsNode(n1));
    }

    @Test //addNode method returns true when adding valid node to empty graph
    public void testAddOneNode() {
        assertTrue(graph.addNode(n1));
    }

    @Test //number of nodes is 1 after adding one node.
    public void testSizeAfterOneAdd() {
        graph.addNode(n1);
        assertEquals(graph.listNodes().size(), 1);
    }

    @Test //adding and removing same node to empty graph leaves graph empty
    public void testOneNodeOneRemoveSize() {
        graph.addNode(n1);
        graph.removeNode(n1);
        assertEquals(graph.listNodes().size(), 0);
    }

    @Test //remove node returns true if node was removed.
    public void testOneNodeOneRemove() {
        graph.addNode(n1);
        assertTrue(graph.removeNode(n1));
    }


    @Test (expected=IllegalArgumentException.class)
    //remove node throws illegal argument exception if node trying to remove doesn't exist
    public void testRemoveNodeThatDoesntExist() {
        graph.addNode(n1);
        graph.removeNode(n2);
    }

    @Test //list nodes on one node graph returns node set with the one node in it
    public void testListNodesOneNode() {
        graph.addNode(n1);
        nodes.add(n1);
        assertEquals(graph.listNodes().size(), 1);
    }

    @Test //there is no children of one node with no outbound edges
    public void testListChildrenOneNode() {
        graph.addNode(n1);
        assertEquals(graph.listChildren(n1), nodes);
    }


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  one node, one edge
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test //containsEdge method returns true when looking for an edge that is in the graph
    public void testContainsOneEdge() {
        graph.addNode(n1);
        DirectedLabeledEdge<String, String> self = new DirectedLabeledEdge<>(n1, n1, "label");
        assertTrue(graph.addEdge(self));
        assertTrue(graph.containsEdge(self));
    }

    @Test //addEdge method returns true when adding valid edge to one node graph
    public void testOneNodeOneEdgeAddEdge() {
        graph.addNode(n1);
        DirectedLabeledEdge<String, String> self = new DirectedLabeledEdge<>(n1, n1, "label");
        assertTrue(graph.addEdge(self));
    }


    @Test //remove edge returns true if the node was removed.
    public void testOneNodeRemoveEdgeReturn() {
        graph.addNode(n1);
        DirectedLabeledEdge<String, String> selfEdge = new DirectedLabeledEdge<>(n1, n1, "label");
        graph.addEdge(selfEdge);
        assertTrue(graph.removeEdge(selfEdge));
    }

    @Test //in a graph with one node and one self edge, outbound edges of that node is
    // the self edge
    public void testOneNodeOneEdgeListChildren() {
        graph.addNode(n1);
        DirectedLabeledEdge<String, String> selfEdge = new DirectedLabeledEdge<>(n1, n1, "label");
        edges.add(selfEdge);
        graph.addEdge(selfEdge);
        assertEquals(graph.listChildren(n1), edges);
    }

    @Test (expected=IllegalArgumentException.class)
    //test adding a non self edge in graph with one node.
    // edges of that node is the two self edges
    public void testOneNodeAddEdgeWithoutDestNode() {
        graph.addNode(n1);
        DirectedLabeledEdge<String, String> edge = new DirectedLabeledEdge<>(n1, n2, "label1");
        graph.addEdge(edge);
    }

    @Test (expected=IllegalArgumentException.class)
    //test adding a non self edge in graph with one node.
    // edges of that node is the two self edges
    public void testOneNodeAddEdgeWithoutSrcNode() {
        graph.addNode(n2);
        DirectedLabeledEdge<String, String> edge = new DirectedLabeledEdge<>(n1, n2, "label1");
        graph.addEdge(edge);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  one node, two edges
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test//addEdge method returns true when adding two valid self edge to two node graph
    public void testOneNodeTwoAddEdge() {
        graph.addNode(n1);
        DirectedLabeledEdge<String, String> self1 = new DirectedLabeledEdge<>(n1, n1, "label1");
        DirectedLabeledEdge<String, String> self2 = new DirectedLabeledEdge<>(n1, n1, "label2");
        assertTrue(graph.addEdge(self1) && graph.addEdge(self2));
    }

    @Test //list children returns correct set when one of two self edges are removed.
    public void testOneNodeTwoEdgesListChildren() {
        graph.addNode(n1);
        DirectedLabeledEdge<String, String> self1 = new DirectedLabeledEdge<>(n1, n1, "label1");
        DirectedLabeledEdge<String, String> self2 = new DirectedLabeledEdge<>(n1, n1, "label2");
        graph.addEdge(self1);
        graph.addEdge(self2);
        edges.add(self1);
        graph.removeEdge(self2);
        assertEquals(graph.listChildren(n1), edges);
    }

    @Test //in a graph with one node and two different self edges, the only outbound
    // edges of that node is the two self edges
    public void testOneNodeTwoEdgeGetChildren() {
        graph.addNode(n1);
        DirectedLabeledEdge<String, String> self1 = new DirectedLabeledEdge<>(n1, n1, "label1");
        DirectedLabeledEdge<String, String> self2 = new DirectedLabeledEdge<>(n1, n1, "label2");
        graph.addEdge(self1);
        graph.addEdge(self2);
        edges.add(self1);
        edges.add(self2);
        assertEquals(graph.listChildren(n1), edges);
    }



    ///////////////////////////////////////////////////////////////////////////////////////
    ////  two nodes, no edges
    ///////////////////////////////////////////////////////////////////////////////////////


    @Test //addNode method returns true when adding two different valid nodes to graph
    public void testAddTwoNodes() {
        assertTrue(graph.addNode(n1) && graph.addNode(n2));
    }


    @Test //addNode method returns false when adding a duplicate node to graph
    public void testAddOneNodeTwice() {
        graph.addNode(n1);
        assertFalse(graph.addNode(n1));
    }


    @Test //number of nodes is 1 after adding one node twice.
    public void testSizeAfterTwoAdd() {
        graph.addNode(n1);
        graph.addNode(n2);
        assertEquals(graph.listNodes().size(), 2);
    }


    @Test //adding two different nodes and removing one node to empty graph leaves the graph
    // with one node
    public void TwoNodeOneRemoveSize() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.removeNode(n1);
        assertEquals(graph.listNodes().size(), 1);
    }

    @Test //remove node returns the node that was removed for two node graphs
    public void testTwoNodeOneRemove() {
        graph.addNode(n1);
        graph.addNode(n2);
        assertTrue(graph.removeNode(n1));
    }

    @Test //list nodes on two node graphs returns node set with the two nodes in it
    public void testListNodesTwoNodes() {
        graph.addNode(n1);
        graph.addNode(n2);
        nodes.add(n1);
        nodes.add(n2);
        assertEquals(nodes, graph.listNodes());
    }

    @Test //there is no children of of either node in a two node graph that has no
    // with no edges
    public void testListChildrenTwoNodes() {
        graph.addNode(n1);
        graph.addNode(n2);
        assertTrue(graph.listChildren(n1).isEmpty() && graph.listChildren(n2).isEmpty() );
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  two nodes, one edge
    ///////////////////////////////////////////////////////////////////////////////////////


    @Test //addEdge method returns true when adding valid edge to two node graph.
    public void testTwoNodesOneEdgeAddEdge() {
        graph.addNode(n1);
        graph.addNode(n2);
        assertTrue(graph.addEdge(e1));
    }

    @Test //remove edge method on a valid two node one edge returns true
    public void testTwoNodeOneEdgeRemovedEdge() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(e1);
        assertTrue( graph.removeEdge(e1));
    }

    @Test (expected=IllegalArgumentException.class)
    //remove node throws illegal argument exception if edge trying to remove doesn't exist
    public void testRemoveEdgeThatDoesntExist() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(e1);
        DirectedLabeledEdge<String, String> e2 = new DirectedLabeledEdge<>(n2, n1, "label2");
        graph.removeEdge(e2);
    }

    @Test //in a graph with a non self edge, there is no children of the child node
    public void testListChildrenOfEdgeChildNode() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(e1);
        assertTrue(graph.listChildren(n2).isEmpty());
    }

    @Test //in a graph with a non self edge, the child of the parent node is the
    //child node.
    public void testGetChildrenOfEdgeParentNode() {
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addEdge(e1);
        edges.add(e1);
        assertEquals(graph.listChildren(n1), edges);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  two nodes, two edges
    ///////////////////////////////////////////////////////////////////////////////////////


    @Test //addEdge method returns true when adding edge with different parent, child, and
    //label to two node one edge graph.
    public void testAddTwoNodesTwoDifferentEdges() {
        graph.addNode(n1);
        graph.addNode(n2);
        DirectedLabeledEdge<String, String> edge1 = new DirectedLabeledEdge<>(n1, n2, "label1");
        DirectedLabeledEdge<String, String> edge2 = new DirectedLabeledEdge<>(n2, n1, "label2");
        graph.addEdge(edge1);
        assertTrue(graph.addEdge(edge2));
    }


    @Test //addEdge method returns true when adding edge with same parent and child
    //node but different label
    public void testAddTwoNodesTwoEdgesDifferentLabel() {
        graph.addNode(n1);
        graph.addNode(n2);
        DirectedLabeledEdge<String, String> edge1 = new DirectedLabeledEdge<>(n1, n2, "label1");
        DirectedLabeledEdge<String, String> edge2 = new DirectedLabeledEdge<>(n1, n2, "label2");
        graph.addEdge(edge1);
        assertTrue(graph.addEdge(edge2));
    }

    @Test //addEdge method returns true when adding edge with different parent and child
    //nodes but same labels
    public void testAddTwoNodesTwoDifferentEdgesSameLabel() {
        graph.addNode(n1);
        graph.addNode(n2);
        DirectedLabeledEdge<String, String> edge1 = new DirectedLabeledEdge<>(n1, n2, "label1");
        DirectedLabeledEdge<String, String> edge2 = new DirectedLabeledEdge<>(n2, n1, "label1");
        graph.addEdge(edge1);
        assertTrue(graph.addEdge(edge2));
    }


    @Test //addEdge method returns false when adding a duplicate edge to graph
    public void testAddTwoNodesOneEdgeTwice() {
        graph.addNode(n1);
        graph.addNode(n2);
        DirectedLabeledEdge<String, String> edge1 = new DirectedLabeledEdge<>(n1, n2, "label1");
        DirectedLabeledEdge<String, String> edge2 = new DirectedLabeledEdge<>(n1, n2, "label1");
        graph.addEdge(edge1);
        assertFalse(graph.addEdge(edge2));
    }


    @Test //test list children after trying to add duplicate edge.
    public void testChildrenTotalAfterAddEdgeTwice() {
        graph.addNode(n1);
        graph.addNode(n2);
        DirectedLabeledEdge<String, String> edge1 = new DirectedLabeledEdge<>(n1, n2, "label1");
        DirectedLabeledEdge<String, String> edge2 = new DirectedLabeledEdge<>(n1, n2, "label1");
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        assertEquals(1, graph.listChildren(n1).size());
    }

@Test //remove edge method on a two node two edge graph returns the edge that was
    // removed
    public void testTwoNodeTwoEdgeRemovedEdge() {
        graph.addNode(n1);
        graph.addNode(n2);
        DirectedLabeledEdge<String, String> edge1 = new DirectedLabeledEdge<>(n1, n2, "label1");
        DirectedLabeledEdge<String, String> edge2 = new DirectedLabeledEdge<>(n2, n1, "label2");
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        assertTrue(graph.removeEdge(edge1));
    }


    @Test //both nodes have no children after removing both edges from a two node
    // two edge graph
    public void testTwoNodeTwoEdgeRemovedBothEdges() {
        graph.addNode(n1);
        graph.addNode(n2);
        DirectedLabeledEdge<String, String> edge1 = new DirectedLabeledEdge<>(n1, n2, "label1");
        DirectedLabeledEdge<String, String> edge2 = new DirectedLabeledEdge<>(n2, n1, "label2");
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.removeEdge(edge1);
        assertTrue(graph.removeEdge(edge2));
    }
}

