package com.resolute.zero.services;

import com.resolute.zero.domains.*;
import com.resolute.zero.helpers.TemplateType;
import com.resolute.zero.repositories.*;
import com.resolute.zero.utilities.ApplicationUtility;
import com.resolute.zero.utilities.TypeMappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    private final CaseTypeRepository caseTypeRepository;
    private final TemplateRepository templateRepository;
//    private final CaseRepository caseRepository;


    public void loadDefaultUsers() {



//        { //updating something not in state to write here
//            caseRepository.findAll().forEach(caseObj->{
//
//                caseObj.getDocumentList().forEach(docs->{
//
//                        var name = docs.getImageName();
//                        var main = docs.getDocumentMainTypeTitle();
//                        var sub = docs.getDocumentSubTypeTitle();
//                        if(main.equals("recording")){
//                            var id = Integer.parseInt(""+name.charAt(6));
//
//                        }
//                });
//            });
//        }

        createCaseTypes();
        createDocMainTypes();
        createDocSubTypes();
        createDefaultAdmin();

        if(templateRepository.count()==0){
            List<Template> templateList = new ArrayList<>();
            var emailTemp = new Template();
            emailTemp.setType(TemplateType.EMAIL);
            emailTemp.setTemplate("sample email");
            var whatsappTemplate = new Template();
            whatsappTemplate.setType(TemplateType.WHATSAPP);
            whatsappTemplate.setTemplate("sample whatsapp text");
            templateList.add(emailTemp);
            templateList.add(whatsappTemplate);
            templateRepository.saveAll(templateList);
        }

    }

    private void createDocSubTypes() {
        if(docSubTypeRepository.count()==0){

            TypeMappingUtil.SUB_TYPE_MAP.forEach((key, value)->{
                var docSubType = new DocSubType();
                docSubType.setSubType(key);
                docSubType.setCode(value);
                docSubTypeRepository.save(docSubType);

            });
        }
    }

    private void createCaseTypes() {
        if(caseTypeRepository.count()==0){
            TypeMappingUtil.CASE_TYPES.forEach(caseType->{
                CaseType type = new CaseType();
                type.setType(caseType);
                caseTypeRepository.save(type);
            });
        }
    }

    private void createDocMainTypes() {
        if(docMainTypeRepository.count()==0){

            TypeMappingUtil.MAIN_TYPE_MAP.forEach((key, value)->{
                var main = new DocMainType();
                main.setMainType(key);
                main.setCode(value);
                docMainTypeRepository.save(main);

            });
        }
    }

    private void createDefaultAdmin() {
        if(userRepository.count()==0L){
            User user = new User();
            user.setUserName("userAdmin");
            user.setPassword(ApplicationUtility.encryptPassword("root1234"));
            user.setRole("admin");
            userRepository.save(user);
        }
    }
}
