package com.wojtek.evento.service;

import com.wojtek.evento.dto.UserDto;
import com.wojtek.evento.model.User;
import com.wojtek.evento.model.VerificationToken;
import com.wojtek.evento.repository.UserRepository;
import com.wojtek.evento.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    public List<UserDto> findAll() {
        List<UserDto> userDtos = new ArrayList<>();
        Iterable<User> users = userRepository.findAll();
        for (User user: users) {
            userDtos.add(
                    UserDto.builder()
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build()
            );
        }
        return userDtos;
    }

    @Transactional
    public Long addUser(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .created(Instant.now())
                .valid(false)
                .build();

        String token = generateVerificationToken(user);

        return userRepository.save(user).getId();
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();
        verificationTokenRepository.save(verificationToken);

        return token;
    }
}
