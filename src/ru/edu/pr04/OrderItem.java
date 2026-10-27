package ru.edu.pr04;

import java.util.Objects;

public class OrderItem {
    public static final int MIN_QUANTITY = 1;
    public static final int MAX_QUANTITY = 100;
    /** Вариант 6: предел количества в строке при объединении повторного товара. Единственное место правила. */
    public static final int MAX_MERGED_QUANTITY = 35;

    private final Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = Objects.requireNonNull(product, "product");
        setQuantity(quantity);
    }

    private void setQuantity(int quantity) {
        if (quantity < MIN_QUANTITY || quantity > MAX_QUANTITY)
            throw new IllegalArgumentException("Quantity must be in [" + MIN_QUANTITY + ".." + MAX_QUANTITY + "]: " + quantity);
        this.quantity = quantity;
    }

    /** Объединение повторной позиции. Package-private: менять строки может только Order (после confirm — нельзя). */
    void increase(int delta) {
        if (delta < MIN_QUANTITY)
            throw new IllegalArgumentException("Delta must be >= " + MIN_QUANTITY + ": " + delta);
        long merged = (long) quantity + delta;
        if (merged > MAX_MERGED_QUANTITY)
            throw new IllegalArgumentException("Merged quantity of '" + product.getId() + "' would be " + merged
                    + ", limit is " + MAX_MERGED_QUANTITY);
        setQuantity((int) merged);
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getLineTotal() { return product.getPrice() * quantity; }
}
