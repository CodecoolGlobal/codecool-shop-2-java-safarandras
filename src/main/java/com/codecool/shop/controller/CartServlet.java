package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.memory.CartDaoMem;
import com.codecool.shop.dao.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.memory.ProductDaoMem;
import com.codecool.shop.dao.memory.SupplierDaoMem;
import com.codecool.shop.model.*;
import com.codecool.shop.model.response.DeleteItemResponse;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.util.DaoSelector;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.codecool.shop.model.response.CartUpdateResponse;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.UpdateCartItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/cart", "/api/cart"}, initParams =
@WebInitParam(name = "cartId", value = "0"))
public class CartServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CartServlet.class);
    CartDao cartDataStore = CartDaoMem.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        logger.info("shopping cart call by USER");
        String cartId = req.getParameter("cartId");
        int id = 0;
        if (cartId != null) {
            id = Integer.parseInt(cartId);
        }

        //dynamic data for header menu
        ProductService productService = DaoSelector.getService();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("categories", productService.getAllProductCategories());
        context.setVariable("suppliers", productService.getAllSupplier());
        context.setVariable("showCart", false);

        Cart cart = cartDataStore.find(id);
        context.setVariable("products", cart.getAllLineItem());
        context.setVariable("total", cart.calculateTotalPrice());
        context.setVariable("currency", cart.getDefaultCurrency());
        engine.process("product/cart.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("add element call by USER");

        String cartId = req.getParameter("cartId");
        int cId = 0;
        if (cartId != null) {
            cId = Integer.parseInt(cartId);
        }

        ProductService productService = DaoSelector.getService();
        String body = req.getReader().readLine();
        Gson gson = new Gson();
        HashMap<String,Integer> productIdMap = gson.fromJson(body,new TypeToken<HashMap<String,Integer>>(){}.getType());
        int productId = productIdMap.get("productId");
        Product product = productService.getProduct(productId);
        Cart cart = cartDataStore.find(cId);
        cart.add(product);

        logger.info("Shopping cart current state: {}", cart.toString());

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("element delete call by USER");

        int cId = 0;    // put it into payload/session

        int itemId = Integer.parseInt(req.getParameter("itemId"));
        Cart cart = cartDataStore.find(cId);
        cart.remove(itemId);
        ProductService productService = DaoSelector.getService();
        DeleteItemResponse deleteItemResponse = new DeleteItemResponse();
        deleteItemResponse = productService.fillDeleteItemResponse(deleteItemResponse, cart, itemId);
        String jsonString = productService.makeJsonStringFromResponse(deleteItemResponse);
        PrintWriter response = resp.getWriter();
        response.println(jsonString);

        logger.info("Shopping cart current state: {}", cart.toString());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("element count modification call by USER");

        int cId = 0;    // put it into payload/session

        Gson gson = new Gson();
        UpdateCartItem updateCartItem = gson.fromJson(req.getReader(), UpdateCartItem.class);

        int itemId = updateCartItem.getItemId();
        int newQuantity = updateCartItem.getQuantity();
        Cart cart = cartDataStore.find(cId);
        cart.update(itemId, newQuantity);
        ProductService productService = DaoSelector.getService();
        LineItem item = cart.find(itemId);
        CartUpdateResponse cartUpdateResponse = new CartUpdateResponse();
        cartUpdateResponse = productService.fillCartUpdateResponse(cartUpdateResponse, cart, item);
        String jsonString = productService.makeJsonStringFromResponse(cartUpdateResponse);
        PrintWriter response = resp.getWriter();
        response.println(jsonString);

        logger.info("Shopping cart current state: {}", cart.toString());

    }
}
