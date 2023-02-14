package com.swaqly.swaqly.ApiDataFetched;

public class Products {
    private static boolean status;
    private static String massege;
    private static String[] products;

    public static boolean isStatus() {
        return status;
    }

    public static void setStatus(boolean status) {
        Products.status = status;
    }

    public static String getMassege() {
        return massege;
    }

    public static void setMassege(String massege) {
        Products.massege = massege;
    }

    public static String[] getProducts() {
        return products;
    }

    public static void setProducts(String[] products) {
        Products.products = products;
    }
}
