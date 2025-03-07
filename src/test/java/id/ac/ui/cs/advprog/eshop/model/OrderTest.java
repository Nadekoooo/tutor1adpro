package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private List<Product> products;

    @BeforeEach
    void setUp() {
        this.products = new ArrayList<>();

        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-4db9-8860-71afaf63bd6c");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductId("a2c62328-63f7-46e4-83c7-f32db6201555");
        product2.setProductName("Sabun Cap Uyee");
        product2.setProductQuantity(1);

        this.products.add(product1);
        this.products.add(product2);
    }

    // 4. Create unhappy path test: Test to create the order with empty products.
    @Test
    void testCreateOrderEmptyProduct() {
        this.products.clear();

        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order(
                    "13652556-012a-4c87-b546-54eb1396d79b",
                    this.products,
                    1708560000L,
                    "Safira Sudrajat"
            );
        });
    }

    @Test
    void testCreate0rderEmptyProduct() {
        this.products.clear();
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                    this.products, 1708560000L, "Safira Sudrajat");
        });
    }

    // 5. Create a happy path test: Test to create the order with no status defined.
    @Test
    void testCreateOrderDefaultStatus() {
        Order order = new Order(
                "13652556-012a-4c87-b546-54eb1396d79b",
                this.products,
                1708560000L,
                "Safira Sudrajat"
        );

        assertEquals(2, order.getProducts().size());
        assertEquals("Sampo Cap Bambang", order.getProducts().get(0).getProductName());
        assertEquals("Sabun Cap Uyee", order.getProducts().get(1).getProductName());
        assertEquals("13652556-012a-4c87-b546-54eb1396d79b", order.getId());
        assertEquals(1708560000L, order.getOrderTime());
        assertEquals("Safira Sudrajat", order.getAuthor());
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    // 6. Create a happy path test: Test to create the order status of "SUCCESS".
    @Test
    void testCreateOrderSuccessStatus() {
        Order order = new Order(
                "13652556-012a-4c87-b546-54eb1396d79b",
                this.products,
                1708560000L,
                "Safira Sudrajat",
                "SUCCESS"
        );

        assertEquals("SUCCESS", order.getStatus());
    }

    // 7. Create an unhappy path test: Test to create the order with invalid status.
    @Test
    void testCreateOrderInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            Order order = new Order(
                    "13652556-012a-4c87-b546-54eb1396d79b",
                    this.products,
                    1708560000L,
                    "Safira Sudrajat",
                    "MEOW"
            );
        });
    }

    // 8. Create a happy path test: Test to edit the order with one correct status.
    @Test
    void testSetStatusToCancelled() {
        Order order = new Order(
                "13652556-012a-4c87-b546-54eb1396d79b",
                this.products,
                1708560000L,
                "Safira Sudrajat"
        );

        order.setStatus("CANCELLED");
        assertEquals("CANCELLED", order.getStatus());
    }

    // 9. Create an unhappy path test: Test to edit the order with invalid status.
    @Test
    void testSetStatusToInvalidStatus() {
        Order order = new Order(
                "13652556-012a-4c87-b546-54eb1396d79b",
                this.products,
                1708560000L,
                "Safira Sudrajat"
        );

        assertThrows(IllegalArgumentException.class, () -> order.setStatus("MEOW"));
    }



}