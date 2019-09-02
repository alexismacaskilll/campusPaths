package graph;

/**
 * <b>DirectedLabeledEdge</b> is an immutable representation of a directed labeled edge in a
 * labeled DirectedGraph. A DirectedLabeledEdge, which can be notated by a pair of nodes and
 * a label (src, dest, label), connects the nodes src and dest in the direction from src to
 * dest with a label.
 *
 *
 * @spec.specfield src : node. The source node of the edge
 * @spec.specfield <br>dest : node. The target node of the edge
 * @spec.specfield <br>label : String. The label of the edge.
 *
 */


public final class DirectedLabeledEdge<E, T extends Comparable<T>> {

    private final E src;
    private final E dest;
    private final T label;

     // Abstraction function:
        // A DirectedLabelEdge, e, is a directed edge from a parent node, e.src, to a child node, this.dest
        // and that has a label, this.label. An edge can have the same src node and dest node, in which it
        // would be a loop, an edge that connects a node to itself.

     //Representation invariant:
        //for every DirectedLabeledEdge e:
        // e.src != null && e.dest != null && e.label != null

    /** Throws an exception if the representation invariant is violated. */
    private void checkRep() {
        assert (src != null) : "Src node of DirectedLabeledEdge cannot be null";
        assert (dest != null) : "Dest node of DirectedLabeledEdge cannot be null";
        assert (label != null) : "Label of DirectedLabeledEdge cannot be null";
    }

    /**Creates a new DirectedLabeledEdge from src to dest with label.
     *@param src the source node for the new DirectedLabeledEdge
     *@param dest the target node for the new DirectedLabeledEdge
     *@param label the label for the new DirectedLabeledEdge
     *@throws IllegalArgumentException if src = null or dest = null or label = null,
     *@spec.effects Constructs a new DirectedLabeledEdge, e, with e.src = src, e.dest = dest, and
     * e.label = label.
     */
    public DirectedLabeledEdge(E src, E dest, T label) {
        if (src == null || dest == null || label == null) {
            throw new IllegalArgumentException("No part of edge can be null.");
        }
        this.src = src;
        this.dest = dest;
        this.label = label;
        checkRep();
    }

    /**Gets the source node, src, of this DirectedLabeledEdge.
     *@return this.src, the source node of this DirectedLabeledEdge
     */
    public E getSrc() {
        return src;
    }

    /**Gets the target node, dest, of this DirectedLabeledEdge.
     *@return this.dest, the target node of this DirectedLabeledEdge
     */
    public E getDest() {
        return dest;
    }

    /**Gets the label of this DirectedLabeledEdge
     *@return this.label, the label of this DirectedLabeledEdge
     */
    public T getLabel() {
        return label;
    }

    /**Returns the hash code value for this edge.
     * @return the hash code value of this edge
     */
    @Override
    public int hashCode() {
        return (this.src.hashCode() * 2) + (this.dest.hashCode() * 3) + (this.label.hashCode() * 5);
    }

    /**Compares the specified object with this edge for for equality. The result is true if and
     * only if obj != null is not null and is a DirectedEdgeLabel object that represents the
     * same edge as this object. Two DirectedEdgeLabels are equivalent if their src and dest nodes
     * and their labels are equivalent.
     * @param obj the object to compare this DirectedEdgeLabel with.
     * @return true if obj represents a DirectedEdgeLabel equivalent to this edge, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        checkRep();
        if (obj == this) {
            return true;
        } else if (!(obj instanceof DirectedLabeledEdge)) {
            return false;
        } else {
            DirectedLabeledEdge e = (DirectedLabeledEdge) obj;
            checkRep();
            return this.src.equals(e.src) && this.dest.equals(e.dest) && this.label.equals(e.label);
        }
    }
    /*
    /**
     * Returns String representation of this edge.
     *
     * @return String representation of this edge
     */
    @Override
    public String toString() {
        checkRep();
        String result = dest + "(" + label + ")";
        return result;
    }

}


