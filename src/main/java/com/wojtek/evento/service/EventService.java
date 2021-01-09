package com.wojtek.evento.service;

import com.wojtek.evento.dto.EventDto;
import com.wojtek.evento.mapper.EventMapper;
import com.wojtek.evento.model.Event;
import com.wojtek.evento.model.User;
import com.wojtek.evento.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import javax.swing.border.EmptyBorder;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserService userService;

    @Transactional
    public EventDto saveEvent(EventDto eventDto) {
//        Event save = Event.builder()
//                .eventName(eventDto.getEventName())
//                .description(eventDto.getDescription())
//                .eventType(eventDto.getEventType())
//                .location(eventDto.getLocation()).build();
        Event save = eventMapper.mapNewEvent(eventDto, userService.getCurrentUser());
        eventRepository.save(save);
        return eventDto;
    }

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::mapEventtoDtoEvent)
                .collect(Collectors.toList());
    }

    public EventDto getEventById(Long id) {
  //      Event eventFound = eventRepository.getOne(id);
//        EventDto eventDto = EventDto.builder()
//                .eventName(eventFound.getEventName())
//                .description(eventFound.getDescription())
//                .eventType(eventFound.getEventType())
//                .dateTime(eventFound.getDateTime())
//                .location(eventFound.getLocation())
//                .host(eventFound.getHost()).build();
        EventDto eventFound = eventMapper.mapEventtoDtoEvent(eventRepository.getOne(id));
        return eventFound;
    }

    public void signCurrentUserToEvent(Long id) {
        List<User> participants = eventRepository.getOne(id).getParticipants();
        participants.add(userService.getCurrentUser());
        eventRepository.getOne(id).setParticipants(participants);
    }

}
