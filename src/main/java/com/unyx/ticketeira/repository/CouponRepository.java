package com.unyx.ticketeira.repository;

import com.unyx.ticketeira.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {
    Optional<Coupon> findByCode(String code);
    List<Coupon> findByEventId(String eventId);
    List<Coupon> findByEventIdAndIsActive(String eventId, Boolean isActive);

}