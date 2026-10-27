package com.vikash.portfolio.controller;

import com.vikash.portfolio.dto.HiringInquiryResponseDto;
import com.vikash.portfolio.dto.InquiryStatsDto;
import com.vikash.portfolio.dto.StatusUpdateRequestDto;
import com.vikash.portfolio.entity.HiringType;
import com.vikash.portfolio.entity.InquiryStatus;
import com.vikash.portfolio.service.HiringInquiryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final HiringInquiryService hiringInquiryService;

    public AdminController(HiringInquiryService hiringInquiryService) {
        this.hiringInquiryService = hiringInquiryService;
    }

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(
            @RequestParam(value = "status", required = false) InquiryStatus status,
            @RequestParam(value = "hiringType", required = false) HiringType hiringType,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(LocalTime.MAX) : null;

        Pageable pageable = PageRequest.of(Math.max(0, page), size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<HiringInquiryResponseDto> inquiriesPage = hiringInquiryService.getInquiries(
                status, hiringType, startDateTime, endDateTime, search, pageable);

        InquiryStatsDto stats = hiringInquiryService.getStatistics();

        model.addAttribute("stats", stats);
        model.addAttribute("inquiriesPage", inquiriesPage);
        model.addAttribute("inquiries", inquiriesPage.getContent());
        model.addAttribute("allStatuses", InquiryStatus.values());
        model.addAttribute("allHiringTypes", HiringType.values());

        // Retain filters in model
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedHiringType", hiringType);
        model.addAttribute("searchQuery", search);
        model.addAttribute("selectedStartDate", startDate);
        model.addAttribute("selectedEndDate", endDate);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);

        return "admin/dashboard";
    }

    @GetMapping("/inquiries/{id}")
    public String viewInquiryDetails(@PathVariable("id") Long id, Model model) {
        HiringInquiryResponseDto inquiry = hiringInquiryService.getInquiryById(id);
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("allStatuses", InquiryStatus.values());
        return "admin/inquiry-details";
    }

    @PostMapping("/inquiries/{id}/status")
    public String updateStatus(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute StatusUpdateRequestDto statusDto,
            RedirectAttributes redirectAttributes) {

        hiringInquiryService.updateInquiryStatus(id, statusDto.getStatus());
        redirectAttributes.addFlashAttribute("successMessage", "Inquiry #" + id + " status updated to " + statusDto.getStatus().getDisplayName());
        return "redirect:/admin/inquiries/" + id;
    }

    @PostMapping("/inquiries/{id}/delete")
    public String deleteInquiry(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        hiringInquiryService.deleteInquiry(id);
        redirectAttributes.addFlashAttribute("successMessage", "Inquiry #" + id + " was deleted successfully.");
        return "redirect:/admin/dashboard";
    }
}
