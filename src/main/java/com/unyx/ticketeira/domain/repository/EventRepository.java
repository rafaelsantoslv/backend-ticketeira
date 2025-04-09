package com.unyx.ticketeira.domain.repository;

import com.unyx.ticketeira.domain.model.Event;
import com.unyx.ticketeira.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}
