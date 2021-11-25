package com.codecool.shop.dao;

import com.codecool.shop.model.Cart;

import java.util.Set;

public interface CartDao {

    void add(Cart cart);
    void update(Cart cart);
    Cart find(int cartId);
    void remove(int cartId);


    Set<Cart> getAll(int userId);

}
