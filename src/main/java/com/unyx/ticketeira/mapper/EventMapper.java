package com.unyx.ticketeira.mapper;

import com.unyx.ticketeira.dto.coupon.CouponDTO;
import com.unyx.ticketeira.dto.courtesy.CourtesyDTO;
import com.unyx.ticketeira.dto.event.*;
import com.unyx.ticketeira.dto.sector.SectorDTO;
import com.unyx.ticketeira.dto.ticket.TicketDTO;
import com.unyx.ticketeira.model.Event;
import com.unyx.ticketeira.model.User;

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

    public static Event toModel(
            EventCreateRequest dto,
            String imagemUUID,
            User user
    ){
        Event event = new Event();
        event.setTitle(dto.title());
        event.setDescription(dto.description());
        event.setLocationName(dto.locationName());
        event.setLocationCity(dto.locationCity());
        event.setLocationState(dto.locationState());
        event.setLocationAddress(dto.locationAddress());
        event.setLocationZip(dto.locationZip());
        event.setAgeRating(dto.ageRating());
        event.setCategory(dto.category());
        event.setEndDate(dto.endDate());
        event.setStartDate(dto.startDate());
        event.setImageUrl(imagemUUID);
        event.setCreator(user);

        return event;
    }

    public static void updateModel(Event event, EventUpdateRequest dto) {
        if(dto.title() != null) event.setTitle(dto.title());
        if(dto.description() != null) event.setDescription(dto.description());
        if(dto.category() != null) event.setCategory(dto.category());
        if(dto.ageRating() != null) event.setAgeRating(dto.ageRating());
        if(dto.locationName() != null) event.setLocationName(dto.locationName());
        if(dto.locationAddress() != null) event.setLocationAddress(dto.locationAddress());
        if(dto.locationCity() != null) event.setLocationCity(dto.locationCity());
        if(dto.locationState() != null) event.setLocationState(dto.locationState());
        if(dto.locationZip() != null) event.setLocationZip(dto.locationZip());
        event.setIsPublished(dto.isPublished());
        event.setIsFeatured(dto.isFeatured());

    }

    public static EventUpdateResponse toUpdateResponse(Event event) {
            return new EventUpdateResponse(
                    event.getId(),
                    event.getTitle(),
                    event.getDescription(),
                    event.getCategory(),
                    event.getAgeRating(),
                    event.getLocationName(),
                    event.getLocationAddress(),
                    event.getLocationCity(),
                    event.getLocationState(),
                    event.getLocationZip(),
                    event.getIsPublished(),
                    event.getIsFeatured()
            );
    }
}
