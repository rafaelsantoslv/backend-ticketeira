package com.unyx.ticketeira.domain.util;

import com.unyx.ticketeira.application.dto.batch.BatchCreateRequest;
import com.unyx.ticketeira.application.dto.batch.BatchCreateResponse;
import com.unyx.ticketeira.application.dto.event.EventCreateRequest;
import com.unyx.ticketeira.application.dto.event.EventListAllByProducerResponse;
import com.unyx.ticketeira.application.dto.sector.SectorCreateRequest;
import com.unyx.ticketeira.application.dto.sector.SectorListAllByEventResponse;
import com.unyx.ticketeira.application.dto.user.RegisterRequest;
import com.unyx.ticketeira.application.dto.user.UpdateUserDTO;
import com.unyx.ticketeira.domain.model.*;

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


   public static SectorListAllByEventResponse convertSectorToDto(Sector sector) {
        return new SectorListAllByEventResponse(
                sector.getId(),
                sector.getName(),
                sector.getCapacity(),
                sector.getDescription()
        );
   }

   public static Sector convertSectorToModel(SectorCreateRequest dto){
        Sector sector = new Sector();

        sector.setName(dto.name());
        sector.setDescription(dto.description());
        sector.setCapacity(dto.capacity());

        return sector;
   }

   public static Batch convertBatchToModel(BatchCreateRequest dto){
       Batch batch = new Batch();

       batch.setName(dto.name());
       batch.setQuantity(dto.quantity());
       batch.setPrice(dto.price());
       batch.setRequiresIdentification(dto.requiresIdentification());

       return batch;
   }

   public static BatchCreateResponse convertBatchToDto(Batch batch) {
        return new BatchCreateResponse(
                batch.getId(),
                batch.getName(),
                batch.getQuantity(),
                batch.getPrice(),
                batch.getIsActive(),
                "Sucess created batch"
        );
   }
}
