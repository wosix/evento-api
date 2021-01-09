package com.wojtek.evento.mapper;

import com.wojtek.evento.dto.EventDto;
import com.wojtek.evento.model.Event;
import com.wojtek.evento.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper instance = Mappers.getMapper(EventMapper.class);

    EventDto mapEventtoDtoEvent(Event event);

    @InheritInverseConfiguration
    Event mapDtoEventToEvent(EventDto eventDto);

    Event mapNewEvent(EventDto eventDto, User user);

}
