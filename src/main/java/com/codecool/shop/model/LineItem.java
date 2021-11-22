package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;

public class LineItem extends BaseModel{
    private HashSet<Product> products = new HashSet<>();
    private Product product;
    private int quantity = 1;
    private final Currency defaultCurrency;
    private final BigDecimal unitPrice;
    private BigDecimal subtotal;

    public LineItem(Product product) {
        super(product.getName(), product.getDescription());
        this.product = product;
        add(product);
        defaultCurrency = product.getDefaultCurrency();
        unitPrice = product.getDefaultPrice();
        subtotal = product.getDefaultPrice();
    }

    public void increaseQuantityAndSubtotal() {
        quantity++;
        subtotal = subtotal.add(product.getDefaultPrice());
    }

    public void decreaseQuantityAndSubtotal() {
        if (quantity > 0) {
            quantity--;
            subtotal = subtotal.subtract(product.getDefaultPrice());
        }
    }

    public boolean isquantityZero() {
        return quantity == 0;
    }

    public boolean isLastProduct() {
        return quantity == 1;
    }

    public void add(Product product) {
        products.add(product);
    }

    public Product getProductById(int id) {
        for (Product product: products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantityAndUpdateSubtotal(int quantity) {
        this.quantity = quantity;
        subtotal = BigDecimal.valueOf(quantity).multiply(product.getDefaultPrice());
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Product getProduct() {
        return product;
    }

    public HashSet<Product> getProducts() {
        return products;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }
}
