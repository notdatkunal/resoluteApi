package com.resolute.zero.controllers;

import com.resolute.zero.domains.Bank;
import com.resolute.zero.domains.User;
import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.BankRepository;
import com.resolute.zero.requests.CreateUserRequest;
import com.resolute.zero.responses.LoginRecordResponse;
import com.resolute.zero.responses.UserModel;
import com.resolute.zero.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class LoginController {


    @GetMapping("/loginRecords/count")
    public long countLoginRecords(){
        return userService.countLoginRecords();
    }
    @GetMapping("/loginRecords")
    public List<LoginRecordResponse> loginRecordResponse(@RequestParam(required = false) Integer pageNumber
            , @PageableDefault(size = 10,page=0,sort = "id") Pageable pageable){
        if (pageNumber != null) {
            pageable = PageRequest.of(pageNumber, pageable.getPageSize(), pageable.getSort());
        }
        return userService.getLoginRecords(pageable);
    }

    @Autowired
    private UserService userService;
    @Autowired
    private BankRepository bankRepository;


    @GetMapping("/login")
    public ResponseEntity<UserModel> login(@ModelAttribute("username") String username, @ModelAttribute("password") String password,@ModelAttribute("ip")String ip,@ModelAttribute("country")String country) {
        System.out.println("hello from login");
        User login = new User(username,password);
        boolean userExists = userService.login(login,ip,country);
        if(userExists) {
            var user = userService.findByUserName(username);
            int bankId = 0;
            if ("bank".equals(user.getRole())) bankId = bankRepository.findBankByUsername(user.getUsername()).get().getId();
            System.out.println("userObject"+user);
        return  ResponseEntity.ok(Helper.Convert.convertUserModel(user,bankId));
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
        concreteUser.setEmail(user.getEmail());
        userService.createUser(concreteUser);
    }

}
