package delivery.service;

import delivery.graph.*;
import delivery.model.*;
import delivery.scheduling.*;
import java.util.*;

public class DeliveryService {

    private Graph graph;
    private DeliveryQueue queue;
    private FleetManager fleet;
    private Map<String, DeliveryRequest> deliveryMap = new HashMap<>();
    private List<DeliveryRequest> deliveredToday = new ArrayList<>();

    public DeliveryService(Graph graph, DeliveryQueue queue, FleetManager fleet) {
        this.graph = graph;
        this.queue = queue;
        this.fleet = fleet;
    }

    public void createDelivery(String destination, Item item, int priority) {
        Vehicle suggested = SizeCategory.chooseVehicle(item);
        boolean needsHeavy = suggested instanceof Truck;

        DeliveryRequest req = new DeliveryRequest(destination, item, priority);

        Vehicle assigned = fleet.requestVehicle(needsHeavy);
        if (assigned != null) {
            req.assignVehicle(assigned);
        } else {
            System.out.println("No available vehicle right now; delivery will be queued without assignment.");
        }

        deliveryMap.put(req.getId(), req);
        queue.add(req);

        System.out.println("Added Delivery ID: " + req.getId());
    }


    public void processDeliveries(String start) {
        System.out.println("\n--- Processing Deliveries ---");
        int count = 0;
        while (!queue.isEmpty()) {
            DeliveryRequest req = queue.poll();
            count++;

            Vehicle v = req.getVehicle();
            if (v == null) {
                Vehicle suggested = SizeCategory.chooseVehicle(req.getItem());
                boolean needsHeavy = suggested instanceof Truck;
                v = fleet.requestVehicle(needsHeavy);
                if (v == null) {
                    System.out.println("No vehicles available for delivery " + req.getId() + ". Requeuing.");
                    queue.add(req);
                    break;
                }
                req.assignVehicle(v);
            }

            req.markInTransit();
            List<String> path = Dijkstra.findPath(graph, start, req.getDestination());

            System.out.println("\n[DELIVERY " + count + "]");
            System.out.println("ID: " + req.getId());
            System.out.println("Item: " + req.getItem().getName());
            System.out.println("Vehicle: " + (req.getVehicle() != null ? req.getVehicle().getType() : "Unassigned"));
            System.out.println("Route: " + path);
            System.out.println("Status: " + req.getStatus());

            req.completeAsync();
        }
        System.out.println("\n--- All deliveries started; completing asynchronously... ---");
    }

    public void showDayReport() {
        for (DeliveryRequest req : deliveryMap.values()) {
            if ("Delivered".equals(req.getStatus()) && !deliveredToday.contains(req)) {
                deliveredToday.add(req);
            }
        }

        System.out.println("DAY REPORT");
        System.out.println("Total Delivered: " + deliveredToday.size());

        if (deliveredToday.isEmpty()) {
            System.out.println("(No deliveries completed yet)");
        } else {
            for (DeliveryRequest req : deliveredToday) {
                System.out.println("ID: " + req.getId());
                System.out.println("Item: " + req.getItem().getName());
                System.out.println("Destination: " + req.getDestination());
                System.out.println("Vehicle: " + (req.getVehicle() != null ? req.getVehicle().getType() : "Unassigned"));
                System.out.println("Status: " + req.getStatus());
                System.out.println("");
            }
        }
    }

    public void showQueue() {
        queue.printQueue();
    }

    public DeliveryRequest trackDelivery(String id) {
        return deliveryMap.get(id);
    }

}
