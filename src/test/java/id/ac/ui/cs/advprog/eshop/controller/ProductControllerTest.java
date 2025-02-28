package id.ac.ui.cs.advprog.eshop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testCreateProductPost() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("id", "1")
                        .param("name", "Test Product"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list")); // controller returns "redirect:list"

        verify(service, org.mockito.Mockito.times(1)).create(any(Product.class));
    }

    @Test
    public void testProductListPage() throws Exception {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");

        when(service.findAll()).thenReturn(Arrays.asList(product));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ProductList"))
                .andExpect(model().attribute("products", Arrays.asList(product)));
    }

    @Test
    public void testEditProductPageFound() throws Exception {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");

        when(service.findById("1")).thenReturn(product);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attribute("product", product));
    }

    @Test
    public void testEditProductPageNotFound() throws Exception {
        when(service.findById("1")).thenReturn(null);

        // Assuming the controller returns the edit view with a null product if not found.
        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attribute("product", (Object) null));
    }

    @Test
    public void testEditProductPost() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("id", "1")
                        .param("name", "Updated Product"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list")); // expecting "redirect:list"

        // Note: The controller calls service.edit(...) rather than update(...).
        verify(service, org.mockito.Mockito.times(1)).edit(any(Product.class));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        // Use the correct parameter name "productId" as expected by the controller.
        mockMvc.perform(post("/product/delete")
                        .param("productId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list")); // expecting "redirect:list"

        verify(service, org.mockito.Mockito.times(1)).delete(eq("1"));
    }


}
