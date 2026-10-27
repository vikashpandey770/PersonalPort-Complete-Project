package com.vikash.portfolio.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class HiringInquiryRequestDto {

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name must not exceed 100 characters")
    private String fullName;

    @NotBlank(message = "Company name is required")
    @Size(max = 150, message = "Company name must not exceed 150 characters")
    private String companyName;

    @NotBlank(message = "Work email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 150, message = "Work email must not exceed 150 characters")
    private String workEmail;

    @Size(max = 25, message = "Contact number must not exceed 25 characters")
    private String contactNumber;

    @NotBlank(message = "Hiring type is required")
    private String hiringType;

    @NotBlank(message = "Job role or project title is required")
    @Size(max = 150, message = "Job role must not exceed 150 characters")
    private String jobRole;

    @Size(max = 100, message = "Salary or budget must not exceed 100 characters")
    private String salaryBudget;

    @NotBlank(message = "Job description is required")
    @Size(max = 3000, message = "Job description must not exceed 3000 characters")
    private String jobDescription;

    @Size(max = 2000, message = "Additional message must not exceed 2000 characters")
    private String additionalMessage;

    @AssertTrue(message = "You must give consent to submit this hiring inquiry")
    private Boolean consent;

    public HiringInquiryRequestDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getHiringType() {
        return hiringType;
    }

    public void setHiringType(String hiringType) {
        this.hiringType = hiringType;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getSalaryBudget() {
        return salaryBudget;
    }

    public void setSalaryBudget(String salaryBudget) {
        this.salaryBudget = salaryBudget;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getAdditionalMessage() {
        return additionalMessage;
    }

    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

    public Boolean getConsent() {
        return consent;
    }

    public void setConsent(Boolean consent) {
        this.consent = consent;
    }
}
