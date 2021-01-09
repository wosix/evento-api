package com.wojtek.evento.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    private String dateTime;

    @OneToOne
    private Location location;

    @ManyToOne
    private User host;

    @ManyToMany
    @JoinTable(name = "user_event", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants;

    public Event(String eventName, String description, EventType eventType, String dateTime, Location location, User host) {
        this.eventName = eventName;
        this.description = description;
        this.eventType = eventType;
        this.dateTime = dateTime;
        this.location = location;
        this.host = host;
        this.participants = new ArrayList<>();
    }

    public Event(String eventName, String description, EventType eventType, String dateTime, Location location, User host, List<User> userList) {
        this.eventName = eventName;
        this.description = description;
        this.eventType = eventType;
        this.dateTime = dateTime;
        this.location = location;
        this.host = host;
        //this.participants = new ArrayList<>();
        this.participants = userList;
    }
}
