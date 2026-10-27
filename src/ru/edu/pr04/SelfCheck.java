package ru.edu.pr04;

import java.util.NoSuchElementException;

public class SelfCheck {
    private static int failed = 0;

    private static void check(String name, boolean cond) {
        System.out.println((cond ? "PASS " : "FAIL ") + name);
        if (!cond) failed++;
    }

    private static boolean throwsEx(Class<? extends Exception> c, Runnable r) {
        try { r.run(); return false; } catch (Exception e) { return c.isInstance(e); }
    }

    private static Order newOrder() { return new OrderService().createOrder(new Employee("E1", "Test")); }

    public static void main(String[] args) {
        Product mouse = new Product("P1", "Mouse", 1000);
        Product kb = new Product("P2", "Keyboard", 4490.5);

        check("Product price", mouse.getPrice() == 1000);
        check("Product: цена 0 -> ошибка", throwsEx(IllegalArgumentException.class, () -> new Product("X", "n", 0)));
        check("Product: отрицательная цена -> ошибка", throwsEx(IllegalArgumentException.class, () -> new Product("X", "n", -100)));
        check("Product: пустой id -> ошибка", throwsEx(IllegalArgumentException.class, () -> new Product(" ", "n", 1)));
        check("Product: null name -> ошибка", throwsEx(IllegalArgumentException.class, () -> new Product("X", null, 1)));

        check("qty нижняя граница 1 ок", new OrderItem(mouse, 1).getQuantity() == 1);
        check("qty 0 -> ошибка", throwsEx(IllegalArgumentException.class, () -> new OrderItem(mouse, 0)));
        check("qty верхняя граница 100 ок", new OrderItem(mouse, 100).getQuantity() == 100);
        check("qty 101 -> ошибка", throwsEx(IllegalArgumentException.class, () -> new OrderItem(mouse, 101)));

        Order o = newOrder();
        o.addProduct(mouse, 2);
        o.addProduct(kb, 1);
        o.addProduct(mouse, 3);
        check("объединение: одна строка на товар", o.getItems().size() == 2);
        check("объединение: количество 5", o.getItems().get(0).getQuantity() == 5);
        check("total = 5*1000 + 4490.5", Math.abs(o.total() - 9490.5) < 1e-9);

        // Вариант 6: предел при объединении = 35
        Order v = newOrder();
        v.addProduct(mouse, 30);
        v.addProduct(mouse, 5);
        check("вариант 6: объединено ровно 35 — допустимо", v.getItems().get(0).getQuantity() == 35);
        check("вариант 6: объединение до 36 -> ошибка", throwsEx(IllegalArgumentException.class, () -> v.addProduct(mouse, 1)));
        check("вариант 6: состояние не испорчено после ошибки", v.getItems().get(0).getQuantity() == 35);

        check("getItems не даёт менять заказ", throwsEx(UnsupportedOperationException.class, () -> o.getItems().clear()));

        o.removeProduct("P2");
        check("remove: позиция удалена", o.getItems().size() == 1);
        check("remove: неизвестный id -> ошибка", throwsEx(NoSuchElementException.class, () -> o.removeProduct("NOPE")));

        check("confirm пустого заказа -> ошибка", throwsEx(IllegalStateException.class, () -> newOrder().confirm()));
        o.confirm();
        check("статус CONFIRMED", o.getStatus() == OrderStatus.CONFIRMED);
        check("после confirm add запрещён", throwsEx(IllegalStateException.class, () -> o.addProduct(kb, 1)));
        check("после confirm remove запрещён", throwsEx(IllegalStateException.class, () -> o.removeProduct("P1")));
        check("повторный confirm запрещён", throwsEx(IllegalStateException.class, o::confirm));

        System.out.println(failed == 0 ? "ALL PASS" : "FAILED: " + failed);
    }
}
