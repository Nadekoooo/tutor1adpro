package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testSaveCreatesNewPayment() {
        Payment payment = new Payment("pay-1", "VOUCHER", Map.of("voucherCode", "ESHOP1234ABC5678"));
        Payment saved = paymentRepository.save(payment);

        assertNotNull(saved);
        assertEquals("pay-1", saved.getId());
        // Verify that the repository now contains the payment.
        Payment found = paymentRepository.findById("pay-1");
        assertNotNull(found);
        assertEquals("pay-1", found.getId());
    }

    @Test
    void testSaveUpdatesExistingPayment() {
        Payment payment = new Payment("pay-2", "VOUCHER", Map.of("voucherCode", "ESHOP1234ABC5678"));
        paymentRepository.save(payment);

        // Create a new payment instance with the same ID, but different method/data.
        Payment updatedPayment = new Payment("pay-2", "BANK_TRANSFER", Map.of("bankName", "BankABC", "referenceCode", "REF12345"));
        paymentRepository.save(updatedPayment);

        Payment found = paymentRepository.findById("pay-2");
        assertNotNull(found);
        assertEquals("BANK_TRANSFER", found.getMethod());
        // Depending on Payment logic, the updated payment status should reflect the new method.
        assertEquals("SUCCESS", found.getStatus());
    }

    @Test
    void testFindByIdNotFound() {
        Payment found = paymentRepository.findById("non-existent-id");
        assertNull(found, "Expected findById to return null for unknown id");
    }

    @Test
    void testFindAllReturnsAllPayments() {
        Payment payment1 = new Payment("pay-3", "VOUCHER", Map.of("voucherCode", "ESHOP1234ABC5678"));
        Payment payment2 = new Payment("pay-4", "BANK_TRANSFER", Map.of("bankName", "BankXYZ", "referenceCode", "REF12345"));
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        var allPayments = paymentRepository.findAll();
        assertEquals(2, allPayments.size(), "Expected repository to return two payments");
    }
}
