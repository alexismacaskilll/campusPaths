package graph;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

/**
 * <b>DirectedGraph</b> is a mutable representation of a graph that is made up
 * from a set of nodes connected by directed edges. A DirectedGraph, G, can be
 * notated by the ordered pair G =(N. E), where N = {n_1, n_2,...,n_i} is a set,
 * a distinct collection, of i nodes and E = {e_1, e_2,...,e_j} is a set, a
 * distinct collection, of j directed edges.
 *
 *
 * @spec.specfield nodes: set. A set of all the nodes in the graph
 * @spec.specfield <br>edges: set. A set of all the directed edges in the graph
 *
 */


public class DirectedGraph<E ,T extends Comparable<T>> {

    private Map<E, Set<DirectedLabeledEdge<E,T>>> graph;
    private final static boolean CHECK_REP_ON = false;

    // Abstraction Function:
    // The String keys of graph are all the nodes in the graph.
    // graph associates each node, key, with a hashSet of its outgoing edges.

    // RepInvariant:
    // graph != null &&
    // each node in graph.keySet() != null && each edge in graph.values() != null &&
    // for each node in graph.keySet(), for each edge in graph.get(node), edge.getSrc().equals(node).

    /** Throws an exception if the representation invariant is violated. */
    private void checkRep() {
        if (CHECK_REP_ON) {
            assert (graph != null);
            for (E node: graph.keySet()) {
                //checks each node != null
                assert (node != null);
                for (DirectedLabeledEdge edge: graph.get(node)) {
                    //Only check each edge != null because checkRep in DirectedLabeledEdge tests
                    // for if any of the elements in edge are null.
                    assert(edge != null);
                }
            }
        }
    }

    /**Creates a new DirectedGraph.
     *@spec.effects constructs a new empty DirectedGraph
     */
    public DirectedGraph() {
        graph = new HashMap<>();
        checkRep();
    }

    /**Returns true if the specified node, n, is present in this.nodes.
     * @param n the node to check if its present in this.nodes
     * @spec.requires n is not null.
     * @return true if this.nodes contains n
     */
    public boolean containsNode(E n) {
        checkRep();
        if (n == null){
            return false;
        } else{
            return graph.containsKey(n);
        }
    }

    /**Returns true if the specified edge, e, is present in this.edges.
     * @param e the edge to check if its present in this.edges
     * @spec.requires e is not null and the src and dest nodes contained in e must exist in
     * this.nodes
     * @return true if this.edges contains e
     */
    public boolean containsEdge(DirectedLabeledEdge<E, T> e) {
        checkRep();
        if (e == null){
            return false;
        } else{
            return graph.get(e.getSrc()).contains(e);
        }
    }

    /**Adds the specified node, n, to this.nodes if it is not already present.
     * @param n the node to be added to this.nodes
     * @spec.requires n is not already present in this.nodes.
     * @throws IllegalArgumentException if n = null
     * @spec.modifies this.
     * @spec.effects adds n to this.nodes
     * @return true if this.nodes did not already contain n
     */
    public boolean addNode(E n) {
        checkRep();
        if (n == null){
            throw new IllegalArgumentException("Cannot add a null node to the graph.");
        } else if (this.containsNode(n)) {
            return false;
        } else{
            graph.put(n, new HashSet<DirectedLabeledEdge<E, T>>());
            checkRep();
            return true;
        }
    }

    /**Adds the specified edge, e, to this.edges graph if it is not already present.
     * @param e the edge to be added to this.edges.
     * @spec.requires e is not already present in this.edges and the src and dest nodes contained in e must
     * exist in this.nodes
     * @throws IllegalArgumentException if e = null
     * @spec.modifies this
     * @spec.effects adds e to this.edges
     * @return true if this.edges did not already contain e
     */
    public boolean addEdge(DirectedLabeledEdge<E, T> e) {
        checkRep();
        if (e == null) {
            throw new IllegalArgumentException("Cannot add a null edge to the graph.");
        } else if ( !(graph.containsKey(e.getSrc()) && graph.containsKey(e.getDest())) ) {
            throw new IllegalArgumentException("Both nodes need to exist in the graph.");
        } else if (this.containsEdge(e)) {
            return false;
        } else {
            graph.get(e.getSrc()).add(e);
            checkRep();
            return true;
        }
    }

    /**Removes the specified node, n, from this.nodes if it is present.
     * @param n the node to be removed from this.nodes
     * @spec.requires n is present in this.nodes
     * @throws IllegalArgumentException if n = null
     * @spec.effects removes n from this.nodes
     * @return true if this.nodes contained n
     */
    public boolean removeNode(E n) {
        checkRep();
        if (n == null) {
            throw new IllegalArgumentException("Cannot remove a null node from the graph.");
        } else if (!graph.containsKey(n)) {
            throw new IllegalArgumentException("Cannot remove a node that doesn't exist in the graph.");
        } else {
            //removes n's out-bound edges
            graph.remove(n);
            //removes all of n's ingoing edges. Could also keep a second map to map nodes to their
            //in going edges for efficiency, but for now I don't think the client will be removing nodes a lot.
            for (E node: graph.keySet()) {
                for (DirectedLabeledEdge edge: graph.get(node)) {
                    if (edge.getDest().equals(n)) {
                        graph.get(node).remove(edge);
                    }
                }
            }
            checkRep();
            return true;
        }
    }

    /**Removes the specified edge, e, from this.edges if it is present.
     * @param e the edge to be removed from this.edges
     * @spec.requires e is present in this.edges
     * @throws IllegalArgumentException if e = null
     * @spec.effects removes e from this.edges
     * @return true if this.edges contained e.
     */

    public boolean removeEdge(DirectedLabeledEdge<E, T> e) {
        checkRep();
        if (e == null) {
            throw new IllegalArgumentException("Cannot remove a null edge from the graph.");
        } else if (!graph.get(e.getSrc()).contains(e)) {
            throw new IllegalArgumentException("Cannot remove an edge that doesn't exist.");
        } else {
            checkRep();
            graph.get(e.getSrc()).remove(e);
            return true;
        }
    }

    /**Returns a Set of nodes that are contained in this.nodes.
     * @return a Set of nodes that are contained in this.nodes.
     */
    public Set<E> listNodes() {
        checkRep();
        //returns unmodifiable set of graph's node set so can provide client with "read-only access to
        // the graph's internal keySet instead of copying using clone and returning a new set.
        return Collections.unmodifiableSet(graph.keySet());
    }

    /**Returns a Set of the out-bound edges of parentNode that are contained in this.edges.
     * @param parentNode the node whose associated out-bound edges are to be returned
     * @spec.requires parentNode is present in this.nodes.
     * @throws IllegalArgumentException if parentNode = null
     * @return a Set of the out-bound edges of parentNode that are contained in this.edges.
     */
    public Set<DirectedLabeledEdge<E, T>> listChildren(E parentNode) {
        checkRep();
        if (parentNode == null) {
            throw new IllegalArgumentException("Cannot list children of a null node");
        } else if (!graph.containsKey(parentNode)) {
            throw new IllegalArgumentException(parentNode + " does not exist in the graph");
        } else {
            checkRep();
            return Collections.unmodifiableSet(graph.get(parentNode));
        }
    }
}