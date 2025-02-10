// ProductRepository.java
package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();
    private long nextId = 1; // simple auto-increment id

    public Product create(Product product) {
        // assign id if not already set
        if (product.getId() == null) {
            product.setId(nextId++);
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(Long id) {
        for (Product p : productData) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public Product update(Product product) {
        Product existing = findById(product.getId());
        if (existing != null) {
            existing.setProductName(product.getProductName());
            existing.setProductQuantity(product.getProductQuantity());
        }
        return existing;
    }

    public void delete(Long id) {
        Product product = findById(id);
        if (product != null) {
            productData.remove(product);
        }
    }
}
