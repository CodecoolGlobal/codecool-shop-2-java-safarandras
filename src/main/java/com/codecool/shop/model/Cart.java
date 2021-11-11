package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.*;

public class Cart {

    private HashSet<LineItem> lineItems;
    private static Cart instance;

    private Cart() {
        lineItems = new HashSet<>();
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    public void add(Product product) {
        LineItem newItem = new LineItem(product);
        if (!lineItems.isEmpty()) {
            for (LineItem lineItem: lineItems) {
                if (product.getId() == lineItem.getProduct().getId()) {
                    lineItem.increaseQuantityAndSubtotal();
                    return;
                }
            }
            newItem.setId(lineItems.size() + 1);
            lineItems.add(newItem);
        } else {
            newItem.setId(1);
            lineItems.add(newItem);
        }

    }

    public LineItem find(int id) {
        return lineItems.stream()
                .filter(t -> t.getProduct().getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void update(int id, int newQuantity) {     // needs product list handling in LineItem if we have different products in the same LineItem
        LineItem itemToUpdate = find(id);
        if (newQuantity == 0) {
            remove(id);
        } else {
            itemToUpdate.setQuantityAndUpdateSubtotal(newQuantity);
        }
    }

    public void remove(int id) {
        System.out.println(find(id).toString());
        lineItems.remove(find(id));
    }

    public HashSet<LineItem> getAllLineItem() {
        return lineItems;
    }

    public int getNumberOfProductsInCart() {
        return lineItems.stream()
                .mapToInt(LineItem::getQuantity)
                .sum();
    }

    public BigDecimal calculateTotalPrice() {
        return lineItems.stream()
                .map(LineItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Currency getDefaultCurrency() {
        if (lineItems.isEmpty()) return null;
        return lineItems.stream().findFirst().get().getDefaultCurrency();
    }

}
