package com.board.demo.service;

import com.board.demo.entity.User;
import com.board.demo.dto.UserDetails;
import com.board.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User userData = userRepository.findByUserId(userId);

        if (userData != null) {
            return new UserDetails(userData);
        }
        throw new
                UsernameNotFoundException("User not exist with name :" + userId);
    }
}