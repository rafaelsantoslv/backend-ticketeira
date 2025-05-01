package com.unyx.ticketeira.domain.util;

import com.unyx.ticketeira.application.dto.event.EventCreateRequest;
import com.unyx.ticketeira.application.dto.event.EventListAllByProducerResponse;
import com.unyx.ticketeira.application.dto.user.RegisterRequest;
import com.unyx.ticketeira.application.dto.user.UpdateUserDTO;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.model.Role;
import com.unyx.ticketeira.domain.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class ConvertDTO {

    public static User convertUser(RegisterRequest dto, Role userRole) {
        User user = new User();

        String passwordEncrypt = PasswordUtil.encryptPassword(dto.password());

        user.setEmail(dto.email());
        user.setPassword(passwordEncrypt);
        user.setName(dto.name());
        user.setDocument(dto.document());
        user.setPhone(dto.phone());
        user.setRole(userRole);

        return user;
    }

    public static User convertUser(User userUpdate, UpdateUserDTO dto) {


        userUpdate.setEmail(dto.email());
        userUpdate.setName(dto.name());
        userUpdate.setDocument(dto.document());
        userUpdate.setPhone(dto.phone());

        return userUpdate;

    }

    public static Event convertEvent(EventCreateRequest dto, User user){
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
        event.setCreator(user);



        return event;
    }

    public static EventListAllByProducerResponse convertEventToDto(Event event) {
        return new EventListAllByProducerResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getAgeRating(),
                event.getLocationName(),
                event.getLocationCity(),
                event.getLocationAddress(),
                event.getLocationState(),
                event.getLocationZip(),
                event.getCategory(),
                event.getImageUrl(),
                event.getIsPublished(),
                event.getIsFeatured(),
                event.getCreator().getName(),
                event.getStartDate(),
                event.getEndDate()
        );
    }


    public static List<EventListAllByProducerResponse> convertListEventToDto(List<Event> events) {
        return events.stream()
                .map(ConvertDTO::convertEventToDto)
                .collect(Collectors.toList());
    }
}
