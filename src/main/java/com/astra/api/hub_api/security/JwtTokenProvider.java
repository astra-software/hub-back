package com.astra.api.hub_api.security;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.astra.api.hub_api.dto.TokenDto;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.annotation.PostConstruct;

@Service
public class JwtTokenProvider {
    
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private Long expireLength;

    @Autowired
    private final UserDetailsService userDetailsService;

    public JwtTokenProvider(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    Algorithm signatureAlgorithm = null;
    
    @PostConstruct
    public void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        signatureAlgorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDto generateToken(Long id, String userName, List<String> roles){
        return new TokenDto(userName, 
        new Date(),
        new Date(new Date().getTime() + expireLength),
        userName);
    }
}
