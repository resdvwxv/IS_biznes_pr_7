package ru.edu.pr04;
public class SelfCheck {
    public static void main(String[] args) {
        boolean ok = false;
        try { ok = (new Product("P1","Mouse",1000).getPrice()==1000); } catch (Exception e) { System.out.println("FAIL exception " + e.getMessage()); }
        System.out.println(ok ? "PASS" : "FAIL  complete TODO methods");
    }
}
