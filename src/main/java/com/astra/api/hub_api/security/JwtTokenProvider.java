package com.astra.api.hub_api.security;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.astra.api.hub_api.dto.TokenDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

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

    static Algorithm signatureAlgorithm = null;
    
    @PostConstruct
    public void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        signatureAlgorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDto createAccessToken(Long id, String userName, List<String> roles){
        Date createdAt = new Date();
        Date expiration = new Date(createdAt.getTime() + expireLength);

        return new TokenDto(userName, 
        createdAt,
        expiration,
        generateToken(id, userName, roles, createdAt, expiration));
    }

    public static String generateToken(Long id, String userName, List<String> roles, Date createdAt, Date expireLength){
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        return JWT.create()
            .withClaim("id", id)
            .withClaim("roles", roles)
            .withIssuedAt(createdAt)
            .withExpiresAt(expireLength)
            .withIssuer(issuerUrl)
            .withSubject(userName)
            .sign(signatureAlgorithm)
            .strip();
    }

    public Authentication getAuthentication(String token){
        DecodedJWT decodedToken = decodeToken(token);
        UserDetails user = userDetailsService.loadUserByUsername(decodedToken.getSubject());
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }
        
    public DecodedJWT decodeToken(String token) {
        return JWT.require(signatureAlgorithm).build().verify(token);
    }

    public String resolveTokenToString(HttpServletRequest request){
        String authorizationHeaderValue = request.getHeader("Authorization");
        if(authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer ")){
            return authorizationHeaderValue.substring("Bearer ".length());
        } else return null;
    }

    //to-do: adicionar método pra validar o token
}
