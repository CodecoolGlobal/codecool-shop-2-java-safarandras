package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
//import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.UpdateCartItem;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart", "/api/cart"})
public class CartServlet extends HttpServlet {
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

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("itemId"));
        Cart.remove(id);
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        UpdateCartItem updateCartItem = gson.fromJson(req.getReader(), UpdateCartItem.class);

        int id = updateCartItem.getItemId();
        int newQuantity = updateCartItem.getQuantity();
        Cart.update(id, newQuantity);
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
