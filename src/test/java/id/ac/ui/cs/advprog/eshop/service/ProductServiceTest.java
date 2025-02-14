package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class
ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Spy
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        // Karena ProductRepository menggunakan list in-memory, setiap test akan memulai dengan repository baru.
    }

    @Test
    public void testEditProduct_Positive() {
        // Buat produk dan simpan ke repository (biarkan auto-assign productId jika null)
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("Produk A");
        product.setProductQuantity(10);
        Product createdProduct = productService.create(product);
        String id = createdProduct.getProductId();

        // Lakukan update pada produk yang sudah ada
        createdProduct.setProductName("Produk A Updated");
        createdProduct.setProductQuantity(20);
        Product updatedProduct = productService.update(createdProduct);

        assertNotNull(updatedProduct, "Produk yang diupdate tidak boleh null");
        assertEquals("Produk A Updated", updatedProduct.getProductName(), "Nama produk harus terupdate");
        assertEquals(20, updatedProduct.getProductQuantity(), "Jumlah produk harus terupdate");
    }

    @Test
    public void testEditProduct_Negative() {
        // Coba update produk dengan productId yang tidak ada di repository
        Product product = new Product();
        product.setProductId("9999"); // id tidak terdaftar
        product.setProductName("Non Existent Product");
        product.setProductQuantity(5);
        Product updatedProduct = productService.update(product);

        // Seharusnya update mengembalikan null karena produk tidak ada
        assertNull(updatedProduct, "Update harus mengembalikan null untuk produk yang tidak ada");
    }

    @Test
    public void testDeleteProduct_Positive() {
        // Buat produk dan simpan ke repository
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("Produk B");
        product.setProductQuantity(15);
        Product createdProduct = productService.create(product);
        String id = createdProduct.getProductId();

        // Hapus produk yang ada
        productService.delete(id);

        // Cek bahwa produk sudah tidak ditemukan
        Product foundProduct = productService.findById(id);
        assertNull(foundProduct, "Produk harus sudah dihapus dan tidak ditemukan");
    }

    @Test
    public void testDeleteProduct_Negative() {
        // Coba hapus produk dengan id yang tidak ada di repository
        String nonExistentId = "9999";
        // Operasi delete seharusnya tidak melempar exception
        assertDoesNotThrow(() -> productService.delete(nonExistentId));

        // Pastikan repository masih kosong (jika belum ada produk yang disimpan sebelumnya)
        assertTrue(productService.findAll().isEmpty(), "Repository harus tetap kosong setelah menghapus produk yang tidak ada");
    }
}
