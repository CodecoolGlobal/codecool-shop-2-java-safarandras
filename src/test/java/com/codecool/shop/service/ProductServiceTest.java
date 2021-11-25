package com.codecool.shop.service;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.memory.ProductDaoMem;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ProductService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServiceTest {

    ProductDao productDaoMock = mock(ProductDao.class);
    ProductCategoryDao productCategoryDaoMock = mock(ProductCategoryDao.class);
    SupplierDao supplierDaoMock = mock(SupplierDao.class);
    ProductService productService = new ProductService(productDaoMock,productCategoryDaoMock,supplierDaoMock);

    @Test
    void getProductsCategory_ReturnsSelectedProductCategory() throws IOException {
        int categoryId = 3;
        ProductCategory expected = new ProductCategory("Mug", "Household items", "Mugs with funny labels");
        when(productCategoryDaoMock.find(3)).thenReturn(expected);
        assertEquals(expected, productService.getProductCategory(categoryId));
    }

    @Test
    void getProductsForCategory_ReturnsSelectedProducts() throws IOException {
        int categoryId = 3;
        Supplier supplierMock = mock(Supplier.class);
        ProductCategory productCategoryMock = mock(ProductCategory.class);
        Product expectedProduct1 = new Product("Bigfoot later haters mug", new BigDecimal("14.99"), "USD", "Bigfoot has had enough of y'all", productCategoryMock, supplierMock, "bigfoot_later_haters.jpg");
        Product expectedProduct2 = new Product("Bigfoot face mug", new BigDecimal("16.99"), "USD", "Idk it's kinda cursed ngl", productCategoryMock, supplierMock, "bigfoot_face_mug.jpg");
        List<Product> expectedList = new LinkedList<>(Arrays.asList(expectedProduct1,expectedProduct2));

        ProductCategory expectedCategory = new ProductCategory("Mug", "Household items", "Mugs with funny labels");
        when(productCategoryDaoMock.find(3)).thenReturn(expectedCategory);
        when(productDaoMock.getBy(expectedCategory)).thenReturn(new LinkedList<>(Arrays.asList(expectedProduct1,expectedProduct2)));

        assertEquals(expectedList, productService.getProductsForCategory(categoryId));
    }

    @Test
    void getAllProductsCategory_ReturnsAllProductCategory() throws IOException {
        int categoryId = 3;
        ProductCategory expected1 = new ProductCategory("Mug", "Household items", "Mugs with funny labels");
        ProductCategory expected2 = new ProductCategory("T-shirt", "Clothing", "Funny T-shirts with our favourite real-life monsters on them");
        List<ProductCategory> expectedList = new LinkedList<>(Arrays.asList(expected1,expected2));

        when(productCategoryDaoMock.getAll()).thenReturn(expectedList);

        assertEquals(expectedList, productService.getAllProductCategories());
    }

    @Test
    void getAllSupplier_oneSupplier() {
        Supplier supplierMock = mock(Supplier.class);
        List<Supplier> suppliersT = new ArrayList<>();
        suppliersT.add(supplierMock);
        when(supplierDaoMock.getAll()).thenReturn(suppliersT);
        assertEquals(suppliersT, productService.getAllSupplier());
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
        assertEquals(suppliersT, productService.getAllSupplier());
    }

    @Test
    void getAllSupplier_noSupplier() {
        List<Supplier> suppliersT = new ArrayList<>();
        when(supplierDaoMock.getAll()).thenReturn(suppliersT);
        assertEquals(suppliersT, productService.getAllSupplier());
    }

    @Test
    void getSupplier_nonExistingId_returnsNull() {  //
        int supplierId = 8;
        when(supplierDaoMock.find(supplierId)).thenReturn(null);
        assertNull(productService.getSupplier(supplierId));
    }

    @Test
    void getSupplier_existingId() {
        int supplierId = 2;
        Supplier supplierT = mock(Supplier.class);
        when(supplierDaoMock.find(supplierId)).thenReturn(supplierT);
        assertEquals(supplierT, productService.getSupplier(supplierId));
    }

    @Test
    void getSupplier_minusValueId_returnNull() {
        int supplierId = -8;
        when(supplierDaoMock.find(supplierId)).thenReturn(null);
        assertNull(productService.getSupplier(supplierId));
    }

    @Test
    void getProductsForSupplier_nonExistingId() {
        int supplierId = 8;
        List<Product> productsT = new ArrayList<>();
        when(supplierDaoMock.find(supplierId)).thenReturn(null);
        Supplier supplierT = null;
        when(productDaoMock.getBy(supplierT)).thenReturn(new ArrayList<>());
        assertEquals(productsT, productService.getProductsForSupplier(supplierId));
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
        assertEquals(productsT, productService.getProductsForSupplier(supplierId));
    }

    @Test
    void getProductsForSupplier_minusValueId() {
        int supplierId = -8;
        List<Product> productsT = new ArrayList<>();
        when(supplierDaoMock.find(supplierId)).thenReturn(null);
        Supplier supplierT = null;
        when(productDaoMock.getBy(supplierT)).thenReturn(productsT);
        assertEquals(productsT, productService.getProductsForSupplier(supplierId));
    }

    @Test
    void getAllProducts_returnsAllProducts(){
        Supplier bigFoot = new Supplier("Bigfoot", "Everything we have on Bigfoot");
        ProductCategory mug = new ProductCategory("Mug", "Household items", "Mugs with funny labels");
        List<Product> products = new ArrayList<>();
        products.add(new Product("Bigfoot later haters mug", new BigDecimal("14.99"), "USD", "Bigfoot has had enough of y'all", mug, bigFoot, "bigfoot_later_haters.jpg"));
        products.add(new Product("Bigfoot face mug", new BigDecimal("16.99"), "USD", "Idk it's kinda cursed ngl", mug, bigFoot, "bigfoot_face_mug.jpg"));
        when(productDaoMock.getAll()).thenReturn(products);

        productService = new ProductService(productDaoMock, productCategoryDaoMock, supplierDaoMock);
        List<Product> result = productService.getAllProducts();

        assertEquals(products, result);
    }

    @Test
    void getProduct_returnsProduct(){
        Supplier bigFoot = new Supplier("Bigfoot", "Everything we have on Bigfoot");
        ProductCategory mug = new ProductCategory("Mug", "Household items", "Mugs with funny labels");
        Product bigFootMug = new Product("Bigfoot later haters mug", new BigDecimal("14.99"), "USD", "Bigfoot has had enough of y'all", mug, bigFoot, "bigfoot_later_haters.jpg");
        when(productDaoMock.find(bigFootMug.getId())).thenReturn(bigFootMug);

        productService = new ProductService(productDaoMock, productCategoryDaoMock, supplierDaoMock);
        Product result = productService.getProduct(bigFootMug.getId());

        assertEquals(bigFootMug, result);

    }
}
