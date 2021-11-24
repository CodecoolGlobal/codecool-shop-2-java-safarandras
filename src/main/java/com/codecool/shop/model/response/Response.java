package com.codecool.shop.model.response;

import java.math.BigDecimal;
import java.util.Currency;

public abstract class Response {
    protected int productId;
    protected Currency defaultCurrency;
    protected BigDecimal total;

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
