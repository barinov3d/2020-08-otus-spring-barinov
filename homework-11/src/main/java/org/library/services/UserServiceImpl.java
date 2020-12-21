package org.library.services;

import org.library.models.User;
import org.library.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    Environment environment;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           Environment environment) {
        this.userRepository = userRepository;
        this.environment = environment;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userModel = userRepository.findByEmail(username);
        if (userModel == null) throw new UsernameNotFoundException(username);
        return new org.springframework.security.core.userdetails.User(userModel.getEmail(), userModel.getPassword(), true, true, true, true, new ArrayList<>());
    }

}
