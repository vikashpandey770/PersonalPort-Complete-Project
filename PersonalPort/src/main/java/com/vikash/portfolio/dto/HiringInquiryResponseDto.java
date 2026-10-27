package com.vikash.portfolio.dto;

import com.vikash.portfolio.entity.HiringInquiry;
import com.vikash.portfolio.entity.InquiryStatus;

import java.time.LocalDateTime;

public class HiringInquiryResponseDto {

    private Long id;
    private String fullName;
    private String companyName;
    private String workEmail;
    private String contactNumber;
    private String hiringType;
    private String hiringTypeDisplayName;
    private String jobRole;
    private String salaryBudget;
    private String jobDescription;
    private String additionalMessage;
    private InquiryStatus status;
    private String statusDisplayName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public HiringInquiryResponseDto() {
    }

    public static HiringInquiryResponseDto fromEntity(HiringInquiry entity) {
        if (entity == null) {
            return null;
        }
        HiringInquiryResponseDto dto = new HiringInquiryResponseDto();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setCompanyName(entity.getCompanyName());
        dto.setWorkEmail(entity.getWorkEmail());
        dto.setContactNumber(entity.getContactNumber());
        if (entity.getHiringType() != null) {
            dto.setHiringType(entity.getHiringType().name());
            dto.setHiringTypeDisplayName(entity.getHiringType().getDisplayName());
        }
        dto.setJobRole(entity.getJobRole());
        dto.setSalaryBudget(entity.getSalaryBudget());
        dto.setJobDescription(entity.getJobDescription());
        dto.setAdditionalMessage(entity.getAdditionalMessage());
        dto.setStatus(entity.getStatus());
        if (entity.getStatus() != null) {
            dto.setStatusDisplayName(entity.getStatus().getDisplayName());
        }
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
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

    public String getHiringType() {
        return hiringType;
    }

    public void setHiringType(String hiringType) {
        this.hiringType = hiringType;
    }

    public String getHiringTypeDisplayName() {
        return hiringTypeDisplayName;
    }

    public void setHiringTypeDisplayName(String hiringTypeDisplayName) {
        this.hiringTypeDisplayName = hiringTypeDisplayName;
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

    public String getStatusDisplayName() {
        return statusDisplayName;
    }

    public void setStatusDisplayName(String statusDisplayName) {
        this.statusDisplayName = statusDisplayName;
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
