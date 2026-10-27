package com.vikash.portfolio.service;

import com.vikash.portfolio.dto.HiringInquiryRequestDto;
import com.vikash.portfolio.dto.HiringInquiryResponseDto;
import com.vikash.portfolio.dto.InquiryStatsDto;
import com.vikash.portfolio.entity.HiringInquiry;
import com.vikash.portfolio.entity.HiringType;
import com.vikash.portfolio.entity.InquiryStatus;
import com.vikash.portfolio.exception.ResourceNotFoundException;
import com.vikash.portfolio.repository.HiringInquiryRepository;
import com.vikash.portfolio.service.impl.HiringInquiryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HiringInquiryServiceTest {

    @Mock
    private HiringInquiryRepository inquiryRepository;

    @Mock
    private EmailNotificationService emailNotificationService;

    @InjectMocks
    private HiringInquiryServiceImpl inquiryService;

    private HiringInquiry sampleInquiry;
    private HiringInquiryRequestDto sampleRequest;

    @BeforeEach
    void setUp() {
        sampleInquiry = new HiringInquiry(
                "Jane Doe",
                "Tech Innovators Inc",
                "jane@techinnovators.com",
                "+1-555-0199",
                HiringType.FULL_TIME,
                "Senior Java Engineer",
                "$90,000 - $110,000",
                "Looking for a Spring Boot engineer to lead backend services.",
                "Excited to connect!"
        );
        sampleInquiry.setId(1L);
        sampleInquiry.setStatus(InquiryStatus.NEW);
        sampleInquiry.setCreatedAt(LocalDateTime.now());
        sampleInquiry.setUpdatedAt(LocalDateTime.now());

        sampleRequest = new HiringInquiryRequestDto();
        sampleRequest.setFullName("Jane Doe");
        sampleRequest.setCompanyName("Tech Innovators Inc");
        sampleRequest.setWorkEmail("jane@techinnovators.com");
        sampleRequest.setContactNumber("+1-555-0199");
        sampleRequest.setHiringType("FULL_TIME");
        sampleRequest.setJobRole("Senior Java Engineer");
        sampleRequest.setSalaryBudget("$90,000 - $110,000");
        sampleRequest.setJobDescription("Looking for a Spring Boot engineer to lead backend services.");
        sampleRequest.setAdditionalMessage("Excited to connect!");
        sampleRequest.setConsent(true);
    }

    @Test
    @DisplayName("Should successfully persist hiring inquiry and invoke notification")
    void submitInquiry_Success() {
        when(inquiryRepository.save(any(HiringInquiry.class))).thenReturn(sampleInquiry);

        HiringInquiryResponseDto response = inquiryService.submitInquiry(sampleRequest);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Jane Doe", response.getFullName());
        assertEquals("Tech Innovators Inc", response.getCompanyName());
        assertEquals(InquiryStatus.NEW, response.getStatus());

        verify(inquiryRepository, times(1)).save(any(HiringInquiry.class));
        verify(emailNotificationService, times(1)).sendOwnerNotification(sampleInquiry);
        verify(emailNotificationService, times(1)).sendRecruiterConfirmation(sampleInquiry);
    }

    @Test
    @DisplayName("Should find inquiry by ID when inquiry exists")
    void getInquiryById_Found() {
        when(inquiryRepository.findById(1L)).thenReturn(Optional.of(sampleInquiry));

        HiringInquiryResponseDto response = inquiryService.getInquiryById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Jane Doe", response.getFullName());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when inquiry ID does not exist")
    void getInquiryById_NotFound() {
        when(inquiryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> inquiryService.getInquiryById(999L));
    }

    @Test
    @DisplayName("Should update inquiry status successfully")
    void updateInquiryStatus_Success() {
        when(inquiryRepository.findById(1L)).thenReturn(Optional.of(sampleInquiry));
        when(inquiryRepository.save(any(HiringInquiry.class))).thenReturn(sampleInquiry);

        HiringInquiryResponseDto response = inquiryService.updateInquiryStatus(1L, InquiryStatus.REVIEWED);

        assertNotNull(response);
        assertEquals(InquiryStatus.REVIEWED, sampleInquiry.getStatus());
        verify(inquiryRepository, times(1)).save(sampleInquiry);
    }

    @Test
    @DisplayName("Should delete inquiry by ID when found")
    void deleteInquiry_Success() {
        when(inquiryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(inquiryRepository).deleteById(1L);

        assertDoesNotThrow(() -> inquiryService.deleteInquiry(1L));
        verify(inquiryRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when deleting non-existent inquiry")
    void deleteInquiry_NotFound() {
        when(inquiryRepository.existsById(999L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> inquiryService.deleteInquiry(999L));
        verify(inquiryRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Should return accurate database-backed dashboard statistics")
    void getStatistics_Success() {
        when(inquiryRepository.count()).thenReturn(15L);
        when(inquiryRepository.countByStatus(InquiryStatus.NEW)).thenReturn(5L);
        when(inquiryRepository.countByStatus(InquiryStatus.REVIEWED)).thenReturn(4L);
        when(inquiryRepository.countByStatus(InquiryStatus.CONTACTED)).thenReturn(3L);
        when(inquiryRepository.countByStatus(InquiryStatus.SHORTLISTED)).thenReturn(2L);
        when(inquiryRepository.countByStatus(InquiryStatus.CLOSED)).thenReturn(1L);

        InquiryStatsDto stats = inquiryService.getStatistics();

        assertEquals(15L, stats.getTotal());
        assertEquals(5L, stats.getNewInquiries());
        assertEquals(4L, stats.getReviewed());
        assertEquals(3L, stats.getContacted());
        assertEquals(2L, stats.getShortlisted());
        assertEquals(1L, stats.getClosed());
    }
}
