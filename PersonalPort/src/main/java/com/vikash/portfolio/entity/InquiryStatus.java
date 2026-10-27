package com.vikash.portfolio.entity;

public enum InquiryStatus {
    NEW("New"),
    REVIEWED("Reviewed"),
    CONTACTED("Contacted"),
    SHORTLISTED("Shortlisted"),
    CLOSED("Closed");

    private final String displayName;

    InquiryStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
