package id.ac.ui.cs.advprog.eshop.model;



import java.util.Map;

public class Payment {

    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;

        // Decide status based on method
        if ("VOUCHER".equalsIgnoreCase(method)) {
            handleVoucherLogic();
        } else if ("BANK_TRANSFER".equalsIgnoreCase(method)) {
            handleBankTransferLogic();
        } else {
            // For now, mark unknown method as REJECTED or handle differently
            this.status = "REJECTED";
        }
    }

    private void handleVoucherLogic() {
        String voucherCode = paymentData.get("voucherCode");
        if (isValidVoucherCode(voucherCode)) {
            this.status = "SUCCESS";
        } else {
            this.status = "REJECTED";
        }
    }

    private void handleBankTransferLogic() {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");
        if (bankName == null || bankName.isBlank() ||
                referenceCode == null || referenceCode.isBlank()) {
            this.status = "REJECTED";
        } else {
            this.status = "SUCCESS";
        }
    }

    private boolean isValidVoucherCode(String code) {
        if (code == null) return false;
        if (code.length() != 16) return false;
        if (!code.startsWith("ESHOP")) return false;

        // Count digits
        int digitCount = 0;
        for (char c : code.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        return (digitCount == 8);
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getId() {
        return this.id;
    }
    public String getStatus() {
        return this.status;
    }
    public String getMethod() {
        return this.method;
    }


}

