package com.wojtek.evento.security;

import com.wojtek.evento.exceptions.EException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.sql.Date;
import java.time.Instant;

@Service
public class JwtClass {

    @Value("${jwt.password}")
    private String password;
    private KeyStore keyStore;
    @Value("${jwt.expiration.time}")
    private Long jwtExpTime;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceStream = getClass().getResourceAsStream("/clientkeystore.jks");
            keyStore.load(resourceStream, password.toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException mex) {
            throw new EException("jwt init error", mex);
        }
    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User prrincipal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(prrincipal.getUsername())
                .signWith(getPrivateKey())
//                .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpTime)))
                .compact();
    }

    public String generateTokenUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(getPrivateKey())
//                .signWith(getPrivateKey(), SignatureAlgorithm.ES256)
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpTime)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("clientkeystore", password.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException mex) {
            throw new EException("jwt get pkey error", mex);
        }
    }

    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("clientkeystore").getPublicKey();
        } catch (KeyStoreException mex) {
            throw new EException("eeeeeeeeeeeee", mex);
        }
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublickey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Long getExpirationTime() {
        return jwtExpTime;
    }
}
