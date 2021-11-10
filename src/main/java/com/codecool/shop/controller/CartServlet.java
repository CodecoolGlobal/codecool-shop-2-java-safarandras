package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
//import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "hackerNewsServlet", urlPatterns = {"/cart"}, loadOnStartup = 1)
public class CartServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("products", Cart.getAll());
        context.setVariable("total", Cart.calculateTotalPrice());
        context.setVariable("currency", Cart.getDefaultCurrency());
        engine.process("product/cart.html", context, resp.getWriter());
    }
}
