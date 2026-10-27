package com.vikash.portfolio.dto;

public class InquiryStatsDto {

    private long total;
    private long newInquiries;
    private long reviewed;
    private long contacted;
    private long shortlisted;
    private long closed;

    public InquiryStatsDto() {
    }

    public InquiryStatsDto(long total, long newInquiries, long reviewed, long contacted, long shortlisted, long closed) {
        this.total = total;
        this.newInquiries = newInquiries;
        this.reviewed = reviewed;
        this.contacted = contacted;
        this.shortlisted = shortlisted;
        this.closed = closed;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getNewInquiries() {
        return newInquiries;
    }

    public void setNewInquiries(long newInquiries) {
        this.newInquiries = newInquiries;
    }

    public long getReviewed() {
        return reviewed;
    }

    public void setReviewed(long reviewed) {
        this.reviewed = reviewed;
    }

    public long getContacted() {
        return contacted;
    }

    public void setContacted(long contacted) {
        this.contacted = contacted;
    }

    public long getShortlisted() {
        return shortlisted;
    }

    public void setShortlisted(long shortlisted) {
        this.shortlisted = shortlisted;
    }

    public long getClosed() {
        return closed;
    }

    public void setClosed(long closed) {
        this.closed = closed;
    }
}
