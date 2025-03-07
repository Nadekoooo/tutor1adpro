package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testCreatePaymentWithValidVoucherCode() {
        // Arrange
        Map<String, String> paymentData = new HashMap<>();
        // Updated voucher code: exactly 16 characters ("ESHOP1234ABC5678")
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        // Act
        Payment payment = new Payment("pay-1", "VOUCHER", paymentData);

        // Assert
        assertEquals("SUCCESS", payment.getStatus());
    }


    @Test
    void testCreatePaymentWithInvalidVoucherCode() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALID-CODE"); // Not 16 chars, not correct format

        Payment payment = new Payment("pay-2", "VOUCHER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithBankTransferValid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BankXYZ");
        paymentData.put("referenceCode", "ABC12345");

        Payment payment = new Payment("pay-3", "BANK_TRANSFER", paymentData);

        assertEquals("BANK_TRANSFER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentWithBankTransferInvalid() {
        // Missing bankName or referenceCode -> REJECTED
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", ""); // empty string
        paymentData.put("referenceCode", "ABC12345");

        Payment payment = new Payment("pay-4", "BANK_TRANSFER", paymentData);

        assertEquals("REJECTED", payment.getStatus());
    }
}
