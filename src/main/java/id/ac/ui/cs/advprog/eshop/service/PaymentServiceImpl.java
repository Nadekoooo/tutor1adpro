package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        // Create a new Payment object
        Payment payment = new Payment("pay-" + order.getId(), method, paymentData);

        // Save to repository
        paymentRepository.save(payment);

        // Update order status if needed
        if ("SUCCESS".equals(payment.getStatus())) {
            order.setStatus("SUCCESS");
        } else if ("REJECTED".equals(payment.getStatus())) {
            order.setStatus("FAILED");
        }
        return payment;
    }


    @Override
    public Payment setStatus(Payment payment, String status) {
        // Simple approach: if it's "SUCCESS", order => "SUCCESS", if "REJECTED", order => "FAILED"
        paymentDataReevaluation(payment, status);
        // or you can just do: payment.setStatus(status) if you had logic in Payment itself.

        // Re-save updated Payment
        paymentRepository.save(payment);
        return payment;
    }

    // (Optional) Helper method to re-check logic or forcibly set status
    private void paymentDataReevaluation(Payment payment, String newStatus) {
        payment.setStatus(newStatus);
        // If Payment references an Order, update order’s status too
        // E.g.:
        // if ("SUCCESS".equals(newStatus)) { payment.getOrder().setStatus("SUCCESS"); }
        // else if ("REJECTED".equals(newStatus)) { payment.getOrder().setStatus("FAILED"); }
    }


    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

}
