package delivery.model;

import java.util.LinkedList;
import java.util.Queue;

public class FleetManager {

    private Queue<Motorcycle> motorcycles = new LinkedList<>();
    private Queue<Truck> trucks = new LinkedList<>();

    public FleetManager(int motorCount, int truckCount) {
        for (int i = 0; i < motorCount; i++) motorcycles.add(new Motorcycle());
        for (int i = 0; i < truckCount; i++) trucks.add(new Truck());
    }

    public Vehicle requestVehicle(boolean heavy) {
        if (heavy) {
            return trucks.poll();
        } else {
            return motorcycles.poll();
        }
    }

    public void returnVehicle(Vehicle v) {
        if (v instanceof Motorcycle) motorcycles.add((Motorcycle) v);
        else trucks.add((Truck) v);
    }

    public int getMotorCount() { return motorcycles.size(); }
    public int getTruckCount() { return trucks.size(); }
}
