package com.kasumov.SpringBootApp.repository;


import com.kasumov.SpringBootApp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {
}
