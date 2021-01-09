package com.wojtek.evento.controllers;

import com.wojtek.evento.dto.EventDto;
import com.wojtek.evento.model.Event;
import com.wojtek.evento.repository.EventRepository;
import com.wojtek.evento.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
public class EventController {

    private final EventService eventService;
    private EventRepository eventRepository;

    @PostMapping("/add")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.saveEvent(eventDto));
    }

    @GetMapping("/all")
    public List<EventDto> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventById(id));
    }

    @PostMapping("/sign/{id}")
    public ResponseEntity<EventDto> signCurrentUserToEvent(@PathVariable Long id) {
        eventService.signCurrentUserToEvent(id);

        return ResponseEntity.status(HttpStatus.OK).body(eventService.getEventById(id));
    }
}
