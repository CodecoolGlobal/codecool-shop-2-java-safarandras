package com.codecool.shop.dao.memory;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codecool.shop.model.Product;

import java.util.HashSet;
import java.util.Set;

public class CartDaoMem implements CartDao {

    Set<Cart> carts = new HashSet<>();
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
    public void update(Cart cart) {
        Cart cartToUpdate = null;
        for (Cart cart1 : carts) {
            if (cart1.getCartId() == cart.getCartId()) {
                cartToUpdate = cart1;
            }
        }
        carts.remove(cartToUpdate);
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
    public Set<Cart> getAll(int userId) {
        logger.debug("Get all Cart called");
        Set<Cart> cartsByUser = new HashSet<>();
        for (Cart cart : carts) {
            if (cart.getUserId() == userId) {
                cartsByUser.add(cart);
            }
        }
        return cartsByUser;
    }


}
