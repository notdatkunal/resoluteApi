package com.resolute.zero.services;

import com.resolute.zero.domains.LoginRecord;
import com.resolute.zero.domains.User;
import com.resolute.zero.repositories.LoginRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginStatusService {

    @Autowired
    private final LoginRecordRepository loginRecordRepository;
    public void register(User systemUser){
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUser(systemUser);
        loginRecordRepository.save(loginRecord);
    }
}
