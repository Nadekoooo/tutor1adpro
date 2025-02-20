package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;

class EshopApplicationTest {

    @Test
    public void testMain() {
        // Passing a flag to avoid starting the full web server
        String[] args = {"--spring.main.web-application-type=none"};
        EshopApplication.main(args);
    }
}
