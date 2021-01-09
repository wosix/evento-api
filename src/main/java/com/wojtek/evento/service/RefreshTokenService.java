package com.wojtek.evento.service;

import com.wojtek.evento.exceptions.EException;
import com.wojtek.evento.model.RefreshToken;
import com.wojtek.evento.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .createdDate(Instant.now())
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public void validRefreshToken(String token) {
        refreshTokenRepository.findByToken(token).orElseThrow(() -> new EException("invalid refresh token"));
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
