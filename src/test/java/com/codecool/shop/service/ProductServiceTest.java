package com.codecool.shop.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.memory.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class ProductServiceTest {

    private ProductDao productDaoMock = mock(ProductDaoMem.class);
    private ProductCategoryDao productCategoryDaoMock = mock(ProductCategoryDao.class);
    private SupplierDao supplierDaoMock = mock(SupplierDao.class);

    @Test
    void getAllSupplier_oneSupplier() {
        Supplier supplierMock = mock(Supplier.class);
        List<Supplier> suppliersT = new ArrayList<>();
        suppliersT.add(supplierMock);
        when(supplierDaoMock.getAll()).thenReturn(suppliersT);
        ProductService productServiceT = new ProductService(productDaoMock,productCategoryDaoMock, supplierDaoMock);
        assertEquals(suppliersT, productServiceT.getAllSupplier());
    }

    @Test
    void getAllSupplier_moreThanOneSuppliers() {
        Supplier supplierMock1 = mock(Supplier.class);
        Supplier supplierMock2 = mock(Supplier.class);
        Supplier supplierMock3 = mock(Supplier.class);
        List<Supplier> suppliersT = new ArrayList<>();
        suppliersT.add(supplierMock1);
        suppliersT.add(supplierMock2);
        suppliersT.add(supplierMock3);
        when(supplierDaoMock.getAll()).thenReturn(suppliersT);
        ProductService productServiceT = new ProductService(productDaoMock,productCategoryDaoMock, supplierDaoMock);
        assertEquals(suppliersT, productServiceT.getAllSupplier());
    }

    @Test
    void getAllSupplier_noSupplier() {
        List<Supplier> suppliersT = new ArrayList<>();
        when(supplierDaoMock.getAll()).thenReturn(suppliersT);
        ProductService productServiceT = new ProductService(productDaoMock,productCategoryDaoMock, supplierDaoMock);
        assertEquals(suppliersT, productServiceT.getAllSupplier());
    }

    @Test
    void getSupplier_nonExistingId_returnsNull() {  //
        int supplierId = 8;
        when(supplierDaoMock.find(supplierId)).thenReturn(null);
        ProductService productServiceT = new ProductService(productDaoMock,productCategoryDaoMock, supplierDaoMock);
        assertNull(productServiceT.getSupplier(supplierId));
    }

    @Test
    void getSupplier_existingId() {
        int supplierId = 2;
        Supplier supplierT = mock(Supplier.class);
        when(supplierDaoMock.find(supplierId)).thenReturn(supplierT);
        ProductService productServiceT = new ProductService(productDaoMock,productCategoryDaoMock, supplierDaoMock);
        assertEquals(supplierT, productServiceT.getSupplier(supplierId));
    }

    @Test
    void getSupplier_minusValueId_returnNull() {
        int supplierId = -8;
        when(supplierDaoMock.find(supplierId)).thenReturn(null);
        ProductService productServiceT = new ProductService(productDaoMock,productCategoryDaoMock, supplierDaoMock);
        assertNull(productServiceT.getSupplier(supplierId));
    }

    @Test
    void getProductsForSupplier_nonExistingId() {
        int supplierId = 8;
        List<Product> productsT = new ArrayList<>();
        when(supplierDaoMock.find(supplierId)).thenReturn(null);
        Supplier supplierT = null;
        when(productDaoMock.getBy(supplierT)).thenReturn(new ArrayList<>());
        ProductService productServiceT = new ProductService(productDaoMock, productCategoryDaoMock, supplierDaoMock);
        assertEquals(productsT, productServiceT.getProductsForSupplier(supplierId));
    }

    @Test
    void getProductsForSupplier_existingId() {
        int supplierId = 2;
        Supplier supplierMock = mock(Supplier.class);
        List<Product> productsT = new ArrayList<>();
        Product productM1 = mock(Product.class);
        Product productM2 = mock(Product.class);
        Product productM3 = mock(Product.class);
        productsT.add(productM1);
        productsT.add(productM2);
        productsT.add(productM3);
        when(supplierDaoMock.find(supplierId)).thenReturn(supplierMock);
        when(productDaoMock.getBy(supplierMock)).thenReturn(productsT);
        ProductService productServiceT = new ProductService(productDaoMock, productCategoryDaoMock, supplierDaoMock);
        assertEquals(productsT, productServiceT.getProductsForSupplier(supplierId));
    }

    @Test
    void getProductsForSupplier_minusValueId() {
        int supplierId = -8;
        List<Product> productsT = new ArrayList<>();
        when(supplierDaoMock.find(supplierId)).thenReturn(null);
        Supplier supplierT = null;
        when(productDaoMock.getBy(supplierT)).thenReturn(productsT);
        ProductService productServiceT = new ProductService(productDaoMock, productCategoryDaoMock, supplierDaoMock);
        assertEquals(productsT, productServiceT.getProductsForSupplier(supplierId));
    }
}
