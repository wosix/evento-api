package com.wojtek.evento.model;

import lombok.*;
import org.joda.time.Instant;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private Instant created;

    private boolean valid;

    @ManyToMany
    @JoinTable(name = "user_event", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "event_id"))
    private final List<Event> events = new ArrayList<>();

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
