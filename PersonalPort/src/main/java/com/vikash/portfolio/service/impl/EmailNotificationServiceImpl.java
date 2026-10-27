package com.vikash.portfolio.service.impl;

import com.vikash.portfolio.entity.HiringInquiry;
import com.vikash.portfolio.service.EmailNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationServiceImpl.class);

    private final ObjectProvider<JavaMailSender> mailSenderProvider;

    @Value("${app.mail.enabled:false}")
    private boolean mailEnabled;

    @Value("${app.mail.receiver:vikashpandey.dev@gmail.com}")
    private String ownerEmail;

    @Value("${spring.mail.username:}")
    private String fromEmail;

    public EmailNotificationServiceImpl(ObjectProvider<JavaMailSender> mailSenderProvider) {
        this.mailSenderProvider = mailSenderProvider;
    }

    @Override
    public void sendOwnerNotification(HiringInquiry inquiry) {
        if (!mailEnabled) {
            log.info("Email notification is disabled. Skipped notifying owner for inquiry #{}", inquiry.getId());
            return;
        }

        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        if (mailSender == null) {
            log.warn("JavaMailSender bean not available. Unable to send owner notification.");
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            if (fromEmail != null && !fromEmail.isBlank()) {
                message.setFrom(fromEmail);
            }
            message.setTo(ownerEmail);
            message.setSubject("[Portfolio Alert] New Hiring Inquiry from " + inquiry.getCompanyName());
            message.setText(String.format(
                    "Hello Vikash,\n\n" +
                    "You have received a new hiring inquiry via your portfolio website!\n\n" +
                    "--- Inquiry Details ---\n" +
                    "Name: %s\n" +
                    "Company: %s\n" +
                    "Email: %s\n" +
                    "Phone: %s\n" +
                    "Hiring Type: %s\n" +
                    "Role: %s\n" +
                    "Budget/Salary: %s\n\n" +
                    "Description:\n%s\n\n" +
                    "Additional Message:\n%s\n\n" +
                    "Submitted on: %s\n\n" +
                    "Manage this inquiry on your admin dashboard.",
                    inquiry.getFullName(),
                    inquiry.getCompanyName(),
                    inquiry.getWorkEmail(),
                    inquiry.getContactNumber() != null ? inquiry.getContactNumber() : "N/A",
                    inquiry.getHiringType() != null ? inquiry.getHiringType().getDisplayName() : "N/A",
                    inquiry.getJobRole(),
                    inquiry.getSalaryBudget() != null ? inquiry.getSalaryBudget() : "Negotiable",
                    inquiry.getJobDescription(),
                    inquiry.getAdditionalMessage() != null ? inquiry.getAdditionalMessage() : "N/A",
                    inquiry.getCreatedAt()
            ));

            mailSender.send(message);
            log.info("Successfully sent notification email to owner for inquiry #{}", inquiry.getId());
        } catch (Exception ex) {
            log.error("Failed to send owner notification email for inquiry #{}: {}", inquiry.getId(), ex.getMessage());
        }
    }

    @Override
    public void sendRecruiterConfirmation(HiringInquiry inquiry) {
        if (!mailEnabled) {
            return;
        }

        JavaMailSender mailSender = mailSenderProvider.getIfAvailable();
        if (mailSender == null) {
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            if (fromEmail != null && !fromEmail.isBlank()) {
                message.setFrom(fromEmail);
            }
            message.setTo(inquiry.getWorkEmail());
            message.setSubject("Thank you for contacting Vikash Pandey - Inquiry Received");
            message.setText(String.format(
                    "Hello %s,\n\n" +
                    "Thank you for reaching out regarding the %s opportunity at %s.\n\n" +
                    "I have received your hiring inquiry and will carefully review the requirements and get back to you shortly.\n\n" +
                    "Best regards,\n" +
                    "Vikash Pandey\n" +
                    "Java Developer | Spring Boot Developer\n" +
                    "GitHub: https://github.com/vikashpandey770\n" +
                    "LinkedIn: https://www.linkedin.com/in/vikash-p-2532aa258/",
                    inquiry.getFullName(),
                    inquiry.getJobRole(),
                    inquiry.getCompanyName()
            ));

            mailSender.send(message);
            log.info("Sent confirmation email to recruiter {} for inquiry #{}", inquiry.getWorkEmail(), inquiry.getId());
        } catch (Exception ex) {
            log.error("Failed to send recruiter confirmation email for inquiry #{}: {}", inquiry.getId(), ex.getMessage());
        }
    }
}
