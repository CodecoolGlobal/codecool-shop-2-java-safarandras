package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
//import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.memory.SupplierDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.service.ProductService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart/checkout"})
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //dynamic data for header menu
        Cart cart = Cart.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductService productService = new ProductService(productCategoryDataStore, supplierDataStore);
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("categories", productService.getAllProductCategories());
        context.setVariable("suppliers", productService.getAllSupplier());
        context.setVariable("showCart", false);

        context.setVariable("products", cart.getAllLineItem());
        context.setVariable("total", cart.calculateTotalPrice());
        context.setVariable("currency", cart.getDefaultCurrency());
        context.setVariable("numberOfProductsInCart", productService.getNumberOfProductsInCart(cart));
        engine.process("product/checkout.html", context, resp.getWriter());
    }
}


