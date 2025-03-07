package id.ac.ui.cs.advprog.eshop.repository;



import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {

    private List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        // Check if payment with same ID exists
        for (int i = 0; i < paymentData.size(); i++) {
            if (paymentData.get(i).getId().equals(payment.getId())) {
                // Update
                paymentData.set(i, payment);
                return payment;
            }
        }
        // If not found, create
        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String paymentId) {
        return paymentData.stream()
                .filter(p -> p.getId().equals(paymentId))
                .findFirst()
                .orElse(null);
    }

    public List<Payment> findAll() {
        return paymentData;
    }
}
