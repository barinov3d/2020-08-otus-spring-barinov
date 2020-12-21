package org.library.security;

import org.library.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;

    public AuthenticationFilter(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        super.setAuthenticationManager(authenticationManager);
    }
}
