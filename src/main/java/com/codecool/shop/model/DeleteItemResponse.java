package com.codecool.shop.model;

import java.math.BigDecimal;
import java.util.Currency;

public class DeleteItemResponse {

    private int productId;
    private Currency defaultCurrency;
    private BigDecimal total;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
