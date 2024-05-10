package com.resolute.zero.services;

import com.resolute.zero.models.User;
import com.resolute.zero.repositories.UserRepository;
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

         return systemUser.getPassword().equals(user.getPassword());


    }
}
