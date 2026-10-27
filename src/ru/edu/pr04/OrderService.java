package ru.edu.pr04;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class OrderService {
    public Order createOrder(Employee e) { return new Order(e); }

    public void validateForConfirm(Order order) {
        if (order == null) throw new IllegalArgumentException("Order is null");
        if (order.getStatus() != OrderStatus.DRAFT) throw new IllegalStateException("Order already confirmed");
        if (order.getItems().isEmpty()) throw new IllegalStateException("Order is empty");
    }

    public void confirmOrder(Order order) {
        validateForConfirm(order);
        order.confirm();
    }

    public void loadCsv(Order order, Path file, List<String> errors) throws IOException {
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        Map<String, Product> known = new HashMap<>();
        for (int n = 1; n < lines.size(); n++) {            // строка 0 — заголовок
            String line = lines.get(n);
            if (line.isBlank()) continue;
            try {
                String[] p = line.split(";", -1);
                if (p.length != 4) throw new IllegalArgumentException("expected 4 fields, got " + p.length);
                double price = Double.parseDouble(p[2].trim());
                int qty = Integer.parseInt(p[3].trim());
                Product product = new Product(p[0], p[1], price);
                Product prev = known.get(product.getId());
                if (prev != null && (prev.getPrice() != product.getPrice() || !prev.getName().equals(product.getName())))
                    throw new IllegalArgumentException("conflicting data for product " + product.getId());
                known.putIfAbsent(product.getId(), product);
                order.addProduct(known.get(product.getId()), qty);
            } catch (RuntimeException ex) {
                errors.add("line " + (n + 1) + ": " + ex.getMessage());
            }
        }
    }
}
