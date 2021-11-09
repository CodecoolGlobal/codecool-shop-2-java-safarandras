package com.codecool.shop.model;

import java.util.*;

public class Cart {

    private static HashSet<LineItem> lineItems = new HashSet<>();

    public static void add(Product product) {
        LineItem newItem = new LineItem(product);
        if (!lineItems.isEmpty()) {
            for (LineItem lineItem: lineItems) {
                if (Objects.equals(product.name, lineItem.getProduct().getName())) {
                    lineItem.increaseQuantityAndSubtotal();
                } else {
                    newItem.setId(lineItems.size() + 1);
                    lineItems.add(newItem);
                }
            }
        } else {
            newItem.setId(1);
            lineItems.add(newItem);
        }

    }

    public static LineItem find(String name) {
        return lineItems.stream()
                .filter(t -> t.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static void remove(String name) {
        lineItems.remove(find(name));
    }

    public static HashSet<LineItem> getAll() {
        return lineItems;
    }

}
