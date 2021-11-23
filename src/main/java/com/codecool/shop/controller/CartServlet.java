package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.codecool.shop.model.CartUpdateResponse;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.UpdateCartItem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/cart", "/api/cart"}, initParams =
@WebInitParam(name = "cartId", value = "0"))
public class CartServlet extends HttpServlet {

    CartDao cartDataStore = CartDaoMem.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cartId = req.getParameter("cartId");
        int id = 0;
        if (cartId != null) {
            id = Integer.parseInt(cartId);
        }

        //dynamic data for header menu
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductService productService = new ProductService(productCategoryDataStore, supplierDataStore);
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

        String cartId = req.getParameter("cartId");
        int cId = 0;
        if (cartId != null) {
            cId = Integer.parseInt(cartId);
        }

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore, productCategoryDataStore, supplierDataStore);

        String body = req.getReader().readLine();
        Gson gson = new Gson();
        HashMap<String,Integer> productIdMap = gson.fromJson(body,new TypeToken<HashMap<String,Integer>>(){}.getType());
        int productId = productIdMap.get("productId");
        Product product = productService.getProduct(productId);
        Cart cart = cartDataStore.find(cId);
        cart.add(product);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cartId = req.getParameter("cartId");
        int cId = 0;
        if (cartId != null) {
            cId = Integer.parseInt(cartId);
        }

        int itemId = Integer.parseInt(req.getParameter("itemId"));
        Cart cart = cartDataStore.find(cId);
        cart.remove(itemId);
        PrintWriter response = resp.getWriter();
        response.println("success");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cartId = req.getParameter("cartId");
        int cId = 0;
        if (cartId != null) {
            cId = Integer.parseInt(cartId);
        }

        Gson gson = new Gson();
        UpdateCartItem updateCartItem = gson.fromJson(req.getReader(), UpdateCartItem.class);

        int itemId = updateCartItem.getItemId();
        int newQuantity = updateCartItem.getQuantity();
        Cart cart = cartDataStore.find(cId);
        cart.update(itemId, newQuantity);

        LineItem item = cart.find(itemId);
        CartUpdateResponse cartUpdateResponse = new CartUpdateResponse();
        cartUpdateResponse.setProductId(item.getProduct().getId());
        cartUpdateResponse.setQuantity(item.getQuantity());
        cartUpdateResponse.setSubtotal(item.getSubtotal());
        cartUpdateResponse.setDefaultCurrency(item.getDefaultCurrency());
        String jsonString = gson.toJson(cartUpdateResponse);
        PrintWriter response = resp.getWriter();
        response.println(jsonString);

    }
}
