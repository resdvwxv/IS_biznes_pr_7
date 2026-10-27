package ru.edu.pr04;
public class OrderItem {
    private final Product product; private int quantity;
    public OrderItem(Product product,int quantity) { this.product=product; setQuantity(quantity); }
    private void setQuantity(int quantity) { /* TODO 2 */ this.quantity=quantity; }
    public void increase(int delta) { /* TODO 3 */ }
    public Product getProduct() { return product; } public int getQuantity() { return quantity; }
    public double getLineTotal() { /* TODO 4 */ return 0.0; }
}
