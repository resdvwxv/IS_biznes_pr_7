package ru.edu.pr04;

import java.util.*;

public class Order {
    private final Employee employee;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.DRAFT;

    public Order(Employee employee) { this.employee = Objects.requireNonNull(employee); }

    public void addProduct(Product product, int quantity) {
        ensureDraft();
        Objects.requireNonNull(product, "product");
        OrderItem existing = find(product.getId());
        if (existing != null) {
            existing.increase(quantity);          // объединяем, а не дублируем строку
        } else {
            items.add(new OrderItem(product, quantity));
        }
    }

    public void removeProduct(String productId) {
        ensureDraft();
        if (productId == null || !items.removeIf(i -> i.getProduct().getId().equals(productId)))
            throw new NoSuchElementException("No product in order: " + productId);
    }

    public double total() {
        double sum = 0;
        for (OrderItem i : items) sum += i.getLineTotal();
        return sum;
    }

    public void confirm() {
        ensureDraft();
        if (items.isEmpty()) throw new IllegalStateException("Cannot confirm empty order");
        status = OrderStatus.CONFIRMED;
    }

    private void ensureDraft() {
        if (status != OrderStatus.DRAFT) throw new IllegalStateException("Order is confirmed and immutable");
    }

    private OrderItem find(String productId) {
        for (OrderItem i : items) if (i.getProduct().getId().equals(productId)) return i;
        return null;
    }

    public Employee getEmployee() { return employee; }
    public List<OrderItem> getItems() { return List.copyOf(items); }
    public OrderStatus getStatus() { return status; }
}
