package com.wojtek.evento.dto;

import lombok.*;

import java.time.Instant;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String authenticationToken;
    private String refreshToken;
    private String username;
    private Instant expireTime;
}
