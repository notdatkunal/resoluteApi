package com.resolute.zero.services;

import com.resolute.zero.domains.User;
import com.resolute.zero.repositories.UserRepository;
import com.resolute.zero.utilities.ApplicationUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StartupAppService {

    @Autowired
    private final UserRepository userRepository ;
    public void loadDefaultUsers() {
        long userCount = userRepository.count();
        if(userCount==0L){
            User user = new User();
            user.setUserName("userAdmin");
            user.setPassword(ApplicationUtility.encryptPassword("root1234"));
            user.setRole("admin");
            userRepository.save(user);
        }
    }
}
