package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.*;

public class Cart {

    private static HashSet<LineItem> lineItems = new HashSet<>();

    public static void add(Product product) {
        LineItem newItem = new LineItem(product);
        if (!lineItems.isEmpty()) {
            for (LineItem lineItem: lineItems) {
                if (product.getId() == lineItem.getProduct().getId()) {
                    lineItem.increaseQuantityAndSubtotal();
                    break;
                }
            }
            newItem.setId(lineItems.size() + 1);
            lineItems.add(newItem);
        } else {
            newItem.setId(1);
            lineItems.add(newItem);
        }

    }

    public static LineItem find(int id) {
        return lineItems.stream()
                .filter(t -> t.getProduct().getId() == id)
                .findFirst()
                .orElse(null);
    }

    public static void update(int id, int newQuantity) {     // needs product list handling in LineItem if we have different products in the same LineItem
        LineItem itemToUpdate = find(id);
        if (newQuantity == 0) {
            remove(id);
        } else {
            itemToUpdate.setQuantityAndUpdateSubtotal(newQuantity);
        }
    }

    public static void remove(int id) {
        lineItems.remove(find(id));
    }

    public static HashSet<LineItem> getAll() {
        return lineItems;
    }

    public static BigDecimal calculateTotalPrice() {
        return lineItems.stream()
                .map(LineItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Currency getDefaultCurrency() {
        if (lineItems.isEmpty()) return null;
        return lineItems.stream().findFirst().get().getDefaultCurrency();
    }

}
