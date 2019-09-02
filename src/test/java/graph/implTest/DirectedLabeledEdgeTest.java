package graph.implTest;

import static org.junit.Assert.*;

import graph.DirectedLabeledEdge;
import org.junit.Before;
import org.junit.Test;


/**
 * This class contains a set of test cases that can be used to test the implementation of the
 * DirectedLabeledEdge class.
 * <p>
 */

public class DirectedLabeledEdgeTest {

    DirectedLabeledEdge<String, String> edge;

    @Before
    public void setUp() {
        edge = new DirectedLabeledEdge<>("src", "dest", "label");

    }
    ///////////////////////////////////////////////////////////////////////////////////////
    ////  constructors
    ///////////////////////////////////////////////////////////////////////////////////////

    //Constructing a DirectedLabeledEdge with a null src node throws IllegalArgumentException.
    @Test(expected=IllegalArgumentException.class)
    public void testNullSrcConstructor() {
        new DirectedLabeledEdge<>(null, "dest", "label");
    }

    //Constructing a DirectedLabeledEdge with a null dest node throws IllegalArgumentException.
    @Test(expected=IllegalArgumentException.class)
    public void testNullDestConstructor() {
        new DirectedLabeledEdge<>("src", null, "label");
    }

    //Constructing a DirectedLabeledEdge with a null label throws IllegalArgumentException.
    @Test(expected=IllegalArgumentException.class)
    public void testNullLabelConstructor() {
        new DirectedLabeledEdge<>("src", "dest", null);
    }


    @Test //Constructing a DirectedLabeledEdge with valid String arguments for src, dest, and label
    public void testEdgeConstructor() {
        DirectedLabeledEdge<String, String> e = new DirectedLabeledEdge<>("src", "dest", "label");
        assertEquals(e, edge);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getSrc, getDest, getLabel
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test //getSrc method of DirectedLabeledEdge
    public void testGetSrc() {
        assertEquals("src", edge.getSrc());
    }

    @Test //getDest method of DirectedLabeledEdge
    public void testGetDest() {
        assertEquals("dest", edge.getDest());
    }

    @Test //getLabel method of DirectedLabeledEdge
    public void testGetLabel() {
        assertEquals("label", edge.getLabel());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  equals
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test //equal with two identical edges
    public void testEqualsSameEdge() {
        DirectedLabeledEdge<String, String> edge2 =  new DirectedLabeledEdge<>("src", "dest", "label");
        assertEquals(edge, edge2);
    }

    @Test //equal with two edges with same src and dest nodes, but different label
    public void testEqualsDifferentLabel() {
        DirectedLabeledEdge<String, String> edge2 =  new DirectedLabeledEdge<>("src", "dest", "label1");
        assertNotEquals(edge, edge2);
    }

    @Test //equal with two edges with different src and dest nodes, but same label
    public void testEqualsDifferentNodesSameLabel() {
        DirectedLabeledEdge<String, String> edge2 =  new DirectedLabeledEdge<>("dest", "src", "label");
        assertNotEquals(edge, edge2);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  hashCode
    ///////////////////////////////////////////////////////////////////////////////////////

    // two identical edges return the same hashCode.
    @Test
    public void testHashCodeSameEdge() {
        DirectedLabeledEdge<String, String> edge2 =  new DirectedLabeledEdge<>("src", "dest", "label");
        assertEquals(edge.hashCode(), edge2.hashCode());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    //// toString
    ///////////////////////////////////////////////////////////////////////////////////////
    // toString returns correct String
    @Test
    public void testToString() {
        assertEquals(edge.toString(), "dest(label)");
    }



}
