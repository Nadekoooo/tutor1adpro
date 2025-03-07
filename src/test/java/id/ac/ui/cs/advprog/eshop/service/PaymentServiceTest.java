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

    // Helper method to create a dummy products list.
    private List<Product> createDummyProducts() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Dummy Product");
        product.setProductQuantity(1);
        products.add(product);
        return products;
    }

    @Test
    void testAddPaymentSuccessVoucher() {
        List<Product> products = createDummyProducts();
        Order order = new Order("order-1", products, 0L, "Author");

        doAnswer(invocation -> invocation.getArgument(0))
                .when(paymentRepository).save(any(Payment.class));

        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678"); // valid voucher code

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("SUCCESS", payment.getStatus(), "Payment status should be SUCCESS for valid voucher");
        assertEquals("SUCCESS", order.getStatus(), "Order status should be updated to SUCCESS");
    }

    @Test
    void testAddPaymentRejectedVoucher() {
        List<Product> products = createDummyProducts();
        Order order = new Order("order-2", products, 0L, "Author");

        doAnswer(invocation -> invocation.getArgument(0))
                .when(paymentRepository).save(any(Payment.class));

        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "INVALIDCODE123"); // invalid voucher

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("REJECTED", payment.getStatus(), "Payment status should be REJECTED for invalid voucher");
        assertEquals("FAILED", order.getStatus(), "Order status should be updated to FAILED");
    }

    @Test
    void testAddPaymentSuccessBankTransfer() {
        List<Product> products = createDummyProducts();
        Order order = new Order("order-3", products, 0L, "Author");

        doAnswer(invocation -> invocation.getArgument(0))
                .when(paymentRepository).save(any(Payment.class));

        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BankXYZ");
        data.put("referenceCode", "REF12345");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", data);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("SUCCESS", payment.getStatus(), "Valid bank transfer should yield SUCCESS status");
        assertEquals("SUCCESS", order.getStatus(), "Order status should be updated to SUCCESS");
    }

    @Test
    void testAddPaymentRejectedBankTransfer() {
        List<Product> products = createDummyProducts();
        Order order = new Order("order-4", products, 0L, "Author");

        doAnswer(invocation -> invocation.getArgument(0))
                .when(paymentRepository).save(any(Payment.class));

        Map<String, String> data = new HashMap<>();
        data.put("bankName", ""); // empty bankName causes rejection
        data.put("referenceCode", "REF12345");

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", data);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("REJECTED", payment.getStatus(), "Missing bankName should yield REJECTED status");
        assertEquals("FAILED", order.getStatus(), "Order status should be updated to FAILED");
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("pay-5", "VOUCHER", Map.of("voucherCode", "ESHOP1234ABC5678"));
        when(paymentRepository.findById("pay-5")).thenReturn(payment);

        Payment result = paymentService.getPayment("pay-5");
        assertNotNull(result);
        assertEquals("pay-5", result.getId(), "Retrieved payment should have the expected id");
    }

    @Test
    void testGetAllPayments() {
        Payment payment1 = new Payment("pay-6", "VOUCHER", Map.of("voucherCode", "ESHOP1234ABC5678"));
        Payment payment2 = new Payment("pay-7", "BANK_TRANSFER", Map.of("bankName", "BankXYZ", "referenceCode", "REF12345"));
        when(paymentRepository.findAll()).thenReturn(List.of(payment1, payment2));

        List<Payment> results = paymentService.getAllPayments();
        assertEquals(2, results.size(), "Expected to retrieve two payments");
    }

    @Test
    void testSetStatusUpdatesPayment() {
        Payment payment = new Payment("pay-8", "VOUCHER", Map.of("voucherCode", "ESHOP1234ABC5678"));
        doAnswer(invocation -> invocation.getArgument(0))
                .when(paymentRepository).save(any(Payment.class));

        Payment updatedPayment = paymentService.setStatus(payment, "REJECTED");
        assertEquals("REJECTED", updatedPayment.getStatus(), "Payment status should be updated to REJECTED");
    }
}