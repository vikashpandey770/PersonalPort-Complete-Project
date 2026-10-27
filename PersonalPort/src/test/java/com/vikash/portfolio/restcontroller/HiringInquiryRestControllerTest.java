package com.vikash.portfolio.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vikash.portfolio.dto.HiringInquiryRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
class HiringInquiryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/hiring-inquiries with valid payload should return 201 Created and success response")
    void submitInquiry_ValidPayload_ReturnsCreated() throws Exception {
        HiringInquiryRequestDto request = new HiringInquiryRequestDto();
        request.setFullName("Alice Johnson");
        request.setCompanyName("Global FinTech Corp");
        request.setWorkEmail("alice.j@globalfintech.com");
        request.setContactNumber("+1-555-8822");
        request.setHiringType("FULL_TIME");
        request.setJobRole("Java Backend Developer");
        request.setSalaryBudget("$85,000");
        request.setJobDescription("We need an experienced Spring Boot engineer for our transaction microservice team.");
        request.setAdditionalMessage("Immediate opening, remote friendly.");
        request.setConsent(true);

        mockMvc.perform(post("/api/hiring-inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message", containsString("Thank you for reaching out! Your hiring inquiry has been submitted successfully.")))
                .andExpect(jsonPath("$.data.fullName").value("Alice Johnson"))
                .andExpect(jsonPath("$.data.companyName").value("Global FinTech Corp"))
                .andExpect(jsonPath("$.data.status").value("NEW"));
    }

    @Test
    @DisplayName("POST /api/hiring-inquiries with missing required fields should return 400 Bad Request")
    void submitInquiry_MissingRequiredFields_ReturnsBadRequest() throws Exception {
        HiringInquiryRequestDto request = new HiringInquiryRequestDto();
        // Leave fullName, companyName, workEmail, etc., empty
        request.setConsent(true);

        mockMvc.perform(post("/api/hiring-inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors.fullName").exists())
                .andExpect(jsonPath("$.errors.companyName").exists())
                .andExpect(jsonPath("$.errors.workEmail").exists())
                .andExpect(jsonPath("$.errors.jobRole").exists())
                .andExpect(jsonPath("$.errors.jobDescription").exists());
    }

    @Test
    @DisplayName("POST /api/hiring-inquiries with invalid email format should return 400 Bad Request")
    void submitInquiry_InvalidEmail_ReturnsBadRequest() throws Exception {
        HiringInquiryRequestDto request = new HiringInquiryRequestDto();
        request.setFullName("Alice Johnson");
        request.setCompanyName("Global FinTech Corp");
        request.setWorkEmail("invalid-email-address");
        request.setHiringType("FULL_TIME");
        request.setJobRole("Java Developer");
        request.setJobDescription("Valid job description here.");
        request.setConsent(true);

        mockMvc.perform(post("/api/hiring-inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors.workEmail").value("Please provide a valid email address"));
    }

    @Test
    @DisplayName("POST /api/hiring-inquiries without consent checkbox should return 400 Bad Request")
    void submitInquiry_WithoutConsent_ReturnsBadRequest() throws Exception {
        HiringInquiryRequestDto request = new HiringInquiryRequestDto();
        request.setFullName("Alice Johnson");
        request.setCompanyName("Global FinTech Corp");
        request.setWorkEmail("alice@company.com");
        request.setHiringType("FULL_TIME");
        request.setJobRole("Java Developer");
        request.setJobDescription("Valid job description here.");
        request.setConsent(false); // No consent

        mockMvc.perform(post("/api/hiring-inquiries")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errors.consent").exists());
    }
}
