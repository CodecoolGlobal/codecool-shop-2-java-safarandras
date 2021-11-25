package com.codecool.shop.service;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.response.CartUpdateResponse;
import com.codecool.shop.model.response.DeleteItemResponse;
import com.codecool.shop.model.response.Response;
import com.google.gson.Gson;

public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public Cart findCart(int cartId) {
        return cartDao.find(cartId);
    }

    public void addCart(Cart cart) {
        cartDao.add(cart);
    }

    public void addProductToCart(int cartId, Product product) {
        Cart cart = cartDao.find(cartId);
        cart.add(product);
        cartDao.update(cart);
    }

    public int getNumberOfProductsInCart(Cart cart){
        return cart.getNumberOfProductsInCart();
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
