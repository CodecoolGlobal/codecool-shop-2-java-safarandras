package com.codecool.shop.dao.implementation;

import com.codecool.shop.controller.ProductController;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;

public class CartDaoMem implements CartDao {

    HashSet<Cart> carts = new HashSet<>();
    private static CartDaoMem instance = null;
    private static final Logger logger = LoggerFactory.getLogger(CartDaoMem.class);

    /* A private Constructor prevents any other class from instantiating.
     */
    private CartDaoMem() {
    }

    public static CartDaoMem getInstance() {
        if (instance == null) {
            instance = new CartDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Cart cart) {
        logger.debug("Add Cart called");
        carts.add(cart);
    }

    @Override
    public Cart find(int cartId) {
        logger.debug("Find Cart called");
        return carts.stream()
                .filter(cart -> cartId == cart.getCartId())
                .findFirst()
                .orElse(null);
    }

    @Override
    public void remove(int cartId) {
        logger.debug("Remove Cart called");
        carts.removeIf(cart -> cartId == cart.getCartId());
    }

    @Override
    public HashSet<Cart> getAll() {
        logger.debug("Get all Cart called");
        return carts;
    }


}
