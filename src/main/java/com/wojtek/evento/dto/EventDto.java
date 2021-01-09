package com.wojtek.evento.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.wojtek.evento.model.EventType;
import com.wojtek.evento.model.Location;
import com.wojtek.evento.model.User;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
@ToString
public class EventDto {


    private String eventName;

    @Size(min = 2, max = 500)
    private String description;

    private EventType eventType;

    private String dateTime;

    private Location location;

    @JsonIgnore
    private User host;

    private List<UserDto> participants;
}
