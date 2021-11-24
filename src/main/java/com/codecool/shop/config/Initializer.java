package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.memory.ProductCategoryDaoMem;
import com.codecool.shop.dao.memory.ProductDaoMem;
import com.codecool.shop.dao.memory.SupplierDaoMem;
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
        Supplier bigFoot = addSupplier("Bigfoot", "Everything we have on Bigfoot");
        Supplier nessie = addSupplier("Nessie", "Everything we have on the Loch Ness monster");
        Supplier mothMan = addSupplier("Mothman", "Everything we have on the Mothman");
        Supplier alien = addSupplier("Alien", "Everything we have on our extraterrestrial friends");

        //setting up a new product category
        ProductCategory misc = addProductCategory("Miscellaneous", "Household items", "A kitchen utensil is a small hand held tool used for food preparation.");
        ProductCategory tShirt = addProductCategory("T-shirt", "Clothing", "Funny T-shirts with our favourite real-life monsters on them");
        ProductCategory mug = addProductCategory("Mug", "Household items", "Mugs with funny labels");

        //setting up products and printing it
        productDataStore.add(new Product(
                "Nessie NASA logo tee",
                new BigDecimal("24.99"),
                "USD",
                "Nessie goes to the moon", tShirt, nessie, "nessie_nasa_logo.png"));
        productDataStore.add(new Product(
                "Bigfoot I don't believe in humans tee",
                new BigDecimal("24.99"),
                "USD",
                "Bigfoot doesn't believe in humans",
                tShirt, bigFoot, "bigfoot_dont_believe_in_humans.jpg"));
        productDataStore.add(new Product(
                "Bigfoot saw me tee",
                new BigDecimal("24.99"),
                "USD",
                "Bigfoot is always looking!",
                tShirt, bigFoot, "bigfoot_saw_me.jpeg"));
        productDataStore.add(new Product(
                "Notorious B.I.G foot tee",
                new BigDecimal("24.99"),
                "USD",
                "",
                tShirt, bigFoot, "bigfoot_notorious_big.jpg"));
        productDataStore.add(new Product(
                "Bigfoot abducted tee",
                new BigDecimal("24.99"),
                "USD",
                "What is this, a crossover episode?",
                tShirt, bigFoot, "bigfoot_ufo.jpg"));
        productDataStore.add(new Product(
                "Bigfoot hanging with his buddies",
                new BigDecimal("24.99"),
                "USD",
                "Another crossover episode??",
                tShirt, bigFoot, "bigfoot_ufo_nessie.jpeg"));
        productDataStore.add(new Product(
                "Bigfoot hide & seek champion tee",
                new BigDecimal("24.99"),
                "USD",
                "He do be good",
                tShirt, bigFoot, "bigfoot_hidenseek_champion.jpeg"));
        productDataStore.add(new Product(
                "Bigfoot social distancing world champion tee",
                new BigDecimal("24.99"),
                "USD",
                "I wonder if he's vaccinated",
                tShirt, bigFoot, "bigfoot_social_distancing_champion.jpg"));
        productDataStore.add(new Product(
                "Bigfoot later haters mug",
                new BigDecimal("14.99"),
                "USD",
                "Bigfoot has had enough of y'all",
                mug, bigFoot, "bigfoot_later_haters.jpg"));
        productDataStore.add(new Product(
                "Bigfoot face mug",
                new BigDecimal("16.99"),
                "USD",
                "Idk it's kinda cursed ngl",
                mug, bigFoot, "bigfoot_face_mug.jpg"));
        productDataStore.add(new Product(
                "The Man, the Moth, the Legend tee",
                new BigDecimal("24.99"),
                "USD",
                "",
                tShirt, mothMan, "mothman_man_moth_legend.jpeg"));
        productDataStore.add(new Product(
                "Alien get in loser tee",
                new BigDecimal("24.99"),
                "USD",
                "Get in loser, we're going shopping",
                tShirt, alien, "alien_get_in_loser.jpeg"));
        productDataStore.add(new Product(
                "Area 51 raid tee",
                new BigDecimal("24.99"),
                "USD",
                "Remember this meme?",
                tShirt, alien, "alien_area_51_raid.jpg"));
        productDataStore.add(new Product(
                "Alien head mug",
                new BigDecimal("16.99"),
                "USD",
                "A bit less cursed than the bigfoot one",
                mug, alien, "alien_head.jpeg"));
        productDataStore.add(new Product(
                "UFO yeet mug",
                new BigDecimal("14.99"),
                "USD",
                "YEET!",
                mug, alien, "alien_yeet.jpg"));
        productDataStore.add(new Product(
                "Go camping they said mug",
                new BigDecimal("14.99"),
                "USD",
                "Wrong place, wrong time",
                mug, alien, "alien_go_camping.jpg"));
        productDataStore.add(new Product(
                "Loch Ness monster pasta spoon",
                new BigDecimal("16.99"),
                "USD",
                "A followup in the Nessie line of kitchen utensils, a nessie shaped pasta spoon.",
                misc, nessie, "nessie_pasta_spoon.jpg"));
        productDataStore.add(new Product(
                "Loch Ness monster ladle",
                new BigDecimal("14.99"),
                "USD",
                "A soup ladle made in the shape of our beloved Nessie.",
                misc, nessie, "nessie_ladle.jpg"));
        productDataStore.add(new Product(
                "Believe in yourself tee",
                new BigDecimal("24.99"),
                "USD",
                "Nessie believes in you!", tShirt, nessie, "nessie_believe_in_yourself.png"));
    }
}
