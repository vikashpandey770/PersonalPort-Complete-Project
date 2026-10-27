package com.vikash.portfolio.restcontroller;

import com.vikash.portfolio.dto.ApiResponseDto;
import com.vikash.portfolio.dto.HiringInquiryRequestDto;
import com.vikash.portfolio.dto.HiringInquiryResponseDto;
import com.vikash.portfolio.service.HiringInquiryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hiring-inquiries")
public class HiringInquiryRestController {

    private static final Logger log = LoggerFactory.getLogger(HiringInquiryRestController.class);

    private final HiringInquiryService hiringInquiryService;

    public HiringInquiryRestController(HiringInquiryService hiringInquiryService) {
        this.hiringInquiryService = hiringInquiryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<HiringInquiryResponseDto>> submitInquiry(
            @Valid @RequestBody HiringInquiryRequestDto requestDto) {

        log.info("Received public hiring inquiry request from company: {}", requestDto.getCompanyName());

        HiringInquiryResponseDto responseDto = hiringInquiryService.submitInquiry(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.ok(
                        "Thank you for reaching out! Your hiring inquiry has been submitted successfully. I will review the details and get back to you.",
                        responseDto
                )
        );
    }
}
