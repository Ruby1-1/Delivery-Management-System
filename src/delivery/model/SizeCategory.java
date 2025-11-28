package delivery.model;

public class SizeCategory {

    public static Vehicle chooseVehicle(Item item) {
        double weight = item.getWeight();
        double size = item.getSize();

        boolean tooHeavy = weight > 50;
        boolean tooBig = size > 5000;

        if (tooHeavy || tooBig) {
            return new Truck();
        }
        return new Motorcycle();
    }
}

