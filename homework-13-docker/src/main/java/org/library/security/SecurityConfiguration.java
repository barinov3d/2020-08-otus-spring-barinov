package org.library.security;

import org.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService usersService;


    public SecurityConfiguration(UserService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/login").anonymous()
                .and()
                .authorizeRequests().antMatchers("/**/update").hasAnyRole("MANAGER", "ADMIN")
                .and()
                .authorizeRequests().antMatchers("/**/delete").hasAnyRole("ADMIN")
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .formLogin();

    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService);
    }

}
