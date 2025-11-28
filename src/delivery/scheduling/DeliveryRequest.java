package delivery.scheduling;

import delivery.model.*;
import java.util.UUID;

public class DeliveryRequest implements Comparable<DeliveryRequest> {
    private String id = UUID.randomUUID().toString().substring(0, 8);
    private String destination;
    private Item item;
    private Vehicle vehicle;
    private int priority;
    private boolean assigned = false;
    private String status = "Queued";

    public DeliveryRequest(String destination, Item item, int priority) {
        this.destination = destination;
        this.item = item;
        this.priority = priority;
    }

    public String getId() { return id; }
    public String getDestination() { return destination; }
    public Item getItem() { return item; }

    public String getStatus() { return status; }

    public void assignVehicle(Vehicle v) { 
        this.vehicle = v; 
        this.assigned = true;
        this.status = "Assigned";
    }

    public void markInTransit() {
        this.status = "InTransit";
    }

    public void markDelivered() {
        this.status = "Delivered";
    }

    public Vehicle getVehicle() { return vehicle; }

    public void completeAsync() {
        new Thread(() -> {
            try {
                int delayMs = 10000 + (int)(Math.random() * 5000);
                Thread.sleep(delayMs);
                markDelivered();
                System.out.println("[DELIVERY COMPLETE] Delivery " + id + " has been completed.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    @Override
    public int compareTo(DeliveryRequest o) {
        return Integer.compare(o.priority, this.priority);
    }
}
