package com.security.springSecurity.controller;

import com.security.springSecurity.reponse.AuthenticationResponse;
import com.security.springSecurity.request.AuthenticationRequest;
import com.security.springSecurity.request.RefreshTokenRequest;
import com.security.springSecurity.util.JwtUtil;
import com.security.springSecurity.util.RefreshTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private RefreshTokenUtil refreshTokenUtil;
    private UserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
//        AuthenticationRequest authenticationRequest = new AuthenticationRequest("user", "123456");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);
        final String refreshToken = refreshTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAuthenticationToken(@RequestBody RefreshTokenRequest refreshTokenRequest) throws Exception {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String username = refreshTokenUtil.extractUsername(refreshToken);

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if (refreshTokenUtil.validateToken(refreshToken, userDetails)) {
            String jwt = jwtUtil.generateToken(userDetails);
            return ResponseEntity.ok(new AuthenticationResponse(jwt, refreshToken));
        } else {
            throw new Exception("Invalid refresh token");
        }
    }
}
