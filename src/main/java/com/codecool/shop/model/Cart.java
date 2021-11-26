package com.codecool.shop.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class Cart {

    private int cartId;
    private int userId;
    private Set<LineItem> lineItems;
    private static final Logger logger = LoggerFactory.getLogger(Cart.class);

    public Cart() {
        lineItems = new HashSet<>();
    }

    public void add(Product product) {
        LineItem newItem = new LineItem(product);
        if (!lineItems.isEmpty()) {
            for (LineItem lineItem: lineItems) {
                if (product.getId() == lineItem.getProduct().getId()) {
                    lineItem.increaseQuantityAndSubtotal();
                    logger.info("item count increased in cart");
                    return;
                }
            }
            newItem.setId(lineItems.size() + 1);
        } else {
            newItem.setId(1);
        }
        lineItems.add(newItem);
        logger.info("item added to cart");
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
        logger.info("item updated in cart");
    }

    public void remove(int id) {
        lineItems.remove(find(id));
        logger.info("item removed from cart");
    }

    public Set<LineItem> getAllLineItem() {
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

    @Override
    public String toString() {
        int count = 0;
        StringBuilder sb = new StringBuilder("Cart id: ")
                .append(cartId);
        if (lineItems.isEmpty()) {
            sb.append(", cart is empty.");
        } else {
            sb.append(", ")
                    .append("cart items: [");
            for (LineItem lineItem : lineItems) {
                count++;
                sb.append("product id: ")
                        .append(lineItem.getProduct().getId())
                        .append(", quantity: ")
                        .append(lineItem.getQuantity())
                        .append(", name: ")
                        .append(lineItem.getName());
                if (count != lineItems.size()) {
                    sb.append("; ");
                } else {
                    sb.append("]");
                }
            }
        }
        return sb.toString();
    }

    public Currency getDefaultCurrency() {
        if (lineItems.isEmpty()) return null;
        return lineItems.stream().findFirst().get().getDefaultCurrency();
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setLineItems(Set<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
