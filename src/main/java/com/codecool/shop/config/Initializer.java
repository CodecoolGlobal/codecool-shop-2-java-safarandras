package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.math.BigDecimal;

@WebListener
public class Initializer implements ServletContextListener {
    private final ProductDao productDataStore = ProductDaoMem.getInstance();
    private final ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
    private final SupplierDao supplierDataStore = SupplierDaoMem.getInstance();

    private Supplier addSupplier(String name, String description){
        Supplier supplier = new Supplier(name, description);
        supplierDataStore.add(supplier);
        return supplier;
    }

    private ProductCategory addProductCategory(String name, String department, String description){
        ProductCategory productCategory = new ProductCategory(name, department, description);
        productCategoryDataStore.add(productCategory);
        return productCategory;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //setting up a new supplier
        Supplier amazon = addSupplier("Amazon", "Digital content and services");
        Supplier lenovo = addSupplier("Lenovo", "Computers");
        Supplier samsung = addSupplier("Samsung", "Smart phones and other electronics");


        //setting up a new product category
        ProductCategory tablet = addProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory smartPhone = addProductCategory("Smart Phone", "Hardware", "A smartphone is a portable device that combines mobile telephone and computing functions into one unit. They are distinguished from feature phones by their stronger hardware capabilities and extensive mobile operating systems, which facilitate wider software, internet (including web browsing over mobile broadband), and multimedia functionality (including music, video, cameras, and gaming), alongside core phone functions such as voice calls and text messaging.");


        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", new BigDecimal("49.9"), "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", new BigDecimal("479"), "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", new BigDecimal("89"), "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Samsung Galaxy A32 5G", new BigDecimal("204.99"), "USD", "The Samsung Galaxy A32 5G's excellent battery life, solid network connectivity, and impressive design are a winning combination for budget-phone shoppers.", smartPhone, samsung));

    }
}
