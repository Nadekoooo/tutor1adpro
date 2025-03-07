package id.ac.ui.cs.advprog.eshop.repository;
import enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
    }

    @Test
    void testSaveCreateNewPayment() {
        Payment payment = new Payment("pay-1", "VOUCHER",
                Map.of("voucherCode", "ESHOP1234ABCD5678"));
        paymentRepository.save(payment);

        Payment found = paymentRepository.findById("pay-1");
        assertNotNull(found);
        assertEquals("pay-1", found.getId());
    }

    @Test
    void testSaveUpdateExistingPayment() {
        Payment payment = new Payment("pay-2", "VOUCHER",
                Map.of("voucherCode", "ESHOP0000ABCD1111"));
        paymentRepository.save(payment);

        // Update method to BANK_TRANSFER
        Payment updatedPayment = new Payment("pay-2", "BANK_TRANSFER",
                Map.of("bankName", "BankABC", "referenceCode", "123456"));
        paymentRepository.save(updatedPayment);

        Payment found = paymentRepository.findById("pay-2");
        assertEquals("BANK_TRANSFER", found.getMethod());
        assertEquals("SUCCESS", found.getStatus());
    }

    @Test
    void testFindByIdNotFound() {
        Payment found = paymentRepository.findById("nope");
        assertNull(found);
    }
}
