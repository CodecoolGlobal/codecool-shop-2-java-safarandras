package com.codecool.shop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private static List<Product> items = new ArrayList<>();

    public static void add(Product product) {
        items.add(product);
    }

    public static Product find(int id) {
        return items.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    public static void remove(int id) {
        items.remove(find(id));
    }

    public static List<Product> getAll() {
        return items;
    }

}
