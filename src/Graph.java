import java.util.ArrayList;
import java.util.List;

public class Graph {
    List<Node> nodes;
    List<Edge> edges;


    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }


    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public List<Node> getNodes() {
        return new ArrayList<>(nodes);
    }

    public List<Edge> getEdges() {
        return new ArrayList<>(edges);
    }

    public void setEdgesToRandomWeightBtwn(int min, int max){
        for (Edge edge : edges) {
            edge.setWeight((int)(Math.random() * ((max - min) + 1)) + min);
        }
    }

    public void printGraphEdges(){
        for (Edge edge :edges){
            System.out.println(edge);
        }
    }
    public void printGraphEdgesWithWeight(){
        for (Edge edge :edges){
            System.out.println(edge.toStringWithWeight());
        }
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Edge> getEdgesContains(Node node){
        List<Edge> result = new ArrayList<>();
        for (Edge edge : edges){
            if (edge.getNode1().equals(node) || edge.getNode2().equals(node))
                result.add(edge);
        }
        return result;
    }



}
