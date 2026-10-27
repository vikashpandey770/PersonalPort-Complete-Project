package com.vikash.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "hiring_inquiries", indexes = {
        @Index(name = "idx_inquiry_status", columnList = "status"),
        @Index(name = "idx_inquiry_created_at", columnList = "createdAt"),
        @Index(name = "idx_inquiry_email", columnList = "workEmail")
})
public class HiringInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @NotBlank(message = "Company name is required")
    @Size(max = 150, message = "Company name cannot exceed 150 characters")
    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @NotBlank(message = "Work email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 150, message = "Email cannot exceed 150 characters")
    @Column(name = "work_email", nullable = false, length = 150)
    private String workEmail;

    @Size(max = 25, message = "Contact number cannot exceed 25 characters")
    @Column(name = "contact_number", length = 25)
    private String contactNumber;

    @NotNull(message = "Hiring type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "hiring_type", nullable = false, length = 30)
    private HiringType hiringType;

    @NotBlank(message = "Job role or project title is required")
    @Size(max = 150, message = "Job role cannot exceed 150 characters")
    @Column(name = "job_role", nullable = false, length = 150)
    private String jobRole;

    @Size(max = 100, message = "Salary or budget cannot exceed 100 characters")
    @Column(name = "salary_budget", length = 100)
    private String salaryBudget;

    @NotBlank(message = "Job description is required")
    @Size(max = 3000, message = "Job description cannot exceed 3000 characters")
    @Column(name = "job_description", nullable = false, length = 3000, columnDefinition = "TEXT")
    private String jobDescription;

    @Size(max = 2000, message = "Additional message cannot exceed 2000 characters")
    @Column(name = "additional_message", length = 2000, columnDefinition = "TEXT")
    private String additionalMessage;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private InquiryStatus status = InquiryStatus.NEW;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public HiringInquiry() {
    }

    public HiringInquiry(String fullName, String companyName, String workEmail, String contactNumber,
                         HiringType hiringType, String jobRole, String salaryBudget,
                         String jobDescription, String additionalMessage) {
        this.fullName = fullName;
        this.companyName = companyName;
        this.workEmail = workEmail;
        this.contactNumber = contactNumber;
        this.hiringType = hiringType;
        this.jobRole = jobRole;
        this.salaryBudget = salaryBudget;
        this.jobDescription = jobDescription;
        this.additionalMessage = additionalMessage;
        this.status = InquiryStatus.NEW;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.status == null) {
            this.status = InquiryStatus.NEW;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public HiringType getHiringType() {
        return hiringType;
    }

    public void setHiringType(HiringType hiringType) {
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

    public InquiryStatus getStatus() {
        return status;
    }

    public void setStatus(InquiryStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
