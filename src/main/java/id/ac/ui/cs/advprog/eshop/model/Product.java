// Product.java
package id.ac.ui.cs.advprog.eshop.model;

public class Product {
    private Long id;
    private String productName;
    private String productQuantity; // adjust type as needed

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductQuantity() { return productQuantity; }
    public void setProductQuantity(String productQuantity) { this.productQuantity = productQuantity; }
}
