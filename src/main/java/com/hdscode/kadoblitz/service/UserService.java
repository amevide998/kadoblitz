package com.hdscode.kadoblitz.service;

import com.hdscode.kadoblitz.entity.User;
import com.hdscode.kadoblitz.models.UserRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    void saveUser(UserRequest request );

    User findByUsername(String username);

    UserDetailsService userDetailsService();

}
