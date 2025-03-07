package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// OrderService Interface
public interface OrderService {
    Order createOrder(Order order);
    Order updateStatus(String orderId, String status);
    Order findById(String orderId);
    List<Order> findAllByAuthor(String author);
}


