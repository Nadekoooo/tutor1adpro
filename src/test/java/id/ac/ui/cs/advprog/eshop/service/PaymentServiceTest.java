package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper method to create a dummy products list
    private List<Product> createDummyProducts() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("dummy-product");
        product.setProductName("Dummy Product");
        product.setProductQuantity(1);
        products.add(product);
        return products;
    }

    @Test
    void testAddPaymentSuccessVoucher() {
        List<Product> products = createDummyProducts();
        Order order = new Order("order-1", products, 0L, "Author");

        // Mock: return the passed Payment when saving
        doAnswer(invocation -> invocation.getArgument(0))
                .when(paymentRepository).save(any(Payment.class));

        Map<String, String> data = new HashMap<>();
        // Updated voucher code to 16 characters
        data.put("voucherCode", "ESHOP1234ABC5678"); // valid voucher code

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", order.getStatus()); // order updated
    }


    @Test
    void testAddPaymentRejectedVoucher() {
        List<Product> products = createDummyProducts();
        Order order = new Order("order-2", products, 0L, "Author");

        doAnswer(invocation -> invocation.getArgument(0))
                .when(paymentRepository).save(any(Payment.class));

        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "INVALID-CODE");
        Payment payment = paymentService.addPayment(order, "VOUCHER", data);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", order.getStatus()); // order updated
    }
    @Test
    void testGetPayment() {
        Payment payment = new Payment("pay-5", "VOUCHER", Map.of("voucherCode", "ESHOP1234ABC5678"));
        when(paymentRepository.findById("pay-5")).thenReturn(payment);

        Payment result = paymentService.getPayment("pay-5");
        assertNotNull(result);
        assertEquals("pay-5", result.getId());
    }

    @Test
    void testGetAllPayments() {
        Payment payment1 = new Payment("pay-6", "VOUCHER", Map.of("voucherCode", "ESHOP1234ABC5678"));
        Payment payment2 = new Payment("pay-7", "BANK_TRANSFER", Map.of("bankName", "BankXYZ", "referenceCode", "REF12345"));
        when(paymentRepository.findAll()).thenReturn(List.of(payment1, payment2));

        assertEquals(2, paymentService.getAllPayments().size());
    }

    @Test
    void testSetStatusUpdatesPayment() {
        // Create a dummy payment (assume its initial status is SUCCESS from valid voucher code)
        Payment payment = new Payment("pay-8", "VOUCHER", Map.of("voucherCode", "ESHOP1234ABC5678"));

        // For this test, assume the PaymentServiceImpl.setStatus method simply updates the payment's status.
        doAnswer(invocation -> invocation.getArgument(0))
                .when(paymentRepository).save(any(Payment.class));

        Payment updatedPayment = paymentService.setStatus(payment, "REJECTED");
        assertEquals("REJECTED", updatedPayment.getStatus());
        // If your setStatus method also updates the related Order, add similar assertions for the Order.
    }
    // Similar tests for BANK_TRANSFER, setStatus, getPayment, getAllPayments, etc.
}
