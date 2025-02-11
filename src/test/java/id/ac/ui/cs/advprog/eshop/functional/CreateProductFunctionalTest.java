package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {


    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    /**
     * Skenario Positif:
     * - Navigasi ke halaman Create Product.
     * - Mengisi form dengan data yang valid.
     * - Submit form dan memastikan terjadinya redirect ke halaman product list.
     * - Memastikan bahwa produk "Sampo Functional" muncul pada product list.
     */
    @Test
    void createProduct_Positive(ChromeDriver driver) {
        // Navigasi ke halaman Create Product
        driver.get(baseUrl + "/product/create");

        // Isi field product name
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Sampo Functional");

        // Isi field product quantity
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("25");

        // Klik tombol submit
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        // Tunggu hingga URL mengandung /product/list (redirect berhasil)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/list"));

        // Verifikasi bahwa halaman product list menampilkan produk baru
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Sampo Functional"),
                "Product list harus menampilkan produk 'Sampo Functional'");
    }

    /**
     * Skenario Negatif:
     * - Navigasi ke halaman Create Product.
     * - Submit form tanpa mengisi field yang wajib.
     * - Diharapkan validasi gagal sehingga halaman tetap berada di /product/create.
     * - (Opsional) Dapat juga memverifikasi munculnya pesan error validasi.
     */
    @Test
    void createProduct_Negative(ChromeDriver driver) {
        // Navigasi ke halaman Create Product
        driver.get(baseUrl + "/product/create");

        // Klik tombol submit tanpa mengisi field apa pun
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        // Tunggu hingga URL menunjukkan bahwa user masih berada di halaman create
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/create"));

        // Verifikasi bahwa URL masih mengandung /product/create
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/product/create"),
                "Seharusnya tidak terjadi redirect ke product list jika data tidak valid");

    }
}
