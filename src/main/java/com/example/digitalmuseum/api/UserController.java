package com.example.digitalmuseum.api;

import com.example.digitalmuseum.model.Security.User;
import com.example.digitalmuseum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/user")
@RestController
public class UserController {
    private final UserService userservice;

    @Autowired
    public UserController(UserService userservice) {
        this.userservice = userservice;
    }

    @PostMapping
    public void addUser(@Valid @NonNull @RequestBody User user){
        userservice.addUser(user);
    }

    @GetMapping
    public List<User> getAllUser(){
        return userservice.getAllUser();
    }

    @GetMapping(path = "{id}")
    public User getUserById(@PathVariable("id") UUID id){
        return userservice.getUserById(id).orElse(null);
    }

    @PutMapping(path = "{id}")
    public void UpdateUserEmail(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody String email){
        userservice.updateUserEmail(id, email);
    }
}
