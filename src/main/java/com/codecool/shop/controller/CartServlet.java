package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
//import com.codecool.shop.dao.implementation.CartDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.CartUpdateResponse;
import com.codecool.shop.model.LineItem;
import com.codecool.shop.model.UpdateCartItem;
import com.google.gson.Gson;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/cart", "/api/cart"})
public class CartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Cart cart = Cart.getInstance();
        context.setVariable("products", cart.getAll());
        context.setVariable("total", cart.calculateTotalPrice());
        context.setVariable("currency", cart.getDefaultCurrency());
        engine.process("product/cart.html", context, resp.getWriter());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("itemId"));
        Cart cart = Cart.getInstance();
        cart.remove(id);
        PrintWriter response = resp.getWriter();
        response.println("success");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Gson gson = new Gson();
        UpdateCartItem updateCartItem = gson.fromJson(req.getReader(), UpdateCartItem.class);

        int id = updateCartItem.getItemId();
        int newQuantity = updateCartItem.getQuantity();
        Cart cart = Cart.getInstance();
        cart.update(id, newQuantity);

        LineItem item = cart.find(id);
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
