package com.unyx.ticketeira.dto.event;

import com.unyx.ticketeira.dto.coupon.CouponDTO;
import com.unyx.ticketeira.dto.courtesy.CourtesyDTO;
import com.unyx.ticketeira.dto.sector.SectorDTO;
import com.unyx.ticketeira.dto.ticket.TicketDTO;

import java.time.LocalDateTime;
import java.util.List;

public record EventDetailsDTO(
        String id,
        String title,
        String locationName,
        String locationCity,
        String locationState,
        String category,
        String imageUrl,
        Boolean isPublished,
        Boolean isFeatured,
        LocalDateTime startDate,
        List<SectorDTO> sectors,
        List<CouponDTO> coupons,
        List<CourtesyDTO> courtesies,
        List<TicketDTO> tickets,
        MetricsDTO metricsDTO
) {
}
