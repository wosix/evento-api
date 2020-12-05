package com.wojtek.evento.config;

import com.wojtek.evento.model.User;
import com.wojtek.evento.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BootData implements CommandLineRunner {

    private UserRepository userRepository;

    public BootData(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(createUser("admin@", "1234"));
        userRepository.save(createUser("emejl@.pl", "1521"));
        userRepository.save(createUser("mail@", "4321"));

    }


    private User createUser(String email, String password) {
        return User.builder()
                .email(email)
                .password(password)
                .build();

    }

}
