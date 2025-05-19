package com.unyx.ticketeira.usecases.event;

import com.unyx.ticketeira.dto.event.EventCreateRequest;
import com.unyx.ticketeira.dto.event.EventCreateResponse;
import com.unyx.ticketeira.model.Event;
import com.unyx.ticketeira.model.UploadInfo;
import com.unyx.ticketeira.model.User;
import com.unyx.ticketeira.repository.EventRepository;
import com.unyx.ticketeira.repository.UserRepository;
import com.unyx.ticketeira.service.CloudflareStorageService;
import com.unyx.ticketeira.util.ConvertDTO;
import com.unyx.ticketeira.exception.UnauthorizedException;
import com.unyx.ticketeira.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddEventProducerUseCase {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CloudflareStorageService cloudflareStorageService;

    public EventCreateResponse execute(String userId, EventCreateRequest dto) {
        User userExists = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User incorrect"));

        if(!userExists.getRole().getName().equalsIgnoreCase("PRODUCER")) {
            throw new UnauthorizedException("Role incorrect");
        }

        UploadInfo uploadInfo = cloudflareStorageService.generateUploadUrl();

        Event convertEvent = ConvertDTO.convertEvent(dto, uploadInfo.getObjectKey(), userExists);

        Event addEvent = eventRepository.save(convertEvent);

        return new EventCreateResponse(addEvent.getId(), uploadInfo.getUploadKey(), "Success created event");
    }
}
