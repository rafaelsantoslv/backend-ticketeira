package com.unyx.ticketeira.application.usecases.event;

import com.unyx.ticketeira.application.dto.event.EventCreateRequest;
import com.unyx.ticketeira.application.dto.event.EventCreateResponse;
import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.model.User;
import com.unyx.ticketeira.domain.repository.EventRepository;
import com.unyx.ticketeira.domain.repository.UserRepository;
import com.unyx.ticketeira.domain.util.ConvertDTO;
import com.unyx.ticketeira.exception.UnauthorizedException;
import com.unyx.ticketeira.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AddEventUseCase {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public AddEventUseCase(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public EventCreateResponse execute(String userId, EventCreateRequest dto) {
        User userExists = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User incorrect"));

        if(!userExists.getRole().getName().equalsIgnoreCase("PRODUCER")) {
            throw new UnauthorizedException("Role incorrect");
        }

        Event convertEvent = ConvertDTO.convertEvent(dto, userExists);

        Event addEvent = eventRepository.save(convertEvent);

        return new EventCreateResponse(addEvent.getId(), "Success created event");
    }
}
