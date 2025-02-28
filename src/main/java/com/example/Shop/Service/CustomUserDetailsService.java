package com.example.Shop.Service;

import com.example.Shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> {
                    if(!user.isEnabled()) {
                        throw new UsernameNotFoundException("User " + username + " is not enabled ...");
                    }
                    if(!user.isAccountNonLocked()) {
                        throw new UsernameNotFoundException("User " + username + " is locked");
                    }
                    if(!user.isAccountNonExpired()) {
                        throw new UsernameNotFoundException("User " + username + " is expired ...");
                    }
                    if(!user.isCredentialsNonExpired()) {
                        throw new UsernameNotFoundException("User " + username + " is credential expired ...");
                    }
                    return user;
                }).orElseThrow(() -> new UsernameNotFoundException("User " + username + " is not found ..."));
    }
}
