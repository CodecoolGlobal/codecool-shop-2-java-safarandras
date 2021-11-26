package com.codecool.shop.controller;

import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.memory.CartDaoMem;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.service.CartService;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.util.DaoSelector;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(urlPatterns = {"/"}, initParams =
@WebInitParam(name = "cartId", value = "0"))
    public class ProductController extends HttpServlet {
    private ProductService productService;
    private CartService cartService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cartId = req.getParameter("cartId");
        int cId = 0;
        if (cartId != null) {
            cId = Integer.parseInt(cartId);
        }
        productService = DaoSelector.getService();
        cartService = DaoSelector.getCartService();
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("categories", productService.getAllProductCategories());
        context.setVariable("suppliers", productService.getAllSupplier());
        context.setVariable("showCart", true);
        context.setVariable("numberOfProductsInCart", cartService.getNumberOfProductsInCart(cartService.findCart(cId)));

        if (req.getParameter("categoryId") != null && Integer.parseInt(req.getParameter("categoryId")) > 0
                && Integer.parseInt(req.getParameter("categoryId")) <= productService.getAllProductCategories().size()) {
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            context.setVariable("category", productService.getProductCategory(categoryId));
            context.setVariable("products", productService.getProductsForCategory(categoryId));
        } else if (req.getParameter("supplierId") != null && Integer.parseInt(req.getParameter("supplierId")) > 0
                && Integer.parseInt(req.getParameter("supplierId")) <= productService.getAllSupplier().size()){
            int supplierId = Integer.parseInt(req.getParameter("supplierId"));
            context.setVariable("category", productService.getSupplier(supplierId));
            context.setVariable("products", productService.getProductsForSupplier(supplierId));
        }else{
            context.setVariable("category", new ProductCategory("All Products", "", ""));
            context.setVariable("products", productService.getAllProducts());
            logger.info("not parameterized main page call by USER");
        }

        engine.process("product/index.html", context, resp.getWriter());
    }

}
