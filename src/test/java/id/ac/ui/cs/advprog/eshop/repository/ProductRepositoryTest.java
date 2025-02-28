package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // Optionally, reinitialize the repository if needed for isolation
        productRepository = new ProductRepository();
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
        // Create a product
        Product product = new Product();
        product.setProductId("1234");
        product.setProductName("Old Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        // Edit the product
        product.setProductName("New Name");
        product.setProductQuantity(20);
        Product editedProduct = productRepository.edit(product);

        // Verify changes
        assertNotNull(editedProduct);
        assertEquals("1234", editedProduct.getProductId());
        assertEquals("New Name", editedProduct.getProductName());
        assertEquals(20, editedProduct.getProductQuantity());
    }

    @Test
    void testEditWithTwoProducts() {
        // 1) Create two products
        Product p1 = new Product();
        p1.setProductId("p1");
        productRepository.create(p1);

        Product p2 = new Product();
        p2.setProductId("p2");
        productRepository.create(p2);

        // 2) Edit the second product so we skip over the first
        p2.setProductName("Updated P2");
        Product edited = productRepository.edit(p2);

        // 3) Verify the loop checks p1 (condition = false), then p2 (condition = true)
        assertNotNull(edited);
        assertEquals("p2", edited.getProductId());
        assertEquals("Updated P2", edited.getProductName());

        // Also verify p1 remains unchanged
        Product stillP1 = productRepository.findById("p1");
        assertEquals("p1", stillP1.getProductId());
    }


    @Test
    void testEditProduct_NonExistent() {
        // Scenario: Try editing a product that does not exist (Negative Test)
        Product product = new Product();
        product.setProductId("nonexistent-id");
        product.setProductName("Nonexistent Product");
        product.setProductQuantity(50);

        // Update should return null because product is not found
        Product result = productRepository.update(product);
        assertNull(result);

        // Verify that the repository remains empty
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
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProduct_NonExistent() {
        // Scenario: Try deleting a product that does not exist (Negative Test)
        productRepository.deleteById("nonexistent-id");

        // Verify that the repository remains empty
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindById_NegativeCase() {
        // Create a product with a known id.
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Try to find a product with a non-matching id.
        Product result = productRepository.findById("2");
        assertNull(result, "Expected findById to return null when no product with the given id exists");
    }
}
