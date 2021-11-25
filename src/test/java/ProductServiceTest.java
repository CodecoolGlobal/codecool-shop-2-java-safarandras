import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.service.ProductService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}
