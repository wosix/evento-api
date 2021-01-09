package com.wojtek.evento.mapper;

import com.wojtek.evento.dto.LocationDto;
import com.wojtek.evento.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocationMapper {
    LocationMapper instance = Mappers.getMapper(LocationMapper.class);

    LocationDto locationToLocationDto(Location location);

    Location dtoToLocation(LocationDto locationDto);
}
