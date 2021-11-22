package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;

import java.util.List;

//public class CartDaoMem implements CartDao {
//
//    Cart cart = new Cart();
//    private static CartDaoMem instance = null;
//
//    /* A private Constructor prevents any other class from instantiating.
//     */
//    private CartDaoMem() {
//    }
//
//    public static CartDaoMem getInstance() {
//        if (instance == null) {
//            instance = new CartDaoMem();
//        }
//        return instance;
//    }
//
//    @Override
//    public void add(Product product) {
//        cart.add(product);
//    }
//
//    @Override
//    public Product find(int id) {
//        return cart.find(id);
//    }
//
//    @Override
//    public void remove(int id) {
//        cart.remove(id);
//    }
//
//    @Override
//    public List<Product> getAll() {
//        return cart.getItems();
//    }
//
//
//}
