package com.unyx.ticketeira.usecases.event;

import com.unyx.ticketeira.dto.event.EventCreateRequest;
import com.unyx.ticketeira.dto.event.EventCreateResponse;
import com.unyx.ticketeira.model.Event;
import com.unyx.ticketeira.model.User;
import com.unyx.ticketeira.repository.EventRepository;
import com.unyx.ticketeira.repository.UserRepository;
import com.unyx.ticketeira.util.ConvertDTO;
import com.unyx.ticketeira.exception.UnauthorizedException;
import com.unyx.ticketeira.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AddEventProducerUseCase {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public AddEventProducerUseCase(EventRepository eventRepository, UserRepository userRepository) {
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
