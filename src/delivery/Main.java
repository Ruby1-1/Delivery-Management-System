package delivery;

import delivery.graph.*;
import delivery.model.*;
import delivery.scheduling.*;
import delivery.service.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Graph graph = new Graph();
        String start = "Cagayan de Oro";
        
        graph.addEdge(start, "Manila", 800);
        graph.addEdge(start, "Cebu", 380);
        graph.addEdge(start, "Davao", 320);
        graph.addEdge(start, "Pampanga", 750);
        
        graph.addEdge("Manila", "BGC", 25);
        graph.addEdge("Manila", "Palawan", 550);
        graph.addEdge("Cebu", "Negros", 90);
        graph.addEdge("Cebu", "Davao", 420);
        graph.addEdge("Davao", "Palawan", 600);
        graph.addEdge("Pampanga", "Manila", 80);
        graph.addEdge("Negros", "Palawan", 400);
        
        graph.addEdge("Manila", start, 800);
        graph.addEdge("Cebu", start, 380);
        graph.addEdge("Davao", start, 320);
        graph.addEdge("Pampanga", start, 750);
        graph.addEdge("BGC", "Manila", 25);
        graph.addEdge("Palawan", "Manila", 550);
        graph.addEdge("Negros", "Cebu", 90);
        graph.addEdge("Davao", "Cebu", 420);
        graph.addEdge("Palawan", "Davao", 600);
        graph.addEdge("Manila", "Pampanga", 80);
        graph.addEdge("Palawan", "Negros", 400);

        DeliveryQueue queue = new DeliveryQueue();
        FleetManager fleet = new FleetManager(2, 1);
        DeliveryService service = new DeliveryService(graph, queue, fleet);

        Scanner sc = new Scanner(System.in);

        outer:
        while (true) {
            System.out.println("DELIVERY SYSTEM");
            System.out.println("1. Create Delivery");
            System.out.println("2. Process Deliveries");
            System.out.println("3. Track Delivery");
            System.out.println("4. Show Delivery Queue");
            System.out.println("5. Day Report");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            String choiceLine = sc.nextLine().trim();
            if (!choiceLine.matches("\\d+")) {
                System.out.println("Invalid choice. Please enter a number from the menu.");
                continue;
            }
            int choice = Integer.parseInt(choiceLine);

            switch (choice) {
                case 1: {
                    System.out.println("Select Destination");
                    String[] destinations = {"Manila", "Cebu", "Cagayan de Oro", "Davao", "BGC", "Palawan", "Pampanga", "Negros"};
                    for (int i = 0; i < destinations.length; i++) {
                        System.out.println((i + 1) + ". " + destinations[i]);
                    }
                    System.out.print("Choose destination (1-8): ");
                    int destChoice = readInt(sc);
                    if (destChoice < 1 || destChoice > destinations.length) {
                        System.out.println("Invalid destination choice.");
                        break;
                    }
                    String dest = destinations[destChoice - 1];

                    System.out.print("Item name: ");
                    String name = sc.nextLine();
                    System.out.print("Weight: ");
                    double w = readDouble(sc);
                    System.out.print("Length: ");
                    double l = readDouble(sc);
                    System.out.print("Width: ");
                    double wid = readDouble(sc);
                    System.out.print("Priority (1 high, 0 normal): ");
                    int pr = readInt(sc);

                    Item item = new Item(name, w, l, wid);
                    service.createDelivery(dest, item, pr);
                    break;
                }
                case 2:
                    service.processDeliveries("Cagayan de Oro");
                    break;
                case 3: {
                    System.out.print("Enter Delivery ID: ");
                    String id = sc.nextLine().trim();
                    DeliveryRequest r = service.trackDelivery(id);
                    if (r == null) {
                        System.out.println("No delivery found with ID " + id);
                    } else {
                        System.out.println("=== Delivery Info ===");
                        System.out.println("ID: " + r.getId());
                        System.out.println("Item: " + r.getItem().getName());
                        System.out.println("Destination: " + r.getDestination());
                        System.out.println("Vehicle: " + (r.getVehicle() != null ? r.getVehicle().getType() : "Unassigned"));
                        System.out.println("Status: " + r.getStatus());
                    }
                    break;
                }
                case 4:
                    service.showQueue();
                    break;
                case 5:
                    service.showDayReport();
                    break;
                case 6:
                    sc.close();
                    System.out.println("Goodbye.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please select from the menu.");
                    continue;
            }

            while (true) {
                System.out.println("\n1) Back to main menu\n2) Exit");
                System.out.print("Choose: ");
                int post = readInt(sc);
                if (post == 1) {
                    continue outer;
                } else if (post == 2) {
                    sc.close();
                    System.out.println("Goodbye.");
                    System.exit(0);
                } else {
                    System.out.println("Invalid option. Enter 1 to return, 2 to exit.");
                }
            }
        }
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number; please enter again: ");
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number; please enter again: ");
            }
        }
    }
}
