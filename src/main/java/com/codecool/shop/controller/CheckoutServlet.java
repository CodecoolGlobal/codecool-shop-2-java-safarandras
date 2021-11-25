package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.memory.CartDaoMem;
import com.codecool.shop.dao.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.memory.SupplierDaoMem;
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
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/cart/checkout"}, initParams =
@WebInitParam(name = "cartId", value = "0"))
public class CheckoutServlet extends HttpServlet {
    private ProductService productService;
    private CartService cartService = new CartService(CartDaoMem.getInstance());

    private static final Logger logger = LoggerFactory.getLogger(CheckoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("checkout page call by USER");

        String cartId = req.getParameter("cartId");
        int cId = 0;
        if (cartId != null) {
            cId = Integer.parseInt(cartId);
        }

        //dynamic data for header menu
        ProductService productService = DaoSelector.getService();
        Cart cart = cartService.findCart(cId);
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("categories", productService.getAllProductCategories());
        context.setVariable("suppliers", productService.getAllSupplier());
        context.setVariable("showCart", false);

        context.setVariable("products", cart.getAllLineItem());
        context.setVariable("total", cart.calculateTotalPrice());
        context.setVariable("currency", cart.getDefaultCurrency());
        context.setVariable("numberOfProductsInCart", cartService.getNumberOfProductsInCart(cart));
        engine.process("product/checkout.html", context, resp.getWriter());
    }
}


