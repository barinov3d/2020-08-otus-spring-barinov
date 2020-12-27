package org.library.security;

import org.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService usersService;

    public SecurityConfiguration(UserService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/login").anonymous()
                .and()
                .authorizeRequests().antMatchers("/author/*/update").hasAnyAuthority( "ROLE_MANAGER","ROLE_ADMIN" )
                .and()
                .authorizeRequests().antMatchers("/book/*/update").hasAnyAuthority( "ROLE_MANAGER","ROLE_ADMIN" )
                .and()
                .authorizeRequests().antMatchers("/author/*/delete").hasAuthority( "ROLE_ADMIN" )
                .and()
                .authorizeRequests().antMatchers("/book/*/delete").hasAuthority( "ROLE_ADMIN" )
                .and()
                .formLogin();

    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService);
    }

}
