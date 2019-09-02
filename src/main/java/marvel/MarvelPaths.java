package marvel;

import graph.DirectedGraph;
import graph.DirectedLabeledEdge;
import java.util.*;
public class MarvelPaths {


    /**
     * <b>MarvelPaths</b> is a representation of a function to find the shortest path between
     * two nodes in a graph.
     *
     */

    // No Abstraction Function or RepInvariant listed because MarvelPaths is not
    // an Abstract Data Type.

    // Abstract description: MarvelPaths is a function to find the shortest path between
    // two nodes in a graph.

    public static void main(String[] args) {

        try {
            Scanner console = new Scanner(System.in);
            String marvelFile = "src/main/resources/marvel/data/marvel.tsv";
            DirectedGraph<String, String> graph = MarvelPaths.loadGraph(marvelFile);
            System.out.println("Enter two character names to find the shortest path between them.");
            boolean continueProgram = true;
            while (continueProgram) {
                System.out.print("Enter starting character: ");
                String start = console.nextLine();
                System.out.print("Enter ending character: ");
                String dest = console.nextLine();
                if(!(graph.containsNode(start) && graph.containsNode(dest))) {
                    System.out.println("No path exists because the graph doesn't contain both the " +
                            "starting character and the ending character.");
                } else {
                    List<DirectedLabeledEdge<String, String>> path = MarvelPaths.findPath(graph, start, dest);
                    if (path.size() == 0) {
                        System.out.println("No path exists from " + start + " to themselves.");
                    }
                    else if (path == null) {
                        System.out.println("No path exists between " + start + " and " + dest + " in " +
                                "the graph.");
                    } else {
                        System.out.println("The shortest path from  " + start + " to " + dest + " is:");
                        for (DirectedLabeledEdge edge: path) {
                            System.out.println(edge.getSrc() + " to " + edge.getDest() + " via "+ edge.getLabel());
                        }
                    }
                }
                System.out.print("Would you like to find another path? Enter y or n. ");
                String again = console.nextLine();
                if (again.equals("n")) {
                    continueProgram = false;
                    System.out.println("Marvel program exited.");
                }

            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.toString());
            e.printStackTrace();
        }

    }

    /**
     * Builds a graph from the data in the file.  the Marvel Universe dataset.
     *
     * @spec.requires filename is a valid file path
     * @throws IllegalArgumentException if filename == null.
     * @param filename the name of the file that the graph is built from.
     * @return a DirectedGraph that maps each character, Char1, to a list of DirectedLabeledEdges,
     * ⟨Char1,Char2, book) that each indicate Char1 appeared in a comic book that Char2 also
     * appeared in.
     */
    public static DirectedGraph<String, String> loadGraph(String filename) {
        if (filename == null ) {
            throw new IllegalArgumentException("Filename cannot be null.");
        }
        Map<String, List<String>> books = MarvelParser.parseData(filename);
        DirectedGraph<String, String> graph = new DirectedGraph<>();
        //then go through each book in the books map
        for (String book : books.keySet()) {
            if (book != null) {
                //get the list of all the characters in that book.
                List<String> bookCharacters = books.get(book);
                //then make edges connecting all of those characters both ways.
                for (int i = 0; i < bookCharacters.size(); i++) {
                    graph.addNode(bookCharacters.get(i));
                    for (int j = 0; j < bookCharacters.size(); j++) {
                        //no reflexive edges
                        if (!(bookCharacters.get(i).equals(bookCharacters.get(j)))) {
                            graph.addNode(bookCharacters.get(j));
                            graph.addEdge(new DirectedLabeledEdge<>(bookCharacters.get(i), bookCharacters.get(j), book));
                            graph.addEdge(new DirectedLabeledEdge<>(bookCharacters.get(j), bookCharacters.get(i), book));
                        }
                    }
                }
            }
        }
        return graph;
    }

    /**
     * Finds the shortest path between two characters in a graph.
     *
     * @spec.requires graph contains start and dest nodes.
     * @throws IllegalArgumentException if graph = null, start = null, dest = null.
     * @param graph the graph that is used to find the shortest distance between characters.
     * @param start a character node in the graph
     * @param dest a character node in the graph
     * @return a List of DirectedLabeledEdges that represents the shortest path between start and
     * dest nodes in the graph.
     */
    public static  List<DirectedLabeledEdge<String, String>> findPath(DirectedGraph<String, String> graph, String start, String dest) {
        if (graph == null || start == null || dest == null) {
            throw new IllegalArgumentException("Graph, start, and dest cannot be null.");
        }
        //if either src or dest node are not in the graph, there can be no path
        if (!(graph.containsNode(start) && graph.containsNode(dest))) {
            throw new IllegalArgumentException("Graph must contain start and dest nodes of path");
        }
        Map<String, List<DirectedLabeledEdge<String, String>>> paths = new HashMap<>();
        paths.put(start, new ArrayList<>());
        Queue<String> queue = new LinkedList<String>(); //better than array list for add/remove so we use linked list
        queue.add(start); //initialize queue to hold start
        //{inv: distance(n_1) ≤ ... ≤ distance(n_r), where n_1,...,n_r are the vertices in the queue at the current
        // iteration and distance(n_i) is the length of the shortest path from start node to the node n_i.
        while (!queue.isEmpty() ) { //while queue is not empty and we haven't found it.
            String nextNode = queue.remove(); //process next node
            if (nextNode.equals(dest)) {
                //List<LabeledEdge<String, String>> path = paths.get(character);
                //return new ArrayList<LabeledEdge<String, String>>(path);
              //  List<DirectedLabeledEdge<String, String>> path = paths.get(nextNode);
               // return new ArrayList<DirectedLabeledEdge<String, String>>(path);
                return new ArrayList<>(paths.get(nextNode)); //if return is empty then its self edge.
            }
            //new TreeSet that implements the sorting of the test script file so in the for-each loop, visit
            // edges in increasing order of m's character name
            Set<DirectedLabeledEdge<String, String>> sortedEdges = new TreeSet<>(new EdgeComparator());
            //adds all of the children nodes to this edges set but in order specified by comparator.
            sortedEdges.addAll( graph.listChildren(nextNode));
            //for each outgoing edge of nextNode, (each adjacent node to nextNode)
            for (DirectedLabeledEdge<String, String> edge : sortedEdges) {
                String adjacentNode = edge.getDest(); //this gets the adjacent node from the edge
                //if the adjacent node has not already been marked,
                if (!(paths.containsKey(adjacentNode)) ) {

                    List<DirectedLabeledEdge<String, String>> newPath = paths.get(nextNode);
                    List<DirectedLabeledEdge<String, String>> newPath1 = new ArrayList<>(newPath);
                    newPath1.add(edge);
                    paths.put(adjacentNode, newPath1); //mark the adjacentNode .
                    queue.add(adjacentNode); //enqueue the adjacentNode
                }
            }
        }
        return null; //means no path exists.
    }


    //private class to be able to return the correct path when there are multiple shortest shortest paths.
    private static class EdgeComparator implements Comparator<DirectedLabeledEdge<String, String>>{

        @Override
        public int compare(DirectedLabeledEdge<String, String> edge1, DirectedLabeledEdge<String, String> edge2) {
            if (!(edge1.getDest().equals(edge2.getDest()))) {
                return edge1.getDest().compareTo(edge2.getDest());
            } else if (!(edge1.getLabel().equals(edge2.getLabel()))) {
                return edge1.getLabel().compareTo(edge2.getLabel());
            } else {
                return 0;

            }
        }
    }
}

