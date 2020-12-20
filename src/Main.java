import java.util.Comparator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Graph g = new Graph();
        g.addNode(new Node("a"));
        g.addNode(new Node("b"));
        g.addNode(new Node("c"));
        g.addNode(new Node("d"));
        g.addNode(new Node("e"));
        g.addNode(new Node("f"));

        //all nodes are attached

        g.addEdge(new Edge(new Node("a"), new Node("b"), 1));
        g.addEdge(new Edge(new Node("a"), new Node("c"), 1));
        g.addEdge(new Edge(new Node("a"), new Node("d"), 1));
        g.addEdge(new Edge(new Node("a"), new Node("e"), 1));
        g.addEdge(new Edge(new Node("a"), new Node("f"), 1));

        g.addEdge(new Edge(new Node("b"), new Node("c"), 1));
        g.addEdge(new Edge(new Node("b"), new Node("d"), 1));
        g.addEdge(new Edge(new Node("b"), new Node("e"), 1));
        g.addEdge(new Edge(new Node("b"), new Node("f"), 1));

        g.addEdge(new Edge(new Node("c"), new Node("d"), 1));
        g.addEdge(new Edge(new Node("c"), new Node("e"), 1));
        g.addEdge(new Edge(new Node("c"), new Node("f"), 1));


        g.addEdge(new Edge(new Node("d"), new Node("e"), 1));
        g.addEdge(new Edge(new Node("d"), new Node("f"), 1));

        g.addEdge(new Edge(new Node("e"), new Node("f"), 1));




        g.setEdgesToRandomWeightBtwn(0, 1);
        g.printGraphEdgesWithWeight();
        System.out.println("--------- Arbres couvrant de poids minimum aléatoire---------");
        primWithWeightedTree(g).printGraphEdgesWithWeight();
        System.out.println("--------- Algorithme d’Aldous-Broder---------");
        adlousBroder(g).printGraphEdges();
        System.out.println("--------- Insertion aléatoire d’arêtes---------");
        randomInsertingEdges(g).printGraphEdges();
        System.out.println("--------- Par suppression de sommet---------");
        deleteRandomNode(g).printGraphEdges();
    }


    public static Graph primWithWeightedTree(Graph g) {

        Graph resultGraph = new Graph();
        DisjointSet<Node> ds = new DisjointSet<>();
        List<Edge> edgesInAscOrder = g.getEdges();
        edgesInAscOrder.sort(Comparator.comparing(Edge::getWeight)); //sort edges by ascending order of weight
        for (Node node : g.getNodes()) {
            ds.makeSet(node);
        }

        for (Edge edge : edgesInAscOrder) {
            Node node1 = ds.find(edge.getNode1());
            Node node2 = ds.find(edge.getNode2());
            if (!ds.find(node1).equals(node2)) {
                resultGraph.addEdge(edge);
                ds.union(node1, node2);
            }

        }

        return resultGraph;
    }

    public static Graph adlousBroder(Graph g) {
        List<Node> notVisitedNodes = g.getNodes();

        Graph resultGraph = new Graph();
        resultGraph.setNodes(g.getNodes());

        Node startingNode = g.getNodes().get((int) (Math.random() * ((g.getNodes().size() - 1) + 1))); // random node as starting node
        notVisitedNodes.remove(startingNode);
        Node currentNode = startingNode;
        while (!notVisitedNodes.isEmpty()) {
            List<Edge> edgesContainsCurrentNode = g.getEdgesContains(currentNode);
            Edge randomEdge = edgesContainsCurrentNode.get((int) (Math.random() * ((edgesContainsCurrentNode.size() - 1) + 1))); // picking a random edge
            Node nextNode = currentNode.equals(randomEdge.getNode1()) ? randomEdge.getNode2() : randomEdge.getNode1(); // getting node  from edge which not equal to current node
            if (notVisitedNodes.contains(nextNode)) {
                resultGraph.addEdge(randomEdge);
                currentNode = nextNode;
                notVisitedNodes.remove(currentNode);
            }
        }

        return resultGraph;
    }

    public static Graph randomInsertingEdges(Graph graph) {
        Graph resultGraph = new Graph();
        resultGraph.setNodes(graph.getNodes());
        DisjointSet<Node> ds = new DisjointSet<>();

        for (Node node : graph.getNodes()) {
            ds.makeSet(node);
        }
        while (resultGraph.getEdges().size() != graph.getNodes().size() - 1) { // |F| != |V | − 1
            Edge randomEdge = graph.getEdges().get((int) (Math.random() * ((graph.getEdges().size() - 1) + 1))); // getting a random edge
            if (!ds.find(randomEdge.getNode1()).equals(ds.find(randomEdge.getNode2()))) { // to prevent from cycles
                ds.union(ds.find(randomEdge.getNode1()), ds.find(randomEdge.getNode2()));
                resultGraph.addEdge(randomEdge);
            }
        }
        return resultGraph;
    }

    public static Graph deleteRandomNode(Graph graph){
        Graph resultGraph = new Graph();
        resultGraph.setNodes(graph.getNodes());
        resultGraph.setEdges(graph.getEdges());

        Node randomNode = resultGraph.getNodes().get((int) (Math.random() * ((resultGraph.getNodes().size() - 1) + 1)));// get a random node
        // get random edge that contains random node
        Edge randomEdge = resultGraph.getEdgesContains(randomNode).get((int) (Math.random() * ((resultGraph.getEdgesContains(randomNode).size() - 1) + 1)));
        resultGraph.nodes.remove(randomNode);// remove random node from graph
        resultGraph.edges.removeAll(graph.getEdgesContains(randomNode)); //remove all edges that contains random node

        resultGraph = adlousBroder(resultGraph); //generate a tree from the other nodes and edges (not containing the random node)
        resultGraph.addNode(randomNode); // add random node to
        resultGraph.addEdge(randomEdge);// add the random edge to make a link between the generated tree and random node

        return resultGraph;
    }
}
