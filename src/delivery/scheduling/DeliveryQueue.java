package delivery.scheduling;

import java.util.PriorityQueue;

public class DeliveryQueue {
    private PriorityQueue<DeliveryRequest> queue = new PriorityQueue<>();

    public void add(DeliveryRequest req) {
        queue.add(req);
    }

    public DeliveryRequest poll() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void printQueue() {
        System.out.println("\n--- Pending Deliveries ---");
        for (DeliveryRequest r : queue) {
            System.out.println("ID: " + r.getId() + " | Item: " + r.getItem().getName());
        }
    }
}
