package com.codecool.shop.service;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.response.CartUpdateResponse;
import com.codecool.shop.model.response.DeleteItemResponse;
import com.codecool.shop.model.response.Response;
import com.google.gson.Gson;

import java.util.Set;

public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public Cart findCart(int cartId) {
        return cartDao.find(cartId);
    }

    public void addCart(Cart cart) {
        Cart foundCart = cartDao.find(cart.getCartId());
        if (foundCart == null) {
            cartDao.add(cart);
        } else {
            cartDao.update(cart);
        }
    }

    public void deleteItemFromCart(Cart cart, int itemId) {
        cart.remove(itemId);
        cartDao.update(cart);
    }

    public void updateCart(Cart cart, int itemId, int newQuantity) {
        cart.update(itemId, newQuantity);
        cartDao.update(cart);
    }

    public void saveCart(int cartId) {
        Cart cart = findCart(cartId);
        addCart(cart);
    }

    public void addProductToCart(int cartId, Product product) {
        Cart cart = cartDao.find(cartId);
        cart.add(product);
        cartDao.update(cart);
    }

    public int getNumberOfProductsInCart(Cart cart){
        if (cart == null) {
            return 0;
        }
        return cart.getNumberOfProductsInCart();
    }

    public Set<Cart> getAllCartsByUser(int userId) {
        return cartDao.getAll(userId);
    }

    public DeleteItemResponse fillDeleteItemResponse(DeleteItemResponse deleteItemResponse, Cart cart, int itemId) {
        deleteItemResponse.setProductId(itemId);
        deleteItemResponse.setTotal(cart.calculateTotalPrice());
        deleteItemResponse.setDefaultCurrency(cart.getDefaultCurrency());
        return deleteItemResponse;
    }

    public String makeJsonStringFromResponse(Response response) {
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    public CartUpdateResponse fillCartUpdateResponse(CartUpdateResponse cartUpdateResponse, Cart cart, LineItem item) {
        cartUpdateResponse.setProductId(item.getProduct().getId());
        cartUpdateResponse.setQuantity(item.getQuantity());
        cartUpdateResponse.setSubtotal(item.getSubtotal());
        cartUpdateResponse.setTotal(cart.calculateTotalPrice());
        cartUpdateResponse.setDefaultCurrency(item.getDefaultCurrency());
        return cartUpdateResponse;
    }

}
