package com.example.car_parking_backend_api.service;

import com.example.car_parking_backend_api.model.User;
import com.example.car_parking_backend_api.model.UserPrincipal;
import com.example.car_parking_backend_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //TODO: USER NOT FOUND EXCEPTION
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + username));

        return new UserPrincipal(user);
    }
}
