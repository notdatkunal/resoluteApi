package com.resolute.zero.services;

import com.resolute.zero.domains.DocMainType;
import com.resolute.zero.domains.DocSubType;
import com.resolute.zero.domains.User;
import com.resolute.zero.repositories.*;
import com.resolute.zero.utilities.ApplicationUtility;
import com.resolute.zero.utilities.TypeMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StartupAppService {

//        caseRepository.findAll().forEach(bankCase -> {
//            bankCase.setHearingsCount(0);
//            bankCase.getProceeding().forEach(proceeding -> {
//                bankCase.setHearingsCount(bankCase.getHearingsCount()+1);
//                if(bankCase.getHearingsCount()<10)
//                    proceeding.setOrderType("H0"+bankCase.getHearingsCount());
//                else
//                    proceeding.setOrderType("H"+bankCase.getHearingsCount());
//                proceedingRepository.save(proceeding);
//            });
//            caseRepository.save(bankCase);
//        });

    @Autowired
    private final UserRepository userRepository ;

    @Autowired
    private final DocMainTypeRepository docMainTypeRepository ;

    @Autowired
    private final DocSubTypeRepository docSubTypeRepository ;



    public void loadDefaultUsers() {


        if(docMainTypeRepository.count()==0){

            TypeMappingUtil.MAIN_TYPE_MAP.forEach((key, value)->{
                var main = new DocMainType();
                main.setMainType(key);
                main.setCode(value);
                docMainTypeRepository.save(main);

            });
        }



        if(docSubTypeRepository.count()==0){

            TypeMappingUtil.SUB_TYPE_MAP.forEach((key, value)->{
                var docSubType = new DocSubType();
                docSubType.setSubType(key);
                docSubType.setCode(value);
                docSubTypeRepository.save(docSubType);

            });
        }

        if(userRepository.count()==0L){
            User user = new User();
            user.setUserName("userAdmin");
            user.setPassword(ApplicationUtility.encryptPassword("root1234"));
            user.setRole("admin");
            userRepository.save(user);
        }
    }
}
