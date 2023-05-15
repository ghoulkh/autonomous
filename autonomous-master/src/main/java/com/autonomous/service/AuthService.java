package com.autonomous.service;

import com.autonomous.exception.AutonomousException;
import com.autonomous.config.JwtToken;
import com.autonomous.model.error.ErrorCode;
import com.autonomous.model.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtToken jwtToken;

    public String validateUsernamePasswordAndGenToken(LoginRequest loginRequest) {
        authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return jwtToken.generateToken(loginRequest.getUsername());
    }
    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AutonomousException(ErrorCode.BANNED_USER);
        } catch (BadCredentialsException e) {
            throw new AutonomousException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }
    }

}
