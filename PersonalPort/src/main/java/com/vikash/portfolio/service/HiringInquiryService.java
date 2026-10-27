package com.vikash.portfolio.service;

import com.vikash.portfolio.dto.HiringInquiryRequestDto;
import com.vikash.portfolio.dto.HiringInquiryResponseDto;
import com.vikash.portfolio.dto.InquiryStatsDto;
import com.vikash.portfolio.entity.HiringType;
import com.vikash.portfolio.entity.InquiryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface HiringInquiryService {

    HiringInquiryResponseDto submitInquiry(HiringInquiryRequestDto requestDto);

    Page<HiringInquiryResponseDto> getInquiries(
            InquiryStatus status,
            HiringType hiringType,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String search,
            Pageable pageable
    );

    HiringInquiryResponseDto getInquiryById(Long id);

    HiringInquiryResponseDto updateInquiryStatus(Long id, InquiryStatus newStatus);

    void deleteInquiry(Long id);

    InquiryStatsDto getStatistics();
}
