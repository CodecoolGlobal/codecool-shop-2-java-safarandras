package com.codecool.shop.service;

import com.codecool.shop.dao.Jdbc.ProductCategoryJdbc;
import com.codecool.shop.dao.Jdbc.ProductJdbc;
import com.codecool.shop.dao.Jdbc.SupplierJdbc;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Cart;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.List;

public class ProductService{
    private final ProductDao productDao;
    private final ProductCategoryDao productCategoryDao;
    private final SupplierDao supplierDao;

    public ProductService() throws SQLException {
        DataSource dataSource = connect();
        supplierDao = new SupplierJdbc(dataSource);
        productCategoryDao = new ProductCategoryJdbc(dataSource);
        productDao = new ProductJdbc(dataSource, productCategoryDao, supplierDao);
    }

    public ProductService(ProductDao productDao, ProductCategoryDao productCategoryDao, SupplierDao supplierDao) {
        this.productDao = productDao;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
    }

    public ProductService(ProductCategoryDao productCategoryDao, SupplierDao supplierDao) {
        this.productDao = null;
        this.productCategoryDao = productCategoryDao;
        this.supplierDao = supplierDao;
    }

    public ProductCategory getProductCategory(int categoryId){
        return productCategoryDao.find(categoryId);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName("codecoolshop");
        dataSource.setUser("safarandras");
        dataSource.setPassword("safarandras");

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }

    public List<Product> getProductsForCategory(int categoryId){
        var category = productCategoryDao.find(categoryId);
        return productDao.getBy(category);
    }

    public List<ProductCategory> getAllProductCategories(){
        return productCategoryDao.getAll();
    }

    public List<Supplier> getAllSupplier(){
        return supplierDao.getAll();
    }

    public Supplier getSupplier(int supplierId){
        return supplierDao.find(supplierId);
    }

    public List<Product> getProductsForSupplier(int supplierId){
        var supplier = supplierDao.find(supplierId);
        return productDao.getBy(supplier);
    }

    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    public Product getProduct(int id){
        return productDao.find(id);
    }

    public int getNumberOfProductsInCart(Cart cart){
        return cart.getNumberOfProductsInCart();
    }
}
