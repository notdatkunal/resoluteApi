package com.resolute.zero.controllers;

import com.resolute.zero.models.User;
import com.resolute.zero.requests.CreateUserRequest;
import com.resolute.zero.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public ResponseEntity<Boolean> login(HttpSession session, @ModelAttribute("username") String username, @ModelAttribute("password") String password) {
            User login = new User(username,password);
            boolean userExists = userService.login(login);
            if(userExists) {
                session.setAttribute("user", userService.findByUserName(username));
            }

        return new ResponseEntity<Boolean>(userExists, HttpStatus.FOUND);
    }

    @PostMapping("/register")
    public void register(@RequestBody CreateUserRequest user){

        User concreteUser = new User();
        concreteUser.setRole(user.getRole());
        concreteUser.setPassword(user.getPassword());
        concreteUser.setUserName(user.getUsername());
        userService.createUser(concreteUser);



    }

}
