package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {

    private List<Product> productData = new ArrayList<>();
    private long nextId = 1;

    public Product create(Product product) {
        // Assign productId if not already set
        if (product.getProductId() == null) {
            product.setProductId(String.valueOf(nextId++));
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        for (Product p : productData) {
            if (p.getProductId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public Product update(Product product) {
        Product existing = findById(product.getProductId());
        if (existing != null) {
            existing.setProductName(product.getProductName());
            existing.setProductQuantity(product.getProductQuantity());
        }
        return existing;
    }

    public void deleteById(String id) {
        Product product = findById(id);
        if (product != null) {
            productData.remove(product);
        }
    }
}
