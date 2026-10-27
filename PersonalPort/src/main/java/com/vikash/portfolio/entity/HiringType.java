package com.vikash.portfolio.entity;

public enum HiringType {
    FULL_TIME("Full-Time Job"),
    INTERNSHIP("Internship"),
    FREELANCE("Freelance Project"),
    CONTRACT("Contract"),
    OTHER("Other");

    private final String displayName;

    HiringType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static HiringType fromString(String text) {
        if (text == null || text.trim().isEmpty()) {
            return OTHER;
        }
        for (HiringType type : HiringType.values()) {
            if (type.name().equalsIgnoreCase(text.trim()) ||
                type.displayName.equalsIgnoreCase(text.trim())) {
                return type;
            }
        }
        return OTHER;
    }
}
