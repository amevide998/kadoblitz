package com.hdscode.kadoblitz.controller;

import com.hdscode.kadoblitz.models.WebResponse;
import com.hdscode.kadoblitz.models.UserRequest;
import com.hdscode.kadoblitz.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping(
            path = "/",
            consumes = "application/json",
            produces = "application/json"
    )
    public WebResponse<String> register(@RequestBody UserRequest request){
        userService.saveUser(request);
        return WebResponse.<String>builder().data("Ok").build();
    }
}
