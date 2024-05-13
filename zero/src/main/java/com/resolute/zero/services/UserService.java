package com.resolute.zero.services;

import com.resolute.zero.models.User;
import com.resolute.zero.repositories.UserRepository;
import com.resolute.zero.utilities.ApplicationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public  class UserService {

    private final UserRepository userRepository;
    public boolean login(User user){

         var userOptional = userRepository.findUserByUserName(user.getUserName());
         if(userOptional.isEmpty())
             return false;

         User systemUser = userOptional.get();
         user.setPassword(ApplicationUtility.encryptPassword(user.getPassword()));
         return systemUser.getPassword().equals(user.getPassword());


    }
    public User findByUserName(String username){

        return userRepository.findUserByUserName(username).orElseThrow();
    }



    public void createUser(User user){

        if(userRepository.findUserByUserName(user.getUserName()).isPresent()){
            throw new RuntimeException("user already exists");
        }
        user.setPassword(ApplicationUtility.encryptPassword(user.getPassword()));
        userRepository.save(user);
    }

}
