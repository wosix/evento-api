package com.wojtek.evento.config;

import com.wojtek.evento.dto.EventDto;
import com.wojtek.evento.exceptions.EException;
import com.wojtek.evento.mapper.EventMapper;
import com.wojtek.evento.model.Event;
import com.wojtek.evento.model.EventType;
import com.wojtek.evento.model.Location;
import com.wojtek.evento.model.User;
import com.wojtek.evento.repository.EventRepository;
import com.wojtek.evento.repository.LocationRepository;
import com.wojtek.evento.repository.UserRepository;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BootData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final PasswordEncoder passwordEncoder;

    public BootData(UserRepository userRepository, EventRepository eventRepository, LocationRepository locationRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.locationRepository = locationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("e '/' d'-'M'-'yyyy '|' HH':'mm");
            DateTime dateTime = DateTime.now().plusDays(653);
            User user1 = createUser("adminuser1", "admin@", "1234422");
            User user2 = createUser("typicaluser", "mail@", "1234422");
            User user3 = createUser("tibijski", "wariat@", "1234422");
            List<User> userList = new ArrayList<>();
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userList.add(user1);
            userList.add(user2);
            userList.add(user3);
            Location location1 = new Location("50.678418", "17.809007");
            userRepository.save(createUser("user123", "emejl@.pl", "1521222"));
            userRepository.save(createUser("defuser5", "mail@", "4321222"));

            Event event1 = new Event("1eventever", "evencik", EventType.ART, dateTime.toString(dateTimeFormatter), location1, user1, userList);
            locationRepository.save(location1);
            eventRepository.save(event1);

            EventDto eventDto = EventMapper.instance.mapEventtoDtoEvent(event1);

            System.out.println(eventDto.getDescription());
            System.out.println(":)");


        } catch (Exception mex) {
            throw new EException("data load failed " + mex);
        }
    }


    private User createUser(String username, String email, String password) {
        return User.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(password))
                .created(DateTime.now(DateTimeZone.UTC).toString())
                .valid(true)
                .build();

    }

}
