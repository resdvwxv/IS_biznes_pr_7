package ru.edu.pr04;

import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Path file = Path.of(args.length > 0 ? args[0] : "data/sample.csv");
        OrderService service = new OrderService();
        Order order = service.createOrder(new Employee("E1", "Иванов Иван"));
        List<String> errors = new ArrayList<>();
        service.loadCsv(order, file, errors);
        for (OrderItem i : order.getItems())
            System.out.printf("%s x%d = %.2f%n", i.getProduct().getName(), i.getQuantity(), i.getLineTotal());
        errors.forEach(e -> System.out.println("ERROR " + e));
        System.out.printf("Итого: %.2f%n", order.total());
        service.confirmOrder(order);
        System.out.println("Статус: " + order.getStatus());
    }
}
