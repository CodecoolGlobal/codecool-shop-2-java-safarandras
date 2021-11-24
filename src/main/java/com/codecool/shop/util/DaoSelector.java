package com.codecool.shop.util;

import com.codecool.shop.dao.Jdbc.ProductCategoryJdbc;
import com.codecool.shop.dao.Jdbc.ProductJdbc;
import com.codecool.shop.dao.Jdbc.SupplierJdbc;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.memory.ProductDaoMem;
import com.codecool.shop.dao.memory.SupplierDaoMem;
import com.codecool.shop.service.ProductService;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DaoSelector {
    private static ProductCategoryDao ProductCategoryDao;
    private static ProductDao ProductDao;
    private static SupplierDao SupplierDao;

    public static ProductService getService(){
        if(true){
            try {
                DataSource dataSource = connect();
                ProductCategoryDao = new ProductCategoryJdbc(dataSource);
                SupplierDao = new SupplierJdbc(dataSource);
                ProductDao = new ProductJdbc(dataSource, ProductCategoryDao, SupplierDao);

            } catch (SQLException e) {
                System.err.println("Database connection unavailable!");
            }
        }
        else {
            ProductDao = ProductDaoMem.getInstance();
            ProductCategoryDao = ProductCategoryDaoMem.getInstance();
            SupplierDao = SupplierDaoMem.getInstance();
        }
        return new ProductService(ProductDao, ProductCategoryDao, SupplierDao);
    }

    public static DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setDatabaseName("codecoolshop");
        dataSource.setUser("safarandras");
        dataSource.setPassword("safarandras");

        System.out.println("Trying to connect...");
        dataSource.getConnection().close();
        System.out.println("Connection OK");

        return dataSource;
    }
}
