package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.memory.CartDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.service.CartService;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.util.DaoSelector;
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

@WebServlet(urlPatterns = {"/cart/save"}, initParams =
@WebInitParam(name = "cartId", value = "0"))
public class SaveCartServlet extends HttpServlet {
    private CartService cartService;

    private static final Logger logger = LoggerFactory.getLogger(CheckoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("save cart call by USER");

        String cartId = req.getParameter("cartId");
        int cId = 0;
        if (cartId != null) {
            cId = Integer.parseInt(cartId);
        }

        //dynamic data for header menu
        cartService = DaoSelector.getCartService();
        cartService.saveCart(cId);

        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}
