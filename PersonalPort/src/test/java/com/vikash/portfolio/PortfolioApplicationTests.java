package com.vikash.portfolio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class PortfolioApplicationTests {

    @Test
    void contextLoads() {
        // Verifies Spring context bootstraps cleanly
    }
}
