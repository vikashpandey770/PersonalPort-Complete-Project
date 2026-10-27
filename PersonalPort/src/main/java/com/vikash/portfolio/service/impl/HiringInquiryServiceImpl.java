package com.vikash.portfolio.service.impl;

import com.vikash.portfolio.dto.HiringInquiryRequestDto;
import com.vikash.portfolio.dto.HiringInquiryResponseDto;
import com.vikash.portfolio.dto.InquiryStatsDto;
import com.vikash.portfolio.entity.HiringInquiry;
import com.vikash.portfolio.entity.HiringType;
import com.vikash.portfolio.entity.InquiryStatus;
import com.vikash.portfolio.exception.ResourceNotFoundException;
import com.vikash.portfolio.repository.HiringInquiryRepository;
import com.vikash.portfolio.service.EmailNotificationService;
import com.vikash.portfolio.service.HiringInquiryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class HiringInquiryServiceImpl implements HiringInquiryService {

    private static final Logger log = LoggerFactory.getLogger(HiringInquiryServiceImpl.class);

    private final HiringInquiryRepository inquiryRepository;
    private final EmailNotificationService emailNotificationService;

    public HiringInquiryServiceImpl(HiringInquiryRepository inquiryRepository,
                                   EmailNotificationService emailNotificationService) {
        this.inquiryRepository = inquiryRepository;
        this.emailNotificationService = emailNotificationService;
    }

    @Override
    @Transactional
    public HiringInquiryResponseDto submitInquiry(HiringInquiryRequestDto requestDto) {
        log.info("Processing new hiring inquiry from company: {}", requestDto.getCompanyName());

        HiringType parsedHiringType = HiringType.fromString(requestDto.getHiringType());

        HiringInquiry inquiry = new HiringInquiry(
                requestDto.getFullName().trim(),
                requestDto.getCompanyName().trim(),
                requestDto.getWorkEmail().trim().toLowerCase(),
                requestDto.getContactNumber() != null ? requestDto.getContactNumber().trim() : null,
                parsedHiringType,
                requestDto.getJobRole().trim(),
                requestDto.getSalaryBudget() != null ? requestDto.getSalaryBudget().trim() : null,
                requestDto.getJobDescription().trim(),
                requestDto.getAdditionalMessage() != null ? requestDto.getAdditionalMessage().trim() : null
        );

        HiringInquiry savedInquiry = inquiryRepository.save(inquiry);
        log.info("Saved hiring inquiry with ID: {}", savedInquiry.getId());

        // Trigger optional notifications
        try {
            emailNotificationService.sendOwnerNotification(savedInquiry);
            emailNotificationService.sendRecruiterConfirmation(savedInquiry);
        } catch (Exception e) {
            log.error("Failed to send email notification for inquiry #{}: {}", savedInquiry.getId(), e.getMessage());
        }

        return HiringInquiryResponseDto.fromEntity(savedInquiry);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<HiringInquiryResponseDto> getInquiries(
            InquiryStatus status,
            HiringType hiringType,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String search,
            Pageable pageable) {

        String trimmedSearch = (search != null && !search.isBlank()) ? search.trim() : null;

        return inquiryRepository.findWithFilters(status, hiringType, startDate, endDate, trimmedSearch, pageable)
                .map(HiringInquiryResponseDto::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public HiringInquiryResponseDto getInquiryById(Long id) {
        HiringInquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HiringInquiry", "id", id));
        return HiringInquiryResponseDto.fromEntity(inquiry);
    }

    @Override
    @Transactional
    public HiringInquiryResponseDto updateInquiryStatus(Long id, InquiryStatus newStatus) {
        HiringInquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HiringInquiry", "id", id));

        inquiry.setStatus(newStatus);
        HiringInquiry updated = inquiryRepository.save(inquiry);
        log.info("Updated status of inquiry #{} to {}", id, newStatus);
        return HiringInquiryResponseDto.fromEntity(updated);
    }

    @Override
    @Transactional
    public void deleteInquiry(Long id) {
        if (!inquiryRepository.existsById(id)) {
            throw new ResourceNotFoundException("HiringInquiry", "id", id);
        }
        inquiryRepository.deleteById(id);
        log.info("Deleted inquiry #{}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public InquiryStatsDto getStatistics() {
        long total = inquiryRepository.count();
        long newCount = inquiryRepository.countByStatus(InquiryStatus.NEW);
        long reviewed = inquiryRepository.countByStatus(InquiryStatus.REVIEWED);
        long contacted = inquiryRepository.countByStatus(InquiryStatus.CONTACTED);
        long shortlisted = inquiryRepository.countByStatus(InquiryStatus.SHORTLISTED);
        long closed = inquiryRepository.countByStatus(InquiryStatus.CLOSED);

        return new InquiryStatsDto(total, newCount, reviewed, contacted, shortlisted, closed);
    }
}
