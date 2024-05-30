package com.resolute.zero.services;

import com.resolute.zero.responses.LoginRecordResponse;
import com.resolute.zero.domains.User;
import com.resolute.zero.helpers.Helper;
import com.resolute.zero.repositories.LoginRecordRepository;
import com.resolute.zero.repositories.UserRepository;
import com.resolute.zero.utilities.ApplicationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public  class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final LoginStatusService loginStatusService;
    @Autowired
    private final LoginRecordRepository loginRecordRepository;
    public boolean login(User user){

         var userOptional = userRepository.findUserByUserName(user.getUsername());
         if(userOptional.isEmpty())
             return false;

         User systemUser = userOptional.get();
         user.setPassword(ApplicationUtility.encryptPassword(user.getPassword()));
         var loginStatus =  systemUser.getPassword().equals(user.getPassword());
         if(loginStatus) loginStatusService.register(systemUser);
         return loginStatus;
    }
    public User findByUserName(String username){

        return userRepository.findUserByUserName(username).orElseThrow();
    }



    public void createUser(User user){

        if(userRepository.findUserByUserName(user.getUsername()).isPresent()){
            throw new RuntimeException("user already exists");
        }
        user.setPassword(ApplicationUtility.encryptPassword(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.findByUserName(username);

    public List<LoginRecordResponse> getLoginRecords() {

        return loginRecordRepository.findAll().stream().map(Helper.Convert::convertLoginRecord).toList();

    }
}
