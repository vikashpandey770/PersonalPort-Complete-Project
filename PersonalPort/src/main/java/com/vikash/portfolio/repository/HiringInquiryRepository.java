package com.vikash.portfolio.repository;

import com.vikash.portfolio.entity.HiringInquiry;
import com.vikash.portfolio.entity.HiringType;
import com.vikash.portfolio.entity.InquiryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface HiringInquiryRepository extends JpaRepository<HiringInquiry, Long> {

    long countByStatus(InquiryStatus status);

    @Query("SELECT h FROM HiringInquiry h WHERE " +
           "(:status IS NULL OR h.status = :status) AND " +
           "(:hiringType IS NULL OR h.hiringType = :hiringType) AND " +
           "(:startDate IS NULL OR h.createdAt >= :startDate) AND " +
           "(:endDate IS NULL OR h.createdAt <= :endDate) AND " +
           "(:search IS NULL OR :search = '' OR " +
           " LOWER(h.fullName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(h.companyName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(h.workEmail) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           " LOWER(h.jobRole) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<HiringInquiry> findWithFilters(
            @Param("status") InquiryStatus status,
            @Param("hiringType") HiringType hiringType,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("search") String search,
            Pageable pageable
    );
}
