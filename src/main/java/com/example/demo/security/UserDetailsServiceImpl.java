package com.example.demo.security;

import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.demo.model.persistence.User appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(appUser.getUsername(), appUser.getPassword(), emptyList());
    }
}
