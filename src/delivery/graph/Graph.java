package delivery.graph;

import java.util.*;

public class Graph {
    private Map<String, List<Node>> adj = new HashMap<>();

    public void addEdge(String from, String to, int weight) {
        adj.putIfAbsent(from, new ArrayList<>());
        adj.putIfAbsent(to, new ArrayList<>());

        adj.get(from).add(new Node(to, weight));
        adj.get(to).add(new Node(from, weight)); 
    }

    public Map<String, List<Node>> getAdj() {
        return adj;
    }

    public static class Node {
        public String vertex;
        public int weight;
        public Node(String v, int w) { vertex = v; weight = w; }
    }
}