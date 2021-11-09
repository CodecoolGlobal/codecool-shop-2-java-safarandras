package com.codecool.shop.model;

import java.math.BigDecimal;

public class LineItem extends BaseModel{
    private final Product product;
    private int quantity = 1;
    private final BigDecimal unitPrice;
    private BigDecimal subtotal;

    public LineItem(Product product) {
        super(product.getName(), product.getDescription());
        this.product = product;
        unitPrice = product.getDefaultPrice();
        subtotal = product.getDefaultPrice();
    }

    public void increaseQuantityAndSubtotal() {
        quantity++;
        subtotal = subtotal.add(product.getDefaultPrice());
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getQuantityAsString() {
        return String.valueOf(quantity);
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        subtotal = BigDecimal.valueOf(quantity).multiply(product.getDefaultPrice());
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public String getSubtotalAsString() {
        return String.valueOf(subtotal);
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getUnitPriceAsString() {
        return String.valueOf(unitPrice);
    }

}
