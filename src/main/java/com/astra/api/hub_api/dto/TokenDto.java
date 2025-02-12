package com.astra.api.hub_api.dto;

import java.time.LocalDateTime;

public record TokenDto(
    String userName,
    LocalDateTime createdAt,
    LocalDateTime expirationTime,
    String token
) {
    
}
