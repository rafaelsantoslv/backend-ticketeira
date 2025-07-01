package com.unyx.ticketeira.mapper;

import com.unyx.ticketeira.dto.coupon.CouponDTO;
import com.unyx.ticketeira.dto.courtesy.CourtesyDTO;
import com.unyx.ticketeira.dto.event.EventDetailsDTO;
import com.unyx.ticketeira.dto.event.MetricsDTO;
import com.unyx.ticketeira.dto.sector.SectorDTO;
import com.unyx.ticketeira.dto.ticket.TicketDTO;
import com.unyx.ticketeira.model.Event;

import java.util.List;

public class EventMapper {
    public static EventDetailsDTO toDto(
            Event event,
            List<SectorDTO> sectors,
            List<CouponDTO> coupons,
            List<CourtesyDTO> courtesies,
            List<TicketDTO> tickets,
            MetricsDTO metricsDTO
    ) {
            return new EventDetailsDTO(
                     event.getId(),
                     event.getTitle(),
                     event.getLocationName(),
                     event.getLocationCity(),
                     event.getLocationState(),
                     event.getCategory(),
                     event.getImageUrl(),
                     event.getIsPublished(),
                     event.getIsFeatured(),
                     event.getStartDate(),
                     sectors,
                     coupons,
                     courtesies,
                     tickets,
                     metricsDTO
            );
    }
}
