package marvel.specTest;

import graph.DirectedGraph;
import graph.DirectedLabeledEdge;
import marvel.MarvelPaths;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class MarvelTestDriver {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            MarvelTestDriver td;

            if (args.length == 0) {
                td = new MarvelTestDriver(new InputStreamReader(System.in),
                        new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File(fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new MarvelTestDriver(new FileReader(tests),
                            new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java marvel.specTest.MarvelTestDriver <name of input script>");
        System.err.println("to read from standard in: java marvel.specTest.MarvelTestDriver");
    }

    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, DirectedGraph<String, String>> graphs;
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     * @effects Creates a new MarvelTestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public MarvelTestDriver(Reader r, Writer w) {
        graphs = new HashMap<>();
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @effects Executes the commands read from the input and writes results to the output
     **/
    public void runTests() throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("LoadGraph")) {
                loadGraph(arguments);
            } else if (command.equals("FindPath")) {
                findPath(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new DirectedGraph<>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        DirectedGraph<String, String> graph = graphs.get(graphName);
        graph.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        DirectedGraph<String, String>  graph = graphs.get(graphName);
        DirectedLabeledEdge<String, String> edge = new DirectedLabeledEdge<>(parentName, childName, edgeLabel);
        graph.addEdge(edge);
        output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }
        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        DirectedGraph<String, String> graph = graphs.get(graphName);
        Set<String> nodes = graph.listNodes();
        //converts set to list
        List<String> nodesList = new ArrayList<>(nodes);
        //sorts the list
        Collections.sort(nodesList);
        output.print(graphName + " contains:");
        for (String node : nodesList) {
            output.print(" " + node);
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }
        String graphName = arguments.get(0);
        String parentName = arguments.get(1).replaceAll("_", " ");
        listChildren(graphName, parentName);
    }


    private void listChildren(String graphName, String parentName) {
        DirectedGraph<String, String> graph = graphs.get(graphName);
        Set<DirectedLabeledEdge<String, String>> children = graph.listChildren(parentName);
        List<String> childrenList = new ArrayList<>();
        //Adds the String representation of each child edge to a list.
        for (DirectedLabeledEdge edge : children) {
            String edgeString = edge.getDest() + "(" + edge.getLabel() + ")";
            childrenList.add(edgeString);
        }
        //sorts the list
        Collections.sort(childrenList);
        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (String edge : childrenList) {
            output.print(" " + edge);
        }
        output.println();
    }

    private void loadGraph(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to loadGraph: " + arguments);
        }
        String graphName = arguments.get(0);
        String filePath = "src/test/resources/marvel/data/" + arguments.get(1);
        loadGraph(graphName, filePath);
    }

    private void loadGraph(String graphName, String filePath) {
        DirectedGraph<String, String> g = MarvelPaths.loadGraph(filePath);
        graphs.put(graphName, g);
        output.println("loaded graph " + graphName);
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String node1 = arguments.get(1).replaceAll("_", " ");
        String node2 = arguments.get(2).replaceAll("_", " ");
        findPath(graphName, node1, node2);
    }

    private void findPath(String graphName, String node1, String node2) {
        DirectedGraph<String, String> graph = graphs.get(graphName);
        if (!graph.containsNode(node1) || !graph.containsNode(node2)) {
            if (!graph.containsNode(node1)) {
                output.println("unknown character " + node1);
            }
            if (!graph.containsNode(node2) ) {
                output.println("unknown character " + node2);
            }
        } else {
            output.println("path from " + node1 + " to " + node2 + ":");
            if (!node1.equals(node2)) {
                List<DirectedLabeledEdge<String, String>> path = MarvelPaths.findPath(graph, node1, node2);
                if (path == null) {
                    output.println("no path found");
                } else {
                    for (DirectedLabeledEdge<String, String> edge : path) {
                        output.println(edge.getSrc() + " to " + edge.getDest()+ " via " +
                                edge.getLabel());
                    }
                }
            }
        }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {
        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }
        public static final long serialVersionUID = 3495;
    }
}
