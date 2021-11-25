package com.codecool.shop.dao.memory;
        
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import java.util.HashSet;
import java.util.Set;

public class CartDaoMem implements CartDao {

    Set<Cart> carts = new HashSet<>();
    private static CartDaoMem instance = null;

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
        return carts.stream()
                .filter(cart -> cartId == cart.getCartId())
                .findFirst()
                .orElse(null);
    }

    @Override
    public void remove(int cartId) {
        carts.removeIf(cart -> cartId == cart.getCartId());
    }

    @Override
    public Set<Cart> getAll(int userId) {
        Set<Cart> cartsByUser = new HashSet<>();
        for (Cart cart : carts) {
            if (cart.getUserId() == userId) {
                cartsByUser.add(cart);
            }
        }
        return cartsByUser;
    }


}
