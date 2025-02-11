// Product.java
package id.ac.ui.cs.advprog.eshop.model;

public class Product {
    private Long id;
    private String productName;
    private int productQuantity; // adjust type as needed

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getProductQuantity() { return productQuantity; }
    public void setProductQuantity(int productQuantity) { this.productQuantity = productQuantity; }
}
