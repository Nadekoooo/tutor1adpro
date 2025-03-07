package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void testCreatePaymentWithValidVoucherCode() {
        // Arrange: Use a 16-character voucher starting with ESHOP and containing exactly 8 digits
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        // Act
        Payment payment = new Payment("pay-1", "VOUCHER", paymentData);

        // Assert: Verify status and that voucher code validation worked as expected.
        assertEquals("SUCCESS", payment.getStatus(), "Expected valid voucher code to yield SUCCESS status");
        assertEquals("VOUCHER", payment.getMethod());
    }

    @Test
    void testCreatePaymentWithInvalidVoucherCode() {
        // Arrange: Invalid voucher code due to wrong length and pattern
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "INVALIDCODE"); // Not 16 characters, does not start with ESHOP, etc.

        // Act
        Payment payment = new Payment("pay-2", "VOUCHER", paymentData);

        // Assert: Expected rejection due to voucher validation failure.
        assertEquals("REJECTED", payment.getStatus(), "Expected invalid voucher code to yield REJECTED status");
    }

    @Test
    void testCreatePaymentWithValidBankTransfer() {
        // Arrange: Valid bank transfer requires non-empty bankName and referenceCode.
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", "BankXYZ");
        paymentData.put("referenceCode", "REF12345");

        // Act
        Payment payment = new Payment("pay-3", "BANK_TRANSFER", paymentData);

        // Assert: Verify a valid bank transfer is marked SUCCESS.
        assertEquals("SUCCESS", payment.getStatus(), "Expected valid bank transfer data to yield SUCCESS status");
        assertEquals("BANK_TRANSFER", payment.getMethod());
    }

    @Test
    void testCreatePaymentWithInvalidBankTransfer() {
        // Arrange: Missing one of the required fields for bank transfer
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", ""); // empty value
        paymentData.put("referenceCode", "REF12345");

        // Act
        Payment payment = new Payment("pay-4", "BANK_TRANSFER", paymentData);

        // Assert: Verify that missing data yields REJECTED status.
        assertEquals("REJECTED", payment.getStatus(), "Expected empty bankName to yield REJECTED status");
    }

    @Test
    void testCreatePaymentWithUnknownMethod() {
        // Arrange: Provide an unknown method to check fallback behavior.
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("someKey", "someValue");

        // Act
        Payment payment = new Payment("pay-5", "UNKNOWN", paymentData);

        // Assert: Unknown methods should result in REJECTED status.
        assertEquals("REJECTED", payment.getStatus(), "Expected unknown method to yield REJECTED status");
    }
}
