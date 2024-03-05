package com.kasumov.SpringBootApp.rest;

import com.kasumov.SpringBootApp.dto.EventDto;
import com.kasumov.SpringBootApp.model.Event;
import com.kasumov.SpringBootApp.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventRestController {

    private final EventService eventService;

    public EventRestController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        Event event = eventService.getById(id);
        if (event == null) {
            return ResponseEntity.noContent().build();
        }
        EventDto result = EventDto.fromEvent(event);
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<EventDto>> getAll() {
        List<Event> eventList = eventService.getAll();
        if (eventList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EventDto> eventDtoList = EventDto.toEventDtos(eventList);
        return ResponseEntity.ok(eventDtoList);
    }

    @PostMapping
    public ResponseEntity<Event> save(@RequestBody Event event) {
        if (event == null) {
            return ResponseEntity.noContent().build();
        }
        Event eventResult = eventService.create(event);
        return ResponseEntity.ok(eventResult);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> update(@PathVariable Long id, @RequestBody Event event) {
        if (id == null || event == null) {
            return ResponseEntity.noContent().build();
        }
        Event eventResult = eventService.update(event);
        return ResponseEntity.ok(eventResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.noContent().build();
        }
        Event event = eventService.getById(id);
        if (event == null) {
            return ResponseEntity.noContent().build();
        }
        eventService.deleteById(id);
        return ResponseEntity.ok(event);
    }
}