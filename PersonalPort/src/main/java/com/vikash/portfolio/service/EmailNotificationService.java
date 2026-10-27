package com.vikash.portfolio.service;

import com.vikash.portfolio.entity.HiringInquiry;

public interface EmailNotificationService {
    void sendOwnerNotification(HiringInquiry inquiry);
    void sendRecruiterConfirmation(HiringInquiry inquiry);
}
