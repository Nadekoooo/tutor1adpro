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

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateCarFunctionalTest {

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
     * Positive scenario:
     * - Navigate to the Create Car page.
     * - Fill out the form with valid data.
     * - Submit the form and verify redirect to Car list page.
     * - Ensure the newly created car appears on the list.
     */
    @Test
    void createCar_Positive(ChromeDriver driver) {
        // Navigate to Create Car page
        driver.get(baseUrl + "/car/createCar");

        // Fill in the car name
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Test Car");

        // Fill in the car color
        WebElement colorInput = driver.findElement(By.id("colorInput"));
        colorInput.sendKeys("Red");

        // Fill in the car quantity (ensure it's a valid non-negative number)
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("10");

        // Click the submit button
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        // Wait until the URL indicates a successful redirect to the car list page (e.g., /car/listCar)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/car/listCar"));

        // Verify the new car appears in the page source
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Test Car"),
                "Car list should display the newly created car 'Test Car'");
    }

    /**
     * Negative scenario:
     * - Navigate to the Create Car page.
     * - Submit the form without filling out required fields.
     * - Expect validation to fail and remain on the same page.
     */
    @Test
    void createCar_Negative(ChromeDriver driver) {
        // Navigate to the Create Car page
        driver.get(baseUrl + "/car/createCar");

        // Click the submit button without filling the form
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        submitButton.click();

        // Wait until URL indicates user remains on the create page (e.g., /car/createCar)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/car/createCar"));

        // Verify that URL still contains "/car/createCar"
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/car/createCar"),
                "User should remain on the Create Car page if required fields are missing");
    }
}
