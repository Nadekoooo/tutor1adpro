package id.ac.ui.cs.advprog.eshop.service;


import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.HashMap;
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

    @Test
    void testAddPaymentSuccessVoucher() {
        Order order = new Order("order-1", /* products */ null, 0L, "Author");
        // Mock is not strictly needed for success path, but let's do it:
        doAnswer(invocation -> invocation.getArgument(0))
                .when(paymentRepository).save(any(Payment.class));

        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABCD5678"); // valid

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", order.getStatus()); // order updated
    }

    @Test
    void testAddPaymentRejectedVoucher() {
        Order order = new Order("order-2", null, 0L, "Author");
        doAnswer(invocation -> invocation.getArgument(0))
                .when(paymentRepository).save(any(Payment.class));

        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "INVALID-CODE");
        Payment payment = paymentService.addPayment(order, "VOUCHER", data);

        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", order.getStatus()); // order updated
    }

    // Similar tests for BANK_TRANSFER, setStatus, getPayment, getAllPayments, etc.
}
