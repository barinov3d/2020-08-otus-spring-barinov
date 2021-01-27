package org.library.services;

import lombok.AllArgsConstructor;
import org.library.models.User;
import org.library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @____(@Autowired))
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userModel = userRepository.findByEmail(username);
        if (userModel == null) throw new UsernameNotFoundException(username);
        return new org.springframework.security.core.userdetails.User(userModel.getEmail(), userModel.getEncryptedPassword(), true, true, true, true, userModel.getAuthorities());
    }

    @Override
    public User createUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
