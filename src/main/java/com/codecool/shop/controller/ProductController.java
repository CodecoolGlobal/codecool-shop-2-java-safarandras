package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cart cart = Cart.getInstance();

        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore, productCategoryDataStore, supplierDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("categories", productService.getAllProductCategories());
        context.setVariable("suppliers", productService.getAllSupplier());
        context.setVariable("showCart", true);
        context.setVariable("numberOfProductsInCart", productService.getNumberOfProductsInCart(cart));

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
        }

        engine.process("product/index.html", context, resp.getWriter());
    }

}
