package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.Currency;

public class CartUpdateResponse {

    private int productId;
    private int quantity;
    private BigDecimal subtotal;
    private Currency defaultCurrency;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }
}
