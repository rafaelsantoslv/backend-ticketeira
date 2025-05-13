package com.unyx.ticketeira.util;

import com.unyx.ticketeira.dto.batch.BatchCreateRequest;
import com.unyx.ticketeira.dto.batch.BatchCreateResponse;
import com.unyx.ticketeira.dto.batch.BatchListAllBySector;
import com.unyx.ticketeira.dto.event.EventCreateRequest;
import com.unyx.ticketeira.dto.event.EventMeListAllByProducerResponse;
import com.unyx.ticketeira.dto.sector.SectorCreateRequest;
import com.unyx.ticketeira.dto.sector.SectorListAllByEventResponse;
import com.unyx.ticketeira.dto.user.RegisterRequest;
import com.unyx.ticketeira.dto.user.UpdateUserDTO;
import com.unyx.ticketeira.model.*;

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

    public static EventMeListAllByProducerResponse convertEventToDto(Event event, Long soldQuantity) {
        return new EventMeListAllByProducerResponse(
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
                soldQuantity
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

   public static BatchListAllBySector convertBatchListToDto(Batch batch) {
        return new BatchListAllBySector(
                batch.getId(),
                batch.getName(),
                batch.getQuantity(),
                batch.getPrice(),
                batch.getIsActive()
        );
   }
}
