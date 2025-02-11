package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("d09f046-9b1b-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap User");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProduct() {
        // Scenario: Edit Product (Positive Test)
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Edit product
        product.setProductName("Sampo Cap Baru");
        product.setProductQuantity(150);
        productRepository.update(product);

        // Verify the updated values
        Iterator<Product> productIterator = productRepository.findAll();
        Product updatedProduct = productIterator.next();
        assertEquals("Sampo Cap Baru", updatedProduct.getProductName());
        assertEquals(150, updatedProduct.getProductQuantity());
    }
    @Test
    void testEditProduct_NonExistent() {
        // Scenario: Try editing a product that does not exist (Negative Test)
        Product product = new Product();
        product.setProductId("nonexistent-id");
        product.setProductName("Nonexistent Product");
        product.setProductQuantity(50);

        productRepository.update(product); // Should not update any product

        // Verify that the product list is still empty (no product to update)
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProduct() {
        // Scenario: Delete Product (Positive Test)
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Delete product
        productRepository.deleteById(product.getProductId());

        // Verify that the product was deleted
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext()); // No products should be left
    }

    @Test
    void testDeleteProduct_NonExistent() {
        // Scenario: Try deleting a product that does not exist (Negative Test)
        Product product = new Product();
        product.setProductId("nonexistent-id");

        // Try deleting a product that doesn't exist
        productRepository.deleteById(product.getProductId());

        // Verify that the product list is still empty
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext()); // No products should be left
    }
}