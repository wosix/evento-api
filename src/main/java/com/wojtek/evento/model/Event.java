package com.wojtek.evento.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventName;

    private String description;

    private EventType eventType;

    private DateTime dateTime;

    private String location;

    @ManyToOne
    private User host;

    @ManyToMany(mappedBy = "events")
    private List<User> participants;

    private Long intrested;

}
