package com.wojtek.evento.config;

import com.wojtek.evento.model.User;
import com.wojtek.evento.repository.UserRepository;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BootData implements CommandLineRunner {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public BootData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(createUser("admin@", "1234422"));
        userRepository.save(createUser("emejl@.pl", "1521222"));
        userRepository.save(createUser("mail@", "4321222"));

    }


    private User createUser(String email, String password) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .created(DateTime.now(DateTimeZone.UTC).toString())
                .valid(true)
                .build();

    }

}
