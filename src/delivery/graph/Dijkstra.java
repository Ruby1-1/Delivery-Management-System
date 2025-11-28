package delivery.graph;

import java.util.*;

public class Dijkstra {

    public static List<String> findPath(Graph graph, String start, String target) {
        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(dist::get));

        for (String v : graph.getAdj().keySet()) {
            dist.put(v, Integer.MAX_VALUE);
        }

        dist.put(start, 0);
        pq.add(start);

        while (!pq.isEmpty()) {
            String cur = pq.poll();

            if (cur.equals(target)) break;

            for (Graph.Node n : graph.getAdj().get(cur)) {
                int alt = dist.get(cur) + n.weight;
                if (alt < dist.get(n.vertex)) {
                    dist.put(n.vertex, alt);
                    prev.put(n.vertex, cur);
                    pq.add(n.vertex);
                }
            }
        }

        return buildPath(prev, target);
    }

    private static List<String> buildPath(Map<String, String> prev, String target) {
        List<String> path = new ArrayList<>();
        String cur = target;

        while (cur != null) {
            path.add(cur);
            cur = prev.get(cur);
        }

        Collections.reverse(path);
        return path;
    }
}


