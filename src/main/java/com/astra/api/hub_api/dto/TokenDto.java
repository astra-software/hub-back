package com.astra.api.hub_api.dto;

import java.util.Date;

public record TokenDto(
    String userName,
    Date createdAt,
    Date expirationTime,
    String token
) {
    
}
