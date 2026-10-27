package com.vikash.portfolio.dto;

import com.vikash.portfolio.entity.InquiryStatus;
import jakarta.validation.constraints.NotNull;

public class StatusUpdateRequestDto {

    @NotNull(message = "Status cannot be null")
    private InquiryStatus status;

    public StatusUpdateRequestDto() {
    }

    public StatusUpdateRequestDto(InquiryStatus status) {
        this.status = status;
    }

    public InquiryStatus getStatus() {
        return status;
    }

    public void setStatus(InquiryStatus status) {
        this.status = status;
    }
}
