package ru.edu.pr04;
import java.util.*;
public class Order {
    private final Employee employee; private final List<OrderItem> items=new ArrayList<>(); private OrderStatus status=OrderStatus.DRAFT;
    public Order(Employee employee) { this.employee=Objects.requireNonNull(employee); }
    public void addProduct(Product product,int quantity) { /* TODO 5  объединить повтор */ }
    public void removeProduct(String productId) { /* TODO 6 */ }
    public double total() { /* TODO 7 */ return 0.0; }
    public void confirm() { /* TODO 8 */ }
    public List<OrderItem> getItems() { return List.copyOf(items); }
    public OrderStatus getStatus() { return status; }
}
