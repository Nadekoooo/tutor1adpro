package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Builder
@Getter
public class Order {

    private String id;
    private List<Product> products;
    private Long orderTime;
    private String author;

    @Setter
    private String status;
    public Order(String id, List<Product> products, Long orderTime, String author) {
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        this.status = "WAITING_PAYMENT";
        if (products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be empty.");
        }
        this.products = products;
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
        this.id = id;
        this.orderTime = orderTime;
        this.author = author;
        String[] statuses = {"WAITING_PAYMENT", "FAILED", "SUCCESS", "CANCELLED"};
        if (Arrays.stream(statuses).noneMatch(item -> item.equals(status))) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = status;
        if (products.isEmpty()) {
            throw new IllegalArgumentException("Products cannot be empty.");
        }
        this.products = products;
    }

    public void setStatus(String status) {
        String[] statuses = {"WAITING_PAYMENT", "FAILED", "SUCCESS", "CANCELLED"};
        if (Arrays.stream(statuses).noneMatch(item -> item.equals(status))) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = status;
    }

}
