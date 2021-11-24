package com.codecool.shop.model.response;

import java.math.BigDecimal;
import java.util.Currency;

public class CartUpdateResponse extends Response {

    private int quantity;
    private BigDecimal subtotal;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

}
