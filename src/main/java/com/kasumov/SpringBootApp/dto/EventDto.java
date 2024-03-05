package com.kasumov.SpringBootApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kasumov.SpringBootApp.model.Event;
import com.kasumov.SpringBootApp.model.File;
import com.kasumov.SpringBootApp.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDto {
    private Long id;
    private User user;
    private File file;

    public Event toEvent() {
        return new Event(id, user, file);
    }

    public static EventDto fromEvent(Event event) {
        return Objects.isNull(event) ? null : new EventDto(event.getId(), event.getUser(), event.getFile());
    }

    public static List<EventDto> toEventDtos(List<Event> events) {
        return events.stream()
                .map(EventDto::fromEvent)
                .collect(Collectors.toList());
    }
}