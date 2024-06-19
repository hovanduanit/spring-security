package com.security.springSecurity.reponse;

import lombok.Getter;

@Getter
public class AuthenticationResponse {
    private final String token;
    private final String refreshToken;

    public AuthenticationResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }
}
