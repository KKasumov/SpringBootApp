package com.kasumov.SpringBootApp.service;


import com.kasumov.SpringBootApp.model.Event;

public interface EventService extends GenericService<Event, Long>{
    Event create(Event event);

    Event update(Event event);

}
