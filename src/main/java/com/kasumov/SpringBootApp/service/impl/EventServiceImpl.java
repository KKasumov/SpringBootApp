package com.kasumov.SpringBootApp.service.impl;

import com.kasumov.SpringBootApp.model.Event;
import com.kasumov.SpringBootApp.model.Status;
import com.kasumov.SpringBootApp.repository.EventRepository;
import com.kasumov.SpringBootApp.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event getById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            log.warn("FindById - no Event found by id: {}", id);
            return null;
        }
        Event event = eventOptional.get();
        log.info("Find Event by id: {}", id);
        return event;
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = eventRepository.findAll();
        log.info("GetAll: {} Events found", events.size());
        return events;
    }

    @Override
    public Event create(Event event) {
        log.info("Create Event: {}", event);
        event.setStatus(Status.ACTIVE);
        return eventRepository.save(event);
    }

    @Override
    public Event update(Event event) {
        log.info("Update Event: {}", event);
        event.setStatus(Status.ACTIVE);
        return eventRepository.save(event);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Event> eventOptional = eventRepository.findById(id);
        if (eventOptional.isEmpty()) {
            log.warn("DeleteById - no Event found by id: {}", id);
        } else {
            Event event = eventOptional.get();
            log.info("Delete Event by id: {}", id);
            event.setStatus(Status.DELETED);
            eventRepository.save(event);
        }
    }
}