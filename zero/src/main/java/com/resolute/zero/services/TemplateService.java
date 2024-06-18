package com.resolute.zero.services;

import com.resolute.zero.domains.Template;
import com.resolute.zero.domains.TemplateRepository;
import com.resolute.zero.helpers.TemplateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TemplateService {
    private final TemplateRepository templateRepository;

    public void updateEmailTemplate(Template emailTemplate) {
        var templateOpt = templateRepository.findByType(TemplateType.EMAIL);
        updateTemplate(emailTemplate, templateOpt);
    }

    public void updateWhatsappTemplate(Template whatsappTemplate) {
        var templateOpt = templateRepository.findByType(TemplateType.WHATSAPP);
        updateTemplate(whatsappTemplate, templateOpt);
    }

    private void updateTemplate(Template whatsappTemplate, Optional<Template> templateOpt) {
        if(templateOpt.isPresent()){
            var tempObj = templateOpt.get();
            tempObj.setTemplate(whatsappTemplate.getTemplate());
            templateRepository.save(tempObj);
        }
    }

    public Template getWhatsappTemplate() {
        return templateRepository.findByType(TemplateType.WHATSAPP).get();
    }

    public Template getEmailTemplate() {
        return templateRepository.findByType(TemplateType.EMAIL).get();
    }
}
