package com.resolute.zero.controllers;

import com.resolute.zero.domains.User;
import com.resolute.zero.helpers.Helper;
import com.resolute.zero.requests.CreateUserRequest;
import com.resolute.zero.responses.LoginRecordResponse;
import com.resolute.zero.responses.UserModel;
import com.resolute.zero.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class LoginController {

    @GetMapping("/loginRecords")
    public List<LoginRecordResponse> loginRecordResponse(){
        return userService.getLoginRecords();
    }

    @Autowired
    private UserService userService;


    @GetMapping("/login")
    public ResponseEntity<UserModel> login(HttpSession session, @ModelAttribute("username") String username, @ModelAttribute("password") String password) {
        System.out.println("hello from login");
        User login = new User(username,password);
        boolean userExists = userService.login(login);
        if(userExists) {
            var user = userService.findByUserName(username);
            System.out.println("userObject"+user);

        return  ResponseEntity.ok(Helper.Convert.convertUserModel(user));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(UserModel.builder()
                        .status(false)
                        .username(username)
                        .build());
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
