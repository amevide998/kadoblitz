package com.hdscode.kadoblitz.service.impl;

import com.hdscode.kadoblitz.entity.User;
import com.hdscode.kadoblitz.models.UserRequest;
import com.hdscode.kadoblitz.repository.UserRepository;
import com.hdscode.kadoblitz.security.BCrypt;
import com.hdscode.kadoblitz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private final ValidationSeviceImpl validationSevice;

    public void saveUser(UserRequest request ){
        validationSevice.validate(request);

        if(userRepository.findFirstByUsername(request.getUsername()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        if(userRepository.findFirstByEmail(request.getEmail()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already used");
        }

        User user = mapToUser(request);
        userRepository.save(user);
    }

    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findFirstByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            }
        };
    }

    public User findByUsername(String username) {
        return userRepository.findFirstByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    private User mapToUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}
