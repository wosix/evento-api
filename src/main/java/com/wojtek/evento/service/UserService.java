package com.wojtek.evento.service;

import com.wojtek.evento.dto.AuthResponse;
import com.wojtek.evento.dto.RefreshTokenRequest;
import com.wojtek.evento.dto.UserDto;
import com.wojtek.evento.exceptions.EException;
import com.wojtek.evento.model.NotificationEmail;
import com.wojtek.evento.model.User;
import com.wojtek.evento.model.VerificationToken;
import com.wojtek.evento.repository.UserRepository;
import com.wojtek.evento.repository.VerificationTokenRepository;
import com.wojtek.evento.security.JwtClass;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtClass jwtClass;

    @Transactional
    public Long addUser(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .created(DateTime.now(DateTimeZone.UTC).toString())
                .valid(false)
                .build();

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Activate your account!", user.getEmail(), "http://localhost:2040/api/auth/accountVerify/" + token));

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

    public void verifyAccount(String token){
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new EException("Invalid token"));
        findUserToVerify(verificationToken.get());
    }

    @Transactional
    protected void findUserToVerify(VerificationToken verificationToken) {
        String mail = verificationToken.getUser().getEmail();
        User user = userRepository.findByEmail(mail).orElseThrow(() -> new EException("Invalid mail"));
        user.setValid(true);
        userRepository.save(user);
    }

    public AuthResponse login(UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtClass.generateToken(authentication);
        return new AuthResponse(token, refreshTokenService.generateRefreshToken().getToken(), userDto.getUsername(), Instant.now().plusMillis(jwtClass.getExpirationTime()));
    }

    public List<UserDto> findAll() {
        List<UserDto> userDtos = new ArrayList<>();
        Iterable<User> users = userRepository.findAll();
        for (User user : users) {
            userDtos.add(
                    UserDto.builder()
                            .email(user.getEmail())
                            .email(user.getUsername())
                            .password(user.getPassword())
                            .build()
            );
        }
        return userDtos;
    }


    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> new EException(""));

    }

    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtClass.generateTokenUsername(refreshTokenRequest.getUsername());
        return AuthResponse.builder()
                .authenticationToken(token)
                .username(refreshTokenRequest.getUsername())
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expireTime(Instant.now().plusMillis(jwtClass.getExpirationTime()))
                .build();
    }
}
