package ru.edu.pr04;

public class Product {
    private final String id;
    private final String name;
    private final double price;

    public Product(String id, String name, double price) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("Product id is empty");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Product name is empty");
        if (Double.isNaN(price) || Double.isInfinite(price) || price <= 0)
            throw new IllegalArgumentException("Product price must be positive: " + price);
        this.id = id.trim();
        this.name = name.trim();
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
