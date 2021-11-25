package com.codecool.shop.service;

import com.codecool.shop.dao.Jdbc.ProductCategoryJdbc;
import com.codecool.shop.dao.Jdbc.ProductJdbc;
import com.codecool.shop.dao.Jdbc.SupplierJdbc;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.*;
import com.codecool.shop.model.response.CartUpdateResponse;
import com.codecool.shop.model.response.DeleteItemResponse;
import com.codecool.shop.model.response.Response;
import com.google.gson.Gson;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ProductService {
    private final ProductDao productDao;
    private final ProductCategoryDao productCategoryDao;
    private final SupplierDao supplierDao;

    public ProductService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
    }

    public ProductCategory getProductCategory(int categoryId) {
        return productCategoryDao.find(categoryId);
    }

    public List<Product> getProductsForCategory(int categoryId) {
        var category = productCategoryDao.find(categoryId);
        return productDao.getBy(category);
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryDao.getAll();
    }

    public List<Supplier> getAllSupplier() {
        return supplierDao.getAll();
    }

    public Supplier getSupplier(int supplierId) {
        return supplierDao.find(supplierId);
    }

    public List<Product> getProductsForSupplier(int supplierId) {
        var supplier = supplierDao.find(supplierId);
        return productDao.getBy(supplier);
    }

    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    public Product getProduct(int id) {
        return productDao.find(id);
    }

    public int getNumberOfProductsInCart(Cart cart) {
        return cart.getNumberOfProductsInCart();
    }

    public DeleteItemResponse fillDeleteItemResponse(DeleteItemResponse deleteItemResponse, Cart cart, int itemId) {
        deleteItemResponse.setProductId(itemId);
        deleteItemResponse.setTotal(cart.calculateTotalPrice());
        deleteItemResponse.setDefaultCurrency(cart.getDefaultCurrency());
        return deleteItemResponse;
    }

    public String makeJsonStringFromResponse(Response response) {
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    public CartUpdateResponse fillCartUpdateResponse(CartUpdateResponse cartUpdateResponse, Cart cart, LineItem item) {
        cartUpdateResponse.setProductId(item.getProduct().getId());
        cartUpdateResponse.setQuantity(item.getQuantity());
        cartUpdateResponse.setSubtotal(item.getSubtotal());
        cartUpdateResponse.setTotal(cart.calculateTotalPrice());
        cartUpdateResponse.setDefaultCurrency(item.getDefaultCurrency());
        return cartUpdateResponse;
    }

}
