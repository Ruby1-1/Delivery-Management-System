package delivery.model;

public class Item {
    private final String name;
    private final double weight;
    private final double length;
    private final double width;

    public Item(String name, double weight, double length, double width) {
        this.name = name;
        this.weight = weight;
        this.length = length;
        this.width = width;
    }

    public double getWeight() { return weight; }
    public double getSize() { return length * width; }
    public String getName() { return name; }
}

